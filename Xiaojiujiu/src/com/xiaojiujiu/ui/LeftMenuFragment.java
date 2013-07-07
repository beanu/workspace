package com.xiaojiujiu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.xiaojiujiu.MainActivity;
import com.xiaojiujiu.MainActivity.Fragments;
import com.xiaojiujiu.R;

/**
 * 左边菜单栏
 * 
 * @author beanu
 */
public class LeftMenuFragment extends SherlockListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.left, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] names = getResources().getStringArray(R.array.menu_names);
		MenuAdapter adapter = new MenuAdapter(names);
		setListAdapter(adapter);
		// switchCategory(0);
		getListView().setItemChecked(0, true);
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

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		switchCategory(position);
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
}
