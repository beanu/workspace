package com.xiaojiujiu.dao;

import java.io.IOException;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.http.AjaxCallBack;
import com.beanu.arad.http.AjaxParams;
import com.beanu.arad.utils.JsonUtil;
import com.beanu.arad.utils.MessageUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.Coupon;
import com.xiaojiujiu.entity.CouponItem;
import com.xiaojiujiu.ui.HttpUtil;

public class CouponDetailDao {

	private Coupon coupon;
	private CouponItem item;

	public CouponDetailDao(CouponItem item) {
		this.item = item;
	}

	public void setItem(CouponItem item) {
		this.item = item;
	}

	public Coupon getCoupon() {
		if (coupon == null)
			return new Coupon();
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public void getDetailInfo(final IDataListener<Coupon> listener) {
		AjaxParams params = new AjaxParams();
		params.put("op", "couponDetail");
		params.put("couponID", item.getItemID());
		params.put("IMEI", Arad.app.deviceInfo.getDeviceID());
		params.put("userID", AppHolder.getInstance().user.getMemberID() + "");

		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					JsonNode node = HttpUtil.handleResult(t);
					coupon = JsonUtil.node2pojo(node.findValue("CouponDetail"), Coupon.class);
					listener.onSuccess(coupon);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AradException e) {
					MessageUtil.showShortToast(Arad.app.getApplicationContext(), e.getMessage());
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo ,String strMsg) {
				listener.onFailure(coupon, t, strMsg);
			}

		});
	}

	/**
	 * 收藏 or 取消收藏 collect =true opType=1 collect =false opType=0
	 * */
	public void collect(boolean collect, final IDataListener<String> listener) {

		try {
			if (collect) {
				coupon.setIsFavorite(1);
				Arad.db.save(coupon);
				Arad.db.save(item);
			} else {
				coupon.setIsFavorite(0);
				Arad.db.delete(coupon);
				Arad.db.delete(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		AjaxParams params = new AjaxParams();
		params.put("op", "collectCoupon");
		params.put("opType", collect ? "1" : "0");
		params.put("couponID", item.getItemID());
		params.put("userID", AppHolder.getInstance().user.getMemberID() + "");

		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					HttpUtil.handleResult(t);
					listener.onSuccess("");
				} catch (AradException e) {
					listener.onFailure("", e, e.getMessage());
					MessageUtil.showShortToast(Arad.app.getApplicationContext(), e.getMessage());
				}

			}

			@Override
			public void onFailure(Throwable t, int errorNo ,String strMsg) {
				listener.onFailure("", t, strMsg);
				MessageUtil.showShortToast(Arad.app.getApplicationContext(),
						Arad.app.getResources().getString(R.string.network_error));
			}

		});
	}
}
