package com.xiaojiujiu.dao;

public interface IDataListener<T> {

	/** 当数据到达之后更新UI */
	public void updateUI(T result);

	// TODO 加上异常处理
	public void onFailure(T result, Throwable t, String strMsg);
}
