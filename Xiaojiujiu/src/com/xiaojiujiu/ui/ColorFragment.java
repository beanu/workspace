package com.xiaojiujiu.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.SherlockFragment;

@SuppressLint("ValidFragment")
public class ColorFragment extends SherlockFragment {

	private int mColorRes = -1;

	public ColorFragment() {
		this(android.R.color.white);
	}

	public ColorFragment(int colorRes) {
		// mColorRes = colorRes;
		Bundle a = new Bundle();
		a.putInt("color", colorRes);
		setArguments(a);
		setRetainInstance(true);
	}

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		AppLogger.d(getResources().getColor(getArguments().getInt("color"))+"ColorFragment onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mColorRes=getArguments().getInt("color");
		if (savedInstanceState != null)
			mColorRes = savedInstanceState.getInt("mColorRes");
		int color = getResources().getColor(mColorRes);
		// construct the RelativeLayout
		RelativeLayout v = new RelativeLayout(getActivity());
		v.setBackgroundColor(color);
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mColorRes", mColorRes);
	}

}
