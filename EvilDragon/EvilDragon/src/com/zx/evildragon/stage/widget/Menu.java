package com.zx.evildragon.stage.widget;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.zx.evildragon.Constant;

public class Menu extends Group {

	private final Button home;
	private final Button bt1;
	private final Button bt2;
	private final Button bt3;
	private final Button bt4;
	private boolean toggle;

	public Menu() {
		super();
		TextureAtlas atlas = Engine.resource("atlas");

		home = new Button(new TextureRegionDrawable(atlas.findRegion("menu_home")));
		bt1 = new Button(new TextureRegionDrawable(atlas.findRegion("menu1")), new TextureRegionDrawable(atlas.findRegion("menu1down")));
		bt2 = new Button(new TextureRegionDrawable(atlas.findRegion("menu2")), new TextureRegionDrawable(atlas.findRegion("menu2down")));
		bt3 = new Button(new TextureRegionDrawable(atlas.findRegion("menu3")), new TextureRegionDrawable(atlas.findRegion("menu3down")));
		bt4 = new Button(new TextureRegionDrawable(atlas.findRegion("menu4")), new TextureRegionDrawable(atlas.findRegion("menu4down")));

		home.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (toggle) {
					addAction(Actions.sequence(Actions.moveBy(-getWidth() + 60, 0, 0.5f, Interpolation.pow3In), Actions.run(new Runnable() {

						@Override
						public void run() {
							toggle = false;
						}
					})));
				} else {
					addAction(Actions.sequence(Actions.moveBy(getWidth() - 60, 0, 0.5f, Interpolation.pow3Out), Actions.run(new Runnable() {

						@Override
						public void run() {
							toggle = true;
						}
					})));
				}
			}

		});

		bt1.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (toggle) {
					addAction(Actions.sequence(Actions.moveBy(-getWidth() + 60, 0, 0.5f, Interpolation.pow3In), Actions.run(new Runnable() {

						@Override
						public void run() {
							toggle = false;
							Engine.getEventManager().fire(Constant.EVENT_GOTO_SCREENDIALOG);
						}
					})));
				}

			}

		});

		Table table = new Table();
		table.setBackground(new NinePatchDrawable(new NinePatch(atlas.createPatch("menu_bg"))));
		table.add(bt1).spaceRight(10);
		table.add(bt2).spaceRight(10);
		table.add(bt3).spaceRight(10);
		table.add(bt4).spaceRight(10);
		// table.debug();
		table.pack();
		this.addActor(table);
		home.setPosition(table.getWidth(), 0);
		this.addActor(home);

		this.setSize(table.getWidth() + home.getWidth(), table.getHeight());
	}
}
