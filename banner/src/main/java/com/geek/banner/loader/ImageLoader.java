package com.geek.banner.loader;

import android.content.Context;
import android.widget.ImageView;

/**
 * @Author: HSL
 * @Time: 2018/12/13 14:01
 * @E-mail: xxx@163.com
 * @Description: ImageView加载器~
 */
public abstract class ImageLoader<T> implements BannerLoader<T, ImageView> {

    @Override
    public ImageView createView(Context context, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
