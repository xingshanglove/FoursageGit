package com.lanjiaomao.foursage.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.lanjiaomao.foursage.bean.Active;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 2016/5/14.
 */
public class GetAllImages {
    public interface getImagesListner{
        public void getImagesSuccess(HashMap<String, List<String>> imageGroups);
        public void getImagesFail();
    }
    public static void getAllImages(final Activity activity, final getImagesListner listener) {
        final HashMap<String, List<String>> imageGroups = new HashMap<String, List<String>>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver resolver = activity.getContentResolver();
                Cursor cursor = resolver.query(imageUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        //获取图片的路径
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        //获取该图片的父路径名
                        String parentName = new File(path).getParentFile().getName();
                        //根据父路径将图片归档
                        if (!imageGroups.containsKey(parentName)) {
                            List<String> childList = new ArrayList<String>();
                            childList.add(path);
                            imageGroups.put(parentName, childList);
                        } else {
                            imageGroups.get(parentName).add(path);
                        }
                    }
                }else{
                    listener.getImagesFail();
                }
                cursor.close();
                listener.getImagesSuccess(imageGroups);
            }
        }).start();
    }
}
