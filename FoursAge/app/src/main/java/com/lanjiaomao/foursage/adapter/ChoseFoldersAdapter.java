package com.lanjiaomao.foursage.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.bean.ImageBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by root on 2016/5/14.
 */
public class ChoseFoldersAdapter extends BaseAdapter{
    private Context context;
    private List<ImageBean> imageBeanList;
    public ChoseFoldersAdapter(Context context, List<ImageBean> imageBeanList){
        this.context=context;
        this.imageBeanList=imageBeanList;
    }
    @Override
    public int getCount() {
        return imageBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_iamges_folder,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.iv_folder_first.setImageBitmap(BitmapFactory.decodeFile(imageBeanList.get(position).getTopImagePath()));
        holder.tv_folder_count.setText(imageBeanList.get(position).getImageCount() + "张");
        holder.tv_folder_name.setText(imageBeanList.get(position).getFolderName());
        return convertView;
    }
    class ViewHolder{
        private ImageView iv_folder_first;
        private TextView tv_folder_count;
        private TextView tv_folder_name;
        public ViewHolder(View view){
            iv_folder_first= (ImageView) view.findViewById(R.id.iv_folder_first);
            tv_folder_count= (TextView) view.findViewById(R.id.tv_folder_count);
            tv_folder_name= (TextView) view.findViewById(R.id.tv_folder_name);
        }
    }
}
