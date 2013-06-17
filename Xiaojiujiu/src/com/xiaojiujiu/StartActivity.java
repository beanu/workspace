package com.xiaojiujiu;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beanu.arad.Arad;
import com.beanu.arad.utils.Log;
import com.beanu.arad.utils.MessageUtil;

public class StartActivity extends Activity {

	private LocationClient mLocClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mLocClient = ((XiaoApplication) Arad.app).mLocationClient;

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(2000);// 设置发起定位请求的间隔时间为2000ms
		option.disableCache(true);// 禁止启用缓存定位
		// option.setPoiNumber(5); //最多返回POI个数
		// option.setPoiDistance(1000); //poi查询距离
		// option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
		mLocClient.setLocOption(option);
		mLocClient.start();

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

	@Override
	protected void onStart() {
		if (mLocClient != null && mLocClient.isStarted())
			mLocClient.requestLocation();
		else
			Log.d("locClient is null or not started");

		super.onStart();
	}

	@Override
	public void onDestroy() {
//		mLocClient.stop();
		super.onDestroy();
	}

}
