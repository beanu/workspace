package com.xiaojiujiu.dao;

import java.io.IOException;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.JsonUtil;
import com.beanu.arad.utils.MessageUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.Coupon;
import com.xiaojiujiu.ui.HttpUtil;

public class CouponDetailDao {

	private Coupon coupon;
	int couponId;

	public CouponDetailDao(int couponId) {
		this.couponId = couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public Coupon getCoupon() {
		if (coupon == null)
			return new Coupon();
		return coupon;
	}

	public void getDetailInfo(final IDataListener<Coupon> listener) {
		AjaxParams params = new AjaxParams();
		params.put("op", "couponDetail");
		params.put("couponID", couponId + "");
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
			public void onFailure(Throwable t, String strMsg) {
				listener.onFailure(coupon, t, strMsg);
			}

		});
	}
}
