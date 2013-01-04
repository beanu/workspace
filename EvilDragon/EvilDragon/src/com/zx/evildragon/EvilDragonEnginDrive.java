package com.zx.evildragon;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.events.Event;
import info.u250.c2d.engine.events.EventListener;
import info.u250.c2d.engine.resources.AliasResourceManager;

import com.zx.evildragon.scenes.SceneDialog;
import com.zx.evildragon.scenes.SceneMain;

public class EvilDragonEnginDrive implements EngineDrive {

	SceneMain main;
	SceneDialog dialog;

	@Override
	public EngineOptions onSetupEngine() {
		final EngineOptions opt = new EngineOptions(new String[] { "data/" }, 480, 800);
		opt.autoResume = true;
		opt.configFile = "com.zx.evildragon";// TODO
		return opt;
	}

	@Override
	public void onLoadedResourcesCompleted() {
		Engine.getPreferences().putBoolean("com.zx.evildragon.showhelp", false);
		Engine.getPreferences().flush();

		main = new SceneMain();
		dialog = new SceneDialog();
		Engine.setMainScene(main);

		Engine.getEventManager().register(Constant.EVENT_GOTO_SCREENDIALOG, new EventListener() {

			@Override
			public void onEvent(Event event) {
				Engine.setMainScene(dialog);
			}
		});

		Engine.getEventManager().register(Constant.EVENT_GOTO_SCREENMAIN, new EventListener() {

			@Override
			public void onEvent(Event event) {
				Engine.setMainScene(main);
			}
		});
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResourcesRegister(AliasResourceManager<String> reg) {
		reg.textureAtlas("atlas", "data/a.atlas");
		reg.font("font", "data/yh.fnt");
		reg.textureAtlas("cat", "data/cat.atlas");
		// reg.texture("CircleTexture", "data/circle.png");
		// reg.texture("BoxTexture", "data/box.png");

	}

}
