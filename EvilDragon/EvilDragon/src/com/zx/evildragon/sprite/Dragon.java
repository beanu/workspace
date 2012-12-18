package com.zx.evildragon.sprite;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.service.Renderable;
import info.u250.c2d.graphic.AnimationSprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Dragon implements Renderable {

	public final AnimationSprite body;//TODO private
	private final AnimationSprite eye;
	private final AnimationSprite head;

//	private final TextureRegion[] keyFrames_all;
//	private final TextureRegion[] keyFrames_speak;

	public Dragon() {
		TextureAtlas atlas = Engine.resource("atlas");
		body = new AnimationSprite(0.5f, atlas, "dragonbody");
		eye = new AnimationSprite(0.5f, atlas, "dragoneye");
		head = new AnimationSprite(0.5f, atlas, "dragonhead");

//		keyFrames_all = body.getAnimationSpriteData().keyFrames;
//
//		keyFrames_speak = new TextureRegion[2];
//		keyFrames_speak[0] = keyFrames_all[0];
//		keyFrames_speak[1] = keyFrames_all[1];
		eye.setVisible(false);
		
		head.stop();
		head.setLoopTimes(10);
		body.stop();
		body.setLoopTimes(3);
	}

	public void render(float delta) {
		body.render(delta);
		head.render(delta);
		eye.render(delta);
	}

	public void setPosition(float x, float y) {
		body.setPosition(x, y);
		head.setPosition(body.getX()-20, body.getY()+body.getHeight()-30);
	}

	public void speak() {
		
	}
	
	public void listen(){
		head.replay();
	}
}
