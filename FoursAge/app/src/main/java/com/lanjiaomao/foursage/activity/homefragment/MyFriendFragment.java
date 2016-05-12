package com.lanjiaomao.foursage.activity.homefragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.adapter.MyFriendAdapter;
import com.lanjiaomao.foursage.bean.Friend;
import com.lanjiaomao.foursage.zxing.android.CaptureActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2016/5/9.
 */
public class MyFriendFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_title;
    private ListView lv_myfriend;
    private List<Friend> friendList;
    private ImageView iv_add_friend;

    MyFriendAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_myfriend_fragment, null);
        initView(view);
        initFriends();
        return view;
    }

    private void initFriends() {
        friendList = new ArrayList<>();

        Friend f1 = new Friend("张三", "张三");
        Friend f2 = new Friend("张四", "张四");
        Friend f3 = new Friend("照亮", "照亮");
        Friend f4 = new Friend("王五", "王五");
        friendList.add(f1);
        friendList.add(f2);
        friendList.add(f3);
        friendList.add(f4);

        adapter = new MyFriendAdapter(getActivity(), friendList);
        lv_myfriend.setAdapter(adapter);
    }

    private void initView(View view) {
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("我的小伙伴");
        tv_title.setOnClickListener(this);
        lv_myfriend = (ListView) view.findViewById(R.id.lv_myfriend);

        iv_add_friend= (ImageView) view.findViewById(R.id.iv_add_friend);
        iv_add_friend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_title:
                Intent intent = new Intent();
                intent.setAction("com.homeactivity.opendrawler");
                getActivity().sendBroadcast(intent);
                break;
            case R.id.iv_add_friend:
                startActivityForResult(new Intent(getActivity(), CaptureActivity.class),1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==getActivity().RESULT_OK){
            String content=data.getStringExtra("codedContent");
        }
    }
}
