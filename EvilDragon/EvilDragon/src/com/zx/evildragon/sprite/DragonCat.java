package com.zx.evildragon.sprite;

import info.u250.c2d.engine.Engine;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class DragonCat extends Group {
	private final TextureAtlas atlas;

	private final Eye eye;
	private final Mouth mouth;
	private final Head head;
	private final Group headGroup;
	private final Body body;

	public DragonCat() {
		atlas = Engine.resource("atlas");

		body = new Body();
		body.setPosition(0, 0);

		headGroup = new Group();
		eye = new Eye();
		mouth = new Mouth();
		head = new Head();
		head.setPosition(0, 0);
		eye.setPosition(23, 64);
		mouth.setPosition(80, 30);
		headGroup.setSize(head.getWidth(), head.getHeight());
		headGroup.addActor(head);
		headGroup.addActor(eye);
		headGroup.addActor(mouth);
		headGroup.setPosition(body.getX() - 20, body.getY() + body.getHeight() - 30);
		headGroup.setOrigin(headGroup.getWidth()/2, 5);

		this.addActor(body);
		this.addActor(headGroup);

		this.setSize(body.getWidth(), body.getWidth());
		eye.blink();
		this.swing();
	}

	public void swing() {
		headGroup.clearActions();
		headGroup.addAction(Actions.forever(Actions.delay(3f,
				Actions.sequence(Actions.rotateBy(2, 0.25f, Interpolation.pow3Out), 
						Actions.rotateBy(-4, 0.5f, Interpolation.pow3Out),
						Actions.rotateBy(2, 0.25f, Interpolation.pow3Out)))));
	}

	public void listen() {
		eye.setVisible(false);
		mouth.setVisible(false);
		head.listen();
	}

	public void think() {
		head.normal();
		this.clearActions();
		this.addAction(Actions.delay(0.5f, Actions.run(new Runnable() {

			@Override
			public void run() {
				eye.setVisible(true);
				eye.blink();
				mouth.setVisible(true);
			}
		})));

	}

	public void speak() {
		mouth.speak();
	}

	// Inner Class
	// Every part of DragonCat Body
	class Head extends AImage {
		private final int[] _listen = new int[] { 1, 2 };
		private final int[] _normal = new int[] { 2, 1 };

		public Head() {
			super(atlas, "dragonhead");
		}

		public void listen() {
			this.clearActions();
			this.animation(_listen, 0.5f);
		}

		public void normal() {
			this.clearActions();
			this.animation(_normal, 0.5f);
		}

	}

	class Eye extends AImage {

		private final int[] _blink = new int[] { 1, 2, 3, 2, 1 };

		public Eye() {
			super(atlas, "dragoneye");
		}

		public void blink() {
			this.clearActions();
			this.addAction(Actions.forever(Actions.delay(4, Actions.run(new Runnable() {

				@Override
				public void run() {
					animation(_blink, 0.15f);
				}
			}))));
		}
	}

	class Mouth extends AImage {
		private final int[] _speak = new int[] { 1, 2, 3, 4, 5, 4, 3, 2 };

		public Mouth() {
			super(atlas, "dragonmouth");
		}

		public void speak() {
			this.animationLoop(_speak, 0.1f);
		}
	}

	class Body extends AImage {
		public Body() {
			super(atlas, "dragonbody");
		}
	}
}
