package com.xiaojiujiu.ui.common;

import android.os.Bundle;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.beanu.arad.base.BaseActivity;
import com.xiaojiujiu.R;

public class MapActivity extends BaseActivity {

	BMapManager mBMapMan = null;
	MapView mMapView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		double lng=getIntent().getDoubleExtra("lng", 0);
		double lat=getIntent().getDoubleExtra("lat", 0);
		String name=getIntent().getStringExtra("name");
		
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("CE7CB31728374259F9D1C6E83553996C299B0C0F", null);
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.map_activity);
		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(12);// 设置地图zoom级别
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}
}
