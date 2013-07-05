package com.xiaojiujiu.dao;

import java.io.IOException;
import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.JsonUtil;
import com.beanu.arad.utils.MessageUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.CouponShop;
import com.xiaojiujiu.ui.HttpUtil;

/** 优惠券适应门店 */
public class CouponShopListDao {

	private String couponID;
	private ArrayList<CouponShop> data;

	public CouponShopListDao(String id) {
		this.couponID = id;
	}

	public ArrayList<CouponShop> getData() {
		return data;
	}

	public void getListInfo(final IDataListener<String> listener) {
		AjaxParams params = new AjaxParams();
		params.put("op", "shopList");
		params.put("couponID", couponID);

		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					JsonNode node = HttpUtil.handleResult(t);
					data = JsonUtil.node2pojo(node.findValue("CouponDetail"),
							new TypeReference<ArrayList<CouponShop>>() {
							});
					listener.onSuccess(t);
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
				listener.onFailure("", t, strMsg);
			}

		});
	}

}
