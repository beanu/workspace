package com.xiaojiujiu.dao;

import java.io.IOException;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.JsonUtil;
import com.beanu.arad.utils.MessageUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.User;
import com.xiaojiujiu.ui.HttpUtil;

public class UserDao {

	String userName;
	String userPassword;

	public UserDao() {
	}

	public UserDao(String name, String password) {
		this.userName = name;
		this.userPassword = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void login(final IDataListener<String> listener) {
		AjaxParams params = new AjaxParams();
		params.put("op", "login");
		params.put("userName", userName == null ? "" : userName);
		params.put("pass", userPassword == null ? "" : userPassword);

		Arad.http.get(Constant.URL_MEMBER, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				// TODO TEST
				t = "{'resCode':'1','userInfo':{'memberID':1,'userName':'xjjuser','password':'123456','nickName':'小九九','tel':'15098653252','jiFen':200.00,'userType':0,'regTime':'1900-01-01 00:00:00','freezeFlag':0,'memberName':'','sex':'男','birthday':'2013-04-13 00:00:00','email':'495705256@163.com','city':'','address':'','userImageBig':'','userImageSmall':'','favShopNum':19,'favCouponNum':12,'msgNum':0}}";

				try {
					JsonNode node = HttpUtil.handleResult(t);
					AppHolder.getInstance().user = JsonUtil.node2pojo(node.findValue("userInfo"), User.class);
					listener.onSuccess(t);

				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AradException e) {
					listener.onFailure(t, e, e.getMessage());
					MessageUtil.showShortToast(Arad.app.getApplicationContext(), e.getMessage());
				}
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				listener.onFailure("", t, strMsg);
				MessageUtil.showShortToast(Arad.app.getApplicationContext(),
						Arad.app.getResources().getString(R.string.network_error));
			}

		});
	}
}
