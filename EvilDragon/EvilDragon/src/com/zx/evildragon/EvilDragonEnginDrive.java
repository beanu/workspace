package com.zx.evildragon;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;

import com.zx.evildragon.scenes.SceneMain;

public class EvilDragonEnginDrive implements EngineDrive {

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

		SceneMain main = new SceneMain();
		Engine.setMainScene(main);
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
