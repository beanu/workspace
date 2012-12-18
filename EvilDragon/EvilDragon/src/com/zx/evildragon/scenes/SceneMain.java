package com.zx.evildragon.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zx.evildragon.sprite.Dragon;
import com.zx.evildragon.stage.UIStage;
import com.zx.evildragon.stage.UIStage.UIEventListener;

public class SceneMain implements Scene, UIEventListener {

	private final Image bg;
	private final Dragon dragon;
	private final TextureAtlas atlas;

	// UI
	private final UIStage ui;

	public SceneMain() {
		atlas = Engine.resource("atlas");

		bg = new Image(atlas.findRegion("bg"));
		dragon = new Dragon();
		dragon.setPosition((Engine.getWidth()-dragon.body.getWidth())/2, 150);

		ui = new UIStage(this);
	}

	@Override
	public void update(float delta) {
		ui.act(delta);
	}

	@Override
	public void render(float delta) {
		Engine.getSpriteBatch().begin();
		bg.draw(Engine.getSpriteBatch(), 1);
		dragon.render(delta);
		Engine.getSpriteBatch().end();
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
		dragon.listen();
	}

}
