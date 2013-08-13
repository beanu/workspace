package com.xiaojiujiu.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.beanu.arad.Arad;
import com.xiaojiujiu.R;
import com.xiaojiujiu.entity.ECardItem;

/**
 * 电子会员卡信息列表Adapter
 * 
 * @author beanu
 * 
 */
public class ECardListAdapter extends BaseAdapter {

	private List<ECardItem> list;
	private LayoutInflater mlinflater;
	private IAdapter listener;

	private static final int VIEW_TYPE_ACTIVITY = 0;
	private static final int VIEW_TYPE_LOADING = 1;

	private class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView address;
		public TextView score;
		public TextView useamount;
		public TextView discount;
	}

	public ECardListAdapter(Context context, List<ECardItem> data, IAdapter listener) {
		this.mlinflater = LayoutInflater.from(context);
		this.list = data;
		this.listener = listener;
	}

	public void setData(List<ECardItem> data) {
		if (data != null)
			this.list = data;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public int getCount() {
		if (list == null)
			return 0;
		if (listener == null)
			return list.size();
		else {
			if (list.size() == 0) {
				return 0;
			}

			return list.size() + (
			// show the status list row if...
					(listener.hasMoreResults() // ...or there's another
												// page
					|| listener.hasError()) // ...or there's an error
					? 1
							: 0);

		}

	}

	@Override
	public int getItemViewType(int position) {
		return (position >= list.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ACTIVITY;
	}

	@Override
	public Object getItem(int position) {
		return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? list.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? list.get(position).hashCode() : -1;
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {

		if (getItemViewType(position) == VIEW_TYPE_LOADING) {

			if (view == null) {
				view = mlinflater.inflate(R.layout.list_item_stream_status, viewGroup, false);
			}

			if (listener.hasError()) {
				view.findViewById(android.R.id.progress).setVisibility(View.GONE);
				((TextView) view.findViewById(android.R.id.text1)).setText("发生错误了");
			} else {
				view.findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
				((TextView) view.findViewById(android.R.id.text1)).setText("正在加载更多");
			}

			return view;

		} else {
			final ECardItem topic = list.get(position);
			if (view == null) {
				view = mlinflater.inflate(R.layout.ecard_list_item, null);
				ViewHolder vh = new ViewHolder();
				vh.title = (TextView) view.findViewById(R.id.ecard_list_item_title);
				vh.address = (TextView) view.findViewById(R.id.ecard_list_item_address);
				vh.img = (ImageView) view.findViewById(R.id.ecard_list_item_img);
				vh.score = (TextView) view.findViewById(R.id.ecard_list_item_score);
				vh.useamount = (TextView) view.findViewById(R.id.ecard_list_item_useamount);
				vh.discount = (TextView) view.findViewById(R.id.ecard_list_item_discount);
				view.setTag(vh);
			}

			ViewHolder holder = (ViewHolder) view.getTag();
			// AppHolder.getInsatnce().imageLoader.DisplayImage(Constant.IMGPATH
			// +
			// "/" + topic.getImgPath(), holder.img,
			// R.drawable.icon_default);
			Arad.imageLoader.display(topic.getItemImageUrl(), holder.img);
			holder.title.setText(topic.getItemTitle());
			holder.address.setText(topic.getItemAddress());
			// holder.score.setText(topic.get)
			holder.useamount.setText(String.valueOf(topic.getUseCount()));
			holder.discount.setText(topic.getAwardInfo());
		}

		return view;
	}
}
