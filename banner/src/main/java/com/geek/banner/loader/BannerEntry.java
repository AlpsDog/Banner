package com.geek.banner.loader;

/**
 * @Project: Banner
 * @Package: com.geek.banner.loader
 * @Author: HSL
 * @Time: 2019/08/07 16:08
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public interface BannerEntry<T> {

    /**
     * 获取Banner数据源
     *
     * @return
     */
    T getBannerPath();

    /**
     * 获取指示器文本
     *
     * @return
     */
    String getIndicatorText();
}
