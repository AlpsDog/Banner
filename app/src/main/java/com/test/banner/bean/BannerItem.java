package com.test.banner.bean;

import com.geek.banner.loader.BannerEntry;

/**
 * @Project: Banner
 * @Package: com.test.banner
 * @Author: HSL
 * @Time: 2019/08/07 16:25
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class BannerItem implements BannerEntry {

    private Object path;
    private String indicatorText;

    public BannerItem() {
    }

    public BannerItem(Object path, String indicatorText) {
        this.path = path;
        this.indicatorText = indicatorText;
    }

    @Override
    public Object getBannerPath() {
        return path;
    }

    @Override
    public String getIndicatorText() {
        return indicatorText;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    public void setIndicatorText(String indicatorText) {
        this.indicatorText = indicatorText;
    }
}
