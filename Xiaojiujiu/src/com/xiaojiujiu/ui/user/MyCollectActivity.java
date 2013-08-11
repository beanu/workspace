package com.xiaojiujiu.ui.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.ui.UIUtil;

/**
 * 我的收藏
 * 
 * @author beanu
 * 
 */

@EActivity(R.layout.my_collect_activity)
public class MyCollectActivity extends MyActivity implements ActionBar.TabListener {

	@ViewById(R.id.my_collect_viewpager) ViewPager viewpager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("我的优惠券");
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {
			@Override
			public void leftSlidingEvent() {

			}

			@Override
			public void rightSlidingEvent() {
				finish();
				UIUtil.intentSlidOut(MyCollectActivity.this);
			}
		});

	}

	@AfterViews
	void init() {
		CollectAdapter adapter = new CollectAdapter(getSupportFragmentManager());
		viewpager.setAdapter(adapter);
		viewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				super.onPageSelected(position);
				final ActionBar actionBar = getSupportActionBar();
				actionBar.setSelectedNavigationItem(position);
			}
		});
		for (int i = 0; i < adapter.getCount(); i++) {
			getSupportActionBar().addTab(
					getSupportActionBar().newTab().setText(adapter.getPageTitle(i)).setTabListener(this));
		}

	}

	private class CollectAdapter extends FragmentPagerAdapter {

		public CollectAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {
				return new MyCollectCoupons_();
			} else if (position == 1) {
				return new MyCollectEcards_();
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String tabLabel = null;
			switch (position) {
			case 0:
				tabLabel = "优惠券";
				break;
			case 1:
				tabLabel = "会员卡";
				break;
			}
			return tabLabel;
		}

	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		viewpager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
}
