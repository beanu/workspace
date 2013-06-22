package com.xiaojiujiu.ui.coupons;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.dao.CouponDetailDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.entity.Coupon;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.widget.LongButton;

/**
 * 优惠详情界面
 * 
 * @author beanu
 * 
 */
public class CouponsDetailActivity extends MyActivity implements OnClickListener {

	private LongButton moreShop;
	private int couponId;
	CouponDetailDao dao;

	@ViewInject(id = R.id.offer_detail_button)
	Button offer_detail_button;
	@ViewInject(id = R.id.ecard_detail_title)
	TextView ecard_detail_title;
	@ViewInject(id = R.id.ecard_shop_name)
	TextView ecard_shop_name;
	@ViewInject(id = R.id.offer_detail_content)
	TextView offer_detail_content;
	@ViewInject(id = R.id.nearby_shop_name)
	TextView nearby_shop_name;
	@ViewInject(id = R.id.nearby_shop_address)
	TextView nearby_shop_address;
	@ViewInject(id = R.id.nearby_shop_distance)
	TextView nearby_shop_distance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_detail_activity);

		// couponId = getIntent().getIntExtra("id", 0);
		couponId = 2;
		dao = new CouponDetailDao(couponId);

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

		offer_detail_button.setOnClickListener(this);

		moreShop = (LongButton) findViewById(R.id.coupon_detail_moreshops);
		moreShop.setOnClickListener(this);

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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.offer_detail_button:
			Intent intent = new Intent(CouponsDetailActivity.this, CouponsDescriptionActivity.class);
			intent.putExtra("url", "http://www.163.com");
			startActivity(intent);
			UIUtil.intentSlidIn(this);
			break;
		case R.id.coupon_detail_moreshops:
			Intent intentMoreShop = new Intent(CouponsDetailActivity.this, CouponsShopApplyActivity.class);
			startActivity(intentMoreShop);
			UIUtil.intentSlidIn(this);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem collectMenuItem = menu.add(Menu.NONE, R.id.menu_collect, Menu.NONE, "收藏");
		collectMenuItem.setIcon(R.drawable.menu_unfav);
		collectMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		MenuItem shareMenuItem = menu.add(Menu.NONE, R.id.menu_share, Menu.NONE, "分享");
		shareMenuItem.setIcon(R.drawable.menu_share);
		shareMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.menu_collect:

			break;
		case R.id.menu_share:
			break;

		}
		super.onOptionsItemSelected(item);
		return false;
	}

	private void refreshPage(Coupon coupon) {
		if (coupon != null) {
			ecard_detail_title.setText(coupon.getCouponTitle());
			ecard_shop_name.setText(coupon.getParentShopName());
			offer_detail_content.setText(coupon.getCouponDesc());
			nearby_shop_name.setText(coupon.getNearestShopName());
			nearby_shop_address.setText(coupon.getNearestShopAddress());
			nearby_shop_distance.setText((int) coupon.getNearestShopDistance() + "米");
		}
	}
}
