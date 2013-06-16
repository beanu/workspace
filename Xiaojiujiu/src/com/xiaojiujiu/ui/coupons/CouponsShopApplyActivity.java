package com.xiaojiujiu.ui.coupons;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.xiaojiujiu.R;
import com.xiaojiujiu.base.MyActivity;
import com.xiaojiujiu.entity.CouponShop;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.CouponShopApplyListAdapter;

/**
 * 优惠券适用门店
 * 
 * @author beanu
 * 
 */
public class CouponsShopApplyActivity extends MyActivity {

	private ListView listView;
	private CouponShopApplyListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_shop_apply_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        enableSlideGestureDetector(true);
        setSlidingEventListener(new SlidingEventListener() {
            @Override
            public void leftSlidingEvent() {

            }

            @Override
            public void rightSlidingEvent() {
                finish();
                UIUtil.intentSlidOut(CouponsShopApplyActivity.this);
            }
        });

		List<CouponShop> data = new ArrayList<CouponShop>();
		for (int i = 0; i < 4; i++) {
			CouponShop shop = new CouponShop();
			shop.setShopTitle("小肥羊" + i);
			shop.setShopAdress("济南速度个发送的嘎");
			shop.setShopDistance("100");
			shop.setShopTel("18663737935");
			data.add(shop);
		}
		adapter = new CouponShopApplyListAdapter(getApplicationContext(), data);

		listView = (ListView) findViewById(R.id.coupon_shop_apply_list);
		listView.setAdapter(adapter);
	}
}
