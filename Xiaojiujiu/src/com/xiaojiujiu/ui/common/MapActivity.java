package com.xiaojiujiu.ui.common;

import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.beanu.arad.base.BaseActivity;
import com.xiaojiujiu.R;

public class MapActivity extends BaseActivity {

	BMapManager mBMapMan = null;
	MapView mMapView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		double lng = getIntent().getDoubleExtra("lng", 0);
		double lat = getIntent().getDoubleExtra("lat", 0);
		String name = getIntent().getStringExtra("name");

		GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));

		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("CE7CB31728374259F9D1C6E83553996C299B0C0F", null);
		// 注意：请在试用setContentView前初始化BMapManager对象，否则会报错
		setContentView(R.layout.map_activity);
		mMapView = (MapView) findViewById(R.id.bmapsView);
		// 设置启用内置的缩放控件
		mMapView.setBuiltInZoomControls(true);

		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		MapController mMapController = mMapView.getController();
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(16);// 设置地图zoom级别

		// 准备overlay图像数据，根据实情情况修复
		Drawable mark = getResources().getDrawable(R.drawable.map_loc_point_focus);

		// 用OverlayItem准备Overlay数据
		OverlayItem item1 = new OverlayItem(point, "item1", "item1");
		// 创建IteminizedOverlay
		OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
		// 将IteminizedOverlay添加到MapView中

		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);
		itemOverlay.addItem(item1);
		mMapView.refresh();

		PopupOverlay pop = new PopupOverlay(mMapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				// 在此处理pop点击事件，index为点击区域索引,点击区域最多可有三个
			}
		});
		Bitmap bmps = BitmapFactory.decodeResource(getResources(), R.drawable.map_popup_store_address);
		// 弹出pop,隐藏pop
		pop.showPopup(bmps, point, 32);

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

	static class OverlayTest extends ItemizedOverlay<OverlayItem> {

		public OverlayTest(Drawable arg0, MapView arg1) {
			super(arg0, arg1);
		}

		protected boolean onTap(int index) {
			System.out.println("item onTap: " + index);
			return true;
		}

		public boolean onTap(GeoPoint pt, MapView mapView) {
			// 在此处理MapView的点击事件，当返回 true时
			super.onTap(pt, mapView);
			return false;
		}

	}
}
