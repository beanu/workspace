package com.xiaojiujiu.ui.ecard;

import android.os.Bundle;

import com.beanu.arad.base.BaseActivity;
import com.beanu.arad.utils.Log;
import com.xiaojiujiu.R;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 电子会员卡 详情界面
 * 
 * @author beanu
 * 
 */
public class ECardDetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecard_detail_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {
			
			@Override
			public void rightSlidingEvent() {
				finish();
				UIUtil.intentSlidOut(ECardDetailActivity.this);
				Log.d("right sliding");
			}
			
			@Override
			public void leftSlidingEvent() {
				Log.d("left sliding");
				
			}
		});
	}

	@Override
	public void onBackPressed() {
		finish();
		UIUtil.intentSlidOut(this);
	}

}
