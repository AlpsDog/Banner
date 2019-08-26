package com.test.banner.application;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

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

    static {
        ////刷新控件配置
        ////刷新控件配置
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置（优先级最低）
                layout.setEnableLoadMore(false);
                layout.setEnableAutoLoadMore(false);
                layout.setEnableOverScrollDrag(false);
                layout.setEnableOverScrollBounce(true);
                //全局设置主题颜色
//                layout.setPrimaryColorsId(R.color.c_fdfdfd, R.color.c_333333);
            }
        });
        //设置全局头部
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setTextSizeTitle(14);
                return classicsHeader;
            }
        });
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
