package com.xiaojiujiu.ui.coupons;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.beanu.arad.Arad;
import com.beanu.arad.utils.AndroidUtil;
import com.beanu.arad.utils.MessageUtil;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.dao.CouponDetailDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.entity.Coupon;
import com.xiaojiujiu.entity.CouponItem;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.common.MapActivity;
import com.xiaojiujiu.ui.widget.LongButton;

/**
 * 优惠详情界面
 * 
 * @author beanu
 * 
 */

@EActivity(R.layout.coupon_detail_activity)
public class CouponsDetailActivity extends MyActivity {

	@Extra("item") CouponItem couponItem;
	CouponDetailDao dao;

	@ViewById(R.id.coupon_detail_moreshops) LongButton moreShop;
	@ViewById Button offer_detail_button;
	@ViewById TextView ecard_detail_title;
	@ViewById TextView ecard_shop_name;
	@ViewById TextView offer_detail_content;
	@ViewById TextView nearby_shop_name;
	@ViewById TextView nearby_shop_address;
	@ViewById TextView nearby_shop_distance;
	@ViewById ImageView nearby_shop_phone;
	@ViewById TextView ecard_detail_content;
	@ViewById ImageView ecard_detail_big_image;
	@ViewById ProgressBar coupon_detail_progress;
	@ViewById ScrollView coupon_detail_layout;
	@ViewById LinearLayout nearby_shop_map_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		enableSlideGestureDetector(true);
		setSlidingEventListener(new SlidingEventListener() {
			@Override
			public void leftSlidingEvent() {

			}

			@Override
			public void rightSlidingEvent() {
				finish();
				UIUtil.intentSlidOut(CouponsDetailActivity.this);
			}
		});

	}

	@AfterViews
	void init() {
		dao = new CouponDetailDao(couponItem);

		if (AndroidUtil.networkStatusOK(getApplicationContext())) {
			dao.getDetailInfo(new IDataListener<Coupon>() {

				@Override
				public void onSuccess(Coupon result) {
					refreshPage(result);
				}

				@Override
				public void onFailure(Coupon result, Throwable t, String strMsg) {
					// TODO ERROR

				}
			});
		} else {
			Coupon coupon = Arad.db.findById(Coupon.class, couponItem.getItemID());
			if (coupon != null) {
				dao.setCoupon(coupon);
				refreshPage(coupon);
			}
		}

	}

	@Click({ R.id.offer_detail_button, R.id.coupon_detail_moreshops, R.id.nearby_shop_phone,
			R.id.nearby_shop_map_layout })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.offer_detail_button:
			Intent intent = new Intent(CouponsDetailActivity.this, CouponsDescriptionActivity.class);
			intent.putExtra("url", dao.getCoupon().getCouponDetailDescUrl());
			startActivity(intent);
			UIUtil.intentSlidIn(this);
			break;
		case R.id.coupon_detail_moreshops:
			Intent intentMoreShop = new Intent(CouponsDetailActivity.this, CouponsShopApplyActivity.class);
			intentMoreShop.putExtra("couponId", couponItem.getItemID());
			startActivity(intentMoreShop);
			UIUtil.intentSlidIn(this);
			break;
		case R.id.nearby_shop_phone:
			if (nearby_shop_phone.getTag() != null && !nearby_shop_phone.getTag().equals(""))
				AndroidUtil.dial(CouponsDetailActivity.this, (String) nearby_shop_phone.getTag());
			break;
		case R.id.nearby_shop_map_layout:
			gotoMap();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem collectMenuItem = menu.add(Menu.NONE, R.id.menu_collect, Menu.NONE, "收藏");
		if (dao.getCoupon().getIsFavorite() == 0)
			collectMenuItem.setIcon(R.drawable.menu_unfav);
		else
			collectMenuItem.setIcon(R.drawable.menu_fav);
		collectMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		MenuItem shareMenuItem = menu.add(Menu.NONE, R.id.menu_share, Menu.NONE, "分享");
		shareMenuItem.setIcon(R.drawable.menu_share);
		shareMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_collect:
			if (AppHolder.getInstance().user.getMemberName() == null) {
				MessageUtil.showShortToast(getApplicationContext(), "请登录后在收藏");
			} else {
				if (dao.getCoupon().getIsFavorite() == 0)
					dao.collect(true, new IDataListener<String>() {

						@Override
						public void onSuccess(String result) {
							item.setIcon(R.drawable.menu_fav);
							MessageUtil.showShortToast(getApplicationContext(), "收藏成功");
						}

						@Override
						public void onFailure(String result, Throwable t, String strMsg) {
							// TODO Auto-generated method stub

						}
					});
				else
					dao.collect(false, new IDataListener<String>() {

						@Override
						public void onSuccess(String result) {
							item.setIcon(R.drawable.menu_unfav);
							MessageUtil.showShortToast(getApplicationContext(), "取消收藏");
						}

						@Override
						public void onFailure(String result, Throwable t, String strMsg) {
							// TODO Auto-generated method stub

						}
					});
			}

			break;
		case R.id.menu_share:
			break;

		}
		super.onOptionsItemSelected(item);
		return false;
	}

	@SuppressLint("NewApi")
	private void refreshPage(Coupon coupon) {
		if (coupon != null) {
			Arad.imageLoader.display(coupon.getBigCouponImageUrl(), ecard_detail_big_image);
			ecard_detail_title.setText(coupon.getCouponTitle());
			ecard_shop_name.setText(coupon.getParentShopName());
			offer_detail_content.setText(coupon.getCouponDesc());
			nearby_shop_name.setText(coupon.getNearestShopName());
			nearby_shop_address.setText(coupon.getNearestShopAddress());
			nearby_shop_distance.setText((int) coupon.getNearestShopDistance() + "米");
			nearby_shop_phone.setTag(coupon.getNearestShopTel());
			ecard_detail_content.setText(coupon.getUseIntroduction());
			moreShop.setText(String.format("适用门店 (%s家)", coupon.getFitShopNum() + ""));
			invalidateOptionsMenu();
			showContent();
		}
	}

	private void showContent() {
		coupon_detail_progress.setVisibility(View.GONE);
		coupon_detail_layout.setVisibility(View.VISIBLE);
	}

	private void gotoMap() {
		double lng = dao.getCoupon().getNearestShopLng();
		double lat = dao.getCoupon().getNearestShopLat();
		if (lng != 0 && lat != 0) {
			Intent intent_map = new Intent(CouponsDetailActivity.this, MapActivity.class);
			intent_map.putExtra("lng", lng);
			intent_map.putExtra("lat", lat);
			intent_map.putExtra("name", dao.getCoupon().getNearestShopName());
			startActivity(intent_map);
			UIUtil.intentSlidIn(this);
		} else {
			MessageUtil.showShortToast(this, "无商家位置信息");
		}
	}
}
