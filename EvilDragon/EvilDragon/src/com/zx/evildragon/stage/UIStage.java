package com.zx.evildragon.stage;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.C2dStage;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.zx.evildragon.EvilDragon;
import com.zx.evildragon.net.ITalk.CallBack;
import com.zx.evildragon.stage.widget.Menu;
import com.zx.evildragon.stage.widget.RecordButton;

public class UIStage extends C2dStage {

	private int btn_counter = -1;// TODO DELETE
	private final Label mTextLeft;
	private final Label mTextRight;
	private final RecordButton mRecordButton;
	private final Menu mMenu;

	private final UIEventListener listener;
	private final CallBack callback;

	public UIStage(UIEventListener uiEventListener) {
		this.listener = uiEventListener;
		TextureAtlas atlas = Engine.resource("atlas");

		// button
		mRecordButton = new RecordButton();
		mRecordButton.setPosition((Engine.getWidth() - mRecordButton.getWidth()) / 2, 5);
		mRecordButton.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Gdx.app.getType() == ApplicationType.Android) {
					// sprite animation
					listener.performListenEvent();

					// perform recognition
					EvilDragon.talk.recognition(callback);

					// UI
					mRecordButton.showWaveAnimation();
				} else {
					btn_counter++;
					Gdx.app.debug("debug", "button click" + btn_counter);
					switch (btn_counter % 5) {
					case 0:
						listener.performListenEvent();
						mRecordButton.showWaveAnimation();
						callback.recognitionBegin();
						break;
					case 1:
						callback.recognitionEnd("识别完毕", true);
						break;
					case 2:
						callback.response("ai对话返回完毕", true);
						break;
					case 3:
						callback.synthesizerBegin();
						break;
					case 4:
						callback.synthesizerEnd(true);
						break;
					}

				}

			}

		});

		// text
		LabelStyle labelStyle = new LabelStyle(Engine.resource("font", BitmapFont.class), new Color(0, 1, 1, 1));
		labelStyle.background = new NinePatchDrawable(new NinePatch(atlas.createPatch("pop_left")));
		mTextLeft = new Label("", labelStyle);
		mTextLeft.setPosition(50, 650);
		mTextLeft.setVisible(false);

		LabelStyle style = new LabelStyle(Engine.resource("font", BitmapFont.class), new Color(0, 1, 1, 1));
		style.background = new NinePatchDrawable(new NinePatch(atlas.createPatch("pop_right")));
		mTextRight = new Label("", style);
		mTextRight.setVisible(false);

		// menu
		mMenu = new Menu();
		mMenu.setPosition(-mMenu.getWidth() + 60, 720);

		callback = new CallBack() {

			@Override
			public void recognitionBegin() {
				mRecordButton.showWaitAnimation();
			}

			@Override
			public void recognitionEnd(String text, boolean success) {
				mRecordButton.hideWaitAnimation();
				if (success) {
					Gdx.app.debug("debug", "问：" + text);
					mTextLeft.setText(text);
					mTextLeft.setVisible(true);
					mTextLeft.pack();
					mTextRight.setVisible(false);
					listener.performThink();
				} else {
					// TODO
				}
			}

			@Override
			public void response(String text, boolean success) {
				if (success) {
					Gdx.app.debug("debug", "答：" + text);
					mTextRight.setText(text);
					mTextRight.setVisible(true);
					mTextRight.pack();
					mTextRight.setPosition(Engine.getWidth() - mTextRight.getWidth() - 50, 600);
				} else {
					// TODO
				}

			}

			@Override
			public void synthesizerBegin() {
				listener.performSpeak();
			}

			@Override
			public void synthesizerEnd(boolean success) {
				listener.performNormal();
				mTextLeft.setVisible(false);
				mTextRight.setVisible(false);
			}

		};

		this.addActor(mMenu);
		this.addActor(mTextLeft);
		this.addActor(mTextRight);
		this.addActor(mRecordButton);
	}

	public static interface UIEventListener {
		public void performListenEvent();

		public void performThink();

		public void performSpeak();

		public void performNormal();
	}
}
