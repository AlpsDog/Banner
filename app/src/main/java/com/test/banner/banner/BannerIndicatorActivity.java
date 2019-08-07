package com.test.banner.banner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.test.banner.R;
import com.test.banner.bean.BannerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerIndicatorActivity extends AppCompatActivity {

    @BindView(R.id.pager_tv)
    TextView pagerTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.pager_2_tv)
    TextView pager2Tv;
    @BindView(R.id.title_2_tv)
    TextView title2Tv;
    @BindView(R.id.banner_2)
    Banner banner2;

    private List<BannerItem> mData = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, BannerIndicatorActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_indicator);
        ButterKnife.bind(this);
        initData();
        initBanner();
        initBanner2();
    }

    private void initData() {
        mData.add(new BannerItem(R.drawable.banner_1, "《悯农》 白居易"));
        mData.add(new BannerItem(R.drawable.banner_2, "锄禾日当午"));
        mData.add(new BannerItem(R.drawable.banner_3, "汗滴禾下午"));
        mData.add(new BannerItem(R.drawable.banner_4, "谁知盘中餐"));
        mData.add(new BannerItem(R.drawable.banner_5, "粒粒皆辛苦"));
    }

    private void initBanner() {
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
                Toast.makeText(BannerIndicatorActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        // 3 翻页事件
        banner.setBannerPagerChangedListener(new Banner.OnBannerSimplePagerListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("hsl777", "onPageSelected: ==>" + position);
                pagerTv.setText(String.format("%s/5", position + 1));
            }
        });
        //额外功能设置banner摘要
        banner.setBannerText(titleTv);
        // 4 最重要一步，加载数据
        banner.loadImagePaths(mData);
    }

    private void initBanner2() {
        // 1. 创建设置BannerLoader
        banner2.setBannerLoader(new ImageLoader() {
            @Override
            public void loadView(Context context, BannerEntry entry, int position, View imageView) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.banner_default)
                        .error(R.drawable.banner_default)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                Glide.with(context).load(entry.getBannerPath()).apply(requestOptions).into((ImageView) imageView);
            }
        });

        // 2. 设置页面点击事件
        banner2.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerIndicatorActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        // 3. 翻页事件
        banner2.setBannerPagerChangedListener(new Banner.OnBannerSimplePagerListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("hsl777", "onPageSelected: ==>" + position);
                pager2Tv.setText(String.format("%s/5", position + 1));
            }
        });
        //额外功能设置banner摘要
        banner2.setBannerText(title2Tv);
        // 4. 最重要一步，加载数据
        banner2.loadImagePaths(mData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.startAutoPlay();
        banner2.startAutoPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.stopAutoPlay();
        banner2.stopAutoPlay();
    }
}
