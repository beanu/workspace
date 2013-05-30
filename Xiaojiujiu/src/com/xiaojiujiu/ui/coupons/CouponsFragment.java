package com.xiaojiujiu.ui.coupons;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.PopupWindow;
import com.beanu.arad.base.BaseFragment;
import com.xiaojiujiu.R;
import com.xiaojiujiu.ui.common.SelectorAreaWindow;

/**
 * 优惠券列表页面
 * 
 * @author beanu
 * 
 */
public class CouponsFragment extends BaseFragment implements OnClickListener {

	private Button distance;
	private Button citys;
	private Button order;

	public static CouponsFragment newInstance() {
		// Bundle args = new Bundle();
		// args.putString(TYPEID, typeId);
		// args.putInt(POSITION, position);
		CouponsFragment fragment = new CouponsFragment();
		// fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.coupons_fragment, container, false);
		distance = (Button) view.findViewById(R.id.distance);
		citys = (Button) view.findViewById(R.id.food);
		order = (Button) view.findViewById(R.id.array);

		distance.setOnClickListener(this);
		citys.setOnClickListener(this);
		order.setOnClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.distance:
			Intent intent = new Intent(getSherlockActivity(), SelectorAreaWindow.class);
			startActivity(intent);
			break;
		case R.id.food:
			break;
		case R.id.array:
			break;
		default:
			break;
		}
	}

}
