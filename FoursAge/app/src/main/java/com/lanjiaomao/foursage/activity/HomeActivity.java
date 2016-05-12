package com.lanjiaomao.foursage.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.activity.base.BaseActivity;
import com.lanjiaomao.foursage.activity.homefragment.CreateActiveFragment;
import com.lanjiaomao.foursage.activity.homefragment.MainFragment;
import com.lanjiaomao.foursage.activity.homefragment.MessageFragment;
import com.lanjiaomao.foursage.activity.homefragment.MyFriendFragment;
import com.lanjiaomao.foursage.view.CircleImageView;
import com.lanjiaomao.foursage.utils.Toasts;


public class HomeActivity extends BaseActivity implements View.OnClickListener {
    //主页面
    private DrawerLayout  drawerLayout;
    private LinearLayout navigation_1;
    private LinearLayout navigation_2;
    private LinearLayout navigation_3;
    private LinearLayout navigation_4;


    private OpenDrawlerLayoutReceiver receiver;
    //双击退出
    private long lastTime=0;

    //用户信息
    private CircleImageView iv_user_icon;
    private TextView tv_user_account;
    private TextView tv_user_school;

    //二维码
    private ImageView iv_zxing;

    private boolean hasLogin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        receiver=new OpenDrawlerLayoutReceiver();
        IntentFilter filter=new IntentFilter("com.homeactivity.opendrawler");
        registerReceiver(receiver,filter);

        hasLogin=spf.getBoolean("haslogin",false);

    }

    private void initView() {
        //初始化主页面
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        navigation_1= (LinearLayout) this.findViewById(R.id.navigation_1);
        navigation_1.setOnClickListener(this);
        navigation_2= (LinearLayout) this.findViewById(R.id.navigation_2);
        navigation_2.setOnClickListener(this);
        navigation_3= (LinearLayout) this.findViewById(R.id.navigation_3);
        navigation_3.setOnClickListener(this);
        navigation_4= (LinearLayout) this.findViewById(R.id.navigation_4);
        navigation_4.setOnClickListener(this);

        transaction.replace(R.id.ll_content, new MainFragment());
        transaction.commit();

        //初始化用户头像
        iv_user_icon= (CircleImageView) this.findViewById(R.id.iv_user_icon);
        iv_user_icon.setOnClickListener(this);
        tv_user_account= (TextView) this.findViewById(R.id.tv_user_account);
        tv_user_account.setOnClickListener(this);
        tv_user_school= (TextView) this.findViewById(R.id.tv_user_school);
        tv_user_school.setOnClickListener(this);
        //二维码
        iv_zxing= (ImageView) this.findViewById(R.id.iv_zxing);
        iv_zxing.setOnClickListener(this);

        initUserInfo();
    }

    private void initUserInfo() {

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            long curTime=System.currentTimeMillis();
            if(curTime-lastTime<1000){
                super.onBackPressed();
            }else{
                Toasts.show(HomeActivity.this,"双击退出");
                lastTime=curTime;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiver!=null)
            unregisterReceiver(receiver);
    }

    public void closeDrawlerLayout(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    @Override
    public void onClick(View v) {
        closeDrawlerLayout();
        if(v.getId()==R.id.navigation_1){
            //主页面
            transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.ll_content, new MainFragment());
            transaction.commit();
            return ;
        }
        if(!checkHasLogin()){
            //没有登录 提醒登陆
            showLoginDialog();
            return ;
        }
        switch (v.getId()){
            case R.id.navigation_2:
                //create activie
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.ll_content,CreateActiveFragment.getCreateFragment());
                transaction.commit();
                break;
            case R.id.navigation_3:
                //通讯录
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.ll_content,new MyFriendFragment());
                transaction.commit();
                break;
            case R.id.navigation_4:
                //msg
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.ll_content,new MessageFragment());
                transaction.commit();
                break;
            case R.id.tv_user_account:
            case R.id.tv_user_school:
                //用户详细信息页面
                startActivity(new Intent(HomeActivity.this,UserInfoActivity.class));
                break;
            case R.id.iv_user_icon:
                //查看高清头像或者选择头衔
                break;
            case R.id.iv_zxing:
                //二维码
                startActivity(new Intent(HomeActivity.this,ZxingActivity.class));
                break;
        }
    }

    private void showLoginDialog() {
        new AlertDialog.Builder(this).setMessage("是否立即登陆").setPositiveButton("登陆", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    private boolean checkHasLogin() {
        if(hasLogin){
            return true;
        }else{
            return true;
        }
    }

    /**
     *其他界面打开drawlerlayout
     */
    class OpenDrawlerLayoutReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(!drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
    }
}
