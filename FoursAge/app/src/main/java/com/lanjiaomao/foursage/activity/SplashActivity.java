package com.lanjiaomao.foursage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.utils.ValidateUtils;

/**
 * Created by root on 2016/5/7.
 */
public class SplashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        final Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                SplashActivity.this.finish();
            }
        },1000);

    }

}
