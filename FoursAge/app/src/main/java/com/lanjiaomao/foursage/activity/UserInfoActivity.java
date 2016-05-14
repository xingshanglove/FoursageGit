package com.lanjiaomao.foursage.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.activity.base.BaseActivity;
import com.lanjiaomao.foursage.adapter.UserIconDescAdapter;
import com.lanjiaomao.foursage.bean.Contacts;
import com.lanjiaomao.foursage.utils.Toasts;
import com.lanjiaomao.foursage.view.CircleImageView;
import com.lanjiaomao.foursage.view.HorizontalListView;
import com.lanjiaomao.foursage.view.IconChosePopWindow;
import com.lanjiaomao.foursage.view.MakeBlurImage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2016/5/10.
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private RelativeLayout rl_header;
    private ImageView iv_head_bg;
    private HorizontalListView gv_user_desc;
    private List<String> userIcons;
    private UserIconDescAdapter adapter;

    private CircleImageView iv_user_icon;


    private static final int TAKEPICTURE_ICON_CODE = 1;
    private static final int TAKEPICTURE_DESC_CODE = 2;
    private static final int CHOSEPICTURE_ICON_CODE = 3;
    private static final int CHOSEPICTURE_DESC_CODE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        intiView();

        initUserInfo();
    }

    private void initUserInfo() {
        //背景模糊图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_1);
        Bitmap newBitmap = MakeBlurImage.fastblur(this, bitmap, 10);
        iv_head_bg.setImageBitmap(newBitmap);
        //个人图像列表
        userIcons = new ArrayList<>();
        userIcons.add("http://v.juhe.cn/movie/picurl?49843575");
        userIcons.add("http://v.juhe.cn/movie/picurl?49843594");
        userIcons.add("http://v.juhe.cn/movie/picurl?49843575");
        userIcons.add("http://v.juhe.cn/movie/picurl?49843575");
        userIcons.add("http://v.juhe.cn/movie/picurl?49843575");
        adapter = new UserIconDescAdapter(this, userIcons);
        gv_user_desc.setAdapter(adapter);


    }

    private void intiView() {
        iv_head_bg = (ImageView) this.findViewById(R.id.iv_head_bg);
        gv_user_desc = (HorizontalListView) this.findViewById(R.id.gv_user_desc);
        gv_user_desc.setOnItemClickListener(this);
        iv_user_icon = (CircleImageView) this.findViewById(R.id.iv_user_icon);
        iv_user_icon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user_icon:
                //查看高清头像或者重新选择头像
                choseTheType(v);
                break;
        }
    }

    /**
     * 头像选择事件
     *
     * @param view
     */
    private void choseTheType(final View view) {
        IconChosePopWindow chosePopWindow = new IconChosePopWindow(UserInfoActivity.this, IconChosePopWindow.TYPE_ICON, new IconChosePopWindow.onChoselistener() {
            @Override
            public void onChoseScane() {
                //查看高清大图
                lookBigImage(view, "http://v.juhe.cn/movie/picurl?49843575");
            }

            @Override
            public void onChoseTake() {
                //拍照
                Intent tIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(tIntent, TAKEPICTURE_ICON_CODE);
            }

            @Override
            public void onChoseGallery() {
                //相册选择
                Intent cIntent = new Intent(UserInfoActivity.this, ChosePictureActivity.class);
                cIntent.putExtra("type", Contacts.CHOSE_PICTURE_FOR_ICON);
                startActivityForResult(cIntent, CHOSEPICTURE_ICON_CODE);
            }

            @Override
            public void onChoseCancle() {
                //取消
            }
        });
        chosePopWindow.showAtLocation(view, Gravity.TOP, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap resultBitmap = (Bitmap) data.getExtras().get("data");
            if (requestCode == TAKEPICTURE_ICON_CODE) {
                //此图片用于设置用户的头像
                iv_user_icon.setImageBitmap(resultBitmap);
                //将图片保存到本地
                saveToLocal(resultBitmap);
            } else if (requestCode == TAKEPICTURE_DESC_CODE) {
                //将此图片添加到用户描述

            }
        }
    }

    /**
     * 头像上传到服务器
     *
     * @param icon_file 头像文件
     */
    private void upLoadToServer(File icon_file) {

    }

    /**
     * 将头像保存到本地
     *
     * @param resultBitmap
     */
    private void saveToLocal(Bitmap resultBitmap) {
        File icon_file = null;
        String flag = spf.getString(Contacts.USER_PHONE, "18149184677") + ".jpg";
        if (isExternalEnable()) {
            //如果外部存储卡可用
            Log.v("-->", Contacts.USER_ICON_EXTERNAL_PATH + flag);
            File pathFile = new File(Contacts.USER_ICON_EXTERNAL_PATH);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            icon_file = new File(Contacts.USER_ICON_EXTERNAL_PATH + flag);
        } else {
            File pathFile = new File(Contacts.USER_ICON_INNER_PATH);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            //外部SD卡不可得
            icon_file = new File(Contacts.USER_ICON_INNER_PATH + flag);
        }
        if (icon_file == null)
            return;
        if (!icon_file.exists()) {
            try {
                icon_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            icon_file.delete();
            try {
                icon_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(icon_file));
                 /* 采用压缩转档方法 */
            resultBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                /* 调用flush()方法，更新BufferStream */
            bos.flush();
                /* 结束OutputStream */
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        upLoadToServer(icon_file);
    }

    public void lookBigImage(View view, String url) {
        final PopupWindow pop = new PopupWindow(UserInfoActivity.this);
        pop.setWidth(screenWidth);
        pop.setHeight(screenHeight);
        ImageView img = new ImageView(UserInfoActivity.this);
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ImageLoader.getInstance().displayImage(url, img);
        pop.setContentView(img);
        pop.showAtLocation(view, Gravity.TOP, 0, 0);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop != null && pop.isShowing())
                    pop.dismiss();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (userIcons.size() == position) {
            //点击了添加按钮
            if (adapter.isAddPossibile()) {
                //执行选择图片事件
                choseEvent(view);
            }
        } else {
            //显示大图
            lookBigImage(view, userIcons.get(position));
        }
    }

    /**
     * 用户添加个人相册信息
     *
     * @param view
     */
    private void choseEvent(final View view) {
        IconChosePopWindow chosePopWindow = new IconChosePopWindow(UserInfoActivity.this, IconChosePopWindow.TYPE_DESC, new IconChosePopWindow.onChoselistener() {
            @Override
            public void onChoseScane() {
                //@null
            }

            @Override
            public void onChoseTake() {
                //拍照
                Intent tIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(tIntent, TAKEPICTURE_DESC_CODE);
            }

            @Override
            public void onChoseGallery() {
                //相册选择
                Intent cIntent = new Intent(UserInfoActivity.this, ChosePictureActivity.class);
                cIntent.putExtra("type", Contacts.CHOSE_PICTURE_FOR_DESC);
                if (10 - userIcons.size() > 0)
                    cIntent.putExtra("maxCount", 10 - userIcons.size());
                else
                    return ;
                startActivityForResult(cIntent, CHOSEPICTURE_ICON_CODE);
            }

            @Override
            public void onChoseCancle() {
                //取消
            }
        });
        chosePopWindow.showAtLocation(view, Gravity.TOP, 0, 0);
    }
}
