package com.xiaojiujiu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.xiaojiujiu.ui.ColorFragment;
import com.xiaojiujiu.ui.LeftMenuFragment;
import com.xiaojiujiu.ui.coupons.CouponsFragment;
import com.xiaojiujiu.ui.ecard.ECardFragment;

public class MainActivity extends SlidingFragmentActivity {

	private long waitTime = 2000;
	private long touchTime = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(com.actionbarsherlock.R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main_activity);

		// setTitle(getResources().getString(R.string.app_name));

		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// buildCustomActionBarTitle();

		setContentView(R.layout.content_fragment);
		setBehindContentView(R.layout.menu_fragment);

		// set the Above View
		if (savedInstanceState == null) {
			initFragments();

			// set the Above View
			// getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,
			// mContent).commit();

			// set the Behind View
			FragmentTransaction secondFragmentTransaction = getSupportFragmentManager().beginTransaction();
			secondFragmentTransaction.replace(R.id.menu_fragment, getMenuFragment(), LeftMenuFragment.class.getName());
			secondFragmentTransaction.commit();
			// getSlidingMenu().showContent();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 两次返回键，退出程序
		if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initFragments() {
		Fragment couponsFragment = getCouponsFragment();
		Fragment eCardFragment = getECardFragment();
		Fragment myCardFragment = getMyCardFragment();

		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		if (!couponsFragment.isAdded()) {
			fragmentTransaction.add(R.id.content_fragment, couponsFragment, "coupons");
			fragmentTransaction.hide(couponsFragment);
		}
		if (!eCardFragment.isAdded()) {
			fragmentTransaction.add(R.id.content_fragment, eCardFragment, "ecard");
			fragmentTransaction.hide(eCardFragment);

		}
		if (!myCardFragment.isAdded()) {
			fragmentTransaction.add(R.id.content_fragment, myCardFragment, "mycard");
			fragmentTransaction.hide(myCardFragment);
		}
		if (!fragmentTransaction.isEmpty()) {
			fragmentTransaction.commit();
			getSupportFragmentManager().executePendingTransactions();
		}
	}

	public LeftMenuFragment getMenuFragment() {
		LeftMenuFragment fragment = ((LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(
				LeftMenuFragment.class.getName()));
		if (fragment == null) {
			fragment = new LeftMenuFragment();
		}
		return fragment;
	}

	public CouponsFragment getCouponsFragment() {
		CouponsFragment fragment = ((CouponsFragment) getSupportFragmentManager().findFragmentByTag("coupons"));
		if (fragment == null)
			fragment = new CouponsFragment();
		return fragment;
	}

	public ECardFragment getECardFragment() {
		ECardFragment fragment = ((ECardFragment) getSupportFragmentManager().findFragmentByTag("ecard"));
		if (fragment == null)
			fragment = new ECardFragment();

		return fragment;
	}

	public ColorFragment getMyCardFragment() {
		ColorFragment fragment = ((ColorFragment) getSupportFragmentManager().findFragmentByTag("mycard"));
		if (fragment == null)
			fragment = new ColorFragment(R.color.red);

		return fragment;
	}

}
