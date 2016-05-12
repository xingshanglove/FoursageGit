package com.lanjiaomao.foursage.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.activity.base.FoursApplication;
import com.lanjiaomao.foursage.bean.User;
import com.lanjiaomao.foursage.utils.Toasts;
import com.lanjiaomao.foursage.utils.ValidateUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText et_input_phonenumber;
    private EditText et_input_vertifiy;
    private EditText et_input_password;
    private EditText et_input_ensure_passwrod;
    private TextView tv_get_vertify;
    private TextView tv_register;
    private ImageView iv_clear_passwrod;
    private ImageView iv_clear_vertifypasswrod;

    private TextView tv_title;
    private ImageView iv_back;

    private MessageReceiver receiver;

    private String phoneNumber;
    private String vertifyPhoneNumber = "";
    private String passWrod;
    private String vertifyPassword;

    private static final int REGISTER_SIGNAL = 0x113;
    private static final int GET_MESSAGE_INFO = 0x112;
    private static final int REFRESH_VERTIFY_DURATION = 0x111;
    private static final int REGISTER_RESPONSE = 0x115;
    private static final int SEND_VETIFYCODE_SUCCESS = 0x116;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_MESSAGE_INFO:
                    //自动填充验证码
                    String code = (String) msg.obj;
                    et_input_vertifiy.setText(code);
                    break;
                case REGISTER_SIGNAL:
                    //开始注册
                    register();
                    break;
                case REGISTER_RESPONSE:
                    //注册反馈信息
                    if (pd != null && pd.isShowing())
                        pd.dismiss();
                    String s= (String) msg.obj;
                    Toasts.show(RegisterActivity.this, s);
                    break;
                case SEND_VETIFYCODE_SUCCESS:
                    //验证码发送成功
                    Toasts.show(RegisterActivity.this, "验证码已发送");
                    break;
            }
        }
    };
    private Timer durationTimer;
    int sendDuration = 60;
    private Handler durationHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            sendDuration--;
            if (sendDuration > 0) {
                //显示倒计时
                tv_get_vertify.setText("获取验证码(" + sendDuration + ")");
            } else {
                //清除
                tv_get_vertify.setEnabled(true);
                tv_get_vertify.setText("获取验证码");
                sendDuration = 60;
                durationTimer.cancel();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initData();
    }

    private void initView() {
        et_input_phonenumber = (EditText) this.findViewById(R.id.et_input_phonenumber);
        et_input_vertifiy = (EditText) this.findViewById(R.id.et_input_vertifiy);
        et_input_password = (EditText) this.findViewById(R.id.et_input_password);
        et_input_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("") && s != null) {
                    iv_clear_passwrod.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        et_input_ensure_passwrod = (EditText) this.findViewById(R.id.et_input_ensure_passwrod);
        et_input_ensure_passwrod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("") && s != null) {
                    iv_clear_vertifypasswrod.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tv_get_vertify = (TextView) this.findViewById(R.id.tv_get_vertify);
        tv_get_vertify.setOnClickListener(this);
        tv_register = (TextView) this.findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        iv_clear_passwrod = (ImageView) this.findViewById(R.id.iv_clear_passwrod);
        iv_clear_passwrod.setOnClickListener(this);
        iv_clear_vertifypasswrod = (ImageView) this.findViewById(R.id.iv_clear_vertifypasswrod);
        iv_clear_vertifypasswrod.setOnClickListener(this);
        tv_title = (TextView) this.findViewById(R.id.tv_title);
        tv_title.setText("注册");
        iv_back = (ImageView) this.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    private void initData() {
        final EventHandler ev = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                super.afterEvent(event, result, data);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        handler.sendEmptyMessage(SEND_VETIFYCODE_SUCCESS);
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        HashMap<String, Object> data1 = (HashMap<String, Object>) data;
                        vertifyPhoneNumber = (String) data1.get("phone");
                        //开始注册
                        handler.sendEmptyMessage(REGISTER_SIGNAL);
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(ev);
        //短信监听
        receiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");


        RegisterActivity.this.registerReceiver(receiver, filter);
    }

    /**
     * 注册信息到服务器
     */
    public void register() {
        if (pd != null && pd.isShowing())
            pd.setMessage("注册中");
        passWrod = et_input_password.getText().toString().trim();
        vertifyPassword = et_input_ensure_passwrod.getText().toString().trim();
        if (!TextUtils.isEmpty(passWrod) && !TextUtils.isEmpty(vertifyPassword) && passWrod.equals(vertifyPassword) && !passWrod.contains(" ") && !vertifyPassword.contains(" ")) {
            //密码相同
            if (phoneNumber.equals(vertifyPhoneNumber)) {
                //手机验证成功,
                //开始注册到服务器
                User user = new User(phoneNumber, passWrod);
                ValidateUtils.register(user, new ValidateUtils.registerListener() {
                    @Override
                    public void successResponse(String s) {
                        Message msg=handler.obtainMessage();
                        msg.what=REGISTER_RESPONSE;
                        try {
                            JSONObject data=new JSONObject(s);
                            String status = data.getString("status");
                            if(status.equals("success")){
                                msg.obj="注册成功";
                                //保存用户信息
                                String phone=data.getString("");
                                String password=data.getString("");
                                saveUserInfo(phone,password);
                            }else if(status.equals("error")){
                                String reason=data.getString("reason");
                                msg.obj=reason;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            msg.obj="注册失败";
                        }
                        handler.sendMessage(msg);
                    }
                    @Override
                    public void failureResponse(String s) {
                        //注册失败
                        Message msg=handler.obtainMessage();
                        msg.obj=s;
                        msg.what=REGISTER_RESPONSE;
                        handler.sendMessage(msg);
                    }
                });
            } else {
                if (pd != null && pd.isShowing())
                    pd.dismiss();
                Toasts.show(RegisterActivity.this, "手机认证失败,请重试");
            }
        } else {
            if (pd != null && pd.isShowing())
                pd.dismiss();
            Toasts.show(RegisterActivity.this, "请确保前后密码输入一致");
        }
    }

    private void saveUserInfo(String phone, String password) {
        //广播修改用户信息
        Intent intent=new Intent("com.lanjiaomao.userinfo_changed");
        sendBroadcast(intent);
        //保存到spf
        SharedPreferences preferences=getSharedPreferences("config",Context.MODE_PRIVATE);
        preferences.edit().putString("user_phone",phone).commit();
        preferences.edit().putString("user_password",password).commit();
        preferences.edit().putBoolean("haslogin", true).commit();

        Intent intentBack=new Intent();
        intentBack.putExtra("result", "success");
        setResult(1, intentBack);
    }

    @Override
    public void onBackPressed() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        } else {
            failureBack();
        }
    }

    public void failureBack(){
        Intent intentBack=new Intent();
        intentBack.putExtra("result", "failure");
        setResult(1, intentBack);
        RegisterActivity.this.finish();
    }

    ProgressDialog pd;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_vertify:
                /**
                 * 获取验证码
                 */
                phoneNumber = et_input_phonenumber.getText().toString();
                if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 11) {
                    Toasts.show(RegisterActivity.this, "请正确输入手机号");
                    return;
                }
                //发送验证码
                SMSSDK.getVerificationCode("+86", phoneNumber);
                //点击后显示刷新间隔
                tv_get_vertify.setEnabled(false);
                durationTimer = new Timer();
                durationTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        durationHandler.sendEmptyMessage(REFRESH_VERTIFY_DURATION);
                    }
                }, 0, 1000);
                break;
            case R.id.tv_register:
                String vertifyCode = et_input_vertifiy.getText().toString();
                if (vertifyCode != null && vertifyCode.length() == 4) {
                    SMSSDK.submitVerificationCode("+86", phoneNumber, vertifyCode);
                } else {
                    Toasts.show(RegisterActivity.this,"请完善信息");
                    return;
                }
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setMessage("验证中");
                pd.setCancelable(false);
                pd.show();
                break;
            case R.id.iv_clear_passwrod:
                et_input_password.setText("");
                iv_clear_passwrod.setVisibility(View.GONE);
                break;
            case R.id.iv_clear_vertifypasswrod:
                et_input_ensure_passwrod.setText("");
                iv_clear_vertifypasswrod.setVisibility(View.GONE);
                break;
            case R.id.iv_back:
                failureBack();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (receiver != null)
            unregisterReceiver(receiver);
        super.onDestroy();
    }

    /**
     * 截取短信自动填充
     */
    class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Object[] objects = (Object[]) intent.getExtras().get("pdus");
            for (Object object : objects) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
                // 短信内容
                String messageBody = message.getMessageBody();
                if (messageBody.contains("Foursage")) {
                    for (int i = 0; i < messageBody.length(); i++) {
                        if (messageBody.charAt(i) >= 48 && messageBody.charAt(i) <= 57) {
                            String vertifyCode = messageBody.substring(i, i + 4);
                            Message msg = handler.obtainMessage();
                            msg.obj = vertifyCode;
                            msg.what = GET_MESSAGE_INFO;
                            handler.sendMessage(msg);
                            break;
                        }
                    }
                }
            }
        }
    }
}
