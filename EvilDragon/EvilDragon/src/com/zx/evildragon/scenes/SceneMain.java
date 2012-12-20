package com.zx.evildragon.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zx.evildragon.sprite.DragonCat;
import com.zx.evildragon.stage.UIStage;
import com.zx.evildragon.stage.UIStage.UIEventListener;

public class SceneMain extends SceneStage implements UIEventListener {

	private final Image bg;
	private final DragonCat dragonCat;
	private final TextureAtlas atlas;

	// UI
	private final UIStage ui;

	public SceneMain() {
		atlas = Engine.resource("atlas");

		bg = new Image(atlas.findRegion("bg"));
		dragonCat = new DragonCat();
		dragonCat.setPosition((Engine.getWidth() - dragonCat.getWidth()) / 2, 150);

		this.addActor(bg);
		this.addActor(dragonCat);
		ui = new UIStage(this);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		ui.act(delta);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		ui.draw();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public InputProcessor getInputProcessor() {
		return ui;
	}

	@Override
	public void performTalkEvent() {
		Gdx.app.debug("debug", "talk");
		dragonCat.listen();
	}

	@Override
	public void performThink() {
		Gdx.app.debug("debug", "think");
		dragonCat.think();
	}

	@Override
	public void performSpeak() {
		Gdx.app.debug("debug", "speak");
		dragonCat.speak();
	}

}
