package com.xiaojiujiu.ui.coupons;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.beanu.arad.Arad;
import com.beanu.arad.pulltorefresh.PullToRefreshBase;
import com.beanu.arad.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.beanu.arad.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.beanu.arad.pulltorefresh.PullToRefreshListFragment;
import com.beanu.arad.support.utils.AppLogger;
import com.xiaojiujiu.entity.Coupon;
import com.xiaojiujiu.ui.adapter.CouponListAdapter;

/**
 * 优惠券列表页面
 * 
 * @author beanu
 * 
 */
public class CouponsListFragment extends PullToRefreshListFragment implements OnRefreshListener<ListView>,
		OnLastItemVisibleListener {

	public static CouponsListFragment newInstance(String typeId, int position) {
		// Bundle args = new Bundle();
		// args.putString(TYPEID, typeId);
		// args.putInt(POSITION, position);
		CouponsListFragment fragment = new CouponsListFragment();
		// fragment.setArguments(args);
		return fragment;
	}

	private CouponListAdapter mAdapter;
	private List<Coupon> mCouponList = new ArrayList<Coupon>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mAdapter = new CouponListAdapter(getSherlockActivity(), mCouponList);
		getListView().setAdapter(mAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Intent intent = new Intent(getSherlockActivity().getApplicationContext(), CouponsDetailActivity.class);
				startActivity(intent);
			}
		});

		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setOnLastItemVisibleListener(this);
		if (getCurrentState(savedInstanceState) == FIRST_TIME_START) {
			pullToRefreshListView.startRefreshNow();
			showListView(false);
		}
	}

	@Override
	public void onLastItemVisible() {

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		AppLogger.d("onRefresh");
		AjaxParams params = new AjaxParams();
		params.put("op", "searchByTypeAndDistance");
		params.put("cityID", "1");
		params.put("radius", "10000");
		params.put("shopFirstCateID", "11");
		params.put("shopSecondCateID", "12");
		params.put("couponTypeID", "15");
		params.put("imei", "863363014570588");
		params.put("orderType", "2");
		params.put("lng", "117.15863");
		params.put("lat", "36.65231");
		params.put("pageSize", "3");
		params.put("pageIndex", "1");
		Arad.http.get("http://www.x99local.com/CouponHandler.ashx", params, new AjaxCallBack<String>() {

			@Override
			public void onStart() {

			}

			@Override
			public void onSuccess(String t) {

				try {
					JSONObject response = new JSONObject(t);
					String resCode = response.getString("resCode");
					if (resCode != null && resCode.equals("1")) {
						JSONArray jsonArray = response.getJSONArray("CouponList");
						ArrayList<Coupon> _list = new ArrayList<Coupon>();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							// Coupon c =
							// AppHolder.getInstance().objectMapper.readValue(item.toString(),
							// Coupon.class);
							Coupon c = new Coupon();
							c.setCouponID(item.getInt("CouponID"));
							c.setCouponTitle(item.getString("CouponTitle"));
							c.setCouponDesc(item.getString("CouponDesc"));
							c.setSmallImageUrl(item.getString("SmallImageUrl"));
							_list.add(c);
						}

						// 如果有重复的去掉重复的，然后在加上最新的信息
						for (Coupon newest : _list) {
							for (Coupon older : mCouponList) {
								if (newest.getCouponID() == older.getCouponID()) {
									mCouponList.remove(older);
									break;
								}
							}
						}
						mCouponList.addAll(0, _list);
						mAdapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				// catch (JsonParseException e) {
				// e.printStackTrace();
				// } catch (JsonMappingException e) {
				// e.printStackTrace();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				finally {
					pullToRefreshListView.onRefreshComplete();
					showListView(true);
				}
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}

		});
	}
}
