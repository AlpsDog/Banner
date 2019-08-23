package com.geek.banner.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @Author: HSL
 * @Time: 2018/11/30 10:34
 * @E-mail: xxx@163.com
 * @Description: 控制ViewPager能否滚动，控制是否仿魅族效果的绘制
 */
public class BannerViewPager extends ViewPager {

    private ArrayList<Integer> childCenterXAbs = new ArrayList<>();
    private SparseArray<Integer> childIndex = new SparseArray<>();
    /**
     * 能否滚动
     */
    private boolean mScrollable = true;
    /**
     * 能否仿魅族效果
     */
    private boolean mEnableMzEffects = false;

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
     * @param childCount
     * @param n
     * @return 第n个位置的child 的绘制索引
     * <p>
     * 重写这个方法来控制字View的绘制顺序
     * 让中间View压着左右两边View，出现仿魅族效果
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int n) {
        if (mEnableMzEffects) {
            if (n == 0 || childIndex.size() != childCount) {
                childCenterXAbs.clear();
                childIndex.clear();
                int viewCenterX = getViewCenterX(this);
                for (int i = 0; i < childCount; ++i) {
                    int indexAbs = Math.abs(viewCenterX - getViewCenterX(getChildAt(i)));
                    //两个距离相同，后来的那个做自增，从而保持abs不同
                    if (childIndex.get(indexAbs) != null) {
                        ++indexAbs;
                    }
                    childCenterXAbs.add(indexAbs);
                    childIndex.append(indexAbs, i);
                }
                //1,0,2  0,1,2
                Collections.sort(childCenterXAbs);
            }
            //那个item距离中心点远一些，就先draw它。（最近的就是中间放大的item,最后draw）
            return childIndex.get(childCenterXAbs.get(childCount - 1 - n));
        } else {
            return super.getChildDrawingOrder(childCount, n);
        }
    }

    private int getViewCenterX(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);
        return array[0] + view.getWidth() / 2;
    }

    /**
     * 设置能否滚动
     *
     * @param scrollable
     */
    public void setScrollable(boolean scrollable) {
        mScrollable = scrollable;
    }

    /**
     * 仿魅族Banner
     * 控制绘制顺序，让中间View覆盖左右两View
     *
     * @param enableMzEffects
     */
    public void setEnableMzEffects(boolean enableMzEffects) {
        mEnableMzEffects = enableMzEffects;
    }
}
