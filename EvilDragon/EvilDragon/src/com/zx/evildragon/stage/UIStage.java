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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.zx.evildragon.EvilDragon;
import com.zx.evildragon.net.ITalk.CallBack;

public class UIStage extends C2dStage {

	private final TextureAtlas atlas;
	private final Button mButtonTalk;
	private int btn_counter = -1;// TODO DELETE
	private final Label mLabelLeft;
	private final Label mLabelRight;

	private final Image mImageWait;
	private final Image mImageWave;
	private final TextureRegionDrawable[] mDrawablesWait;
	private final TextureRegionDrawable[] mDrawablesWave;
	private int counter;

	private final UIEventListener listener;
	private final CallBack callback;

	public UIStage(UIEventListener uiEventListener) {
		this.listener = uiEventListener;
		atlas = Engine.resource("atlas");

		mButtonTalk = new Button(new TextureRegionDrawable(atlas.findRegion("voicetalking_button01")), new TextureRegionDrawable(
				atlas.findRegion("voicetalking_button02")));
		mButtonTalk.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Gdx.app.getType() == ApplicationType.Android) {
					// sprite animation
					listener.performListenEvent();

					// perform recognition
					EvilDragon.talk.recognition(callback);

					// UI
					showWaveAnimation();
				} else {
					btn_counter++;
					Gdx.app.debug("debug", "button click" + btn_counter);
					switch (btn_counter % 5) {
					case 0:
						listener.performListenEvent();
						showWaveAnimation();
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

		LabelStyle labelStyle = new LabelStyle(Engine.resource("font", BitmapFont.class), new Color(0, 1, 1, 1));
		labelStyle.background = new NinePatchDrawable(new NinePatch(atlas.createPatch("pop_left")));
		mLabelLeft = new Label("", labelStyle);
		mLabelLeft.setPosition(50, 700);
		mLabelLeft.setVisible(false);

		LabelStyle style = new LabelStyle(Engine.resource("font", BitmapFont.class), new Color(0, 1, 1, 1));
		style.background = new NinePatchDrawable(new NinePatch(atlas.createPatch("pop_right")));
		mLabelRight = new Label("", style);
		mLabelRight.setVisible(false);

		mDrawablesWave = new TextureRegionDrawable[] { new TextureRegionDrawable(atlas.findRegion("wave1")),
				new TextureRegionDrawable(atlas.findRegion("wave2")), new TextureRegionDrawable(atlas.findRegion("wave3")),
				new TextureRegionDrawable(atlas.findRegion("wave4")), new TextureRegionDrawable(atlas.findRegion("wave5")) };
		mDrawablesWait = new TextureRegionDrawable[] { new TextureRegionDrawable(atlas.findRegion("wait1")),
				new TextureRegionDrawable(atlas.findRegion("wait2")), new TextureRegionDrawable(atlas.findRegion("wait3")) };

		mImageWave = new Image(mDrawablesWave[0]);
		mImageWave.setVisible(false);
		mImageWave.setPosition((Engine.getWidth() - mImageWave.getWidth()) / 2, 10);

		mImageWait = new Image(mDrawablesWait[0]);
		mImageWait.setVisible(false);
		mImageWait.setPosition((Engine.getWidth() - mImageWave.getWidth()) / 2, 10);

		callback = new CallBack() {

			@Override
			public void recognitionBegin() {
				showWaitAnimation();
			}

			@Override
			public void recognitionEnd(String text, boolean success) {
				hideWaitAnimation();
				if (success) {
					Gdx.app.debug("debug", "问：" + text);
					mLabelLeft.setText(text);
					mLabelLeft.setVisible(true);
					mLabelLeft.pack();
					mLabelRight.setVisible(false);
					listener.performThink();
				} else {
					// TODO
				}
			}

			@Override
			public void response(String text, boolean success) {
				if (success) {
					Gdx.app.debug("debug", "答：" + text);
					mLabelRight.setText(text);
					mLabelLeft.setVisible(false);
					mLabelRight.setVisible(true);
					mLabelRight.pack();
					mLabelRight.setPosition(Engine.getWidth() - mLabelRight.getWidth() - 50, 600);
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
				mLabelLeft.setVisible(false);
				mLabelRight.setVisible(false);
			}

		};

		mButtonTalk.setPosition((Engine.getWidth() - mButtonTalk.getWidth()) / 2, 10);
		this.addActor(mLabelLeft);
		this.addActor(mLabelRight);
		this.addActor(mImageWave);
		this.addActor(mImageWait);
		this.addActor(mButtonTalk);
	}

	private void showWaveAnimation() {
		counter = 0;
		mImageWave.setVisible(true);
		mImageWave.addAction(Actions.forever(Actions.delay(0.2f, Actions.run(new Runnable() {

			@Override
			public void run() {
				counter++;
				int temp = counter % 5;
				mImageWave.setDrawable(mDrawablesWave[temp]);
			}
		}))));
	}

	private void showWaitAnimation() {
		mImageWave.clearActions();
		mImageWave.setDrawable(mDrawablesWave[0]);
		mImageWave.setVisible(false);

		// mImageWait
		counter = 0;
		mImageWait.setVisible(true);
		mImageWait.addAction(Actions.forever(Actions.delay(0.2f, Actions.run(new Runnable() {

			@Override
			public void run() {
				counter++;
				int temp = counter % 3;
				mImageWait.setDrawable(mDrawablesWait[temp]);
			}
		}))));
	}

	private void hideWaitAnimation() {
		mImageWait.clearActions();
		mImageWait.setDrawable(mDrawablesWait[0]);
		mImageWait.setVisible(false);
	}

	public static interface UIEventListener {
		public void performListenEvent();

		public void performThink();

		public void performSpeak();

		public void performNormal();
	}
}
