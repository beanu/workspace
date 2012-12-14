package com.zx.evildragon;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;

import com.zx.evildragon.net.ITalk;

public class EvilDragon extends Engine {

	public static ITalk talk;
	
	public EvilDragon(ITalk talkListener){
		EvilDragon.talk=talkListener;
	}
	
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EvilDragonEnginDrive();
	}

}
