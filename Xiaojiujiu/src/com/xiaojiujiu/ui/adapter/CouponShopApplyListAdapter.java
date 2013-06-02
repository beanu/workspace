package com.xiaojiujiu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.beanu.arad.utils.AndroidUtil;
import com.xiaojiujiu.R;
import com.xiaojiujiu.entity.CouponShop;

import java.util.List;

/**
 * 优惠券适用门店列表Adapter
 * 
 * @author beanu
 * 
 */
public class CouponShopApplyListAdapter extends BaseAdapter {

	private List<CouponShop> list;
	private LayoutInflater mlinflater;
	private Context context;

	private class ViewHolder {
		public TextView title;
		public TextView address;
		public TextView distance;
		public ImageView phoneImage;
	}

	public CouponShopApplyListAdapter(Context context, List<CouponShop> data) {
		this.context = context;
		this.mlinflater = LayoutInflater.from(context);
		this.list = data;
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
		final CouponShop shop = list.get(position);
		if (view == null) {
			view = mlinflater.inflate(R.layout.coupon_shop_apply_item, null);
			ViewHolder vh = new ViewHolder();
			vh.title = (TextView) view.findViewById(R.id.nearby_shop_name);
			vh.address = (TextView) view.findViewById(R.id.nearby_shop_address);
			vh.distance = (TextView) view.findViewById(R.id.nearby_shop_distance);
			vh.phoneImage = (ImageView) view.findViewById(R.id.nearby_shop_phone);
			view.setTag(vh);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		holder.title.setText(shop.getShopTitle());
		holder.address.setText(shop.getShopAdress());
		holder.distance.setText(shop.getShopDistance());
		holder.phoneImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AndroidUtil.dial(context, shop.getShopTel());
			}
		});

		return view;
	}
}
