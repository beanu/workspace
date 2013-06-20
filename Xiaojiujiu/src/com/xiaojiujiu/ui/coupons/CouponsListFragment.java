package com.xiaojiujiu.ui.coupons;

import net.tsz.afinal.http.AjaxCallBack;
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

import com.beanu.arad.utils.Log;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshListFragment;
import com.beanu.arad.widget.pulltorefresh.PullToRefreshListView;
import com.xiaojiujiu.R;
import com.xiaojiujiu.dao.CouponListDao;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.CouponListAdapter;
import com.xiaojiujiu.ui.common.SelectorAreaWindow;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow.OnSelectedListener;
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

	private Button btn_shoptype;
	private Button btn_area;
	private Button btn_sort;
	private SelectorShopTypeWindow selectorShopTypeWindow;
	private SelectorAreaWindow selectorAreaWindow;
	private SelectorSortWindow selectorSortWindow;

	// private Map<String, String> param;
	CouponListDao dao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			selectorShopTypeWindow = new SelectorShopTypeWindow(getSherlockActivity());
			selectorAreaWindow = new SelectorAreaWindow(getSherlockActivity());
			selectorSortWindow = new SelectorSortWindow(getSherlockActivity());
			setSelectedWindowListener();

			dao = new CouponListDao();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.coupons_fragment, container, false);
		empty = (TextView) view.findViewById(R.id.empty);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listView);

		footerView = inflater.inflate(R.layout.pull_to_refresh_listview_footer_layout, null);
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

		mAdapter = new CouponListAdapter(getSherlockActivity(), dao.getCouponList());
		getListView().setAdapter(mAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Intent intent = new Intent(getSherlockActivity().getApplicationContext(), CouponsDetailActivity.class);
				intent.putExtra("id", dao.getCouponList().get(position).getItemID());
				startActivity(intent);
				UIUtil.intentSlidIn(getSherlockActivity());
			}
		});

		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setOnLastItemVisibleListener(this);
		if (getCurrentState(savedInstanceState) == FIRST_TIME_START) {
			pullToRefreshListView.setRefreshing(false);
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
		showFooterView();
		dao.nextPage(new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				dismissFooterView();
				mAdapter.notifyDataSetChanged();
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				dismissFooterView();
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}
		});

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		Log.d("onRefresh");

		dao.pulltorefresh(new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				mAdapter.notifyDataSetChanged();
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}

			@Override
			public void onFailure(Throwable t, String strMsg) {
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}
		});

	}

	// 初始化筛选框的事件
	private void setSelectedWindowListener() {
		selectorShopTypeWindow.setOnSelectedListener(new OnSelectedListener() {

			@Override
			public void onSelected(String selectedId, String selectedName) {
				btn_shoptype.setText(selectedName);
//				param.put("shopSecondCateID", selectedId);
//				updateData();
			}
		});

		selectorAreaWindow.setOnSelectedListener(new OnSelectedListener() {

			@Override
			public void onSelected(String selectedId, String selectedName) {
				btn_area.setText(selectedName);
//				param.put("businessDistrictID", selectedId);
//				updateData();
			}
		});

		selectorSortWindow.setOnSelectedListener(new OnSelectedListener() {

			@Override
			public void onSelected(String selectedId, String selectedName) {
				btn_sort.setText(selectedName);
//				param.put("orderType", selectedId);
//				updateData();
			}
		});
	}

	// 筛选数据
//	private void updateData() {
//		AjaxParams params = new AjaxParams(param);
//		Arad.http.get(Constant.URL_COUPON, params, new AjaxCallBack<String>() {
//
//			@Override
//			public void onStart() {
//
//			}
//
//			@Override
//			public void onSuccess(String t) {
//
//				try {
//					JSONObject response = new JSONObject(t);
//					String resCode = response.getString("resCode");
//					if (resCode != null && resCode.equals("1")) {
//						JSONArray jsonArray = response.getJSONArray("ItemList");
//
//						ArrayList<CouponItem> _list = new ArrayList<CouponItem>();
//						for (int i = 0; i < jsonArray.length(); i++) {
//							JSONObject item = jsonArray.getJSONObject(i);
//
//							CouponItem c = new CouponItem();
//							// c.setItemID(item.getInt("CouponID"));
//							c.setItemTitle(item.getString("ItemTitle"));
//							c.setItemDetail(item.getString("ItemDetail"));
//							c.setItemImageUrl(item.getString("ItemImageUrl"));
//							c.setDistance(item.getDouble("Distance"));
//							c.setItemType(item.getInt("ItemType"));
//							c.setItemAddress(item.getString("ItemAddress"));
//							_list.add(c);
//						}
//
//						// 如果有重复的去掉重复的，然后在加上最新的信息
//						mCouponList.clear();
//
//						mCouponList.addAll(0, _list);
//						mAdapter.notifyDataSetChanged();
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				} finally {
//					pullToRefreshListView.onRefreshComplete();
//					showListView(true);
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable t, String strMsg) {
//				pullToRefreshListView.onRefreshComplete();
//				showListView(true);
//			}
//
//		});
//
//	}
}
