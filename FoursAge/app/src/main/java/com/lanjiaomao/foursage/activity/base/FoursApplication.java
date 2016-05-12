package com.lanjiaomao.foursage.activity.base;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lanjiaomao.foursage.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.smssdk.SMSSDK;

/**
 * Created by root on 2016/5/8.
 */
public class FoursApplication extends Application{
    static RequestQueue queue;
    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, "fd9dd6fa1e48", "2fce8b4ece4d882eec24ecd83a139912");
        Drawable drawable=getResources().getDrawable(R.drawable.ic_default);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable)
                .showImageOnFail(drawable).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                        //.displayer(new RoundedBitmapDisplayer(25))
                .cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
        queue= Volley.newRequestQueue(this);
    }
    public static RequestQueue getRequestQueue(){
        return queue;
    }
}
