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
import com.xiaojiujiu.entity.EntityCard;

/**
 * 实体卡列表Adapter
 * 
 * @author beanu
 * 
 */
public class EntityCardListAdapter extends BaseAdapter {

	private List<EntityCard> list;
	private LayoutInflater inflater;

	private class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView content;
	}

	public EntityCardListAdapter(Context context, List<EntityCard> data) {
		this.inflater = LayoutInflater.from(context);
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
		final EntityCard card = list.get(position);
		if (view == null) {
			view = inflater.inflate(R.layout.entity_card_list_item, null);
			ViewHolder vh = new ViewHolder();
			vh.title = (TextView) view.findViewById(R.id.entity_card_title);
			vh.content = (TextView) view.findViewById(R.id.entity_card_content);
			vh.img = (ImageView) view.findViewById(R.id.entity_card_imageView);
			view.setTag(vh);
		}

		ViewHolder holder = (ViewHolder) view.getTag();
		// AppHolder.getInsatnce().imageLoader.DisplayImage(Constant.IMGPATH +
		// "/" + topic.getImgPath(), holder.img,
		// R.drawable.icon_default);
		Arad.imageLoader.display(card.getImgPath(), holder.img, R.drawable.default_avatar);
		holder.title.setText(card.getName());
		holder.content.setText(card.getValidPeriod());
		return view;
	}
}
