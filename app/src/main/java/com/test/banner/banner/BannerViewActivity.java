package com.test.banner.banner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.geek.banner.Banner;
import com.geek.banner.loader.BannerEntry;
import com.geek.banner.loader.BannerLoader;
import com.test.banner.R;
import com.test.banner.bean.BannerItem;
import com.test.banner.widget.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;
import cn.jzvd.Jzvd;

/**
 * @Author: HSL
 * @Time: 2019/8/7 13:44
 * @E-mail: xxx@163.com
 * @Description: 自定义BannerView~
 */
public class BannerViewActivity extends AppCompatActivity {

    @BindView(R.id.banner)
    Banner banner;

    private List<BannerItem> mData = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, BannerViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_view);
        ButterKnife.bind(this);
        initData();
        initBanner();
    }

    private void initData() {
        mData.add(new BannerItem(R.drawable.banner_1, ""));
        mData.add(new BannerItem("", ""));
        mData.add(new BannerItem(R.drawable.banner_3, ""));
        mData.add(new BannerItem("", ""));
        mData.add(new BannerItem(R.drawable.banner_5, ""));
    }

    private void initBanner() {
        //First 创建BannerLoader
        /**
         * 加载器
         * 第一个泛型：图片的URL FILE res...等
         * 第二个泛型：banner显示的View，可以是任意View
         */
        BannerLoader<Object, View> mBannerLoader = new BannerLoader<Object, View>() {

            /**
             * 如何加载图片资源，由自己决定
             * @param context
             * @param path
             * @param view
             */
            @Override
            public void loadView(Context context, BannerEntry path, int position, View view) {
                if (position == 1) {
                    CountdownView countdownView = view.findViewById(R.id.time_cdv);
                    countdownView.start(995550000);
                } else if (position == 3) {
                    ((BannerView) view).startPlay();
                } else {
                    RequestOptions requestOptions = new RequestOptions()
                            .placeholder(R.drawable.banner_default)
                            .error(R.drawable.banner_default)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                    Glide.with(context).load(path.getBannerPath()).apply(requestOptions).into((ImageView) view);
                }
            }

            /**
             * 怎样创建View，也由自己决定
             * @param context
             * @return
             */
            @Override
            public View createView(Context context, int position) {
                View bannerView = null;
                if (position == 1) {
                    bannerView = LayoutInflater.from(context).inflate(R.layout.banner_item_second_pager, null);
                } else if (position == 3) {
                    bannerView = new BannerView(context);
                } else {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    bannerView = imageView;
                }
                return bannerView;
            }
        };

        //second 设置BannerLoader
        banner.setBannerLoader(mBannerLoader);

        //third 设置页面点击事件
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerViewActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        //four 翻页事件
        banner.setBannerPagerChangedListener(new Banner.OnBannerSimplePagerListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("hsl777", "onPageSelected: ==>" + position);
            }
        });
        //last 最重要一步，加载数据
        banner.loadImagePaths(mData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopAutoPlay();
        Jzvd.releaseAllVideos();
    }
}
