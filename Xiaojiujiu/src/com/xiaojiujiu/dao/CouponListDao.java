package com.xiaojiujiu.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.beanu.arad.Arad;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.CouponItem;

/**
 * 数据逻辑业务（优惠券列表）
 * 
 * @author beanu
 * 
 */
public class CouponListDao {

	private Map<String, String> param;
	private List<CouponItem> mCouponList;

	public CouponListDao() {

		param = new HashMap<String, String>();
		param.put("op", "searchByTypeAndDistance");
		param.put("cityID", "1");
		param.put("radius", "5000");
		param.put("shopFirstCateID", "11");
		param.put("shopSecondCateID", "12");
		param.put("couponTypeID", "15");
		param.put("imei", Arad.app.deviceInfo.getDeviceID());
		param.put("orderType", "2");
		param.put("lng", AppHolder.getInstance().location.getLongitude() + "");
		param.put("lat", AppHolder.getInstance().location.getLatitude() + "");
		param.put("pageSize", "10");
		param.put("pageIndex", "1");

		mCouponList = new ArrayList<CouponItem>();
	}

	/**
	 * 下一页
	 * 
	 * @param callBack
	 */
	public void nextPage(final AjaxCallBack<String> callBack) {

		param.put("pageIndex", (Integer.parseInt(param.get("pageIndex")) + 1) + "");
		AjaxParams params = new AjaxParams(param);
		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onStart() {
				callBack.onStart();
			}

			@Override
			public void onSuccess(String t) {

				List<CouponItem> _list = new ArrayList<CouponItem>();

				try {
					JSONObject response = new JSONObject(t);
					String resCode = response.getString("resCode");
					if (resCode != null && resCode.equals("1")) {
						JSONArray jsonArray = response.getJSONArray("ItemList");

						// _list =
						// AppHolder.getInstance().objectMapper.readValue(response.getString("ItemList"),
						// new TypeReference<List<CouponItem>>() {
						// });
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);

							CouponItem c = new CouponItem();
							// c.setItemID(item.getInt("CouponID"));
							c.setItemTitle(item.getString("ItemTitle"));
							c.setItemDetail(item.getString("ItemDetail"));
							c.setItemImageUrl(item.getString("ItemImageUrl"));
							c.setDistance(item.getDouble("Distance"));
							c.setItemType(item.getInt("ItemType"));
							c.setItemAddress(item.getString("ItemAddress"));
							_list.add(c);
						}

					}
				} catch (JSONException e) {
					e.printStackTrace();
//				} catch (JsonParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (JsonMappingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
				} finally {
					if (_list != null && _list.size() > 0)
						mCouponList.addAll(_list);
					callBack.onSuccess(t);
				}
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				callBack.onFailure(t, strMsg);
			}

		});

	}

	public void pulltorefresh(final AjaxCallBack<String> callBack) {
		Map<String, String> _param = new HashMap<String, String>(param);
		_param.put("pageIndex", "1");

		AjaxParams params = new AjaxParams(_param);
		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onStart() {
				callBack.onStart();
			}

			@Override
			public void onSuccess(String t) {

				try {
					JSONObject response = new JSONObject(t);
					String resCode = response.getString("resCode");
					if (resCode != null && resCode.equals("1")) {
						JSONArray jsonArray = response.getJSONArray("ItemList");

						ArrayList<CouponItem> _list = new ArrayList<CouponItem>();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);

							CouponItem c = new CouponItem();
							// c.setItemID(item.getInt("CouponID"));
							c.setItemTitle(item.getString("ItemTitle"));
							c.setItemDetail(item.getString("ItemDetail"));
							c.setItemImageUrl(item.getString("ItemImageUrl"));
							c.setDistance(item.getDouble("Distance"));
							c.setItemType(item.getInt("ItemType"));
							c.setItemAddress(item.getString("ItemAddress"));
							_list.add(c);
						}

						// 如果有重复的去掉重复的，然后在加上最新的信息
						for (CouponItem newest : _list) {
							for (CouponItem older : mCouponList) {
								if (newest.getItemID() == older.getItemID()) {
									mCouponList.remove(older);
									break;
								}
							}
						}
						mCouponList.addAll(0, _list);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {

					callBack.onSuccess(t);
					// pullToRefreshListView.onRefreshComplete();
					// showListView(true);
				}
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				// pullToRefreshListView.onRefreshComplete();
				// showListView(true);
				callBack.onFailure(t, strMsg);
			}

		});
	}

	public List<CouponItem> getCouponList() {
		return mCouponList;
	}

}
