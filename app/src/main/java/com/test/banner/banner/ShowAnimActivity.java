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
import com.geek.banner.transformer.simple.AccordionTransformer;
import com.geek.banner.transformer.simple.BackgroundToForegroundTransformer;
import com.geek.banner.transformer.simple.CubeInTransformer;
import com.geek.banner.transformer.simple.CubeOutTransformer;
import com.geek.banner.transformer.simple.DefaultTransformer;
import com.geek.banner.transformer.simple.DepthPageTransformer;
import com.geek.banner.transformer.simple.FlipHorizontalTransformer;
import com.geek.banner.transformer.simple.FlipVerticalTransformer;
import com.geek.banner.transformer.simple.ForegroundToBackgroundTransformer;
import com.geek.banner.transformer.simple.RotateDownTransformer;
import com.geek.banner.transformer.simple.RotateUpTransformer;
import com.geek.banner.transformer.simple.ScaleInOutTransformer;
import com.geek.banner.transformer.simple.StackTransformer;
import com.geek.banner.transformer.simple.TabletTransformer;
import com.geek.banner.transformer.simple.ZoomInTransformer;
import com.geek.banner.transformer.simple.ZoomOutSlideTransformer;
import com.geek.banner.transformer.simple.ZoomOutTranformer;
import com.test.banner.R;
import com.test.banner.widget.flowlayout.FlowLayout;
import com.test.banner.widget.flowlayout.TagAdapter;
import com.test.banner.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author: HSL
 * @Time: 2019/8/7 9:58
 * @E-mail: xxx@163.com
 * @Description: 展示BANNER动画，动画取自：
 * https://github.com/youth5201314/banner
 * https://github.com/hongyangAndroid/MagicViewPager
 * 感谢两位大神
 */
public class ShowAnimActivity extends AppCompatActivity {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.banner_2)
    Banner banner2;
    @BindView(R.id.single_tfl)
    TagFlowLayout singleTfl;
    @BindView(R.id.multi_tfl)
    TagFlowLayout multiTfl;

    private List<String> mData = new ArrayList<>();
    private List<String> mSingle = new ArrayList<>();
    private List<String> mMulti = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, ShowAnimActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_anim);
        ButterKnife.bind(this);
        initData();
        initBanner();
        initBanner2();
        initTFL();
        initTFL2();
    }

    private void initData() {
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565154160895&di=ca29eee5a6ff2dea4e3ec15761e0f13b&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F03%2F75%2Fe140fc12da7ac89783b3dd0698d6b2ea.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565154160895&di=791b5647e6f58fc49ba134a09a4fc77a&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F04%2F95%2F44%2F605925c4ae73de4.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565154160895&di=8a4bb565aad507c119e5e5c7d6c88ecb&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01654f5777e1a70000012e7eefe905.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565154160894&di=527d9758d2e2518ad7e0b52236871665&imgtype=0&src=http%3A%2F%2Fku.90sjimg.com%2Fback_pic%2F00%2F03%2F20%2F58561dc0b9c8bd7.jpg");
        mData.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1565154160894&di=a177dc95e640d1c9564000ba8682725d&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F02%2F72%2Fs_1198_e160f1f6a2a449109f20536ad2100ae0.jpg");

        mSingle.add("Default");
        mSingle.add("Accordion");
        mSingle.add("BackgroundToForeground");
        mSingle.add("ForegroundToBackground");
        mSingle.add("CubeIn");
        mSingle.add("CubeOut");
        mSingle.add("DepthPage");
        mSingle.add("FlipHorizontal");
        mSingle.add("FlipVertical");
        mSingle.add("RotateDown");
        mSingle.add("RotateUp");
        mSingle.add("ScaleInOut");
        mSingle.add("Stack");
        mSingle.add("Tablet");
        mSingle.add("ZoomIn");
        mSingle.add("ZoomOut");
        mSingle.add("ZoomOutSlide");

        mMulti.add("AlphaPage");
        mMulti.add("NonPage");
        mMulti.add("RotateDownPage");
        mMulti.add("RotateUpPage");
        mMulti.add("RotateY");
        mMulti.add("ScaleIn");

    }

    private void initBanner() {
        //First 创建BannerLoader
        // TODO: 2019/8/6 创建BannerLoader

        //second 设置BannerLoader
        banner.setBannerLoader(mBannerLoader);

        //third 设置页面点击事件
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(ShowAnimActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
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

    private void initBanner2() {
        //First 创建BannerLoader
        // TODO: 2019/8/6 创建BannerLoader

        //second 设置BannerLoader
        banner2.setBannerLoader(mBannerLoader);

        //third 设置页面点击事件
        banner2.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                Toast.makeText(ShowAnimActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        //four 翻页事件
        banner2.setBannerPagerChangedListener(new Banner.OnBannerSimplePagerListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("hsl777", "onPageSelected: ==>" + position);
            }
        });

        //last 最重要一步，加载数据
        banner2.loadImagePaths(mData);
    }

    private void initTFL() {
        singleTfl.setAdapter(new TagAdapter<String>(mSingle) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(ShowAnimActivity.this)
                        .inflate(R.layout.banner_amin_flow_tab, singleTfl, false);
                tv.setText(s);
                return tv;
            }

        });
        singleTfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(ShowAnimActivity.this, mSingle.get(position), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        singleTfl.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if (!selectPosSet.isEmpty()) {
                    for (Integer integer : selectPosSet) {
                        if (integer == 0) {
                            banner.setBannerTransformer(new DefaultTransformer());
                        } else if (integer == 1) {
                            banner.setBannerTransformer(new AccordionTransformer());
                        } else if (integer == 2) {
                            banner.setBannerTransformer(new BackgroundToForegroundTransformer());
                        } else if (integer == 3) {
                            banner.setBannerTransformer(new ForegroundToBackgroundTransformer());
                        } else if (integer == 4) {
                            banner.setBannerTransformer(new CubeInTransformer());
                        } else if (integer == 5) {
                            banner.setBannerTransformer(new CubeOutTransformer());
                        } else if (integer == 6) {
                            banner.setBannerTransformer(new DepthPageTransformer());
                        } else if (integer == 7) {
                            banner.setBannerTransformer(new FlipHorizontalTransformer());
                        } else if (integer == 8) {
                            banner.setBannerTransformer(new FlipVerticalTransformer());
                        } else if (integer == 9) {
                            banner.setBannerTransformer(new RotateDownTransformer());
                        } else if (integer == 10) {
                            banner.setBannerTransformer(new RotateUpTransformer());
                        } else if (integer == 11) {
                            banner.setBannerTransformer(new ScaleInOutTransformer());
                        } else if (integer == 12) {
                            banner.setBannerTransformer(new StackTransformer());
                        } else if (integer == 13) {
                            banner.setBannerTransformer(new TabletTransformer());
                        } else if (integer == 14) {
                            banner.setBannerTransformer(new ZoomInTransformer());
                        } else if (integer == 15) {
                            banner.setBannerTransformer(new ZoomOutTranformer());
                        } else {
                            banner.setBannerTransformer(new ZoomOutSlideTransformer());
                        }
                    }
                }
            }
        });

    }

    private void initTFL2() {
        multiTfl.setAdapter(new TagAdapter<String>(mMulti) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(ShowAnimActivity.this)
                        .inflate(R.layout.banner_amin_flow_tab, singleTfl, false);
                tv.setText(s);
                return tv;
            }
        });
        multiTfl.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(ShowAnimActivity.this, "动态设置一屏三页动画，会混乱，请在布局文件中设置", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        multiTfl.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
//                if (!selectPosSet.isEmpty()) {
//                    for (Integer integer : selectPosSet) {
//                        if (integer == 0) {
//                            banner2.setBannerTransformer(new AlphaPageTransformer());
//                        } else if (integer == 1) {
//                            banner2.setBannerTransformer(new NonPageTransformer());
//                        } else if (integer == 2) {
//                            banner2.setBannerTransformer(new RotateDownPageTransformer());
//                        } else if (integer == 3) {
//                            banner2.setBannerTransformer(new RotateUpPageTransformer());
//                        } else if (integer == 4) {
//                            banner2.setBannerTransformer(new RotateYTransformer());
//                        } else {
//                            banner2.setBannerTransformer(new ScaleInTransformer());
//                        }
//                    }
//                }
            }
        });
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

    /**
     * 加载器
     * 第一个泛型：图片的URL FILE res...等
     * 第二个泛型：banner显示的View，可以是任意View
     */
    private BannerLoader<String, ImageView> mBannerLoader = new BannerLoader<String, ImageView>() {

        /**
         * 如何加载图片资源，由自己决定
         * @param context
         * @param path
         * @param imageView
         */
        @Override
        public void loadView(Context context, String path, ImageView imageView) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.banner_default)
                    .error(R.drawable.banner_default)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            Glide.with(context).load(path).apply(requestOptions).into(imageView);
        }

        /**
         * 怎样创建View，也由自己决定
         * @param context
         * @return
         */
        @Override
        public ImageView createView(Context context) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }
    };
}
