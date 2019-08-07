package com.test.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.test.banner.banner.BaseUseActivity;
import com.test.banner.banner.ShowAnimActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author: HSL
 * @Time: 2019/8/5 11:30
 * @E-mail: xxx@163.com
 * @Description: 主页~
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.banner_base_use)
    TextView bannerBaseUse;
    @BindView(R.id.banner_anim_show)
    TextView bannerAnimShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * 基本使用
     */
    @OnClick(R.id.banner_base_use)
    public void onBaseUseClicked() {
        BaseUseActivity.start(this);
    }

    /**
     * 动画展示
     */
    @OnClick(R.id.banner_anim_show)
    public void onAnimShowClicked() {
        ShowAnimActivity.start(this);
    }
}
