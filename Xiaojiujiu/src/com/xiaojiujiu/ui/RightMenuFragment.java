package com.xiaojiujiu.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beanu.arad.base.BaseFragment;
import com.xiaojiujiu.R;

/**
 * 右边菜单栏
 * 
 * @author beanu
 * 
 */
public class RightMenuFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.right, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

//	public void switchCategory(int position) {
//
//		switch (position) {
//		case 0:
//			showCouponsFragment();
//			break;
//		case 1:
//			showECardFragment();
//			break;
//		case 2:
//			showMyCardFragment();
//			break;
//		}
//		// drawButtonsBackground(position);
//
//	}

//	private void showMyCardFragment() {
//		FragmentTransaction ft = getFragmentManager().beginTransaction();
//
//		ft.hide(rightFragments.get(_INDEX1));
//		ft.hide(rightFragments.get(_INDEX2));
//		ColorFragment fragment = (ColorFragment) rightFragments.get(_INDEX3);
//		ft.show(fragment);
//		ft.commit();
//		((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
//	}
//
//	private void showECardFragment() {
//		FragmentTransaction ft = getFragmentManager().beginTransaction();
//
//		ft.hide(rightFragments.get(_INDEX1));
//		ft.hide(rightFragments.get(_INDEX3));
//		ECardFListragment fragment = (ECardFListragment) rightFragments.get(_INDEX2);
//		ft.show(fragment);
//		ft.commit();
//		((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
//	}
//
//	private void showCouponsFragment() {
//		FragmentTransaction ft = getFragmentManager().beginTransaction();
//
//		ft.hide(rightFragments.get(_INDEX2));
//		ft.hide(rightFragments.get(_INDEX3));
//		CouponsListFragment fragment = (CouponsListFragment) rightFragments.get(_INDEX1);
//		ft.show(fragment);
//		ft.commit();
//		((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
//
//	}
//
//	@Override
//	public void onListItemClick(ListView lv, View v, int position, long id) {
//
//		switch (position) {
//		case 0:
//			showCouponsFragment();
//			break;
//		case 1:
//			showECardFragment();
//			break;
//		case 2:
//			showMyCardFragment();
//			break;
//		case 3:
//			// newContent = new ColorFragment(android.R.color.white);
//			break;
//		case 4:
//			// newContent = new ColorFragment(android.R.color.black);
//			break;
//		}
//	}

}
