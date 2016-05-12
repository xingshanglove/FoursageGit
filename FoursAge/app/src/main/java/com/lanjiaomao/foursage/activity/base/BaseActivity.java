package com.lanjiaomao.foursage.activity.base;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.bean.User;

/**
 * Created by root on 2016/5/8.
 */
public class BaseActivity extends Activity{
    public  SharedPreferences spf;
    public SharedPreferences.Editor editor;
    public FragmentManager fragmentManager;
    public FragmentTransaction transaction;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spf=getSharedPreferences("config", Context.MODE_PRIVATE);
        editor=spf.edit();
        fragmentManager=getFragmentManager();
        transaction=fragmentManager.beginTransaction();


    }

}
