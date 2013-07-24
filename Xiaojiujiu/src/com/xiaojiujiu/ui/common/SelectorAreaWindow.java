package com.xiaojiujiu.ui.common;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

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
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.Category;
import com.xiaojiujiu.ui.adapter.SelectorLeftAdapter;
import com.xiaojiujiu.ui.adapter.SelectorRightAdapter;
import com.xiaojiujiu.ui.common.SelectorShopTypeWindow.OnSelectedListener;

public class SelectorAreaWindow {

	private Context context;
	private PopupWindow popupWindow;
	private ListView listRight;
	private ListView listLeft;
	private LinearLayout layout;

	private SelectorRightAdapter rightAdapter;
	private SelectorLeftAdapter leftAdapter;

	List<String> leftData = new ArrayList<String>();
	private OnSelectedListener listener;
	private String curentName;

	public SelectorAreaWindow(Context ctx) {
		this.context = ctx;

		leftAdapter = new SelectorLeftAdapter(context, leftData);
		rightAdapter = new SelectorRightAdapter(context, AppHolder.getInstance().area, 0);
		initView();
		popupWindow = new PopupWindow(context);
		popupWindow.setWidth((int) context.getResources().getDimension(R.dimen.popupWindow_width));
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

		if (AppHolder.getInstance().area.size() == 0) {
			AjaxParams params = new AjaxParams();
			params.put("op", "district");
			params.put("cityid", "1");
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
		listLeft = (ListView) layout.findViewById(R.id.listLeft);
		listLeft.setAdapter(leftAdapter);
		listLeft.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				rightAdapter.setLeftPosition(position);
				rightAdapter.notifyDataSetChanged();

				if (position == 0) {
					dismissPopupwindow();
					// String parentId =
					// AppHolder.getInstance().area.get(0).getCategoryID() + "";
					String parentName = AppHolder.getInstance().area.get(0).getCategoryName();
					curentName = parentName;
					listener.onSelected(null, null, parentName);
				} else if (position == 1) {
					dismissPopupwindow();
					String selectedId = AppHolder.getInstance().area.get(1).getCategoryID() + "";
					String selectedName = AppHolder.getInstance().area.get(1).getCategoryName();
					curentName = selectedName;
					listener.onSelected("DISTANCE", selectedId, selectedName);
				}

			}
		});

		listRight = (ListView) layout.findViewById(R.id.listRight);
		listRight.setAdapter(rightAdapter);
		listRight.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismissPopupwindow();
				if (listener != null) {

					String selectedId = AppHolder.getInstance().area.get(rightAdapter.leftPoition)
							.getChildCategoryList().get(position).getCategoryID()
							+ "";
					String selectedName = AppHolder.getInstance().area.get(rightAdapter.leftPoition)
							.getChildCategoryList().get(position).getCategoryName();
					curentName=selectedName;
					if (rightAdapter.leftPoition == 1) {// 距离
						listener.onSelected("DISTANCE", selectedId, selectedName);
					} else {// 其他
						String parentId = AppHolder.getInstance().area.get(rightAdapter.leftPoition).getCategoryID()
								+ "";
						String parentName = AppHolder.getInstance().area.get(rightAdapter.leftPoition)
								.getCategoryName();

						if (position == 0) {
							curentName=parentName;
							listener.onSelected(parentId, null, parentName);
						} else {
							listener.onSelected(parentId, selectedId, selectedName);
						}
					}

				}

			}
		});

	}

	private void praseJson(String json) {

		try {
			JSONObject response = new JSONObject(json);
			String resCode = response.getString("resCode");
			if (resCode != null && resCode.equals("1")) {
				JSONArray jsonArray = response.getJSONArray("ShopTypeList");
				Category allCate = new Category();
				allCate.setCategoryID(0);
				allCate.setCategoryName("全部");
				allCate.setLetter("q");
				allCate.setChildCategoryList(new ArrayList<Category>());
				AppHolder.getInstance().area.add(allCate);

				addDistanceCategory();

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject item = jsonArray.getJSONObject(i);
					Category category = new Category();
					category.setCategoryID(item.getInt("CategoryID"));
					category.setCategoryName(item.getString("CategoryName"));
					category.setLetter(item.getString("Letter"));

					JSONArray child = item.getJSONArray("ChildCategoryList");
					List<Category> childList = new ArrayList<Category>();
					Category all = new Category();
					all.setCategoryID(item.getInt("CategoryID"));
					all.setCategoryName("全部");
					all.setLetter(item.getString("Letter"));
					childList.add(all);

					for (int j = 0; j < child.length(); j++) {
						JSONObject second = child.getJSONObject(j);
						Category c = new Category();
						c.setCategoryID(second.getInt("CategoryID"));
						c.setCategoryName(second.getString("CategoryName"));
						c.setLetter(second.getString("Letter"));
						childList.add(c);
					}
					category.setChildCategoryList(childList);
					AppHolder.getInstance().area.add(category);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void updateData() {
		for (Category c : AppHolder.getInstance().area) {
			leftData.add(c.getCategoryName());
		}
		leftAdapter.notifyDataSetChanged();
		rightAdapter.notifyDataSetChanged();
		listLeft.setItemChecked(1, true);
		listRight.setItemChecked(1, true);
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

	public String getCurentName() {
		return curentName;
	}

	private void addDistanceCategory() {
		// 加入距离记录
		Category distanceCategory = new Category();
		distanceCategory.setCategoryID(0);
		distanceCategory.setCategoryName("附近");
		distanceCategory.setLetter("q");
		// // 1000
		ArrayList<Category> list = new ArrayList<Category>();
		// Category distance1K = new Category();
		// distance1K.setCategoryID(1000);
		// distance1K.setCategoryName("1千米");
		// distance1K.setLetter("1");
		//
		// Category distance3K = new Category();
		// distance3K.setCategoryID(3000);
		// distance3K.setCategoryName("3千米");
		// distance3K.setLetter("3");
		//
		// Category distance5K = new Category();
		// distance5K.setCategoryID(5000);
		// distance5K.setCategoryName("5千米");
		// distance5K.setLetter("5");
		//
		// list.add(distance1K);
		// list.add(distance3K);
		// list.add(distance5K);
		distanceCategory.setChildCategoryList(list);
		AppHolder.getInstance().area.add(distanceCategory);
	}
}
