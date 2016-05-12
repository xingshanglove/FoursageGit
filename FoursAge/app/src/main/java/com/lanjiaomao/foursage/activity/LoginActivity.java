package com.lanjiaomao.foursage.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.activity.base.BaseActivity;
import com.lanjiaomao.foursage.bean.User;
import com.lanjiaomao.foursage.utils.Toasts;
import com.lanjiaomao.foursage.utils.ValidateUtils;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by root on 2016/5/7.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_user_account;
    private EditText et_user_pwd;
    private TextView tv_login;
    private LinearLayout tv_register;
    private ImageView iv_back;
    private ProgressDialog pd;


    Handler loginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 初始化控件
        initView();
    }

    private void initView() {
        et_user_account = (EditText) this.findViewById(R.id.et_user_account);
        et_user_pwd = (EditText) this.findViewById(R.id.et_user_pwd);
        tv_login = (TextView) this.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
        tv_register = (LinearLayout) this.findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        iv_back= (ImageView) this.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 确认登陆
             */
            case R.id.tv_login:
                String userName = et_user_account.getText().toString().trim();
                String password = et_user_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toasts.show(LoginActivity.this, "账号或者密码不能为空");
                    return;
                }
                User user = new User(userName, password);
                /**
                 * 异步进行登陆过程
                 */
                pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("登陆中");
                pd.show();
                ValidateUtils.login(user, new ValidateUtils.isLoginListener() {
                    @Override
                    public void validate(String s) {
                        pd.dismiss();
                        Message msg = loginHandler.obtainMessage();
                        try {
                            JSONObject data = new JSONObject(s);
                            String status = data.getString("status");
                            if (status.equals("success")) {
                                //
                                String phone = data.getString("user_telephone");
                                String password = data.getString("user_password");
                                //更改用户信息
                                changeConfig(phone, password);
                                LoginActivity.this.finish();
                            } else if (status.equals("error")) {
                                String reason = data.getString("reason");
                                msg.obj = reason;
                            }
                        } catch (JSONException e) {

                        }
                        loginHandler.sendMessage(msg);
                    }

                    @Override
                    public void unvalidate(String s) {
                        pd.dismiss();
                        Message msg = loginHandler.obtainMessage();
                        msg.obj = s;
                        loginHandler.sendMessage(msg);
                    }
                });
                break;
            /**
             * 注册界面
             */
            case R.id.tv_register:
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 0);
                break;
            case R.id.iv_back:
                super.onBackPressed();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            String result=data.getStringExtra("result");
            if(result.equals("success")){
                LoginActivity.this.finish();
            }
        }
    }

    private void changeConfig(String phone, String password) {
        editor.putString("user_phone", phone).commit();
        editor.putString("user_password", password).commit();
        editor.putBoolean("haslogin", true).commit();
        LoginActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        if (pd != null && pd.isShowing())
            pd.dismiss();
        super.onBackPressed();
    }

}
