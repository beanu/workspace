package com.xiaojiujiu.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListFragment;
import com.xiaojiujiu.MainActivity;
import com.xiaojiujiu.R;
import com.xiaojiujiu.ui.coupons.CouponsFragment;
import com.xiaojiujiu.ui.ecard.ECardFListragment;
import com.xiaojiujiu.ui.entitycard.EntityCardFragment;
import com.xiaojiujiu.ui.freshnews.FreshNewsListFragment;

import java.util.ArrayList;

/**
 * 左边菜单栏
 *
 * @author beanu
 */
public class LeftMenuFragment extends SherlockListFragment {

    // private int currentIndex = -1;

    private ArrayList<Fragment> rightFragments = new ArrayList<Fragment>();

    private static final int _INDEX1 = 0;
    private static final int _INDEX2 = 1;
    private static final int _INDEX3 = 2;
    private static final int _INDEX4 = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.left, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] colors = getResources().getStringArray(R.array.color_names);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, colors);
        setListAdapter(colorAdapter);

        rightFragments.add(_INDEX1, ((MainActivity) getSherlockActivity()).getCouponsFragment());
        rightFragments.add(_INDEX2, ((MainActivity) getSherlockActivity()).getECardFragment());
        rightFragments.add(_INDEX3, ((MainActivity) getSherlockActivity()).getEntityCardFragment());
        rightFragments.add(_INDEX4, ((MainActivity) getSherlockActivity()).getFreshNewsFragment());
        switchCategory(0);
    }

    public void switchCategory(int position) {

        switch (position) {
            case 0:
                showCouponsFragment();
                ((MainActivity) getSherlockActivity()).showEntityCardMenu(false);
                break;
            case 1:
                showECardFragment();
                ((MainActivity) getSherlockActivity()).showEntityCardMenu(false);
                break;
            case 2:
                showEntityCardFragment();
                ((MainActivity) getSherlockActivity()).showEntityCardMenu(true);
                break;
            case 3:
                showFreshNewsFragment();
                ((MainActivity) getSherlockActivity()).showEntityCardMenu(false);
                break;
        }
        // drawButtonsBackground(position);

    }

    private void showEntityCardFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.hide(rightFragments.get(_INDEX1));
        ft.hide(rightFragments.get(_INDEX2));
        ft.hide(rightFragments.get(_INDEX4));
        EntityCardFragment fragment = (EntityCardFragment) rightFragments.get(_INDEX3);
        ft.show(fragment);
        ft.commit();
        ((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
    }

    private void showECardFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.hide(rightFragments.get(_INDEX1));
        ft.hide(rightFragments.get(_INDEX3));
        ft.hide(rightFragments.get(_INDEX4));
        ECardFListragment fragment = (ECardFListragment) rightFragments.get(_INDEX2);
        ft.show(fragment);
        ft.commit();
        ((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();
    }

    private void showCouponsFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.hide(rightFragments.get(_INDEX2));
        ft.hide(rightFragments.get(_INDEX3));
        ft.hide(rightFragments.get(_INDEX4));
        CouponsFragment fragment = (CouponsFragment) rightFragments.get(_INDEX1);
        ft.show(fragment);
        ft.commit();
        ((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();

    }

    private void showFreshNewsFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(rightFragments.get(_INDEX1));
        ft.hide(rightFragments.get(_INDEX2));
        ft.hide(rightFragments.get(_INDEX3));
        FreshNewsListFragment fragment = (FreshNewsListFragment) rightFragments.get(_INDEX4);
        ft.show(fragment);
        ft.commit();
        ((MainActivity) getSherlockActivity()).getSlidingMenu().showContent();

    }

    @Override
    public void onListItemClick(ListView lv, View v, int position, long id) {

        switch (position) {
            case 0:
                showCouponsFragment();
                ((MainActivity) getSherlockActivity()).showEntityCardMenu(false);
                break;
            case 1:
                showECardFragment();
                ((MainActivity) getSherlockActivity()).showEntityCardMenu(false);
                break;
            case 2:
                showEntityCardFragment();
                ((MainActivity) getSherlockActivity()).showEntityCardMenu(true);
                break;
            case 3:
                showFreshNewsFragment();
                ((MainActivity) getSherlockActivity()).showEntityCardMenu(false);
                break;
            case 4:
                // newContent = new ColorFragment(android.R.color.black);
                break;
        }
    }

}
