package com.geek.banner.transformer.complex;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateUpPageTransformer extends BasePageTransformer {

    private static final float DEFAULT_MAX_ROTATE = 15.0f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;

    public RotateUpPageTransformer() {
    }

    public RotateUpPageTransformer(float maxRotate) {
        this(maxRotate, NonPageTransformer.INSTANCE);
    }

    public RotateUpPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MAX_ROTATE, pageTransformer);
    }

    public RotateUpPageTransformer(float maxRotate, ViewPager.PageTransformer pageTransformer) {
        mMaxRotate = maxRotate;
        mPageTransformer = pageTransformer;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void pageTransform(View view, float position) {
        if (position < -1) {
            // This page is way off-screen to the left.
            view.setRotation(mMaxRotate);
            view.setPivotX(view.getWidth());
            view.setPivotY(0);

        } else if (position <= 1) {
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {
                view.setPivotX(view.getWidth() * (0.5f + 0.5f * (-position)));
                view.setPivotY(0);
                view.setRotation(-mMaxRotate * position);
            } else {
                view.setPivotX(view.getWidth() * 0.5f * (1 - position));
                view.setPivotY(0);
                view.setRotation(-mMaxRotate * position);
            }
        } else {
            // This page is way off-screen to the right.
            // ViewHelper.setRotation(view, ROT_MAX);
            view.setRotation(-mMaxRotate);
            view.setPivotX(0);
            view.setPivotY(0);
        }
    }
}  