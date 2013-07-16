package com.xiaojiujiu.ui.ecard;

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
import com.xiaojiujiu.dao.ECardListDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.ECardListAdapter;
import com.xiaojiujiu.ui.common.SelectorAreaWindow;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow.OnSelectedListener;
import com.xiaojiujiu.ui.common.SelectorSortWindow;

/**
 * 电子会员卡列表页面
 * 
 * @author beanu
 * 
 */
public class ECardFListragment extends PullToRefreshListFragment implements OnRefreshListener<ListView>,
		OnLastItemVisibleListener, OnClickListener {

	public static ECardFListragment newInstance(String typeId, int position) {
		// Bundle args = new Bundle();
		// args.putString(TYPEID, typeId);
		// args.putInt(POSITION, position);
		ECardFListragment fragment = new ECardFListragment();
		// fragment.setArguments(args);
		return fragment;
	}

	private ECardListAdapter mAdapter;
	// private List<ECard> mECardList = new ArrayList<ECard>();

	private FrameLayout layout;
	private Button btn_shoptype;
	private Button btn_area;
	private Button btn_sort;
	private SelectorShopTypeWindow selectorShopTypeWindow;
	private SelectorAreaWindow selectorAreaWindow;
	private SelectorSortWindow selectorSortWindow;

	ECardListDao dao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			selectorShopTypeWindow = new SelectorShopTypeWindow(getSherlockActivity());
			selectorAreaWindow = new SelectorAreaWindow(getSherlockActivity());
			selectorSortWindow = new SelectorSortWindow(getSherlockActivity());
			setSelectedWindowListener();

			dao = new ECardListDao();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.ecard_list_fragment, container, false);

		layout = (FrameLayout) view.findViewById(R.id.ecard_layout);
		layout.setForeground(getResources().getDrawable(R.drawable.popup_window_dim));
		layout.getForeground().setAlpha(0);

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
		mAdapter = new ECardListAdapter(getSherlockActivity(), dao.getECardList());
		getListView().setAdapter(mAdapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Intent intent = new Intent(getSherlockActivity().getApplicationContext(), ECardDetailActivity_.class);
				Bundle b = new Bundle();
				b.putSerializable("item", dao.getECardList().get(position - 1));
				intent.putExtras(b);
				startActivity(intent);
				UIUtil.intentSlidIn(getSherlockActivity());
			}
		});

		pullToRefreshListView.setOnRefreshListener(this);
		pullToRefreshListView.setOnLastItemVisibleListener(this);
		if (getCurrentState(savedInstanceState) == FIRST_TIME_START) {
			pullToRefreshListView.setRefreshing(false);
			showListView(false);
		} else {
			showListView(true);
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

	IDataListener<String> listener = new IDataListener<String>() {

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

	// 初始化筛选框的事件
	private void setSelectedWindowListener() {

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
