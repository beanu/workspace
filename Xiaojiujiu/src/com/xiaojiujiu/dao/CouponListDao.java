package com.xiaojiujiu.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.utils.JsonUtil;
import com.beanu.arad.utils.MessageUtil;
import com.beanu.arad.utils.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.CouponItem;
import com.xiaojiujiu.ui.HttpUtil;

/**
 * 数据逻辑业务（优惠券列表）
 * 
 * @author beanu
 * 
 */
public class CouponListDao {

	private Map<String, String> param;
	private List<CouponItem> mCouponList;
	private final int PageSize = 10;

	public CouponListDao() {

		param = new HashMap<String, String>();
		param.put("op", "searchByTypeAndDistance");
		param.put("cityID", "1");
		param.put("radius", "5000");
		param.put("shopFirstCateID", "");
		param.put("shopSecondCateID", "");
		param.put("couponTypeID", "");// 优惠券类型
		param.put("orderType", "2");
		param.put("districtID", "");
		param.put("businessDistrictID", "");

		param.put("imei", Arad.app.deviceInfo.getDeviceID());
		param.put("lng", AppHolder.getInstance().location.getLongitude() + "");
		param.put("lat", AppHolder.getInstance().location.getLatitude() + "");
		param.put("pageSize", String.valueOf(PageSize));
		param.put("pageIndex", "1");

		mCouponList = new ArrayList<CouponItem>();
	}

	/**
	 * 下一页
	 * 
	 * @param callBack
	 */
	public void nextPage(final IDataListener<String> listener) {

		param.put("pageIndex", (Integer.parseInt(param.get("pageIndex")) + 1) + "");
		AjaxParams params = new AjaxParams(param);
		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				ArrayList<CouponItem> _list = null;

				try {
					JsonNode node = HttpUtil.handleResult(t);
					_list = JsonUtil.node2pojo(node.findValue("ItemList"), new TypeReference<ArrayList<CouponItem>>() {
					});

				} catch (AradException e) {
					MessageUtil.showShortToast(Arad.app.getApplicationContext(), e.getMessage());
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (_list != null && _list.size() > 0) {
						for (CouponItem last : _list) {
							List<CouponItem> _temp=new ArrayList<CouponItem>(mCouponList);
							for (CouponItem item : _temp) {
								if (item.getItemID() == last.getItemID()) {
									break;
								}
								mCouponList.add(last);
							}
						}
					}
					listener.onSuccess(t);
				}
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				listener.onFailure(null, t, strMsg);
			}

		});

	}

	public void pulltorefresh(final IDataListener<String> listener) {
		Map<String, String> _param = new HashMap<String, String>(param);
		_param.put("pageIndex", "1");

		AjaxParams params = new AjaxParams(_param);
		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onSuccess(String t) {

				try {
					JsonNode node = HttpUtil.handleResult(t);
					ArrayList<CouponItem> _list = JsonUtil.node2pojo(node.findValue("ItemList"),
							new TypeReference<ArrayList<CouponItem>>() {
							});

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
			public void onFailure(Throwable t, String strMsg) {
				listener.onFailure(null, t, strMsg);
			}

		});
	}

	public List<CouponItem> getCouponList() {
		return mCouponList;
	}

	public void onClickShop(String parentId, String shopId, IDataListener<String> listener) {
		if (!StringUtil.isNull(shopId) && !StringUtil.isNull(parentId)) {
			param.put("shopFirstCateID", parentId);
			param.put("shopSecondCateID", shopId);
		} else if (!StringUtil.isNull(parentId) && StringUtil.isNull(shopId)) {
			param.put("shopFirstCateID", parentId);
			param.put("shopSecondCateID", "");
		}

		updateData(listener);
	}

	public void onClickArea(String parentId, String areaId, IDataListener<String> listener) {
		if (!StringUtil.isNull(areaId) && !StringUtil.isNull(parentId)) {
			param.put("districtID", parentId);
			param.put("businessDistrictID", areaId);
		} else if (!StringUtil.isNull(parentId) && StringUtil.isNull(areaId)) {
			param.put("districtID", parentId);
			param.put("businessDistrictID", "");
		}

		updateData(listener);

	}

	public void onClickSort(String parentId, IDataListener<String> listener) {

		param.put("orderType", parentId);
		updateData(listener);

	}

	// 筛选数据
	private void updateData(final IDataListener<String> listener) {
		AjaxParams params = new AjaxParams(param);
		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onStart() {

			}

			@Override
			public void onSuccess(String t) {

				try {

					JsonNode node = HttpUtil.handleResult(t);
					ArrayList<CouponItem> _list = JsonUtil.node2pojo(node.findValue("ItemList"),
							new TypeReference<ArrayList<CouponItem>>() {
							});

					// 如果有重复的去掉重复的，然后在加上最新的信息
					mCouponList.clear();
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
			public void onFailure(Throwable t, String strMsg) {
				listener.onFailure("", t, strMsg);
			}

		});

	}

}
