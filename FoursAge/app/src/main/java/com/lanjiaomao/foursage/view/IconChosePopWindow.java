package com.lanjiaomao.foursage.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;

/**
 * Created by root on 2016/5/13.
 */
public class IconChosePopWindow extends PopupWindow implements View.OnClickListener {
    private TextView tv_scane;
    private TextView tv_take;
    private TextView tv_chose;
    private TextView tv_cancle;
    private View view;

    public static final String TYPE_ICON="type_icon";
    public static final String TYPE_DESC="type_desc";

    private onChoselistener onChoselistener;
    public interface onChoselistener{
        public void onChoseScane();
        public void onChoseTake();
        public void onChoseGallery();
        public void onChoseCancle();
    }


    public IconChosePopWindow(Activity context,String type,onChoselistener onChoselistener){
        this.onChoselistener=onChoselistener;
        view=View.inflate(context, R.layout.pop_chose_icon_type,null);
        initView(type);

        this.setContentView(view);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setWidth(w);
        this.setHeight(h);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(55000000);
        this.setBackgroundDrawable(dw);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        this.update();

    }

    private void initView(String type) {
        tv_scane= (TextView) view.findViewById(R.id.tv_scane);
        tv_scane.setOnClickListener(this);
        tv_take= (TextView) view.findViewById(R.id.tv_take);
        tv_take.setOnClickListener(this);
        tv_chose= (TextView) view.findViewById(R.id.tv_chose);
        tv_chose.setOnClickListener(this);
        tv_cancle= (TextView) view.findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(this);
        if(type.equals(TYPE_DESC)){
            tv_scane.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        switch (v.getId()){
            case R.id.tv_scane:
                onChoselistener.onChoseScane();
                break;
            case R.id.tv_take:
                onChoselistener.onChoseTake();
                break;
            case R.id.tv_chose:
                onChoselistener.onChoseGallery();
                break;
            case R.id.tv_cancle:
                onChoselistener.onChoseCancle();
                break;
        }
    }
}
