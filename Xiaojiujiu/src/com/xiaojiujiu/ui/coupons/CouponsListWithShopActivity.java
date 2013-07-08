package com.xiaojiujiu.ui.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.dao.CouponListWithShopDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.CouponListAdapter;

/**
 * 某个商家下的所有优惠券
 * 
 * @author beanu
 * 
 */

@EActivity(R.layout.coupons_list_withshop_activity)
public class CouponsListWithShopActivity extends MyActivity {

	@ViewById(R.id.coupons_withshop_listview) ListView listview;
	@ViewById(R.id.coupons_withshop_progress) ProgressBar progressBar;

	CouponListWithShopDao dao;
	CouponListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.coupons_list_withshop_activity);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {
			@Override
			public void leftSlidingEvent() {

			}

			@Override
			public void rightSlidingEvent() {
				finish();
				UIUtil.intentSlidOut(CouponsListWithShopActivity.this);
			}
		});
	}

	@AfterViews
	void init() {
		int shopId = getIntent().getIntExtra("id", 0);
		dao = new CouponListWithShopDao(String.valueOf(shopId));

		adapter = new CouponListAdapter(this, dao.getCouponList(), CouponListAdapter.CouponListWithShop);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Intent intent = new Intent(CouponsListWithShopActivity.this, CouponsDetailActivity_.class);
				Bundle b = new Bundle();
				b.putSerializable("item", dao.getCouponList().get(position));
				intent.putExtras(b);
				startActivity(intent);
				UIUtil.intentSlidIn(CouponsListWithShopActivity.this);
			}
		});

		dao.getInfo(new IDataListener<String>() {

			@Override
			public void onSuccess(String result) {
				progressBar.setVisibility(View.GONE);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(String result, Throwable t, String strMsg) {
				// TODO 无数据
				progressBar.setVisibility(View.GONE);
			}
		});
	}

}
