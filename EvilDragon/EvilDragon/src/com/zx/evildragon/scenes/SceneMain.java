package com.zx.evildragon.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zx.evildragon.sprite.Dragon;
import com.zx.evildragon.stage.UIStage;
import com.zx.evildragon.stage.UIStage.UIListener;

public class SceneMain implements Scene, UIListener {

	private final Image bg;
	private final Dragon dragon;
	private final TextureAtlas atlas;

	// UI
	private final UIStage ui;

	public SceneMain() {
		atlas = Engine.resource("atlas");

		bg = new Image(atlas.findRegion("bg"));
		dragon = new Dragon();
		dragon.sprite.setScale(0.7f);

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
		dragon.sprite.render(delta);
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
		return new InputProcessor() {

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				dragon.speak();
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

	@Override
	public void talkListener() {

	}

}