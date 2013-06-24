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
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.beanu.arad.Arad;
import com.xiaojiujiu.AppHolder;
import com.xiaojiujiu.R;
import com.xiaojiujiu.base.Constant;
import com.xiaojiujiu.entity.Category;
import com.xiaojiujiu.ui.adapter.SelectorLeftAdapter;
import com.xiaojiujiu.ui.adapter.SelectorRightAdapter;
import com.xiaojiujiu.ui.widget.Pull_ListView;

public class SelectorShopTypeWindow {

	private Context context;
	private PopupWindow popupWindow;
	private Pull_ListView listRight;
	private Pull_ListView listLeft;
	private LinearLayout layout;

	private SelectorRightAdapter rightAdapter;
	private SelectorLeftAdapter leftAdapter;

	List<String> leftData = new ArrayList<String>();
	private OnSelectedListener listener;

	/**
	 * 选择事件
	 * 
	 * @author beanu
	 * 
	 */
	public interface OnSelectedListener {
		public void onSelected(String parentId, String selectedId, String selectedName);

		public void dismiss();
	}

	public SelectorShopTypeWindow(Context ctx) {
		this.context = ctx;

		if (AppHolder.getInstance().shopType.size() == 0) {
			AjaxParams params = new AjaxParams();
			params.put("op", "shop");
			Arad.http.get(Constant.URL_CATEGORY, params, new AjaxCallBack<String>() {

				@Override
				public void onSuccess(String t) {
					praseJson(t);
					updateData();
				}

				@Override
				public void onFailure(Throwable t, String strMsg) {

				}

			});
		} else {
			updateData();
		}

		leftAdapter = new SelectorLeftAdapter(context, leftData);
		rightAdapter = new SelectorRightAdapter(context, AppHolder.getInstance().shopType, 0);
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
	}

	private void initView() {
		layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.selector_list_activity, null);
		listLeft = (Pull_ListView) layout.findViewById(R.id.listLeft);
		listLeft.setAdapter(leftAdapter);
		listLeft.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// leftAdapter.notifyDataSetInvalidated();
				rightAdapter.setLeftPosition(position);
				rightAdapter.notifyDataSetChanged();

			}
		});

		listRight = (Pull_ListView) layout.findViewById(R.id.listRight);
		listRight.setAdapter(rightAdapter);
		listRight.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismissPopupwindow();
				if (listener != null) {
					String selectedId = AppHolder.getInstance().shopType.get(rightAdapter.leftPoition)
							.getChildCategoryList().get(position).getCategoryID()
							+ "";
					String parentId = AppHolder.getInstance().shopType.get(rightAdapter.leftPoition).getCategoryID()
							+ "";
					String selectedName = AppHolder.getInstance().shopType.get(rightAdapter.leftPoition)
							.getChildCategoryList().get(position).getCategoryName();
					String parentName = AppHolder.getInstance().shopType.get(rightAdapter.leftPoition)
							.getCategoryName();

					if (position == 0) {
						listener.onSelected(parentId, null, parentName);
					} else {
						listener.onSelected(parentId, selectedId, selectedName);
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
					AppHolder.getInstance().shopType.add(category);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void updateData() {
		for (Category c : AppHolder.getInstance().shopType) {
			leftData.add(c.getCategoryName());
		}
		leftAdapter.notifyDataSetChanged();
		rightAdapter.notifyDataSetChanged();
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
