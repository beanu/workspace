package com.xiaojiujiu.ui.ecard;

import android.os.Bundle;

import com.beanu.arad.base.BaseActivity;
import com.xiaojiujiu.R;

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
	}
}
