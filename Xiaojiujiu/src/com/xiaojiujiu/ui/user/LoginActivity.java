package com.xiaojiujiu.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.beanu.arad.base.BaseActivity;
import com.xiaojiujiu.R;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 登陆页面
 * 
 * @author beanu
 * 
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	private TextView registerTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login_activity);

		registerTextView = (TextView) findViewById(R.id.login_register);
		registerTextView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_register:
			Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			UIUtil.intentSlidIn(this);
			break;
		}

	}

}
