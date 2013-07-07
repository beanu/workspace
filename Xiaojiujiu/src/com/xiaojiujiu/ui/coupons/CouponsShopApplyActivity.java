package com.xiaojiujiu.ui.coupons;

import android.os.Bundle;
import android.widget.ListView;

import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.dao.CouponShopListDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.CouponShopApplyListAdapter;

/**
 * 优惠券适用门店
 * 
 * @author beanu
 * 
 */
public class CouponsShopApplyActivity extends MyActivity {

	private ListView listView;
	private CouponShopApplyListAdapter adapter;
	CouponShopListDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_shop_apply_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {
			@Override
			public void leftSlidingEvent() {

			}

			@Override
			public void rightSlidingEvent() {
				finish();
				UIUtil.intentSlidOut(CouponsShopApplyActivity.this);
			}
		});

		String id = String.valueOf(getIntent().getIntExtra("couponId", 0));
		dao = new CouponShopListDao(id);
		dao.getListInfo(new IDataListener<String>() {

			@Override
			public void onSuccess(String result) {
				adapter.setData(dao.getData());
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onFailure(String result, Throwable t, String strMsg) {

			}
		});

		adapter = new CouponShopApplyListAdapter(getApplicationContext(), dao.getData());
		listView = (ListView) findViewById(R.id.coupon_shop_apply_list);
		listView.setAdapter(adapter);
	}
}
