package com.xiaojiujiu.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.http.AjaxCallBack;
import com.beanu.arad.http.AjaxParams;
import com.beanu.arad.utils.JsonUtil;
import com.beanu.arad.utils.MessageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.CouponItem;
import com.xiaojiujiu.ui.HttpUtil;

/**
 * 数据逻辑业务（优惠券列表）
 * 
 * @author beanu
 * 
 */
public class CouponListWithShopDao {

	private Map<String, String> param;
	private List<CouponItem> mCouponList;

	public CouponListWithShopDao(String shopId) {

		param = new HashMap<String, String>();
		param.put("op", "getCouponListByShopID");
		param.put("shopID", shopId);
		param.put("pageSize", "100");
		param.put("pageIndex", "1");

		mCouponList = new ArrayList<CouponItem>();
	}

	public void getInfo(final IDataListener<String> listener) {

		AjaxParams params = new AjaxParams(param);
		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					JsonNode node = HttpUtil.handleResult(t);
					ArrayList<CouponItem> _list = JsonUtil.node2pojo(node.findValue("ItemList"),
							new TypeReference<ArrayList<CouponItem>>() {
							});
					mCouponList.addAll(0, _list);

				} catch (AradException e) {
					MessageUtil.showShortToast(Arad.app.getApplicationContext(), e.getMessage());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					listener.onSuccess(t);
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo ,String strMsg) {
				listener.onFailure(null, t, strMsg);
			}

		});
	}

	public List<CouponItem> getCouponList() {
		return mCouponList;
	}

}
