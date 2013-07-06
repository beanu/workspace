package com.xiaojiujiu.base;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.view.MenuItem;
import com.beanu.arad.base.BaseActivityWithGD;
import com.xiaojiujiu.MainActivity;
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
		case android.R.id.home:// ActionBar左方的返回按钮，需要覆写
			// 在action bar点击app icon; 回到 home

			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
