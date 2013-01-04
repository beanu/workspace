package com.zx.evildragon.scenes;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zx.evildragon.sprite.DragonCat;
import com.zx.evildragon.stage.UIStage;
import com.zx.evildragon.stage.UIStage.UIEventListener;

public class SceneMain extends SceneStage implements UIEventListener {

	private final TextureAtlas atlas;
	// UI
	private final UIStage ui;
	// Sprite
	private final DragonCat dragon;
	// Particle
	private final ParticleEffect effect;

	public SceneMain() {
		atlas = Engine.resource("atlas");
		dragon = new DragonCat();
		dragon.setPosition(240, 300);
		this.addActor(new Image(atlas.findRegion("bg")));
		this.addActor(dragon);

		ui = new UIStage(this);
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("data/p.p"), Gdx.files.internal("data"));

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
		Engine.getSpriteBatch().begin();
		effect.draw(Engine.getSpriteBatch(), delta);
		Engine.getSpriteBatch().end();
		// Table.drawDebug(ui);
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public InputProcessor getInputProcessor() {
		InputMultiplexer inputs = new InputMultiplexer();
		inputs.addProcessor(ui);
		inputs.addProcessor(new InputAdapter() {

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				effect.reset();
				effect.setPosition(screenX, Gdx.graphics.getHeight() - screenY);
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				effect.setPosition(screenX, Gdx.graphics.getHeight() - screenY);
				return false;
			}

		});
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
