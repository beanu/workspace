package com.xiaojiujiu.ui.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xiaojiujiu.R;
import com.xiaojiujiu.ui.adapter.SelectorLeftAdapter;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow.OnSelectedListener;
import com.xiaojiujiu.ui.widget.Pull_ListView;

/**
 * 排序
 * 
 * @author beanu
 * 
 */
public class SelectorSortWindow {

	private Context context;
	private PopupWindow popupWindow;
	private Pull_ListView listLeft;
	private Pull_ListView listRight;
	private LinearLayout layout;

	private SelectorLeftAdapter leftAdapter;

	List<String> leftData = new ArrayList<String>();
	private OnSelectedListener listener;

	public SelectorSortWindow(Context ctx) {
		this.context = ctx;

		leftData.add("离我最近");
		leftData.add("最新发布");
		leftData.add("人气最高");

		leftAdapter = new SelectorLeftAdapter(context, leftData);

		initView();
		popupWindow = new PopupWindow(context);
		popupWindow.setWidth((int) (context.getResources().getDimension(R.dimen.popupWindow_width) / 2));
		popupWindow.setHeight((int) context.getResources().getDimension(R.dimen.popupWindow_height));
		popupWindow.setContentView(layout);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
	}

	private void initView() {
		layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.selector_list_activity, null);
		layout.setLayoutParams(new LinearLayout.LayoutParams((int) (context.getResources().getDimension(
				R.dimen.popupWindow_width) / 2), (int) context.getResources().getDimension(R.dimen.popupWindow_height)));
		listLeft = (Pull_ListView) layout.findViewById(R.id.listLeft);
		listLeft.setAdapter(leftAdapter);
		listLeft.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismissPopupwindow();
				if (listener != null) {
					listener.onSelected(position + "", null, leftData.get(position));
				}

			}
		});

		listRight = (Pull_ListView) layout.findViewById(R.id.listRight);
		listRight.setVisibility(View.GONE);

	}

	/**
	 * 显示浮动框，筛选器
	 */
	public void showPopupwindow(View view) {
		if (popupWindow != null) {
			if (popupWindow.isShowing())
				popupWindow.dismiss();
			else {
				popupWindow.showAsDropDown(view);
			}

		}
	}

	public void dismissPopupwindow() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}

	}

	public void setOnSelectedListener(OnSelectedListener listener) {
		this.listener = listener;
	}
}
