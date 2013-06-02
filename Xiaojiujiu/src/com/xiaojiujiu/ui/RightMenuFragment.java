package com.xiaojiujiu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.beanu.arad.base.BaseFragment;
import com.xiaojiujiu.R;
import com.xiaojiujiu.ui.user.LoginActivity;
import com.xiaojiujiu.ui.user.SettingActivity;

/**
 * 右边菜单栏
 *
 * @author beanu
 */
public class RightMenuFragment extends BaseFragment implements OnClickListener {

    private RelativeLayout user_layout;
    private TextView right_setting;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right, null);
        user_layout = (RelativeLayout) view.findViewById(R.id.user_layout);
        user_layout.setOnClickListener(this);

        right_setting = (TextView) view.findViewById(R.id.right_setting);
        right_setting.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_layout:
                Intent user_login = new Intent(getSherlockActivity(), LoginActivity.class);
                startActivity(user_login);
                UIUtil.intentSlidIn(getSherlockActivity());
                break;
            case R.id.right_setting:
                Intent setting_intent = new Intent(getSherlockActivity(), SettingActivity.class);
                startActivity(setting_intent);
                UIUtil.intentSlidIn(getSherlockActivity());
                break;
        }

    }

    // public void switchCategory(int position) {
    //
    // switch (position) {
    // case 0:
    // showCouponsFragment();
    // break;
    // case 1:
    // showECardFragment();
    // break;
    // case 2:
    // showMyCardFragment();
    // break;
    // }
    // // drawButtonsBackground(position);
    //
    // }

    // private void showMyCardFragment() {
    // FragmentTransaction ft = getFragmentManager().beginTransaction();
    //
    // ft.hide(rightFragments.get(_INDEX1));
    // ft.hide(rightFragments.get(_INDEX2));
    // ColorFragment fragment = (ColorFragment) rightFragments.get(_INDEX3);
    // ft.show(fragment);
    // ft.commit();
    // ((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
    // }
    //
    // private void showECardFragment() {
    // FragmentTransaction ft = getFragmentManager().beginTransaction();
    //
    // ft.hide(rightFragments.get(_INDEX1));
    // ft.hide(rightFragments.get(_INDEX3));
    // ECardFListragment fragment = (ECardFListragment)
    // rightFragments.get(_INDEX2);
    // ft.show(fragment);
    // ft.commit();
    // ((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
    // }
    //
    // private void showCouponsFragment() {
    // FragmentTransaction ft = getFragmentManager().beginTransaction();
    //
    // ft.hide(rightFragments.get(_INDEX2));
    // ft.hide(rightFragments.get(_INDEX3));
    // CouponsListFragment fragment = (CouponsListFragment)
    // rightFragments.get(_INDEX1);
    // ft.show(fragment);
    // ft.commit();
    // ((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
    //
    // }
    //
    // @Override
    // public void onListItemClick(ListView lv, View v, int position, long id) {
    //
    // switch (position) {
    // case 0:
    // showCouponsFragment();
    // break;
    // case 1:
    // showECardFragment();
    // break;
    // case 2:
    // showMyCardFragment();
    // break;
    // case 3:
    // // newContent = new ColorFragment(android.R.color.white);
    // break;
    // case 4:
    // // newContent = new ColorFragment(android.R.color.black);
    // break;
    // }
    // }

}
