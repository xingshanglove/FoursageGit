package com.lanjiaomao.foursage.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by root on 2016/5/7.
 */
public class Toasts {
    public static  void show(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void show(Context context,Integer msg){
        show(context,msg+"");
    }
}
