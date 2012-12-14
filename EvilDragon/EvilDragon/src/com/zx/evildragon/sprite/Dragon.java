package com.zx.evildragon.sprite;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.graphic.AnimationSprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dragon {

	public final AnimationSprite sprite;

	private final TextureRegion[] keyFrames_all;
	private final TextureRegion[] keyFrames_speak;

	public Dragon() {
		sprite = new AnimationSprite(0.5f, Engine.resource("atlas",
				TextureAtlas.class), "dragon");
		keyFrames_all = sprite.getAnimationSpriteData().keyFrames;

		keyFrames_speak = new TextureRegion[2];
		keyFrames_speak[0] = keyFrames_all[0];
		keyFrames_speak[1] = keyFrames_all[1];
		sprite.stop();
		sprite.setLoopTimes(3);
	}

	public void render(float delta) {
		sprite.render(delta);
	}

	public void speak() {
		sprite.getAnimationSpriteData().keyFrames = keyFrames_speak;
		sprite.replay();
	}
}
