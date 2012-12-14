package com.zx.evildragon;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class EvilDragonDesktop {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ED";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 800;

		new LwjglApplication(new EvilDragon(), cfg)
				.setLogLevel(LwjglApplication.LOG_DEBUG);

	}

}
