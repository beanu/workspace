package com.zx.evildragon.stage.widget;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class RecordButton extends Group {
	private final TextureAtlas atlas;

	private final Button mButtonTalk;
	private final Image mImageWait;
	private final Image mImageWave;
	private final TextureRegionDrawable[] mDrawablesWait;
	private final TextureRegionDrawable[] mDrawablesWave;

	private int counter;

	public RecordButton() {
		atlas = Engine.resource("atlas");
		mButtonTalk = new Button(new TextureRegionDrawable(atlas.findRegion("voicetalking_button01")), new TextureRegionDrawable(
				atlas.findRegion("voicetalking_button02")));

		mDrawablesWave = new TextureRegionDrawable[] { new TextureRegionDrawable(atlas.findRegion("wave1")),
				new TextureRegionDrawable(atlas.findRegion("wave2")), new TextureRegionDrawable(atlas.findRegion("wave3")),
				new TextureRegionDrawable(atlas.findRegion("wave4")), new TextureRegionDrawable(atlas.findRegion("wave5")) };
		mDrawablesWait = new TextureRegionDrawable[] { new TextureRegionDrawable(atlas.findRegion("wait1")),
				new TextureRegionDrawable(atlas.findRegion("wait2")), new TextureRegionDrawable(atlas.findRegion("wait3")) };

		mImageWave = new Image(mDrawablesWave[0]);
		mImageWave.setVisible(false);

		mImageWait = new Image(mDrawablesWait[0]);
		mImageWait.setVisible(false);

		this.setSize(mImageWave.getWidth(), mButtonTalk.getHeight());
		mButtonTalk.setPosition((this.getWidth() - mButtonTalk.getWidth()) / 2, 0);

		this.addActor(mImageWave);
		this.addActor(mImageWait);
		this.addActor(mButtonTalk);
	}

	public void showWaveAnimation() {
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

	public void showWaitAnimation() {
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

	public void hideWaitAnimation() {
		mImageWait.clearActions();
		mImageWait.setDrawable(mDrawablesWait[0]);
		mImageWait.setVisible(false);
	}
}
