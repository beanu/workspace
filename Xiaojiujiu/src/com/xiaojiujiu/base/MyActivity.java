package com.xiaojiujiu.base;

import android.os.Bundle;

import com.beanu.arad.base.BaseActivityWithGD;
import com.xiaojiujiu.R;

public class MyActivity extends BaseActivityWithGD {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setIcon(R.drawable.head_nine);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	}

}
