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

	public CouponListAdapter(Context context, List<CouponItem> data, String type) {
		this.mlinflater = LayoutInflater.from(context);
		this.list = data;
		this.type = type;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return Integer.valueOf(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewGroup) {
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
					Arad.imageLoader.display(url, holder.tag1,R.drawable.transparent);
				if (i == 1)
					Arad.imageLoader.display(url, holder.tag2,R.drawable.transparent);
				if (i == 2)
					Arad.imageLoader.display(url, holder.tag3,R.drawable.transparent);
				if (i == 3)
					Arad.imageLoader.display(url, holder.tag4,R.drawable.transparent);

			}
		}

		if (topic.getItemType() == 0) {
			// 单个优惠券
		} else if (topic.getItemType() == 0) {
			// 多个组合优惠券
		}

		Arad.imageLoader.display(topic.getItemImageUrl(), holder.img,R.drawable.default_img);
		holder.title.setText(topic.getItemTitle());
		holder.content.setText(topic.getItemDetail());
		if (type.equals(CouponListWithShop)) {
			holder.address.setVisibility(View.GONE);
		} else {
			holder.address.setText("地址：" + topic.getItemAddress());
		}

		return view;
	}

	public void setData(List<CouponItem> list) {
		this.list = list;
	}
	
	
}
