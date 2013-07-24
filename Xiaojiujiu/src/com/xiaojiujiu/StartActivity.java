package com.xiaojiujiu;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beanu.arad.Arad;
import com.beanu.arad.utils.Log;
import com.beanu.arad.utils.MessageUtil;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.dao.UserDao;

public class StartActivity extends Activity {

	private boolean bupdate, blogin, blocation;
	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);

		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(2000);// 设置发起定位请求的间隔时间为2000ms
		option.disableCache(true);// 禁止启用缓存定位
		// option.setPoiNumber(5); //最多返回POI个数
		// option.setPoiDistance(1000); //poi查询距离
		// option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);
		mLocationClient.start();

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
				bupdate = true;
				toMainActivity();
			}

			@Override
			public void onFailure(Throwable t,int errorNo , String strMsg) {
				MessageUtil.showShortToast(getApplicationContext(), "网络异常");
				bupdate = true;
				toMainActivity();
			}
		});

		//  自动登录
		autoLogin();
	}

	private void toMainActivity() {
		if (bupdate && blogin && blocation) {
			Intent intent = new Intent(StartActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	protected void onStart() {
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestLocation();
		else
			Log.d("locClient is null or not started");

		super.onStart();
	}

	@Override
	public void onDestroy() {
		// mLocClient.stop();
		super.onDestroy();
	}

	private void autoLogin() {
		String name = Arad.preferences.getString(Constant.P_username, "");
		String password = Arad.preferences.getString(Constant.P_password, "");

		if (!name.equals("") && !password.equals("")) {
			UserDao dao = new UserDao(name, password);
			dao.login(new IDataListener<String>() {

				@Override
				public void onSuccess(String result) {
					blogin = true;
					toMainActivity();
				}

				@Override
				public void onFailure(String result, Throwable t, String strMsg) {
					blogin = true;
					toMainActivity();
				}
			});
		} else {
			blogin = true;
			toMainActivity();
		}
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;

			AppHolder.getInstance().location = location;
			if (location.getLocType() == 61 || location.getLocType() == 161) {
				Arad.preferences.putString(Constant.P_log, location.getLongitude() + "");
				Arad.preferences.putString(Constant.P_lat, location.getLatitude() + "");
				Arad.preferences.flush();
			} else {
				AppHolder.getInstance().location.setLongitude(Double.valueOf(Arad.preferences.getString(Constant.P_log,
						"117.029782")));
				AppHolder.getInstance().location.setLatitude(Double.valueOf(Arad.preferences.getString(Constant.P_lat,
						"36.670976")));
			}
			mLocationClient.stop();
			blocation = true;
			toMainActivity();

			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				// sb.append("\n省：");
				// sb.append(location.getProvince());
				// sb.append("\n市：");
				// sb.append(location.getCity());
				// sb.append("\n区/县：");
				// sb.append(location.getDistrict());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			sb.append("\nsdk version : ");
			sb.append(mLocationClient.getVersion());
			sb.append("\nisCellChangeFlag : ");
			sb.append(location.isCellChangeFlag());
			Log.i(sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
			mLocationClient.stop();
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			Log.i(sb.toString());
		}
	}
}
