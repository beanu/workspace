package com.xiaojiujiu.entity;

/**
 * 用户信息实体类
 * @author beanu
 *
 */
public class User {
	private String id;
	private String name;
	private String passwd;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
