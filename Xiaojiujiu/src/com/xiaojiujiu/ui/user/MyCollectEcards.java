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
import com.xiaojiujiu.dao.EcardDetailDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.entity.ECard;
import com.xiaojiujiu.entity.ECardItem;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.ECardListAdapter;
import com.xiaojiujiu.ui.ecard.ECardDetailActivity_;

@EFragment
public class MyCollectEcards extends BaseListFragment {

	ECardListAdapter adapter;
	List<ECardItem> data = new ArrayList<ECardItem>();

	@AfterViews
	void init() {
		// data = Arad.db.findAll(CouponItem.class);
		adapter = new ECardListAdapter(getActivity(), data);
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
				Intent intent = new Intent(getActivity(), ECardDetailActivity_.class);
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
		data = Arad.db.findAll(ECardItem.class);
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
			EcardDetailDao dao = new EcardDetailDao(data.get(position));
			ECard ecard = Arad.db.findById(ECard.class, data.get(position).getItemID());
			if (ecard != null) {
				data.remove(position);
				adapter.notifyDataSetChanged();
				dao.setEcard(ecard);
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
