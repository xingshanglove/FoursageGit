package com.lanjiaomao.foursage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by root on 2016/5/10.
 */
public class UserIconDescAdapter extends BaseAdapter {
    Context context;
    List<String> icons;

    public UserIconDescAdapter(Context context, List<String> icons) {
        this.context = context;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return icons.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_user_desc_icon, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position < icons.size())
            ImageLoader.getInstance().displayImage(icons.get(position), holder.iv_item_icon);
        else{
            holder.iv_item_icon.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_plus_big));
        }
        return convertView;
    }

    class ViewHolder {
        RoundImageView iv_item_icon;
        public ViewHolder(View view) {
            iv_item_icon= (RoundImageView) view.findViewById(R.id.iv_item_icon);
        }
    }
}
