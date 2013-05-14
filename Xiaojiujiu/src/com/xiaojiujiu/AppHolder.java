package com.xiaojiujiu;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaojiujiu.entity.User;

/**
 * 全局信息
 * 
 * @author beanu
 * 
 */
public class AppHolder {

	private static AppHolder instance;

	private AppHolder() {
		user = new User();
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		initTest();
	}

	public static AppHolder getInstance() {
		if (instance == null) {
			instance = new AppHolder();
		}
		return instance;
	}

	// 用户信息
	public User user;

	public ObjectMapper objectMapper;

	// TODO 测试数据
	private void initTest() {
		user.setId("1");
		user.setName("xjjuser");
		user.setPasswd("123456");
	}
}
