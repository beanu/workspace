package com.xiaojiujiu;

import android.content.Intent;
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
import com.xiaojiujiu.ui.RightMenuFragment;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.coupons.CouponsFragment;
import com.xiaojiujiu.ui.ecard.ECardFListragment;
import com.xiaojiujiu.ui.entitycard.AddEntityCardActivity;
import com.xiaojiujiu.ui.entitycard.EntityCardFragment;

/**
 * 主页
 *
 * @author beanu
 */
public class MainActivity extends SlidingFragmentActivity {

    private long waitTime = 2000;
    private long touchTime = 0;
    private boolean showEntityCardMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.top_bg));

        // setTitle(getResources().getString(R.string.app_name));

        SlidingMenu sm = getSlidingMenu();
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
            initFragments();

            // set the Left View
            FragmentTransaction secondFragmentTransaction = getSupportFragmentManager().beginTransaction();
            secondFragmentTransaction.replace(R.id.menu_left, getLeftMenuFragment(),
                    LeftMenuFragment.class.getName());

            // set the right view
            secondFragmentTransaction.replace(R.id.menu_right, getRightMenuFragment(),
                    RightMenuFragment.class.getName());
            secondFragmentTransaction.commit();

            // getSlidingMenu().showContent();
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
                Intent intent=new Intent(MainActivity.this, AddEntityCardActivity.class);
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

    private void initFragments() {
        Fragment couponsFragment = getCouponsFragment();
        Fragment eCardFragment = getECardFragment();
        Fragment myCardFragment = getEntityCardFragment();

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

    public CouponsFragment getCouponsFragment() {
        CouponsFragment fragment = ((CouponsFragment) getSupportFragmentManager().findFragmentByTag("coupons"));
        if (fragment == null)
            fragment = new CouponsFragment();
        return fragment;
    }

    public ECardFListragment getECardFragment() {
        ECardFListragment fragment = ((ECardFListragment) getSupportFragmentManager().findFragmentByTag("ecard"));
        if (fragment == null)
            fragment = new ECardFListragment();

        return fragment;
    }

    public EntityCardFragment getEntityCardFragment() {
        EntityCardFragment fragment = ((EntityCardFragment) getSupportFragmentManager().findFragmentByTag("mycard"));
        if (fragment == null)
            fragment = EntityCardFragment.newInstance();

        return fragment;
    }

    public void showEntityCardMenu(boolean enableShow) {
        showEntityCardMenu = enableShow;
        supportInvalidateOptionsMenu();
    }

}
