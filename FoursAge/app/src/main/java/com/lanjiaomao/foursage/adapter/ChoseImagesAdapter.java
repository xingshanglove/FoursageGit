package com.lanjiaomao.foursage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.bean.ImageBean;
import com.lanjiaomao.foursage.view.CheckImageView;

import java.util.List;

/**
 * Created by root on 2016/5/14.
 */
public class ChoseImagesAdapter extends BaseAdapter {
    private Context context;
    private List<String> images;

    public ChoseImagesAdapter(Context context, ImageBean imageBean) {
        this.context = context;
        this.images = imageBean.getImages();
        Log.v("--->", images.size() + "/" + images.get(0));
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_images, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.chk_imageview.setImageResource(images.get(position));
        return convertView;
    }

    class ViewHolder {
        CheckImageView chk_imageview;

        public ViewHolder(View view) {
            chk_imageview = (CheckImageView) view.findViewById(R.id.chk_imageview);
        }
    }
}
