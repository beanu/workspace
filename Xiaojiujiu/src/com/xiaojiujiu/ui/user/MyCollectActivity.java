package com.xiaojiujiu.ui.user;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.beanu.arad.Arad;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.xiaojiujiu.base.MyListActivity;
import com.xiaojiujiu.entity.CouponItem;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.CouponListAdapter;
import com.xiaojiujiu.ui.coupons.CouponsDetailActivity_;

/**
 * 我的收藏
 * 
 * @author beanu
 * 
 */

@EActivity
public class MyCollectActivity extends MyListActivity {

	CouponListAdapter adapter;
	List<CouponItem> data = new ArrayList<CouponItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {
			@Override
			public void leftSlidingEvent() {

			}

			@Override
			public void rightSlidingEvent() {
				finish();
				UIUtil.intentSlidOut(MyCollectActivity.this);
			}
		});

		init();
	}
	
	void init() {
//		data = Arad.db.findAll(CouponItem.class);
		adapter = new CouponListAdapter(getApplicationContext(), data, CouponListAdapter.CouponList);
		setListAdapter(adapter);
		readAllDataFromDB();
	}

	@Background
	void readAllDataFromDB() {
		data = Arad.db.findAll(CouponItem.class);
		updateUI();
	}

	@UiThread
	void updateUI() {
		adapter.setData(data);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// 进入详情页
		Intent intent = new Intent(MyCollectActivity.this, CouponsDetailActivity_.class);
		Bundle b = new Bundle();
		b.putSerializable("item", data.get(position));
		intent.putExtras(b);
		startActivity(intent);
	}

}
