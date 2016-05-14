package com.lanjiaomao.foursage.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;

/**
 * Created by root on 2016/5/13.
 */
public class Info_item extends LinearLayout{

    private TextView tv_title;
    private EditText et_content;

    public Info_item(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_info_item, this);
        tv_title= (TextView) this.findViewById(R.id.tv_title);
        et_content= (EditText) this.findViewById(R.id.et_content);
        //初始化内容
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.Info_item);
        String title=ta.getString(R.styleable.Info_item_info_title);
        if(title!=null){
            tv_title.setText(title);
        }else{
            tv_title.setText("");
        }
       String content=ta.getString(R.styleable.Info_item_info_content);
        if(content!=null){
            et_content.setText(content);
        }else{
            et_content.setText("");
        }
    }
}
