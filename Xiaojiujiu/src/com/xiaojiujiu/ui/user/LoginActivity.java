package com.xiaojiujiu.ui.user;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtil;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.dao.UserDao;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 登陆页面
 * 
 * @author beanu
 * 
 */
public class LoginActivity extends MyActivity implements OnClickListener {

	@ViewInject(id = R.id.login_username) EditText login_username;
	@ViewInject(id = R.id.login_password) EditText login_password;
	@ViewInject(id = R.id.login_submit, click = "onClick") Button login_button;
	@ViewInject(id = R.id.login_register, click = "onClick") TextView login_register;
	@ViewInject(id = R.id.login_findPassword, click = "onClick") ImageView login_findPassword;

	UserDao dao;
	final int requestCode = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login_activity);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {
			@Override
			public void leftSlidingEvent() {

			}

			@Override
			public void rightSlidingEvent() {
				finish();
				UIUtil.intentSlidOut(LoginActivity.this);
			}
		});

		dao = new UserDao();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_register:
			Intent intent = new Intent(LoginActivity.this, RegisterActivity_.class);
			startActivityForResult(intent, requestCode);
			UIUtil.intentSlidIn(this);
			break;
		case R.id.login_submit:
			String name = login_username.getText().toString();
			String password = login_password.getText().toString();
			if (name.equals("") || password.equals("")) {
				MessageUtil.showShortToast(getApplicationContext(), "用户名,密码不能为空");
			} else {
				UIUtil.showWaitDialog(getSupportFragmentManager());
				dao.setUserName(name);
				dao.setUserPassword(password);
				dao.login(new IDataListener<String>() {

					@Override
					public void onSuccess(String result) {
						Arad.preferences.putString(Constant.P_username, dao.getUserName());
						Arad.preferences.putString(Constant.P_password, dao.getUserPassword());		
						Arad.preferences.flush();
						
						UIUtil.hideDialog(getSupportFragmentManager());
						MessageUtil.showShortToast(getApplicationContext(), "登陆成功");
						setResult(RESULT_OK);
						LoginActivity.this.finish();
					}

					@Override
					public void onFailure(String result, Throwable t, String strMsg) {
						UIUtil.hideDialog(getSupportFragmentManager());
					}
				});
			}

			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK){
			if(requestCode==this.requestCode){
			 String name=data.getStringExtra("name");
			 login_username.setText(name);
			}	
		}
		
			
	}
}
