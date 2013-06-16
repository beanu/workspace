package com.xiaojiujiu.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaojiujiu.R;
import com.xiaojiujiu.entity.FreshNews;

/**
 * Created by beanu on 13-6-3.
 */
public class FreshNewsAdapter extends BaseAdapter {

    private List<FreshNews> list;
    private LayoutInflater inflater;

    private class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView content;
    }

    public FreshNewsAdapter(Context context, List<FreshNews> data) {
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
        final FreshNews freshNews = list.get(position);
        if (view == null) {
            view = inflater.inflate(R.layout.fresh_news_list_item, null);
            ViewHolder vh = new ViewHolder();
            vh.title = (TextView) view.findViewById(R.id.fresh_news_title);
            vh.content = (TextView) view.findViewById(R.id.fresh_news_content);
            vh.img = (ImageView) view.findViewById(R.id.fresh_news_img);
            view.setTag(vh);
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        // AppHolder.getInsatnce().imageLoader.DisplayImage(Constant.IMGPATH +
        // "/" + topic.getImgPath(), holder.img,
        // R.drawable.icon_default);
//        Arad.imageLoader.display(freshNews.get, holder.img, R.drawable.default_avatar);
        holder.title.setText(freshNews.getStoreName());
        holder.content.setText(freshNews.getContent());
        return view;
    }

}
