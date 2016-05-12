package com.lanjiaomao.foursage.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.activity.base.BaseActivity;
import com.lanjiaomao.foursage.utils.EncodeZxing;

/**
 * Created by root on 2016/5/10.
 */
public class ZxingActivity extends BaseActivity {
    ImageView iv_zxing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        iv_zxing = (ImageView) this.findViewById(R.id.iv_zxing);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/storage/icon_1.jpg";
        Bitmap zBitmap = EncodeZxing.createBitmap("18149184677", path);
        if (zBitmap != null)
            iv_zxing.setImageBitmap(zBitmap);
        else{
            //
        }
    }
}
