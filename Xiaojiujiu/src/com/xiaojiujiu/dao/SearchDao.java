package com.xiaojiujiu.dao;

import java.io.IOException;
import java.util.ArrayList;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.http.AjaxCallBack;
import com.beanu.arad.http.AjaxParams;
import com.beanu.arad.utils.JsonUtil;
import com.beanu.arad.utils.MessageUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.CouponItem;
import com.xiaojiujiu.ui.HttpUtil;

public class SearchDao {

	String keyword;
	ArrayList<CouponItem> data;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public ArrayList<CouponItem> getData() {
		return data;
	}

	public void request(final IDataListener<String> listener) {
		AjaxParams params = new AjaxParams();
		params.put("op", "searchByKeywords");
		params.put("imei", Arad.app.deviceInfo.getDeviceID());
		params.put("orderType", "2");
		params.put("lng", AppHolder.getInstance().location.getLongitude() + "");
		params.put("lat", AppHolder.getInstance().location.getLatitude() + "");
		params.put("pageSize", "20");
		params.put("pageIndex", "1");
		params.put("keyword", keyword);

		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					JsonNode node = HttpUtil.handleResult(t);
					data = JsonUtil.node2pojo(node.findValue("ItemList"), new TypeReference<ArrayList<CouponItem>>() {
					});
					listener.onSuccess(t);

				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AradException e) {
					listener.onFailure(t, e, e.getMessage());
					MessageUtil.showShortToast(Arad.app.getApplicationContext(), e.getMessage());
				}
			}

			@Override
			public void onFailure(Throwable t,int errorNo , String strMsg) {
				listener.onFailure("", t, strMsg);
				MessageUtil.showShortToast(Arad.app.getApplicationContext(),
						Arad.app.getResources().getString(R.string.network_error));
			}

		});
	}
}
