package com.zx.evildragon.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class AImage extends Image {
	final Array<TextureRegionDrawable> array = new Array<TextureRegionDrawable>();

	private int temp;

	public AImage(TextureAtlas atlas, String prefix) {
		super(atlas.findRegion(prefix + 1));
		for (int count = 1;; count++) {
			TextureRegion region = atlas.findRegion(prefix + count);
			if (region != null) {
				TextureRegionDrawable tr = new TextureRegionDrawable(region);
				array.add(tr);
			} else {
				break;
			}
		}
	}

	public AImage(TextureAtlas atlas, String prefix, int defalut) {
		super(atlas.findRegion(prefix + defalut));
		for (int count = 1;; count++) {
			TextureRegion region = atlas.findRegion(prefix + count);
			if (region != null) {
				TextureRegionDrawable tr = new TextureRegionDrawable(region);
				array.add(tr);
			} else {
				break;
			}
		}
	}

	public void animation(final int[] count, float duration) {
		temp = 0;
		this.addAction(Actions.repeat(count.length - 1, Actions.delay(duration, Actions.run(new Runnable() {

			@Override
			public void run() {
				temp++;
				int a = temp % count.length;
				setDrawable(array.get(count[a] - 1));
			}
		}))));
	}

	public void animationRepeat(final int[] count, float duration, int repeat) {
		temp = 0;
		this.addAction(Actions.repeat((count.length - 1) * repeat, Actions.delay(duration, Actions.run(new Runnable() {

			@Override
			public void run() {
				temp++;
				int a = temp % count.length;
				setDrawable(array.get(count[a] - 1));
			}
		}))));
	}

	public void animationLoop(final int[] count, float duration) {
		temp = 0;
		this.addAction(Actions.forever(Actions.delay(duration, Actions.run(new Runnable() {

			@Override
			public void run() {
				temp++;
				int a = temp % count.length;
				setDrawable(array.get(count[a] - 1));
			}
		}))));
	}

	@Override
	public void setVisible(boolean visible) {
		if (!visible) {
			this.clearActions();
		}
		super.setVisible(visible);
	}
}
