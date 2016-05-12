package com.lanjiaomao.foursage.activity.homefragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;

/**
 * Created by root on 2016/5/9.
 */
public class CreateActiveFragment extends Fragment implements View.OnClickListener {
    private TextView tv_title;
    private ImageView iv_back;
    private RelativeLayout rl_choice_more;
    private RelativeLayout rl_choice_run;
    private RelativeLayout rl_choice_yumaoqiu;
    private RelativeLayout rl_choice_wangqiu;
    private RelativeLayout rl_choice_tennis;
    private RelativeLayout rl_choice_study;
    private Handler handler;
    public static CreateActiveFragment createActiveFragment;

    public static CreateActiveFragment getCreateFragment() {
        if (createActiveFragment != null) {
            return createActiveFragment;
        } else {
            createActiveFragment = new CreateActiveFragment();
            return createActiveFragment;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_createactive_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("创建活动");
        tv_title.setOnClickListener(this);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        rl_choice_more = (RelativeLayout) view.findViewById(R.id.rl_choice_more);
        rl_choice_more.setOnClickListener(this);
        rl_choice_run = (RelativeLayout) view.findViewById(R.id.rl_choice_run);
        rl_choice_run.setOnClickListener(this);
        rl_choice_yumaoqiu = (RelativeLayout) view.findViewById(R.id.rl_choice_yumaoqiu);
        rl_choice_yumaoqiu.setOnClickListener(this);
        rl_choice_wangqiu = (RelativeLayout) view.findViewById(R.id.rl_choice_wangqiu);
        rl_choice_wangqiu.setOnClickListener(this);
        rl_choice_tennis = (RelativeLayout) view.findViewById(R.id.rl_choice_tennis);
        rl_choice_tennis.setOnClickListener(this);
        rl_choice_study = (RelativeLayout) view.findViewById(R.id.rl_choice_study);
        rl_choice_study.setOnClickListener(this);
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

        }
    }
}
