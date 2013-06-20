package com.xiaojiujiu.dao;

import java.io.IOException;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import com.beanu.arad.Arad;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

	public void getDetailInfo(final IDao<Coupon> listener) {
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
						coupon = AppHolder.getInstance().objectMapper.readValue(response.getString("CouponDetail"),
								Coupon.class);
						// refreshPage(coupon);
						listener.updateUI(coupon);
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

			}

		});
	}
}
