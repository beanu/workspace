package com.xiaojiujiu.dao;

import java.io.IOException;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.Coupon;

public class CouponDetailDao {

	private Coupon coupon;
	int couponId;

	public CouponDetailDao(int couponId) {
		this.couponId = couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public void getDetailInfo(final IDataListener<Coupon> listener) {
		AjaxParams params = new AjaxParams();
		params.put("op", "couponDetail");
		params.put("couponID", couponId + "");
		params.put("IMEI", Arad.app.deviceInfo.getDeviceID());
		params.put("userID", AppHolder.getInstance().user.getId());

		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					JsonNode node = JsonUtil.json2node(t);
					String resCode = node.findValue("resCode").asText();
					if (resCode != null && resCode.equals("1")) {
						coupon = JsonUtil.node2pojo(node.findValue("CouponDetail"), Coupon.class);
						listener.updateUI(coupon);
					}
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
				listener.onFailure(coupon, t, strMsg);
			}

		});
	}
}
