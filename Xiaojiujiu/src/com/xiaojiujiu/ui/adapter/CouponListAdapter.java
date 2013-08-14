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
import com.xiaojiujiu.entity.CouponItem;

/**
 * 优惠信息列表Adapter
 * 
 * @author beanu
 * 
 */
public class CouponListAdapter extends BaseAdapter {

	public static final String CouponList = "couponlist";
	public static final String CouponListWithShop = "couponlistwithshop";

	private List<CouponItem> list;
	private LayoutInflater mlinflater;
	private String type;

	private IAdapter listener;
	private static final int VIEW_TYPE_ACTIVITY = 0;
	private static final int VIEW_TYPE_LOADING = 1;

	private class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView content;
		public TextView address;

		public ImageView tag1;
		public ImageView tag2;
		public ImageView tag3;
		public ImageView tag4;
	}

	public CouponListAdapter(Context context, List<CouponItem> data, String type, IAdapter listener) {
		this.mlinflater = LayoutInflater.from(context);
		this.list = data;
		this.type = type;
		this.listener = listener;
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
			CouponItem topic = list.get(position);
			if (view == null) {
				view = mlinflater.inflate(R.layout.coupon_list_item, null);
				ViewHolder vh = new ViewHolder();
				vh.title = (TextView) view.findViewById(R.id.coupon_list_item_title);
				vh.content = (TextView) view.findViewById(R.id.coupon_list_item_content);
				vh.img = (ImageView) view.findViewById(R.id.coupon_list_item_img);
				vh.address = (TextView) view.findViewById(R.id.coupon_list_item_address);

				vh.tag1 = (ImageView) view.findViewById(R.id.coupon_list_item_tag1);
				vh.tag2 = (ImageView) view.findViewById(R.id.coupon_list_item_tag2);
				vh.tag3 = (ImageView) view.findViewById(R.id.coupon_list_item_tag3);
				vh.tag4 = (ImageView) view.findViewById(R.id.coupon_list_item_tag4);
				view.setTag(vh);
			}

			ViewHolder holder = (ViewHolder) view.getTag();
			if (topic.getCouponTypeIconUrlList() != null) {
				int size = topic.getCouponTypeIconUrlList().size() > 4 ? 4 : topic.getCouponTypeIconUrlList().size();
				for (int i = 0; i < size; i++) {
					String url = topic.getCouponTypeIconUrlList().get(i);
					if (i == 0)
						Arad.imageLoader.display(url, holder.tag1);
					if (i == 1)
						Arad.imageLoader.display(url, holder.tag2);
					if (i == 2)
						Arad.imageLoader.display(url, holder.tag3);
					if (i == 3)
						Arad.imageLoader.display(url, holder.tag4);

				}
			}

			if (topic.getItemType() == 0) {
				// 单个优惠券
			} else if (topic.getItemType() == 0) {
				// 多个组合优惠券
			}

			Arad.imageLoader.display(topic.getItemImageUrl(), holder.img, R.drawable.default_img);
			holder.title.setText(topic.getItemTitle());
			holder.content.setText(topic.getItemDetail());
			if (type.equals(CouponListWithShop)) {
				holder.address.setVisibility(View.GONE);
			} else {
				holder.address.setText("地址：" + topic.getItemAddress());
			}
		}

		return view;
	}

	public void setData(List<CouponItem> list) {
		this.list = list;
	}

}
