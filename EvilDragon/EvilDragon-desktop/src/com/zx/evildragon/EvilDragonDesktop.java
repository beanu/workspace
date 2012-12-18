package com.zx.evildragon;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zx.evildragon.net.ITalk;

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

		ITalk talk = new ITalk() {

			@Override
			public void recognition(CallBack callback) {
				callback.recognitionBegin();
				callback.recognitionEnd("问", true);
				callback.response("答", true);
				callback.synthesizerBegin();
				callback.synthesizerEnd(true);
			}
		};

		new LwjglApplication(new EvilDragon(talk), cfg).setLogLevel(LwjglApplication.LOG_DEBUG);

	}

}
