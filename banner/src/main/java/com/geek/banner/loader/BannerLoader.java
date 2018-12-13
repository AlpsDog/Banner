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


    void loadView(Context context, T path, V imageView);

    V createView(Context context);
}
