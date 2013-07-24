package com.xiaojiujiu;

import com.beanu.arad.AradApplication;
import com.beanu.arad.AradApplicationConfig;

public class XiaoApplication extends AradApplication {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected AradApplicationConfig appConfig() {
		AradApplicationConfig config = new AradApplicationConfig();
		return config;
	}
}
