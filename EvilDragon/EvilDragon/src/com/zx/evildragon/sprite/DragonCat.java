package com.zx.evildragon.sprite;

import info.u250.c2d.engine.Engine;
import info.u250.spriter.plugin.Spriter;
import info.u250.spriter.plugin.SpriterKeyFrameProvider;
import info.u250.spriter.plugin.SpriterPlayer;
import info.u250.spriter.plugin.file.FileLoader;
import info.u250.spriter.plugin.objects.SpriterKeyFrame;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DragonCat extends Actor implements IAnimation {

	private final SpriterPlayer sp;
	private final ArrayList<SpriterPlayer> players;
	private int normalIndex, listenIndex, speakIndex, thinkIndex;
	private int animationIndex;

	public DragonCat() {
		FileLoader<Sprite> loader = new SpriteLoader();
		SpriteDrawer drawer = new SpriteDrawer(loader, Engine.getSpriteBatch());

		Spriter spriter = Spriter.getSpriter("data/cat.scml", loader);
		List<SpriterKeyFrame[]> keyframes = SpriterKeyFrameProvider.generateKeyFramePool(spriter.getSpriterData());
		this.players = new ArrayList<SpriterPlayer>();
		for (int i = 0; i < 1; i++) {
			SpriterPlayer sp = new SpriterPlayer(spriter.getSpriterData(), drawer, keyframes);
			this.players.add(sp);
			sp.setFrameSpeed(10);
		}

		this.sp = this.players.get(0);
		this.sp.setFrameSpeed(10);
		this.sp.setAnimatioIndex(0, 0, 0);
		// this.sp.update(x, y);

		this.normalIndex = this.sp.getAnimationIndexByName("normal");
		this.listenIndex = this.sp.getAnimationIndexByName("listen");
		this.speakIndex = this.sp.getAnimationIndexByName("speak");
		this.thinkIndex = this.sp.getAnimationIndexByName("think");
		this.animationIndex = this.normalIndex;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		for (SpriterPlayer sp : this.players)
			sp.draw();
		super.draw(batch, parentAlpha);
	}

	@Override
	public void act(float delta) {
		int k = 0;
		for (int i = 0; k < this.players.size(); i++) {
			for (int j = 0; j < 10 && k < this.players.size(); j++) {
				this.players.get(k).update(this.getX() + 400 * j, this.getY() - i * 400);
				k++;
			}
		}
		super.act(delta);
	}

	@Override
	public void normal() {
		animationIndex = normalIndex;
		for (SpriterPlayer sp : this.players)
			sp.setAnimatioIndex(this.animationIndex, 10, 120);
	}

	@Override
	public void listen() {
		animationIndex = listenIndex;
		for (SpriterPlayer sp : this.players)
			sp.setAnimatioIndex(this.animationIndex, 10, 120);

	}

	@Override
	public void think() {
		animationIndex = thinkIndex;
		for (SpriterPlayer sp : this.players)
			sp.setAnimatioIndex(this.animationIndex, 10, 120);
	}

	@Override
	public void speak() {
		animationIndex = speakIndex;
		for (SpriterPlayer sp : this.players)
			sp.setAnimatioIndex(this.animationIndex, 10, 120);
	}

}
