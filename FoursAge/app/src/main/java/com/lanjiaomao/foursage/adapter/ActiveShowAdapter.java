package com.lanjiaomao.foursage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.bean.Active;

import java.util.List;

/**
 * Created by root on 2016/5/10.
 */
public class ActiveShowAdapter extends BaseAdapter{
    List<Active> activeList;
    Context context;
    public ActiveShowAdapter(Context context,List<Active> activeList){
        this.context=context;
        this.activeList=activeList;
    }
    @Override
    public int getCount() {
        return 5;
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
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_active_show,null);
        }
        return convertView;
    }
    class ViewHodler{

    }
}
