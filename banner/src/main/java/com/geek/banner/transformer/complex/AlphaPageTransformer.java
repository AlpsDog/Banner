package com.geek.banner.transformer.complex;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
/**
 * @Author: HSL
 * @Time: 2018/12/13 14:05
 * @E-mail: xxx@163.com
 * @Description: 透明
 * 来自https://github.com/hongyangAndroid/MagicViewPager
 */
public class AlphaPageTransformer extends BasePageTransformer {
    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public AlphaPageTransformer() {

    }

    public AlphaPageTransformer(float minAlpha) {
        this(minAlpha, NonPageTransformer.INSTANCE);
    }

    public AlphaPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_ALPHA, pageTransformer);
    }

    public AlphaPageTransformer(float minAlpha, ViewPager.PageTransformer pageTransformer) {
        mMinAlpha = minAlpha;
        mPageTransformer = pageTransformer;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void pageTransform(View view, float position) {
        view.setScaleX(0.999f);
        if (position < -1) {
            view.setAlpha(mMinAlpha);
        } else if (position <= 1) {
            if (position < 0) {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else {
            view.setAlpha(mMinAlpha);
        }
    }
}
