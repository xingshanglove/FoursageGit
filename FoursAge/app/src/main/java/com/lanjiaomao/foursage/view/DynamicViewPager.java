package com.lanjiaomao.foursage.view;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;


import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;

import com.lanjiaomao.foursage.R;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DynamicViewPager extends RelativeLayout {

    //viewpager 和下面的点
    private android.support.v4.view.ViewPager vp_imgs;
    private LinearLayout ll_dots;

    //缩放比例和平移距离
    private float mScale;
    private float mTrans;

    private static final float SCALE_MAX = 0.5f;
    private static final String TAG = "myviewpager";

    //存放imgs用于缩放
    private HashMap<Integer, View> mChildViews = new LinkedHashMap<Integer, View>();

    //左右img
    private View leftView;
    private View rightView;
    //imgd的url
    private String[] urls;
    //所有点
    private ImageView[] dots;

    private Context context;
    //当前页数
    private int currentPage = 0;
    private OnPageClickListener onPageClickListener;

    public interface OnPageClickListener {
        public void onPageClick(int position);
    }

    //设置页面点击事件
    public void setOnPageClickListener(OnPageClickListener clickListener) {
        this.onPageClickListener = clickListener;
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    public DynamicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View rootView = View.inflate(context, R.layout.myviewpager, this);
        vp_imgs = (ViewPager) rootView.findViewById(R.id.vp_imgs);
        ll_dots = (LinearLayout) rootView.findViewById(R.id.ll_dots);

        vp_imgs.setPageTransformer(true,new CubeTransformer());
    }

    /**
     * 为viewpager设置参数
     *
     * @param urls
     */
    public void setUrls(String[] urls) {
        this.urls = urls;
        dots = new ImageView[urls.length];
        initDots();
        vp_imgs.setAdapter(new PagesAdapter());
        vp_imgs.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                //当页面改变 修改点的属性
                for (int i = 0; i < dots.length; i++) {
                    dots[i].setEnabled(true);
                }
                dots[index].setEnabled(false);
                currentPage = index;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        //定时自动滑动
        Timer timer = new Timer();
        timer.schedule(task, 1000, 3000);

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int nextPage = (currentPage + 1) % (urls.length);
            vp_imgs.setCurrentItem(nextPage, true);
        }

        ;
    };
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            handler.sendEmptyMessage(0x111);
        }
    };

    /**
     * 填充小点
     */
    private void initDots() {
        for (int i = 0; i < urls.length; i++) {
            ImageView dot = new ImageView(context);
            dot.setLayoutParams(new LayoutParams(25, 25));
            dot.setImageResource(R.drawable.dot);
            dot.setPadding(5, 5, 5, 5);
            ll_dots.addView(dot);
            dots[i] = dot;
        }
        dots[0].setEnabled(false);
    }



    /**
     * 为viewpager填充数据
     *
     * @author root
     */
    class PagesAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return urls.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView img = new ImageView(context);
            img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPageClickListener.onPageClick(position);
                }
            });
            ImageLoader.getInstance().displayImage(urls[position], img);
            img.setScaleType(ScaleType.FIT_XY);
            container.addView(img);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 动画
     */
    class CubeTransformer implements ViewPager.PageTransformer {

        /**
         * position参数指明给定页面相对于屏幕中心的位置。它是一个动态属性，会随着页面的滚动而改变。当一个页面填充整个屏幕是，它的值是0，
         * 当一个页面刚刚离开屏幕的右边时，它的值是1。当两个也页面分别滚动到一半时，其中一个页面的位置是-0.5，另一个页面的位置是0.5。基于屏幕上页面的位置
         * ，通过使用诸如setAlpha()、setTranslationX()、或setScaleY()方法来设置页面的属性，来创建自定义的滑动动画。
         */
        @Override
        public void transformPage(View view, float position) {
            if (position <= 0) {
                //从右向左滑动为当前View

                //设置旋转中心点；
                ViewHelper.setPivotX(view, view.getMeasuredWidth());
                ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);

                //只在Y轴做旋转操作
                ViewHelper.setRotationY(view, 90f * position);
            } else if (position <= 1) {
                //从左向右滑动为当前View
                ViewHelper.setPivotX(view, 0);
                ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);
                ViewHelper.setRotationY(view, 90f * position);
            }
        }
    }

}
