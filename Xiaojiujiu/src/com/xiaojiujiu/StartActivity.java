package com.xiaojiujiu;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.MessageUtil;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.start_activity);

		// 访问服务器新版本
		AjaxParams params = new AjaxParams();
		params.put("op", "upgrade");
		params.put("version", "1.0");
		Arad.http.get("http://www.x99local.com/SystemHandler.ashx", params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {
				toMainActivity();
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				MessageUtil.showShortToast(getApplicationContext(), "网络异常");
				toMainActivity();

			}

		});
	}

	private void toMainActivity() {
		Intent intent = new Intent(StartActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
