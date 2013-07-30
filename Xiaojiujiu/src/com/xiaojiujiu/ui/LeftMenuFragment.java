package com.xiaojiujiu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.zxing.CaptureActivity;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import com.xiaojiujiu.MainActivity;
import com.xiaojiujiu.MainActivity.Fragments;
import com.xiaojiujiu.R;

/**
 * 左边菜单栏
 * 
 * @author beanu
 */

@EFragment(R.layout.left)
public class LeftMenuFragment extends SherlockFragment implements OnItemClickListener {

	@ViewById ListView left_list;
	@ViewById ImageButton left_scan_button;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] names = getResources().getStringArray(R.array.menu_names);
		MenuAdapter adapter = new MenuAdapter(names);
		left_list.setAdapter(adapter);
		// switchCategory(0);
		left_list.setOnItemClickListener(this);
		left_list.setItemChecked(0, true);
	}

	@Click
	void left_scan_button() {
		Intent intent = new Intent(getSherlockActivity(), CaptureActivity.class);
		startActivity(intent);
	}

	public void switchCategory(int position) {

		switch (position) {
		case 0:
			((MainActivity) getSherlockActivity()).showFragment(Fragments.coupons);
			break;
		case 1:
			((MainActivity) getSherlockActivity()).showFragment(Fragments.ecard);
			break;
		case 2:
			((MainActivity) getSherlockActivity()).showFragment(Fragments.mycard);
			break;
		case 3:
			((MainActivity) getSherlockActivity()).showFragment(Fragments.freshNews);
			break;
		}
		// drawButtonsBackground(position);

	}

	class MenuAdapter extends BaseAdapter {

		String[] names;
		LayoutInflater mlinflater;

		public MenuAdapter(String[] names) {
			this.names = names;
			this.mlinflater = LayoutInflater.from(getSherlockActivity());
		}

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return names[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = mlinflater.inflate(R.layout.biz_navigation_item_layout, null);
			TextView text = (TextView) view.findViewById(R.id.biz_navi_icon);
			text.setText(names[position]);

			return view;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switchCategory(position);
	}
}
