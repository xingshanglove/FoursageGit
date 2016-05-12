package com.lanjiaomao.foursage.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lanjiaomao.foursage.activity.base.FoursApplication;
import com.lanjiaomao.foursage.bean.Contacts;
import com.lanjiaomao.foursage.bean.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 2016/5/7.
 */
public class ValidateUtils {
    public interface isLoginListener {
        public void validate(String s);
        public void unvalidate(String s);
    }
    public interface  registerListener{
        public void successResponse(String s);
        public void failureResponse(String s);
    }

    public static boolean isLogin() {
        return false;
    }

    public static void login(final User user, final isLoginListener listener) {
//        StringRequest request = new StringRequest(StringRequest.Method.POST, Contacts.LOGIN_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                listener.validate(s);
//                FoursApplication.getRequestQueue().cancelAll("login");
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                listener.unvalidate(volleyError.getMessage());
//                FoursApplication.getRequestQueue().cancelAll("login");
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map=new HashMap<>();
//                map.put("user_telephone",user.getPhoneNumber());
//                map.put("user_password",user.getPassWord());
//                return map;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                return super.getHeaders();
//            }
//        };
//        request.setTag("login");
//        FoursApplication.getRequestQueue().add(request);

        if(user.getPhoneNumber().equals("18149184677")&&user.getPassWord().equals("123")){
            listener.validate("  {\"status\":\"success\",\"user_telephone\":\"18149184677\",\"user_password\":\"123\"\n" +
                    "}");
        }else{
            listener.validate("{\"status\":\"error\",\"reason\":\"账号或者密码错误\"\n" +
                    "}");
        }
    }

    public static void register(final User user, final registerListener listener) {
        StringRequest request = new StringRequest(StringRequest.Method.POST, Contacts.REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.successResponse(s);
                FoursApplication.getRequestQueue().cancelAll("register");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.failureResponse(volleyError.getMessage());
                FoursApplication.getRequestQueue().cancelAll("register");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map=new HashMap<>();
                map.put("user_telephone",user.getPhoneNumber());
                map.put("user_password",user.getPassWord());
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }
        };
        request.setTag("register");
        FoursApplication.getRequestQueue().add(request);
    }
}
