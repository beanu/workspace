package com.zx.evildragon.stage;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.C2dStage;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.zx.evildragon.EvilDragon;
import com.zx.evildragon.net.ITalk.RecongBack;

public class UIStage extends C2dStage {

	private final TextureAtlas atlas;
	private final Button mButtonTalk;
	private final Label mLabelText;

	private final UIListener listener;

	public UIStage(UIListener uilistener) {
		this.listener = uilistener;

		atlas = Engine.resource("atlas");
		mButtonTalk = new Button(new TextureRegionDrawable(atlas.findRegion("button_darkblue")), new TextureRegionDrawable(atlas.findRegion("button_green")));
		mButtonTalk.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				listener.talkListener();
			}

		});

		LabelStyle labelStyle = new LabelStyle(Engine.getDefaultFont(), new Color(1, 1, 1, 1));
		mLabelText = new Label("", labelStyle);

		EvilDragon.talk.recognition(new RecongBack() {

			@Override
			public void end(String text) {
				if (Gdx.app.getType() == Application.ApplicationType.Android) {
					mLabelText.setText(text);
				}

			}
		});

		this.addActor(mButtonTalk);
	}

	public static interface UIListener {
		public void talkListener();
	}
}
