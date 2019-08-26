package com.test.banner.banner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.geek.banner.Banner;
import com.geek.banner.loader.BannerEntry;
import com.geek.banner.loader.ImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.test.banner.R;
import com.test.banner.bean.BannerItem;
import com.test.banner.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: HSL
 * @Time: 2019/8/23 15:35
 * @E-mail: xxx@163.com
 * @Description: 数据展示~
 */
public class BannerDataActivity extends AppCompatActivity {

    @BindView(R.id.pager_tv)
    TextView pagerTv;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.pager_tv_2)
    TextView pagerTv2;
    @BindView(R.id.banner_2)
    Banner banner2;
    @BindView(R.id.pager_tv_3)
    TextView pagerTv3;
    @BindView(R.id.banner_mz)
    Banner bannerMz;
    @BindView(R.id.refresh_srl)
    SmartRefreshLayout refreshSrl;

    private List<BannerItem> mData = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, BannerDataActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_data);
        ButterKnife.bind(this);
        initData();
        initBanner(banner);
        initBanner(banner2);
        initBanner(bannerMz);
        refreshSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshSrl.finishRefresh();
                int max = new Random().nextInt(5);
                List<BannerItem> data = new ArrayList<>();
                for (int i = 0; i < mData.size(); i++) {
                    if (i > max || max == 0) break;
                    data.add(mData.get(i));
                }
                pagerTv.setText(data.size() + "张");
                banner.loadImagePaths(data);
                if (max < 2) {
                    banner2.loadImagePaths(data);
                    pagerTv2.setText(data.size() + "张");
                } else {
                    bannerMz.loadImagePaths(data);
                    pagerTv3.setText(data.size() + "张");
                }
            }
        });
    }

    private void initData() {
        mData.add(new BannerItem(R.drawable.banner_1, ""));
        mData.add(new BannerItem(R.drawable.banner_2, ""));
        mData.add(new BannerItem(R.drawable.banner_3, ""));
        mData.add(new BannerItem(R.drawable.banner_4, ""));
        mData.add(new BannerItem(R.drawable.banner_5, ""));
        pagerTv.setText("5张");
        pagerTv2.setText("5张");
        pagerTv3.setText("5张");
    }

    private void initBanner(Banner banner) {
        // 1. 创建设置BannerLoader
        banner.setBannerLoader(new ImageLoader() {
            @Override
            public void loadView(Context context, BannerEntry entry, int position, View imageView) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.banner_default)
                        .error(R.drawable.banner_default)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                Glide.with(context).load(entry.getBannerPath()).apply(requestOptions).into((ImageView) imageView);
            }
        });

        // 2 设置页面点击事件
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerDataActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        // 3 翻页事件
        banner.setBannerPagerChangedListener(new Banner.OnBannerSimplePagerListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("hsl777", "onPageSelected: ==>"
                        + position
                        + "\n" + banner);
            }
        });

        // 4 最重要一步，加载数据
        banner.loadImagePaths(mData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.startAutoPlay();
        banner2.startAutoPlay();
        bannerMz.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopAutoPlay();
        banner2.stopAutoPlay();
        bannerMz.stopAutoPlay();
    }
}
