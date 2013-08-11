package com.xiaojiujiu.ui.user;

import android.os.Bundle;

import com.googlecode.androidannotations.annotations.EActivity;
import com.xiaojiujiu.base.MyListActivity;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 我的会员卡列表
 * 
 * @author beanu
 * 
 */

@EActivity
public class MyEcardActivity extends MyListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("我的会员卡");
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {
			@Override
			public void leftSlidingEvent() {

			}

			@Override
			public void rightSlidingEvent() {
				finish();
				UIUtil.intentSlidOut(MyEcardActivity.this);
			}
		});

	}

}
