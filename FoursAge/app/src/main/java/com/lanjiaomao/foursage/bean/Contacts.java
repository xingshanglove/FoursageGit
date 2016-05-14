package com.lanjiaomao.foursage.bean;

import android.os.Environment;

/**
 * Created by root on 2016/5/11.
 */
public class Contacts {
    /**
     * 用户资料
     */
    public static final String USER_PHONE="user_phone";
    public static final String USER_PASSWORD="user_password";
    /**
     *
     */
    public static final String REGISTER_URL="http://172.22.0.1:8080/Foursage/registerServlet";
    public static final String LOGIN_URL="http://172.22.0.1:8080/Foursage/loginServlet";
    public static final String USER_ICON_EXTERNAL_PATH= Environment.getExternalStorageDirectory().getAbsoluteFile()+"/Android/data/com.lanjiaomao.foursage/icon/";
    public static final String USER_ICON_INNER_PATH="/data/data/com.lanjiaomao.foursage/icon/";
    public static final String CHOSE_PICTURE_FOR_ICON="icon";
    public static final String CHOSE_PICTURE_FOR_DESC="desc";
}
