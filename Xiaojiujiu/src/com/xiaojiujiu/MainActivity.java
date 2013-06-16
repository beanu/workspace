package com.xiaojiujiu;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.xiaojiujiu.ui.LeftMenuFragment;
import com.xiaojiujiu.ui.RightMenuFragment;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.coupons.CouponsListFragment;
import com.xiaojiujiu.ui.ecard.ECardFListragment;
import com.xiaojiujiu.ui.entitycard.AddEntityCardActivity;
import com.xiaojiujiu.ui.entitycard.EntityCardFragment;
import com.xiaojiujiu.ui.freshnews.FreshNewsListFragment;

/**
 * 主页
 * 
 * @author beanu
 */
public class MainActivity extends SlidingFragmentActivity {

	private long waitTime = 2000;
	private long touchTime = 0;
	private boolean showEntityCardMenu;
	private FragmentsManager fragmentManager;

	private SlidingMenu sm;

	public enum Fragments {
		coupons, ecard, mycard, freshNews
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.top_bg));

		// setTitle(getResources().getString(R.string.app_name));

		sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadowleft);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// buildCustomActionBarTitle();

		setContentView(R.layout.main_fragment);
		setBehindContentView(R.layout.menu_left_fragment);

		sm.setSecondaryMenu(R.layout.menu_right_fragment);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);

		if (savedInstanceState == null) {

			// set the Content View
			fragmentManager = new FragmentsManager(this, R.id.content_fragment);
			fragmentManager.addFragment(Fragments.coupons.name(), CouponsListFragment.class, null);
			fragmentManager.addFragment(Fragments.ecard.name(), ECardFListragment.class, null);
			fragmentManager.addFragment(Fragments.mycard.name(), EntityCardFragment.class, null);
			fragmentManager.addFragment(Fragments.freshNews.name(), FreshNewsListFragment.class, null);

			// set the Left View
			FragmentTransaction secondFragmentTransaction = getSupportFragmentManager().beginTransaction();
			secondFragmentTransaction.replace(R.id.menu_left, getLeftMenuFragment(), LeftMenuFragment.class.getName());

			// set the right view
			secondFragmentTransaction.replace(R.id.menu_right, getRightMenuFragment(),
					RightMenuFragment.class.getName());
			secondFragmentTransaction.commit();

			// getSlidingMenu().showContent();
			showFragment(Fragments.coupons);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_activity, menu);

		if (showEntityCardMenu) {
			MenuItem ecMenuItem = menu.add(Menu.NONE, R.id.menu_add_entity_card, Menu.NONE, "addEntityCard");
			ecMenuItem.setIcon(R.drawable.menu_add);
			ecMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.menu_add_entity_card:
			Intent intent = new Intent(MainActivity.this, AddEntityCardActivity.class);
			startActivity(intent);
			UIUtil.intentSlidIn(MainActivity.this);
			break;
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

	public LeftMenuFragment getLeftMenuFragment() {
		LeftMenuFragment fragment = ((LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(
				LeftMenuFragment.class.getName()));
		if (fragment == null) {
			fragment = new LeftMenuFragment();
		}
		return fragment;
	}

	public RightMenuFragment getRightMenuFragment() {
		RightMenuFragment fragment = ((RightMenuFragment) getSupportFragmentManager().findFragmentByTag(
				RightMenuFragment.class.getName()));
		if (fragment == null) {
			fragment = new RightMenuFragment();
		}
		return fragment;
	}

	private void showEntityCardMenu(boolean enableShow) {
		showEntityCardMenu = enableShow;
		supportInvalidateOptionsMenu();
	}

	public void showFragment(Fragments fm) {
		switch (fm) {
		case coupons:
			fragmentManager.changFragment(Fragments.coupons.name());
			showEntityCardMenu(false);
			break;
		case ecard:
			fragmentManager.changFragment(Fragments.ecard.name());
			showEntityCardMenu(false);
			break;
		case mycard:
			fragmentManager.changFragment(Fragments.mycard.name());
			showEntityCardMenu(true);
			break;
		case freshNews:
			fragmentManager.changFragment(Fragments.freshNews.name());
			showEntityCardMenu(false);
			break;
		}
		sm.showContent();
	}

	/**
	 * Fragment的管理
	 * 
	 * @author beanu
	 * 
	 */
	public static class FragmentsManager {
		private final FragmentActivity mActivity;
		// private final TabHost mTabHost;
		private final int mContainerId;
		private final HashMap<String, FragmentInfo> mTabs = new HashMap<String, FragmentInfo>();
		FragmentInfo mLastFragment;

		static final class FragmentInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;
			private Fragment fragment;

			FragmentInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		/**
		 * 
		 * @param activity
		 *            依附的主Activity
		 * @param containerId
		 *            填充Fragment的id
		 */
		public FragmentsManager(FragmentActivity activity, int containerId) {
			mActivity = activity;
			mContainerId = containerId;
		}

		public void addFragment(String tag, Class<?> clss, Bundle args) {

			FragmentInfo info = new FragmentInfo(tag, clss, args);

			// Check to see if we already have a fragment for this tab, probably
			// from a previously saved state. If so, deactivate it, because our
			// initial state is that a tab isn't shown.
			info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
			if (info.fragment != null && !info.fragment.isDetached()) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				ft.detach(info.fragment);
				ft.commit();
			}

			mTabs.put(tag, info);
		}

		public void changFragment(String tag) {

			FragmentInfo newTab = mTabs.get(tag);
			if (mLastFragment != newTab) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				if (mLastFragment != null) {
					if (mLastFragment.fragment != null) {
						ft.detach(mLastFragment.fragment);
					}
				}
				if (newTab != null) {
					if (newTab.fragment == null) {
						newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
						ft.add(mContainerId, newTab.fragment, newTab.tag);
					} else {
						ft.attach(newTab.fragment);
					}
				}

				mLastFragment = newTab;
				ft.commit();
				mActivity.getSupportFragmentManager().executePendingTransactions();

			}

		}
	}

}
