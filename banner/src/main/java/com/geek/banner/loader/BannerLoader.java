package com.geek.banner.loader;

import android.content.Context;
import android.view.View;

/**
 * @Author: HSL
 * @Time: 2018/12/13 14:01
 * @E-mail: xxx@163.com
 * @Description: Banner加载器~
 */
public interface BannerLoader<T, V extends View> {

    /**
     * 加载VIEW
     *
     * @param context
     * @param entry
     * @param position  显示的位置
     * @param imageView
     */
    void loadView(Context context, BannerEntry entry, int position, V imageView);

    /**
     * 创建View
     *
     * @param context
     * @param position
     * @return
     */
    V createView(Context context, int position);
}
