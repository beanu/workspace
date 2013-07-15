package com.xiaojiujiu.ui.ecard;

import android.os.Bundle;

import com.beanu.arad.utils.Log;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.dao.EcardDetailDao;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 电子会员卡 详情界面
 * 
 * @author beanu
 * 
 */
public class ECardDetailActivity extends MyActivity {
	EcardDetailDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ecard_detail_activity);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {

			@Override
			public void rightSlidingEvent() {
				if (!isFinishing())
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

}
