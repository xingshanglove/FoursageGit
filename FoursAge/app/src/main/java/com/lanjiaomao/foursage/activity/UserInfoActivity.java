package com.lanjiaomao.foursage.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.activity.base.BaseActivity;
import com.lanjiaomao.foursage.adapter.UserIconDescAdapter;
import com.lanjiaomao.foursage.view.HorizontalListView;
import com.lanjiaomao.foursage.view.MakeBlurImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2016/5/10.
 */
public class UserInfoActivity extends BaseActivity {
    private RelativeLayout rl_header;
    private ImageView iv_head_bg;
    private HorizontalListView gv_user_desc;
    private List<String> userIcons;
    private UserIconDescAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_1);
        Bitmap newBitmap = MakeBlurImage.fastblur(this, bitmap, 10);
        iv_head_bg = (ImageView) this.findViewById(R.id.iv_head_bg);
        iv_head_bg.setImageBitmap(newBitmap);

        gv_user_desc = (HorizontalListView) this.findViewById(R.id.gv_user_desc);

        userIcons = new ArrayList<>();
        userIcons.add("http://v.juhe.cn/movie/picurl?49843575");
        userIcons.add("http://v.juhe.cn/movie/picurl?49843594");
        userIcons.add("http://v.juhe.cn/movie/picurl?49843574");
        adapter=new UserIconDescAdapter(this,userIcons);
        gv_user_desc.setAdapter(adapter);
    }
}
