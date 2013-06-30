package com.xiaojiujiu.ui.user;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.beanu.arad.utils.MessageUtil;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.dao.RegisterDao;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 注册页面
 * 
 * @author beanu
 */

@EActivity(R.layout.user_register_activity)
public class RegisterActivity extends MyActivity {

	@ViewById EditText register_username;
	@ViewById EditText register_password;
	@ViewById EditText register_againPassword;
	@ViewById Button register_register;
	@ViewById TextView register_toLogin;

	RegisterDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dao = new RegisterDao();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {
			@Override
			public void leftSlidingEvent() {

			}

			@Override
			public void rightSlidingEvent() {
				finish();
				UIUtil.intentSlidOut(RegisterActivity.this);
			}
		});
	}

	@AfterViews
	protected void init() {

	}

	@Click
	void register_registerClicked() {
		String password = register_password.getText().toString();
		String passwordAgain = register_againPassword.getText().toString();
		String name = register_username.getText().toString();

		if (name.equals("")) {
			MessageUtil.showShortToast(getApplicationContext(), "用户名不能为空");
			return;
		}

		if (!password.equals(passwordAgain)) {
			MessageUtil.showShortToast(getApplicationContext(), "密码不一致");
			return;
		}

		// register
		dao.setUserName(name);
		dao.setUserPassWord(password);

		dao.register(new IDataListener<String>() {

			@Override
			public void onSuccess(String result) {
				getIntent().putExtra("name", dao.getUserName());
				setResult(RESULT_OK, getIntent());
				finish();
			}

			@Override
			public void onFailure(String result, Throwable t, String strMsg) {

			}
		});

	}
}
