package com.xiaojiujiu.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaojiujiu.R;
import com.xiaojiujiu.entity.Category;

public class SelectorRightAdapter extends BaseAdapter {

	Context context;
	LayoutInflater layoutInflater;
	// String[][] data;
	List<Category> list;
	public int leftPoition;

	public SelectorRightAdapter(Context context, List<Category> list, int leftPoition) {
		this.context = context;
		this.list = list;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.leftPoition = leftPoition;
	}

	public void setLeftPosition(int position) {
		this.leftPoition = position;
	}

	@Override
	public int getCount() {
		if (list.size()==0)
			return 0;
		return list.get(leftPoition).getChildCategoryList().size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(leftPoition).getChildCategoryList().get(position).getCategoryName();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.selector_list_right_item, null);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView.findViewById(R.id.selector_list_right_item_textView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText((String) getItem(position));
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
	}

}
