package com.xiaojiujiu.ui.user;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.beanu.arad.Arad;
import com.beanu.arad.base.BaseListFragment;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.xiaojiujiu.dao.CouponDetailDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.entity.Coupon;
import com.xiaojiujiu.entity.CouponItem;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.CouponListAdapter;
import com.xiaojiujiu.ui.coupons.CouponsDetailActivity_;

@EFragment
public class MyCollectCoupons extends BaseListFragment {

	CouponListAdapter adapter;
	List<CouponItem> data = new ArrayList<CouponItem>();

	@AfterViews
	void init() {
		adapter = new CouponListAdapter(getActivity(), data, CouponListAdapter.CouponList);
		setListAdapter(adapter);
		getListView().setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
				menu.add(Menu.NONE, Menu.FIRST, 0, "删除");
			}
		});
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 进入详情页
				Intent intent = new Intent(getActivity(), CouponsDetailActivity_.class);
				Bundle b = new Bundle();
				b.putSerializable("item", data.get(position));
				intent.putExtras(b);
				startActivity(intent);
				UIUtil.intentSlidIn(getActivity());

			}
		});
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
	public boolean onContextItemSelected(android.view.MenuItem item) {

		if (item.getItemId() == Menu.FIRST) {
			AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			final int position = menuInfo.position;
			CouponDetailDao dao = new CouponDetailDao(data.get(position));
			Coupon coupon = Arad.db.findById(Coupon.class, data.get(position).getItemID());
			if (coupon != null) {
				data.remove(position);
				adapter.notifyDataSetChanged();
				dao.setCoupon(coupon);
				dao.collect(false, new IDataListener<String>() {

					@Override
					public void onSuccess(String result) {
					}

					@Override
					public void onFailure(String result, Throwable t, String strMsg) {

					}
				});
			}

		}

		return false;
	};
}
