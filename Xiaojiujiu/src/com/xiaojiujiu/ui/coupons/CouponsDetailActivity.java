package com.xiaojiujiu.ui.coupons;

import java.io.IOException;

import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.beanu.arad.Arad;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.base.MyActivity;
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

	private Button offer_detail_button;
	private LongButton moreShop;
	private int couponId;
	private Coupon coupon;

	@ViewInject(id=R.id.ecard_detail_title) TextView ecard_detail_title;
	@ViewInject(id=R.id.ecard_shop_name) TextView ecard_shop_name;
	@ViewInject(id=R.id.offer_detail_content) TextView offer_detail_content;
	@ViewInject(id=R.id.nearby_shop_name) TextView nearby_shop_name;
	@ViewInject(id=R.id.nearby_shop_address) TextView nearby_shop_address;
	@ViewInject(id=R.id.nearby_shop_distance) TextView nearby_shop_distance;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coupon_detail_activity);

//		ecard_detail_title=(TextView)findViewById(R.id.ecard_detail_title);
//		ecard_shop_name=(TextView)findViewById(R.id.ecard_shop_name);
//		offer_detail_content=(TextView)findViewById(R.id.offer_detail_content);
//		nearby_shop_name=(TextView)findViewById(R.id.nearby_shop_name);
//		nearby_shop_address=(TextView)findViewById(R.id.nearby_shop_address);
//		nearby_shop_distance=(TextView)findViewById(R.id.nearby_shop_distance);
		
		couponId = getIntent().getIntExtra("id", 0);
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

		offer_detail_button = (Button) findViewById(R.id.offer_detail_button);
		offer_detail_button.setOnClickListener(this);

		moreShop = (LongButton) findViewById(R.id.coupon_detail_moreshops);
		moreShop.setOnClickListener(this);
		
		getInfo();
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

	private void getInfo() {
		AjaxParams params = new AjaxParams();
		params.put("op", "couponDetail");
		params.put("couponID", couponId + "");
		params.put("IMEI", Arad.app.deviceInfo.getDeviceID());
		params.put("userID", AppHolder.getInstance().user.getId());

		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					JSONObject response = new JSONObject(t);
					String resCode = response.getString("resCode");
					if (resCode != null && resCode.equals("1")) {
						coupon=AppHolder.getInstance().objectMapper.readValue(response.getString("CouponDetail"), Coupon.class);
						refreshPage(coupon);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, strMsg);
			}

		});
	}
	
	private void refreshPage(Coupon coupon){
		if(coupon!=null){
			ecard_detail_title.setText(coupon.getCouponTitle());
			ecard_shop_name.setText(coupon.getParentShopName());
			offer_detail_content.setText(coupon.getCouponDesc());
			nearby_shop_name.setText(coupon.getNearestShopName());
			nearby_shop_address.setText(coupon.getNearestShopAddress());
			nearby_shop_distance.setText(coupon.getNearestShopDistance());
		}
	}
}
