package com.zx.evildragon.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zx.evildragon.sprite.DragonCat;
import com.zx.evildragon.stage.UIStage;
import com.zx.evildragon.stage.UIStage.UIEventListener;

public class SceneMain extends SceneStage implements UIEventListener {

	private final Image bg;
	private final TextureAtlas atlas;
	// UI
	private final UIStage ui;

	// sprite
	private final DragonCat dragon;

	public SceneMain() {
		atlas = Engine.resource("atlas");
		bg = new Image(atlas.findRegion("bg"));
		dragon = new DragonCat();
		dragon.setPosition(240, 300);

		this.addActor(bg);
		this.addActor(dragon);

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
		InputMultiplexer inputs = new InputMultiplexer();
		inputs.addProcessor(ui);
		// inputs.addProcessor(new InputProcessor() {
		//
		// @Override
		// public boolean touchUp(int screenX, int screenY, int pointer, int
		// button) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public boolean touchDragged(int screenX, int screenY, int pointer) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public boolean touchDown(int screenX, int screenY, int pointer, int
		// button) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public boolean scrolled(int amount) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public boolean mouseMoved(int screenX, int screenY) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public boolean keyUp(int keycode) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public boolean keyTyped(char character) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		//
		// @Override
		// public boolean keyDown(int keycode) {
		// if (keycode == Keys.ESCAPE)
		// Gdx.app.exit();
		// if (keycode == Keys.UP)
		// for (SpriterPlayer sp : players)
		// sp.flipY();
		// if (keycode == Keys.LEFT) {
		// hspeed = 1f;
		// animationIndex = listenIndex;
		// for (SpriterPlayer sp : players)
		// if (sp.getFlipX() == 1)
		// sp.flipX();
		// return true;
		// } else if (keycode == Keys.RIGHT) {
		// animationIndex = speakIndex;
		// return true;
		// } else if (keycode == Keys.S) {
		// animationIndex = thinkIndex;
		// return true;
		// }
		// if (keycode == Keys.A) {
		// for (SpriterPlayer sp : players)
		// sp.setFrameSpeed(10);
		// return true;
		// }
		// if (keycode == Keys.PLUS) {
		// for (SpriterPlayer sp : players)
		// sp.setScale(sp.getScale() + 0.1f);
		// }
		// if (keycode == Keys.MINUS) {
		// for (SpriterPlayer sp : players)
		// sp.setScale(sp.getScale() - 0.1f);
		// }
		// return false;
		// }
		// });

		return inputs;
	}

	@Override
	public void performListenEvent() {
		Gdx.app.debug("debug", "listen");
		dragon.listen();
	}

	@Override
	public void performThink() {
		Gdx.app.debug("debug", "think");
		dragon.think();
	}

	@Override
	public void performSpeak() {
		Gdx.app.debug("debug", "speak");
		dragon.speak();
	}

	@Override
	public void performNormal() {
		Gdx.app.debug("debug", "normal");
		dragon.normal();
	}

}
