package com.xiaojiujiu.ui.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.xiaojiujiu.dao.IDataListener;
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

	private FrameLayout layout;
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
		layout = (FrameLayout) view.findViewById(R.id.coupon_layout);
		layout.setForeground(getResources().getDrawable(R.drawable.popup_window_dim));
		layout.getForeground().setAlpha(0);

		empty = (TextView) view.findViewById(R.id.empty);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listView);

		footerView = inflater.inflate(R.layout.pull_to_refresh_listview_footer_layout, null);
		getListView().addFooterView(footerView);
		getListView().setHeaderDividersEnabled(false);
		getListView().setSelector(R.drawable.base_list_selector);
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

		mAdapter = new CouponListAdapter(getSherlockActivity(), dao.getCouponList(), CouponListAdapter.CouponList);
		getListView().setAdapter(mAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				int itemType = dao.getCouponList().get(position - 1).getItemType();
				if (itemType == 0) {
					// 进入详情页
					Intent intent = new Intent(getSherlockActivity().getApplicationContext(),
							CouponsDetailActivity_.class);
					Bundle b=new Bundle();
					b.putSerializable("item", dao.getCouponList().get(position - 1));
					intent.putExtras(b);
					startActivity(intent);

				} else {
					// 进入列表页
					Intent intent = new Intent(getSherlockActivity().getApplicationContext(),
							CouponsListWithShopActivity.class);
					intent.putExtra("id", dao.getCouponList().get(position - 1).getItemID());
					startActivity(intent);
				}
				UIUtil.intentSlidIn(getSherlockActivity());
			}
		});

		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setOnLastItemVisibleListener(this);
		if (getCurrentState(savedInstanceState) == FIRST_TIME_START) {
			pullToRefreshListView.setRefreshing(false);
			showListView(false);
		}else{
			showListView(true);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selector_shoptype:
			selectorShopTypeWindow.showPopupwindow(btn_shoptype);
			showDim();
			break;
		case R.id.selector_area:
			selectorAreaWindow.showPopupwindow(btn_area);
			showDim();
			break;
		case R.id.selector_sort:
			selectorSortWindow.showPopupwindow(btn_sort);
			showDim();
			break;
		default:
			break;
		}
	}

	@Override
	public void onLastItemVisible() {
		showFooterView();
		dao.nextPage(new IDataListener<String>() {

			@Override
			public void onSuccess(String result) {
				mAdapter.notifyDataSetChanged();
				dismissFooterView();
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}

			@Override
			public void onFailure(String result, Throwable t, String strMsg) {
				// TODO error
				dismissFooterView();
				pullToRefreshListView.onRefreshComplete();
				showListView(true);

			}
		});

	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		Log.d("onRefresh");

		dao.pulltorefresh(new IDataListener<String>() {

			@Override
			public void onSuccess(String result) {
				mAdapter.notifyDataSetChanged();
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}

			@Override
			public void onFailure(String result, Throwable t, String strMsg) {
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}
		});

	}

	// 初始化筛选框的事件
	private void setSelectedWindowListener() {

		final IDataListener<String> listener = new IDataListener<String>() {

			@Override
			public void onSuccess(String result) {
				mAdapter.notifyDataSetChanged();
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}

			@Override
			public void onFailure(String result, Throwable t, String strMsg) {
				pullToRefreshListView.onRefreshComplete();
				showListView(true);
			}
		};

		selectorShopTypeWindow.setOnSelectedListener(new OnSelectedListener() {
			@Override
			public void onSelected(String parentId, String selectedId, String selectedName) {
				btn_shoptype.setText(selectedName);
				dao.onClickShop(parentId, selectedId, listener);
			}

			@Override
			public void dismiss() {
				hideDim();
			}
		});

		selectorAreaWindow.setOnSelectedListener(new OnSelectedListener() {

			@Override
			public void onSelected(String parentId, String selectedId, String selectedName) {
				btn_area.setText(selectedName);
				dao.onClickArea(parentId, selectedId, listener);
			}

			@Override
			public void dismiss() {
				hideDim();
			}
		});

		selectorSortWindow.setOnSelectedListener(new OnSelectedListener() {

			@Override
			public void onSelected(String parentId, String selectedId, String selectedName) {
				btn_sort.setText(selectedName);
				dao.onClickSort(parentId, listener);
			}

			@Override
			public void dismiss() {
				hideDim();
			}
		});
	}

	/**
	 * 显示阴影
	 */
	private void showDim() {
		if (layout != null) {
			layout.getForeground().setAlpha(160);
		}
	}

	private void hideDim() {
		if (layout != null) {
			layout.getForeground().setAlpha(0);
		}
	}
}
