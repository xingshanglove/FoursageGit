package com.lanjiaomao.foursage.activity.homefragment;


import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanjiaomao.foursage.R;
import com.lanjiaomao.foursage.adapter.ActiveShowAdapter;
import com.lanjiaomao.foursage.bean.Active;
import com.lanjiaomao.foursage.view.DynamicViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2016/5/9.
 */
public class MainFragment extends Fragment implements View.OnClickListener, DynamicViewPager.OnPageClickListener, AbsListView.OnScrollListener, View.OnTouchListener {
    DynamicViewPager dynamicViewPager;
    private TextView tv_title;
    private ImageView iv_back;

    private ListView lv_actives;
    private TextView tv_active_type;
    private LinearLayout ll_chose_type;
    private RelativeLayout rl_active_type;

    private ActiveShowAdapter adapter;

    List<Active> activeList;

    //测试
    LinearLayout ll_scroll_scale;
    LinearLayout ll_refresh;

    LinearLayout.LayoutParams layoutParams;
    private int maxHeight;

    LinearLayout.LayoutParams refreshParams;
    int refreshHeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_main_fragment, null);
        initView(view);
        RefreshActiveInfo();
        return view;
    }

    private void RefreshActiveInfo() {
        activeList = new ArrayList<>();

        adapter = new ActiveShowAdapter(getActivity(), activeList);
        lv_actives.setAdapter(adapter);
    }

    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("首页");
        tv_title.setOnClickListener(this);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        dynamicViewPager = (DynamicViewPager) view.findViewById(R.id.dv_host_activie);
        dynamicViewPager.setUrls(new String[]{"http://v.juhe.cn/movie/picurl?49843575", "http://v.juhe.cn/movie/picurl?49843594", "http://v.juhe.cn/movie/picurl?49843935", "http://v.juhe.cn/movie/picurl?49843574"});
        dynamicViewPager.setOnPageClickListener(this);
        lv_actives = (ListView) view.findViewById(R.id.lv_actives);

        lv_actives.setDivider(new ColorDrawable());
        lv_actives.setDividerHeight(5);


        tv_active_type = (TextView) view.findViewById(R.id.tv_active_type);
        ll_chose_type = (LinearLayout) view.findViewById(R.id.ll_chose_type);
        ll_chose_type.setOnClickListener(this);
        rl_active_type = (RelativeLayout) view.findViewById(R.id.rl_active_type);


        /**
         * test
         */
        ll_scroll_scale = (LinearLayout) view.findViewById(R.id.ll_scroll_scale);
        ll_refresh = (LinearLayout) view.findViewById(R.id.ll_refresh);


        lv_actives.setOnTouchListener(this);
        lv_actives.setOnScrollListener(this);

        layoutParams = (LinearLayout.LayoutParams) ll_scroll_scale.getLayoutParams();
        maxHeight = layoutParams.height;

        refreshParams = (LinearLayout.LayoutParams) ll_refresh.getLayoutParams();
        refreshHeight = refreshParams.height;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
            case R.id.tv_title:
                Intent intent = new Intent();
                intent.setAction("com.homeactivity.opendrawler");
                getActivity().sendBroadcast(intent);
                break;
            case R.id.ll_chose_type:
                break;
        }
    }

    @Override
    public void onPageClick(int position) {

    }

    boolean scrollFlag = false;

    /**
     * listview滑动监听
     *
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            scrollFlag = true;
        } else {
            scrollFlag = false;
        }
    }

    int firstItem;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstItem = firstVisibleItem;
    }

    int lastX;
    int lastY;
    boolean isHidden = false;
    boolean canDragDown = false;
    boolean isRefreshVisiable=false;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int curX = (int) event.getX();
        int curY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = curX;
                lastY = curY;
                if (firstItem == 0 && lv_actives.getChildAt(0).getTop() == 0)
                    canDragDown = true;
                else
                    canDragDown = false;
                break;
            case MotionEvent.ACTION_MOVE:

                if (Math.abs(curY - lastY) > Math.abs(curX - lastX)) {
                    // 竖直滑动
                    int dis = curY - lastY;
                    if (Math.abs(dis) > 100 && dis > 0) {
                        // 向shang滑动
                        if (canDragDown && isHidden) {
                            System.out.println(curY);
                            showHeadLayout();
                        } else if (!isHidden && canDragDown&&!isRefreshVisiable) {
                            showRefreshLayout(dis);
                        }
                    } else if (Math.abs(dis) > 100 && dis < 0) {
                        // 向下滑动
                        if (!isHidden) {
                            hideHeaderLayout();
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(refreshParams.height<40){
                    refreshParams.height=0;
                    ll_refresh.setLayoutParams(refreshParams);
                    ll_refresh.requestLayout();
                    isRefreshVisiable=false;
                }else {
                    refreshParams.height=80;
                    ll_refresh.setLayoutParams(refreshParams);
                    ll_refresh.requestLayout();
                    isRefreshVisiable=true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshParams.height=0;
                            ll_refresh.setLayoutParams(refreshParams);
                            ll_refresh.requestLayout();
                            isRefreshVisiable=false;
                        }
                    },1500);
                }
                break;
        }
        return false;
    }

    private void showRefreshLayout(int dis) {
        if (dis <= 500){
            float scale = dis / 500.0f;
            refreshParams.height= (int) (80*scale);
            ll_refresh.setLayoutParams(refreshParams);
            ll_refresh.requestLayout();
        }else{
            refreshParams.height=80;
            ll_refresh.setLayoutParams(refreshParams);
            ll_refresh.requestLayout();
            isRefreshVisiable=true;
            //d加载
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshParams.height=0;
                    ll_refresh.setLayoutParams(refreshParams);
                    ll_refresh.requestLayout();
                    isRefreshVisiable=false;
                }
            },1500);
        }

    }

    private void showHeadLayout() {
        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (Float) animation.getAnimatedValue();
                if (val != 1) {
                    layoutParams.height = (int) (maxHeight * val);
                } else {
                    layoutParams.height = maxHeight;
                    isHidden = false;
                }
                ll_scroll_scale.setLayoutParams(layoutParams);
                ll_scroll_scale.requestLayout();
            }
        });

    }

    private void hideHeaderLayout() {
        refreshParams.height=0;
        ll_refresh.setLayoutParams(refreshParams);
        ll_refresh.requestLayout();
        isRefreshVisiable=false;

        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.0f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (Float) animation.getAnimatedValue();
                if (val != 0) {
                    layoutParams.height = (int) (maxHeight * val);
                } else {
                    layoutParams.height = 0;
                    isHidden = true;
                }
                ll_scroll_scale.setLayoutParams(layoutParams);
                ll_scroll_scale.requestLayout();
            }
        });
    }
}
