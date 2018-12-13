package com.test.banner.application;

import android.app.Application;

/**
 * @Author: HSL
 * @Time: 2018/12/13 14:29
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class AppContext extends Application {

    private static AppContext instance;

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化工作
        //初始化工作
        //初始化工作
    }
}
