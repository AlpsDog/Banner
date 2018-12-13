package com.test.banner.banner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.geek.banner.Banner;
import com.geek.banner.loader.BannerLoader;
import com.test.banner.R;
import com.test.banner.widget.BarrageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BannerActivity extends AppCompatActivity {

    @BindView(R.id.refresh_tv)
    TextView refreshTv;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.banner_2)
    Banner banner2;
    @BindView(R.id.banner_3)
    Banner banner3;
    @BindView(R.id.banner_4)
    Banner banner4;
    @BindView(R.id.banner_5)
    Banner banner5;
    @BindView(R.id.banner_6)
    Banner banner6;
    @BindView(R.id.banner_7)
    Banner banner7;
    @BindView(R.id.banner_8)
    Banner banner8;
    @BindView(R.id.banner_9)
    Banner banner9;
    @BindView(R.id.banner_10_text)
    TextView banner10Text;
    @BindView(R.id.banner_10_index)
    TextView banner10Index;
    @BindView(R.id.banner_10)
    Banner banner10;
    @BindView(R.id.barrage_view)
    BarrageView barrageView;
    @BindView(R.id.banner_11)
    Banner banner11;

    private List<String> mURLs = new ArrayList<>();
    private List<Integer> mRes = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, BannerActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        initBanner();
    }

    private void initBanner() {
        mURLs.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg");
        mURLs.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        mURLs.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        mURLs.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        mURLs.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");

        mRes.add(R.drawable.banner_1);
        mRes.add(R.drawable.banner_2);
        mRes.add(R.drawable.banner_3);
        mRes.add(R.drawable.banner_4);
        mRes.add(R.drawable.banner_5);

        mTitle.add("悯农~");
        mTitle.add("锄禾日当午~");
        mTitle.add("汗滴禾下土~");
        mTitle.add("谁知盘中餐~");
        mTitle.add("粒粒皆辛苦~");

        banner.setBannerLoader(mBannerLoader);
        banner.loadImagePaths(mURLs);
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        banner.setBannerPagerChangedListener(new Banner.OnBannerPagerChangedListener() {
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("banner_change", "onPageScrolled:位置 - " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("banner_change", "onPageSelected:位置 -  " + position);
            }
        });

        banner2.setBannerLoader(mBannerLoader3);
        banner2.loadImagePaths(mRes);
        banner2.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        List<String> data3_4 = new ArrayList<>();
        data3_4.add(mURLs.get(0));
        data3_4.add(mURLs.get(1));
        banner3.setBannerLoader(mBannerLoader);
        banner3.loadImagePaths(data3_4);
        banner3.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        banner4.setBannerLoader(mBannerLoader);
        banner4.loadImagePaths(data3_4);
        banner4.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        List<String> data5_6 = new ArrayList<>();
        data5_6.add(mURLs.get(0));
        banner5.setBannerLoader(mBannerLoader);
        banner5.loadImagePaths(data5_6);
        banner5.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        banner6.setBannerLoader(mBannerLoader);
        banner6.loadImagePaths(data5_6);
        banner6.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        banner7.setBannerLoader(mBannerLoader2);
        banner7.loadImagePaths(mURLs);
        banner7.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        banner8.setBannerLoader(mBannerLoader2);
        banner8.loadImagePaths(mURLs);
        banner8.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        banner9.setBannerLoader(mBannerLoader2);
        banner9.loadImagePaths(null);
        banner9.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        banner10.setBannerLoader(mBannerLoader3);
        banner10.loadImagePaths(mRes);
        banner10.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        banner10Text.setText(mTitle.get(0));
        banner10Index.setText("1/" + mTitle.size());
        banner10.setBannerPagerChangedListener(new Banner.OnBannerPagerChangedListener() {
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > mTitle.size()) return;
                banner10Text.setText(mTitle.get(position));
                banner10Index.setText(position + 1 + "/" + mTitle.size());
            }
        });
        banner11.setBannerLoader(mBannerLoader);
        banner11.loadImagePaths(mURLs);
        banner11.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });
        barrageView.addTextItem("哇~这个人好牛逼!");
        barrageView.addTextItem("大蛇!");
        barrageView.addTextItem("秀儿是你吗？");
        barrageView.addTextItem("拿来朕的50m大刀");
        barrageView.addTextItem("这人是个傻逼~");
        barrageView.addTextItem("大人，是你吗？");
        barrageView.addTextItem("垃圾~");
        barrageView.addTextItem("我是VIP");
        barrageView.addTextItem("我是你爹，儿贼~~~");
        barrageView.addTextItem("2333333333333333!!");
        barrageView.addTextItem("jfdkjgl;daj00eif09ejfefj");
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
        banner2.startAutoPlay();
        banner3.startAutoPlay();
        banner4.startAutoPlay();
        banner5.startAutoPlay();
        banner6.startAutoPlay();
        banner7.startAutoPlay();
        banner8.startAutoPlay();
        banner9.startAutoPlay();
        banner10.startAutoPlay();
        banner11.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
        banner2.stopAutoPlay();
        banner3.stopAutoPlay();
        banner4.stopAutoPlay();
        banner5.stopAutoPlay();
        banner6.stopAutoPlay();
        banner7.stopAutoPlay();
        banner8.stopAutoPlay();
        banner9.stopAutoPlay();
        banner10.stopAutoPlay();
        banner11.stopAutoPlay();
    }

    private BannerLoader<String, ImageView> mBannerLoader = new BannerLoader<String, ImageView>() {
        @Override
        public void loadView(Context context, String path, ImageView imageView) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            Glide.with(context)
                    .load(path)
                    .apply(requestOptions)
                    .into(imageView);
        }

        @Override
        public ImageView createView(Context context) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }
    };

    private BannerLoader<String, View> mBannerLoader2 = new BannerLoader<String, View>() {
        @Override
        public void loadView(Context context, String path, View view) {
            ImageView banner = view.findViewById(R.id.custom_iv);
            TextView desc = view.findViewById(R.id.custom_tv);
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            Glide.with(context)
                    .load(path)
                    .apply(requestOptions)
                    .into(banner);
            desc.setText("我就是来看看位置!");
        }

        @Override
        public View createView(Context context) {
            View pager = LayoutInflater.from(context).inflate(R.layout.cv_custom_banner_item, null);
            return pager;
        }
    };

    private BannerLoader<Integer, ImageView> mBannerLoader3 = new BannerLoader<Integer, ImageView>() {
        @Override
        public void loadView(Context context, Integer path, ImageView imageView) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            Glide.with(context)
                    .load(path)
                    .apply(requestOptions)
                    .into(imageView);
        }

        @Override
        public ImageView createView(Context context) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }
    };


    @OnClick(R.id.refresh_tv)
    public void onViewClicked() {
        //刷新
        Toast.makeText(this, "刷新~", Toast.LENGTH_SHORT).show();
        banner.loadImagePaths(mURLs);
        banner2.loadImagePaths(mRes);
        banner3.loadImagePaths(mURLs);
        banner4.loadImagePaths(mURLs);
        banner5.loadImagePaths(mURLs);
        banner6.loadImagePaths(mURLs);
        banner7.loadImagePaths(mURLs);
        banner8.loadImagePaths(mURLs);
        banner9.loadImagePaths(mURLs);
        banner10.loadImagePaths(mRes);
        banner11.loadImagePaths(mURLs);
    }

}
