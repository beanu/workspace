package com.xiaojiujiu.ui.common;

import android.os.Bundle;

import com.beanu.arad.utils.Log;
import com.xiaojiujiu.base.MyListActivity;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.dao.SearchDao;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.CouponListAdapter;

public class SearchActivity extends MyListActivity implements IDataListener<String> {

	SearchDao dao;
	CouponListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {

			@Override
			public void rightSlidingEvent() {
				if (!isFinishing())
					finish();
				// UIUtil.intentSlidOut(SearchActivity.this);
				Log.d("right sliding");
			}

			@Override
			public void leftSlidingEvent() {
				Log.d("left sliding");

			}
		});

		String keyword = getIntent().getExtras().getString("keyword");
		dao = new SearchDao();
		dao.setKeyword(keyword);

		adapter = new CouponListAdapter(this, dao.getData(), CouponListAdapter.CouponList);
		setListAdapter(adapter);
		if (dao.getData() == null)
			dao.request(this);
	}

	@Override
	public void onSuccess(String result) {
		adapter.setData(dao.getData());
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onFailure(String result, Throwable t, String strMsg) {
		// TODO Auto-generated method stub

	}

}
