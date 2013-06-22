package com.xiaojiujiu;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.xiaojiujiu.entity.Category;
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
		shopType = new ArrayList<Category>();
		area = new ArrayList<Category>();
		location = new BDLocation();

	}

	public static AppHolder getInstance() {
		if (instance == null) {
			instance = new AppHolder();
		}
		return instance;
	}

	// 用户信息
	public User user;

	// 位置信息
	public BDLocation location;

	// 商户类型
	public List<Category> shopType;
	public List<Category> area;
}
