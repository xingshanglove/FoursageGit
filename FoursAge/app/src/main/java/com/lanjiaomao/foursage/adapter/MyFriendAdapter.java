package com.lanjiaomao.foursage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.bean.Friend;
import com.lanjiaomao.foursage.view.CircleImageView;

import java.util.List;

/**
 * Created by root on 2016/5/9.
 */
public class MyFriendAdapter extends BaseAdapter{
    private List<Friend> friendList;
    private Context context;
    public MyFriendAdapter(Context context, List<Friend> friendList) {
        this.friendList=friendList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_myfriend,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Friend friend=friendList.get(position);
        holder.userName.setText(friend.getName());
        return convertView;
    }
    class ViewHolder{
        CircleImageView userIcon;
        TextView userName;
        public ViewHolder(View view){
            userIcon= (CircleImageView) view.findViewById(R.id.iv_usericon);
            userName= (TextView) view.findViewById(R.id.tv_username);
        }
    }
}
