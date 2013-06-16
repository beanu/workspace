//package com.xiaojiujiu.ui.coupons;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//
//import com.beanu.arad.base.BaseFragment;
//import com.xiaojiujiu.R;
//import com.xiaojiujiu.ui.LeftMenuFragment;
//import com.xiaojiujiu.ui.common.SelectorAreaWindow;
//
///**
// * 优惠券列表页面
// * 
// * @author beanu
// * 
// */
//public class CouponsFragment extends BaseFragment implements OnClickListener {
//
//	private Button distance;
//	private Button citys;
//	private Button order;
//	private SelectorAreaWindow selectorAreaWindow;
//
//	public static CouponsFragment newInstance() {
//		// Bundle args = new Bundle();
//		// args.putString(TYPEID, typeId);
//		// args.putInt(POSITION, position);
//		CouponsFragment fragment = new CouponsFragment();
//		// fragment.setArguments(args);
//		return fragment;
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		if (savedInstanceState == null)
//			selectorAreaWindow = new SelectorAreaWindow(getSherlockActivity());
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.coupons_fragment, container, false);
//		distance = (Button) view.findViewById(R.id.distance);
//		citys = (Button) view.findViewById(R.id.food);
//		order = (Button) view.findViewById(R.id.array);
//
//		distance.setOnClickListener(this);
//		citys.setOnClickListener(this);
//		order.setOnClickListener(this);
//
//		return view;
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//		ft.replace(R.id.coupons_list_fragment, getCouponsListFragment(), LeftMenuFragment.class.getName());
//		ft.commit();
//
//	}
//
//	private CouponsListFragment getCouponsListFragment() {
//		CouponsListFragment fragment = ((CouponsListFragment) getChildFragmentManager().findFragmentByTag(
//				CouponsListFragment.class.getName()));
//		if (fragment == null) {
//			fragment = new CouponsListFragment();
//		}
//		return fragment;
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.distance:
//			// Intent intent = new Intent(getSherlockActivity(),
//			// SelectorAreaWindow.class);
//			// startActivity(intent);
//			selectorAreaWindow.showPopupwindow(distance);
//			break;
//		case R.id.food:
//			break;
//		case R.id.array:
//			break;
//		default:
//			break;
//		}
//	}
//
//}
