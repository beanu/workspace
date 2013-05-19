package com.xiaojiujiu.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.xiaojiujiu.MainActivity;
import com.xiaojiujiu.R;
import com.xiaojiujiu.ui.coupons.CouponsListFragment;
import com.xiaojiujiu.ui.ecard.ECardFListragment;

/**
 * 左边菜单栏
 * @author beanu
 *
 */
public class LeftMenuFragment extends SherlockListFragment {

	// private int currentIndex = -1;

	private ArrayList<Fragment> rightFragments = new ArrayList<Fragment>();

	private static final int _INDEX1 = 0;
	private static final int _INDEX2 = 1;
	private static final int _INDEX3 = 2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] colors = getResources().getStringArray(R.array.color_names);
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, colors);
		setListAdapter(colorAdapter);

		rightFragments.add(_INDEX1, ((MainActivity) getSherlockActivity()).getCouponsFragment());
		rightFragments.add(_INDEX2, ((MainActivity) getSherlockActivity()).getECardFragment());
		rightFragments.add(_INDEX3, ((MainActivity) getSherlockActivity()).getMyCardFragment());
		switchCategory(0);
	}

	public void switchCategory(int position) {

		switch (position) {
		case 0:
			showCouponsFragment();
			break;
		case 1:
			showECardFragment();
			break;
		case 2:
			showMyCardFragment();
			break;
		}
		// drawButtonsBackground(position);

	}

	private void showMyCardFragment() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		ft.hide(rightFragments.get(_INDEX1));
		ft.hide(rightFragments.get(_INDEX2));
		ColorFragment fragment = (ColorFragment) rightFragments.get(_INDEX3);
		ft.show(fragment);
		ft.commit();
		((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
	}

	private void showECardFragment() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		ft.hide(rightFragments.get(_INDEX1));
		ft.hide(rightFragments.get(_INDEX3));
		ECardFListragment fragment = (ECardFListragment) rightFragments.get(_INDEX2);
		ft.show(fragment);
		ft.commit();
		((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
	}

	private void showCouponsFragment() {
		FragmentTransaction ft = getFragmentManager().beginTransaction();

		ft.hide(rightFragments.get(_INDEX2));
		ft.hide(rightFragments.get(_INDEX3));
		CouponsListFragment fragment = (CouponsListFragment) rightFragments.get(_INDEX1);
		ft.show(fragment);
		ft.commit();
		((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();

	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {

		switch (position) {
		case 0:
			showCouponsFragment();
			break;
		case 1:
			showECardFragment();
			break;
		case 2:
			showMyCardFragment();
			break;
		case 3:
			// newContent = new ColorFragment(android.R.color.white);
			break;
		case 4:
			// newContent = new ColorFragment(android.R.color.black);
			break;
		}
	}

}
