package com.xiaojiujiu.ui.coupons;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

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
public class CouponsListWithShopActivity extends MyActivity {

	@ViewInject(id = R.id.coupons_withshop_listview) ListView listview;
	@ViewInject(id = R.id.coupons_withshop_progress) ProgressBar progressBar;

	CouponListWithShopDao dao;
	CouponListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupons_list_withshop_activity);

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

		int shopId = getIntent().getIntExtra("id", 0);
		dao = new CouponListWithShopDao(String.valueOf(shopId));

		adapter = new CouponListAdapter(this, dao.getCouponList(), CouponListAdapter.CouponListWithShop);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Intent intent = new Intent(CouponsListWithShopActivity.this, CouponsDetailActivity.class);
				intent.putExtra("id", dao.getCouponList().get(position).getItemID());
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
