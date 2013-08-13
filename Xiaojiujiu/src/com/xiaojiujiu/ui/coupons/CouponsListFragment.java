package com.xiaojiujiu.ui.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.dao.CouponListDao;
import com.xiaojiujiu.dao.IDataListener;
import com.xiaojiujiu.entity.Category;
import com.xiaojiujiu.ui.UIUtil;
import com.xiaojiujiu.ui.adapter.CouponListAdapter;
import com.xiaojiujiu.ui.common.SelectorAreaWindow;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow.OnSelectedListener;
import com.xiaojiujiu.ui.common.SelectorSortWindow;
import com.xiaojiujiu.ui.widget.dialog.CouponTypeDialogFragment;

/**
 * 优惠券列表页面
 * 
 * @author beanu
 * 
 */
public class CouponsListFragment extends BaseFragment implements OnClickListener {

	public static CouponsListFragment newInstance(String typeId, int position) {
		// Bundle args = new Bundle();
		// args.putString(TYPEID, typeId);
		// args.putInt(POSITION, position);
		CouponsListFragment fragment = new CouponsListFragment();
		// fragment.setArguments(args);
		return fragment;
	}

	private PullToRefreshListView listView;
	private CouponListAdapter mAdapter;

	private FrameLayout layout;
	private Button btn_shoptype;
	private Button btn_area;
	private Button btn_sort;
	private Button coupons_floating_btn;
	private SelectorShopTypeWindow selectorShopTypeWindow;
	private SelectorAreaWindow selectorAreaWindow;
	private SelectorSortWindow selectorSortWindow;

	private String couponTypeId = "";

	// private Map<String, String> param;
	CouponListDao dao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// if (savedInstanceState == null) {
		selectorShopTypeWindow = new SelectorShopTypeWindow(getActivity());
		selectorAreaWindow = new SelectorAreaWindow(getActivity());
		selectorSortWindow = new SelectorSortWindow(getActivity());
		setSelectedWindowListener();

		dao = new CouponListDao();
		dao.requestCouponType(new IDataListener<String>() {

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(String result, Throwable t, String strMsg) {

			}
		});
		// }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.coupons_fragment, container, false);
		layout = (FrameLayout) view.findViewById(R.id.coupon_layout);
		layout.setForeground(getResources().getDrawable(R.drawable.popup_window_dim));
		layout.getForeground().setAlpha(0);

		listView = (PullToRefreshListView) view.findViewById(R.id.coupon_listview);

		// 刷选
		btn_shoptype = (Button) view.findViewById(R.id.selector_shoptype);
		btn_area = (Button) view.findViewById(R.id.selector_area);
		btn_sort = (Button) view.findViewById(R.id.selector_sort);

		if (selectorShopTypeWindow.getCurentName() != null) {
			btn_shoptype.setText(selectorShopTypeWindow.getCurentName());
		}
		if (selectorAreaWindow.getCurentName() != null) {
			btn_area.setText(selectorAreaWindow.getCurentName());
		}
		if (selectorSortWindow.getCurentName() != null) {
			btn_sort.setText(selectorSortWindow.getCurentName());
		}

		coupons_floating_btn = (Button) view.findViewById(R.id.coupons_floating_btn);

		btn_shoptype.setOnClickListener(this);
		btn_area.setOnClickListener(this);
		btn_sort.setOnClickListener(this);
		coupons_floating_btn.setOnClickListener(this);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mAdapter = new CouponListAdapter(getActivity(), dao.getCouponList(), CouponListAdapter.CouponList);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				int itemType = dao.getCouponList().get(position - 1).getItemType();
				if (itemType == 0) {
					// 进入详情页
					Intent intent = new Intent(getActivity().getApplicationContext(), CouponsDetailActivity_.class);
					Bundle b = new Bundle();
					b.putSerializable("item", dao.getCouponList().get(position - 1));
					intent.putExtras(b);
					startActivity(intent);

				} else {
					// 进入列表页
					Intent intent = new Intent(getActivity().getApplicationContext(),
							CouponsListWithShopActivity_.class);
					intent.putExtra("id", dao.getCouponList().get(position - 1).getItemID());
					startActivity(intent);
				}
				UIUtil.intentSlidIn(getActivity());
			}
		});

		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				dao.pulltorefresh(new IDataListener<String>() {

					@Override
					public void onSuccess(String result) {
						mAdapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}

					@Override
					public void onFailure(String result, Throwable t, String strMsg) {
						listView.onRefreshComplete();
					}
				});
			}
		});

		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				dao.nextPage(new IDataListener<String>() {

					@Override
					public void onSuccess(String result) {
						mAdapter.notifyDataSetChanged();
						// listView.onBottomComplete();
						// listView.setHasMore(true);
					}

					@Override
					public void onFailure(String result, Throwable t, String strMsg) {
						// listView.setHasMore(false);
						// listView.onBottomComplete();
					}
				});

			}
		});

		// 开始手动loading
		listView.setRefreshing(false);
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
		case R.id.coupons_floating_btn:
			showCouponTypeDialog(getFragmentManager());
			break;
		default:
			break;
		}
	}

	IDataListener<String> listener = new IDataListener<String>() {

		@Override
		public void onSuccess(String result) {
			mAdapter.notifyDataSetChanged();
			listView.onRefreshComplete();

		}

		@Override
		public void onFailure(String result, Throwable t, String strMsg) {
			dao.getCouponList().clear();
			mAdapter.notifyDataSetChanged();
			listView.onRefreshComplete();
//			showListView(true);
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

	public void onCouponTypeSelected(String id) {
		// showListView(false);
		couponTypeId = id;
		if (id != null && id.equals(""))
			coupons_floating_btn.setBackgroundResource(R.drawable.floating_button);
		else if (id != null && !id.equals("")) {
			for (Category category : AppHolder.getInstance().couponType) {
				if (category.getCategoryID() == Integer.valueOf(id)) {
					coupons_floating_btn.setBackgroundResource(R.drawable.floating_empty_button);
					coupons_floating_btn.setText(category.getLetter());
					break;
				}
			}
		}

		dao.onClickCouponType(id, listener);
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

	private void showCouponTypeDialog(FragmentManager fm) {
		FragmentTransaction ft = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);
		ft.commit();
		CouponTypeDialogFragment dialog = CouponTypeDialogFragment.newInstance(couponTypeId);
		dialog.show(fm, "dialog");
	}
}
