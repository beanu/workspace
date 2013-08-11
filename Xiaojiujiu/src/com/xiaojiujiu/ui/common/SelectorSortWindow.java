package com.xiaojiujiu.ui.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.beanu.arad.Arad;
import com.beanu.arad.http.AjaxCallBack;
import com.beanu.arad.http.AjaxParams;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.Category;
import com.xiaojiujiu.ui.adapter.SelectorLeftAdapter;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow.OnSelectedListener;

/**
 * 排序
 * 
 * @author beanu
 * 
 */
public class SelectorSortWindow {

	private Context context;
	private PopupWindow popupWindow;
	private ListView listLeft;
	private ListView listRight;
	private LinearLayout layout;

	private SelectorLeftAdapter leftAdapter;

	List<String> leftData = new ArrayList<String>();
	private OnSelectedListener listener;
	private String curentName;

	public SelectorSortWindow(Context ctx) {
		this.context = ctx;

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
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				dismissPopupwindow();

			}
		});

		if (AppHolder.getInstance().sort.size() == 0) {
			AjaxParams params = new AjaxParams();
			params.put("op", "orderby");
			Arad.http.get(Constant.URL_CATEGORY, params, new AjaxCallBack<String>() {

				@Override
				public void onSuccess(String t) {
					praseJson(t);
					updateData();
				}

				@Override
				public void onFailure(Throwable t,int errorNo , String strMsg) {

				}

			});
		} else {
			updateData();
		}
	}

	private void initView() {
		layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.selector_list_activity, null);
		layout.setLayoutParams(new LinearLayout.LayoutParams((int) (context.getResources().getDimension(
				R.dimen.popupWindow_width) / 2), (int) context.getResources().getDimension(R.dimen.popupWindow_height)));
		listLeft = (ListView) layout.findViewById(R.id.listLeft);
		listLeft.setAdapter(leftAdapter);
		listLeft.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismissPopupwindow();
				if (listener != null) {
					int parentId = AppHolder.getInstance().sort.get(position).getCategoryID();
					String name = AppHolder.getInstance().sort.get(position).getCategoryName();
					listener.onSelected(parentId + "", null, name);
					curentName=name;
				}

			}
		});

		listRight = (ListView) layout.findViewById(R.id.listRight);
		listRight.setVisibility(View.GONE);

	}

	public String getCurentName() {
		return curentName;
	}
	
	private void praseJson(String json) {

		try {
			JSONObject response = new JSONObject(json);
			String resCode = response.getString("resCode");
			if (resCode != null && resCode.equals("1")) {
				JSONArray jsonArray = response.getJSONArray("orderbyList");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
					Category category = new Category();
					category.setCategoryID(item.getInt("CategoryID"));
					category.setCategoryName(item.getString("CategoryName"));
					category.setLetter(item.getString("Letter"));
					AppHolder.getInstance().sort.add(category);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void updateData() {
		for (Category c : AppHolder.getInstance().sort) {
			leftData.add(c.getCategoryName());
		}
		leftAdapter.notifyDataSetChanged();
		listLeft.setItemChecked(0, true);
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
		if (listener != null) {
			listener.dismiss();
		}

	}

	public void setOnSelectedListener(OnSelectedListener listener) {
		this.listener = listener;
	}
}
