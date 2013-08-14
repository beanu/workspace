package com.xiaojiujiu;

import java.util.HashMap;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnCloseListener;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.xiaojiujiu.ui.LeftMenuFragment_;
import com.xiaojiujiu.ui.RightMenuFragment_;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.common.SearchActivity;
import com.xiaojiujiu.ui.coupons.CouponsListFragment;
import com.xiaojiujiu.ui.ecard.ECardListFragment;
import com.xiaojiujiu.ui.entitycard.AddEntityCardActivity;
import com.xiaojiujiu.ui.share.ShareFragment_;
import com.xiaojiujiu.ui.widget.dialog.CouponTypeDialogFragment.OnCouponTypeSelectedListener;

/**
 * 主页
 * 
 * @author beanu
 */
public class MainActivity extends SlidingFragmentActivity implements OnCouponTypeSelectedListener {

	private long waitTime = 2000;
	private long touchTime = 0;
	private boolean showEntityCardMenu, showSearch;
	private FragmentsManager fragmentManager;

	private SlidingMenu sm;

	public enum Fragments {
		coupons, ecard, share
		// mycard, freshNews
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

		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.head_nine);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		// buildCustomActionBarTitle();

		setContentView(R.layout.main_fragment);
		setBehindContentView(R.layout.menu_left_fragment);

		sm.setSecondaryMenu(R.layout.menu_right_fragment);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);

		if (savedInstanceState == null) {

			// set the Content View
			fragmentManager = new FragmentsManager(this, R.id.content_fragment);
			fragmentManager.addFragment(Fragments.coupons.name(), CouponsListFragment.class, null);
			fragmentManager.addFragment(Fragments.ecard.name(), ECardListFragment.class, null);
			fragmentManager.addFragment(Fragments.share.name(), ShareFragment_.class, null);

			// fragmentManager.addFragment(Fragments.mycard.name(),
			// EntityCardFragment.class, null);
			// fragmentManager.addFragment(Fragments.freshNews.name(),
			// FreshNewsListFragment.class, null);

			// set the Left View
			FragmentTransaction secondFragmentTransaction = getSupportFragmentManager().beginTransaction();
			secondFragmentTransaction.replace(R.id.menu_left, getLeftMenuFragment(), LeftMenuFragment_.class.getName());

			// set the right view
			secondFragmentTransaction.replace(R.id.menu_right, getRightMenuFragment(),
					RightMenuFragment_.class.getName());
			secondFragmentTransaction.commit();

			// getSlidingMenu().showContent();
			showFragment(Fragments.coupons);

		}
	}

	@Override
	protected void onResume() {
		// Intent intent = new Intent(this, SensorService.class);
		// startService(intent);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// Intent intent = new Intent(this, SensorService.class);
		// stopService(intent);
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_activity, menu);

		if (showEntityCardMenu) {
			MenuItem ecMenuItem = menu.add(Menu.NONE, R.id.menu_add_entity_card, Menu.NONE, "addEntityCard");
			ecMenuItem.setIcon(R.drawable.menu_add);
			ecMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}

		MenuItem searchItem = menu.findItem(R.id.action_search);
		searchItem.setVisible(showSearch);

		// if (showSearch) {
		// searchItem.setVisible(true);
		// menu.add(Menu.NONE, R.id.menu_search, Menu.NONE,
		// "Search").setIcon(R.drawable.ic_search)
		// .setActionView(R.layout.collapsible_edittext)
		// .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS |
		// MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		// }

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
		case R.id.action_search:
			SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
			searchView.setIconifiedByDefault(true);
			searchView.setIconified(false);
			if (Build.VERSION.SDK_INT >= 14) {
				// when edittest is empty, don't show cancal button
				searchView.onActionViewExpanded();
			}
			searchView.setOnCloseListener(new OnCloseListener() {

				@Override
				public boolean onClose() {
					// to avoid click x button and the edittext hidden
					return true;
				}
			});

			searchView.setOnQueryTextListener(new OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String keyword) {
					// 先隐藏键盘
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
							MainActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

					// 跳转到搜索结果界面
					if (!keyword.equals("")) {
						Bundle b = new Bundle();
						b.putString("keyword", keyword);
						Intent intent = new Intent(MainActivity.this, SearchActivity.class);
						intent.putExtras(b);
						startActivity(intent);
					}

					return true;
				}

				@Override
				public boolean onQueryTextChange(String arg0) {
					// TODO Auto-generated method stub
					return false;
				}
			});
			break;
		case R.id.menu_search:
			// final EditText editText = (EditText) item.getActionView();
			// editText.setOnKeyListener(new OnKeyListener() {
			//
			// @Override
			// public boolean onKey(View v, int keyCode, KeyEvent event) {
			// return false;
			//
			// }
			// });
			// item.setOnActionExpandListener(new OnActionExpandListener() {
			//
			// @Override
			// public boolean onMenuItemActionExpand(MenuItem item) {
			// editText.requestFocus();
			// // editText.postDelayed(new Runnable() {
			// // @Override
			// // public void run() {
			// // InputMethodManager imm = (InputMethodManager)
			// // getSystemService(Context.INPUT_METHOD_SERVICE);
			// // imm.showSoftInput(editText,
			// // InputMethodManager.SHOW_IMPLICIT);
			// // }
			// // }, 200);
			//
			// return true;
			// }
			//
			// @Override
			// public boolean onMenuItemActionCollapse(MenuItem item) {
			// // TODO Auto-generated method stub
			// return false;
			// }
			// });
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

	public LeftMenuFragment_ getLeftMenuFragment() {
		LeftMenuFragment_ fragment = ((LeftMenuFragment_) getSupportFragmentManager().findFragmentByTag(
				LeftMenuFragment_.class.getName()));
		if (fragment == null) {
			fragment = new LeftMenuFragment_();
		}
		return fragment;
	}

	public RightMenuFragment_ getRightMenuFragment() {
		RightMenuFragment_ fragment = ((RightMenuFragment_) getSupportFragmentManager().findFragmentByTag(
				RightMenuFragment_.class.getName()));
		if (fragment == null) {
			fragment = new RightMenuFragment_();
		}
		return fragment;
	}

	private void showMenu(boolean enableEntityCardMenu, boolean enableSearch) {
		showEntityCardMenu = enableEntityCardMenu;
		showSearch = enableSearch;
		supportInvalidateOptionsMenu();
	}

	public void showFragment(Fragments fm) {
		switch (fm) {
		case coupons:
			fragmentManager.changFragment(Fragments.coupons.name());
			showMenu(false, true);
			break;
		case ecard:
			fragmentManager.changFragment(Fragments.ecard.name());
			showMenu(false, true);
			break;
		case share:
			fragmentManager.changFragment(Fragments.share.name());
			showMenu(false, true);
			break;
		// case mycard:
		// fragmentManager.changFragment(Fragments.mycard.name());
		// showMenu(true, false);
		// break;
		// case freshNews:
		// fragmentManager.changFragment(Fragments.freshNews.name());
		// showMenu(false, true);
		// break;
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

	@Override
	public void onSelected(String id) {
		CouponsListFragment fragment = (CouponsListFragment) getSupportFragmentManager().findFragmentByTag(
				Fragments.coupons.name());
		if (fragment != null)
			fragment.onCouponTypeSelected(id);

	}
}
