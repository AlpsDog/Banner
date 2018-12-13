package com.geek.banner.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author: HSL
 * @Time: 2018/11/30 10:34
 * @E-mail: xxx@163.com
 * @Description: 控制ViewPager能否滚动
 */
public class BannerViewPager extends ViewPager {

    //能否滚动
    private boolean mScrollable = true;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScrollable)
            return super.onTouchEvent(ev);
        else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mScrollable)
            return super.onInterceptTouchEvent(ev);
        else
            return false;

    }

    /**
     * 设置能否滚动
     *
     * @param scrollable
     */
    public void setScrollable(boolean scrollable) {
        mScrollable = scrollable;
    }
}
