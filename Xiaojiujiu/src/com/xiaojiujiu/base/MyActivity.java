package com.xiaojiujiu.base;

import android.os.Bundle;
import android.view.MenuItem;

import com.beanu.arad.base.BaseActivityWithGD;
import com.beanu.arad.utils.AnimUtil;
import com.xiaojiujiu.R;

public class MyActivity extends BaseActivityWithGD {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setIcon(R.drawable.head_nine);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
//			Intent intent = new Intent(this, MainActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			this.finish();
			AnimUtil.intentSlidOut(this);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
