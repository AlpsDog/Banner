package com.geek.banner.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.geek.banner.constant.BannerConfig;

/**
 * @Author: HSL
 * @Time: 2018/12/13 14:12
 * @E-mail: xxx@163.com
 * @Description: 控制ViewPager翻页的时间~
 */
public class BannerScroller extends Scroller {

    //ViewPager翻页的时间
    private int mDuration = BannerConfig.SCROLL_TIME;

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    /**
     * 设置翻页时间
     *
     * @param duration
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

}
