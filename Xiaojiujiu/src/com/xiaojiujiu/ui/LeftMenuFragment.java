package com.xiaojiujiu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
		String[] colors = getResources().getStringArray(R.array.color_names);
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, colors);
		setListAdapter(colorAdapter);
//		switchCategory(0);

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

}
