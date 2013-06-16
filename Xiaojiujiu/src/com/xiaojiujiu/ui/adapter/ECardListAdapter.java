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
import com.xiaojiujiu.entity.ECard;

/**
 * 电子会员卡信息列表Adapter
 * 
 * @author beanu
 * 
 */
public class ECardListAdapter extends BaseAdapter {

	private List<ECard> list;
	private LayoutInflater mlinflater;

	private class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView content;
	}

	public ECardListAdapter(Context context, List<ECard> data) {
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
		final ECard topic = list.get(position);
		if (view == null) {
			view = mlinflater.inflate(R.layout.ecard_list_item, null);
			ViewHolder vh = new ViewHolder();
			vh.title = (TextView) view.findViewById(R.id.ecard_list_item_title);
			vh.content = (TextView) view.findViewById(R.id.ecard_list_item_address);
			vh.img = (ImageView) view.findViewById(R.id.ecard_list_item_img);
			view.setTag(vh);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		// AppHolder.getInsatnce().imageLoader.DisplayImage(Constant.IMGPATH +
		// "/" + topic.getImgPath(), holder.img,
		// R.drawable.icon_default);
		Arad.imageLoader.display(topic.getSmallImageUrl(), holder.img, R.drawable.ic_launcher);
		holder.title.setText(topic.geteCardTitle());
		holder.content.setText(topic.geteCardDesc());
		return view;
	}
}
