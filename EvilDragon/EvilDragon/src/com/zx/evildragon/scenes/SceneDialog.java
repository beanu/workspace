package com.zx.evildragon.scenes;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.zx.evildragon.Constant;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.SceneStage;

public class SceneDialog extends SceneStage {
	private final TextureAtlas atlas;
	private final Label label;

	public SceneDialog() {
		atlas = Engine.resource("atlas");

		LabelStyle style = new LabelStyle(Engine.resource("font", BitmapFont.class), new Color(0, 1, 1, 1));
		label = new Label("", style);
		label.setWrap(true);
		ScrollPane pane = new ScrollPane(label);
		pane.setSize(Engine.getWidth() - 80, Engine.getHeight() - 120);
		pane.setPosition((Engine.getWidth() - pane.getWidth()) / 2, (Engine.getHeight() - pane.getHeight()) / 2);

		this.addActor(new Image(atlas.findRegion("bg")));
		this.addActor(pane);

		// TODO delete
		this.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				Engine.getEventManager().fire(Constant.EVENT_GOTO_SCREENMAIN);
			}

		});
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
	}

	@Override
	public void show() {
		label.setText("你好\n hello\n ");
	}

	@Override
	public void hide() {
		label.setText("");
	}

	@Override
	public InputProcessor getInputProcessor() {
		return super.getInputProcessor();
	}

}
