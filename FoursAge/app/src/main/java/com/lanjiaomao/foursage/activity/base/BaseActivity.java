package com.lanjiaomao.foursage.activity.base;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;

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

    public int screenWidth;
    public int screenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spf=getSharedPreferences("config", Context.MODE_PRIVATE);
        editor=spf.edit();
        fragmentManager=getFragmentManager();
        transaction=fragmentManager.beginTransaction();

        screenWidth=getWindowManager().getDefaultDisplay().getWidth();
        screenHeight=getWindowManager().getDefaultDisplay().getHeight();

    }

    /**
     * SD卡是否可用
     * @return
     */
    public boolean isExternalEnable(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }
}
