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

import com.beanu.arad.base.BaseFragment;
import com.beanu.arad.pulltorefresh.PullToRefreshBase;
import com.beanu.arad.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import com.beanu.arad.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.beanu.arad.pulltorefresh.PullToRefreshListView;
import com.beanu.arad.utils.Log;
import com.xiaojiujiu.R;
import com.xiaojiujiu.dao.ECardListDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.ECardListAdapter;
import com.xiaojiujiu.ui.adapter.IAdapter;
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
public class ECardListFragment extends BaseFragment implements OnClickListener, IAdapter {

	public static ECardListFragment newInstance(String typeId, int position) {
		// Bundle args = new Bundle();
		// args.putString(TYPEID, typeId);
		// args.putInt(POSITION, position);
		ECardListFragment fragment = new ECardListFragment();
		// fragment.setArguments(args);
		return fragment;
	}

	private PullToRefreshListView listView;
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
			selectorShopTypeWindow = new SelectorShopTypeWindow(getActivity());
			selectorAreaWindow = new SelectorAreaWindow(getActivity());
			selectorSortWindow = new SelectorSortWindow(getActivity());
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

		listView = (PullToRefreshListView) view.findViewById(R.id.ecard_listview);

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
		mAdapter = new ECardListAdapter(getActivity(), dao.getECardList(), this);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				Intent intent = new Intent(getActivity().getApplicationContext(), ECardDetailActivity_.class);
				Bundle b = new Bundle();
				b.putSerializable("item", dao.getECardList().get(position - 1));
				intent.putExtras(b);
				startActivity(intent);
				UIUtil.intentSlidIn(getActivity());
			}
		});

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

				Log.d("onRefresh");

				dao.pulltorefresh(new IDataListener<String>() {

					@Override
					public void onSuccess(String result) {
						mAdapter.notifyDataSetChanged();
						listView.onRefreshComplete();
						// showListView(true);
					}

					@Override
					public void onFailure(String result, Throwable t, String strMsg) {
						listView.onRefreshComplete();
						// showListView(true);
					}
				});

			}
		});

		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Log.d("onLastItemVisible");
				// showFooterView();
				dao.nextPage(new IDataListener<String>() {

					@Override
					public void onSuccess(String result) {
						mAdapter.notifyDataSetChanged();
					}

					@Override
					public void onFailure(String result, Throwable t, String strMsg) {
						mAdapter.notifyDataSetChanged();
					}
				});

			}
		});

		listView.setRefreshing(false);
		// pullToRefreshListView.setOnRefreshListener(this);
		// pullToRefreshListView.setOnLastItemVisibleListener(this);
		if (getCurrentState(savedInstanceState) == FIRST_TIME_START) {
			// pullToRefreshListView.setRefreshing(false);
			// showListView(false);
		} else {
			// showListView(true);
		}
	}

	IDataListener<String> listener = new IDataListener<String>() {

		@Override
		public void onSuccess(String result) {
			mAdapter.notifyDataSetChanged();
			listView.onRefreshComplete();
			// showListView(true);
		}

		@Override
		public void onFailure(String result, Throwable t, String strMsg) {
			dao.getECardList().clear();
			mAdapter.notifyDataSetChanged();
			listView.onRefreshComplete();
			// showListView(true);
		}
	};

	// 初始化筛选框的事件
	private void setSelectedWindowListener() {

		selectorShopTypeWindow.setOnSelectedListener(new OnSelectedListener() {
			@Override
			public void onSelected(String parentId, String selectedId, String selectedName) {
				btn_shoptype.setText(selectedName);
				// showListView(false);
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
				// showListView(false);
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
				// showListView(false);
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

	@Override
	public boolean hasMoreResults() {
		return dao.isNext();
	}

	@Override
	public boolean hasError() {
		return false;
	}
}
