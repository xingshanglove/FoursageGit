package com.lanjiaomao.foursage.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;

/**
 * Created by root on 2016/5/14.
 */
public class CheckImageView extends RelativeLayout {
    private ImageView iv_image;
    private ImageView iv_check_status;
    private TextView tv_checked_count;

    public CheckImageView(Context context) {
        super(context);
    }

    public CheckImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.checked_imageview, this);
        iv_image = (ImageView) this.findViewById(R.id.iv_image);
        iv_check_status = (ImageView) this.findViewById(R.id.iv_check_status);
        tv_checked_count = (TextView) this.findViewById(R.id.tv_checked_count);
    }

    public void setChecked(boolean checked) {
        if (checked == Boolean.TRUE) {
            iv_check_status.setImageResource(R.drawable.circle_check);
        } else {
            iv_check_status.setImageResource(R.drawable.circle_uncheck);
            tv_checked_count.setText("");
        }
    }

    public void setCount(int count) {
        tv_checked_count.setText(count + "");
    }

    public void setImageResource(String path) {
        if (iv_image != null)
            iv_image.setImageBitmap(BitmapFactory.decodeFile(path));
    }
}
