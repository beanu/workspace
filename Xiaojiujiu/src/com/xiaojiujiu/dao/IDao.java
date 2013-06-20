package com.xiaojiujiu.dao;

public interface IDao<T> {
	
	/**当数据到达之后更新UI*/
	public void updateUI(T result);
	
	//TODO 加上异常处理
//	public void updateUI(T result,Exception e);
}
