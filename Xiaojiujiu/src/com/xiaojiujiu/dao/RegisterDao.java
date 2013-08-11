package com.xiaojiujiu.dao;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.http.AjaxCallBack;
import com.beanu.arad.http.AjaxParams;
import com.beanu.arad.utils.MessageUtil;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.ui.HttpUtil;

public class RegisterDao {

	String userName;
	String userPassWord;

	public RegisterDao(){}
	
	public RegisterDao(String userName, String userPassWord) {
		this.userName = userName;
		this.userPassWord = userPassWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassWord() {
		return userPassWord;
	}

	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}

	public void register(final IDataListener<String> listener) {

		AjaxParams params = new AjaxParams();
		params.put("op", "reg");
		params.put("userName", userName);
		params.put("tel", Arad.app.deviceInfo.getPhoneNumber());
		params.put("pass", userPassWord);

		Arad.http.get(Constant.URL_MEMBER, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					HttpUtil.handleResult(t);
					// String userID = node.findValue("userID").asText();
					listener.onSuccess("");

				} catch (AradException e) {
//					listener.onFailure("", e, e.getMessage());
					MessageUtil.showShortToast(Arad.app.getApplicationContext(), e.getMessage());
				}

			}

			@Override
			public void onFailure(Throwable t,int errorNo , String strMsg) {
				MessageUtil.showShortToast(Arad.app.getApplicationContext(), Arad.app.getResources().getString(R.string.network_error));
			}

		});

	}

}
