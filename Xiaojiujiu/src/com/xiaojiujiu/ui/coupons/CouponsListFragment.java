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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.beanu.arad.utils.Log;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshListFragment;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshListView;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.CouponItem;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.CouponListAdapter;
import com.xiaojiujiu.ui.common.SelectorAreaWindow;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow;
import com.xiaojiujiu.ui.common.SelectorSortWindow;

/**
 * 优惠券列表页面
 * 
 * @author beanu
 * 
 */
public class CouponsListFragment extends PullToRefreshListFragment implements OnRefreshListener<ListView>,
		OnLastItemVisibleListener, OnClickListener {

	public static CouponsListFragment newInstance(String typeId, int position) {
		// Bundle args = new Bundle();
		// args.putString(TYPEID, typeId);
		// args.putInt(POSITION, position);
		CouponsListFragment fragment = new CouponsListFragment();
		// fragment.setArguments(args);
		return fragment;
	}

	private CouponListAdapter mAdapter;
	private List<CouponItem> mCouponList = new ArrayList<CouponItem>();

	private Button btn_shoptype;
	private Button btn_area;
	private Button btn_sort;
	private SelectorShopTypeWindow selectorShopTypeWindow;
	private SelectorAreaWindow selectorAreaWindow;
	private SelectorSortWindow selectorSortWindow;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			selectorShopTypeWindow = new SelectorShopTypeWindow(getSherlockActivity());
			selectorAreaWindow = new SelectorAreaWindow(getSherlockActivity());
			selectorSortWindow = new SelectorSortWindow(getSherlockActivity());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.coupons_fragment, container, false);
		empty = (TextView) view.findViewById(R.id.empty);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pull_listView);

		footerView = inflater.inflate(R.layout.pull_to_refresh_footer, null);
		getListView().addFooterView(footerView);
		getListView().setHeaderDividersEnabled(false);
		dismissFooterView();

		// 刷选
		btn_shoptype = (Button) view.findViewById(R.id.selector_shoptype);
		btn_area = (Button) view.findViewById(R.id.selector_area);
		btn_sort = (Button) view.findViewById(R.id.selector_sort);

		btn_shoptype.setOnClickListener(this);
		btn_area.setOnClickListener(this);
		btn_sort.setOnClickListener(this);

		return view;
	}

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
				UIUtil.intentSlidIn(getSherlockActivity());
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selector_shoptype:
			selectorShopTypeWindow.showPopupwindow(btn_shoptype);
			break;
		case R.id.selector_area:
			selectorAreaWindow.showPopupwindow(btn_area);
			break;
		case R.id.selector_sort:
			selectorSortWindow.showPopupwindow(btn_sort);
			break;
		default:
			break;
		}
	}

	@Override
	public void onLastItemVisible() {

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		Log.d("onRefresh");
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
		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {

			@Override
			public void onStart() {

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
						mAdapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				} finally {
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
