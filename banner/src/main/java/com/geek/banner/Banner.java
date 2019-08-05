package com.geek.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.geek.banner.constant.BannerConfig;
import com.geek.banner.loader.BannerLoader;
import com.geek.banner.transformer.complex.AlphaPageTransformer;
import com.geek.banner.transformer.complex.NonPageTransformer;
import com.geek.banner.transformer.complex.RotateDownPageTransformer;
import com.geek.banner.transformer.complex.RotateUpPageTransformer;
import com.geek.banner.transformer.complex.RotateYTransformer;
import com.geek.banner.transformer.complex.ScaleInTransformer;
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
import com.geek.banner.widget.BannerScroller;
import com.geek.banner.widget.BannerViewPager;
import com.geek.banner.widget.WeakHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HSL
 * @Time: 2018/12/13 15:09
 * @E-mail: xxx@163.com
 * @Description: 超级轮播图~
 */
public class Banner extends RelativeLayout implements ViewPager.OnPageChangeListener {

    public static final String TAG = "banner";
    public static final int NORMAL_EXTRA_NUM = 2;
    public static final int MULTIPAGE_EXTRA_NUM = 4;

    private BannerViewPager mViewPager;
    private LinearLayout mIndicatorLl;
    private ImageView mDefaultImg;

    private WeakHandler mWeakHandler = new WeakHandler();
    private ArrayList<View> mBannerItems = new ArrayList<>();
    private List<Object> mImagePaths = new ArrayList<>();

    //能否自动轮播
    private boolean mCanAutoPlay;
    //是否一屏多页
    private boolean mIsMultiPage;
    //是否显示指示器
    private boolean mShowIndicator;
    //轮播间隔时长
    private int mIntervalTime;
    //VP滚动时长
    private int mPagerScrollTime;
    //左右页可见的宽度
    private int mExposeWidth;
    //页面间距
    private int mPageSpacing;
    //单页轮播动画
    private int mSingleTransform;
    //多页轮播动画
    private int mMultiTransform;
    //Banner无数据默认显示
    private int mDefaultBanner;
    //指示器选中宽度
    private int mIndicatorSelectedW;
    //指示器默认宽度
    private int mIndicatorDefaultW;
    //指示器选中高度
    private int mIndicatorSelectedH;
    //指示器默认高度
    private int mIndicatorDefaultH;
    //指示器间距
    private int mIndicatorSpacing;
    //指示器距离底部高度
    private int mIndicatorMarginBottom;
    //指示器选中样式
    private int mIndicatorSelectD;
    //指示器默认样式
    private int mIndicatorDefaultD;

    //加载ViewPager页面实例
    private BannerLoader mBannerLoader;
    //当前Banner下标
    private int mCurrentIndex = 1;
    //当前指示器位置
    private int mCurIndicatorIndex = 0;
    //需要的页数
    private int mNeedPagers;
    //实际页数
    private int mRealPagers;

    private BannerPagerAdapter mBannerPagerAdapter;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.merge_banner, this);
        initAttributes(context, attrs, defStyleAttr);
        initView();
        initViewPagerScroll();
    }

    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        // TODO: 2018/11/30
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        mCanAutoPlay = ta.getBoolean(R.styleable.Banner_banner_auto_play, BannerConfig.CAN_AUTO_PLAY);
        mIsMultiPage = ta.getBoolean(R.styleable.Banner_banner_multi_page, BannerConfig.MULTI_PAGE);
        mShowIndicator = ta.getBoolean(R.styleable.Banner_banner_show_indicator, true);
        mDefaultBanner = ta.getResourceId(R.styleable.Banner_banner_default_image, R.drawable.no_banner);
        mIntervalTime = ta.getInteger(R.styleable.Banner_banner_interval_time, BannerConfig.INTERVAL_TIME);
        mPagerScrollTime = ta.getInteger(R.styleable.Banner_banner_scroll_time, BannerConfig.SCROLL_TIME);
        mExposeWidth = (int) ta.getDimension(R.styleable.Banner_banner_expose_width, BannerConfig.EXPOSE_WIDTH);
        mPageSpacing = (int) ta.getDimension(R.styleable.Banner_banner_page_spacing, BannerConfig.PAGE_SPACING);
        mIndicatorSelectedW = (int) ta.getDimension(R.styleable.Banner_indicator_select_width, dip2px(context, 8));
        mIndicatorDefaultW = (int) ta.getDimension(R.styleable.Banner_indicator_default_width, dip2px(context, 8));
        mIndicatorSelectedH = (int) ta.getDimension(R.styleable.Banner_indicator_select_height, dip2px(context, 8));
        mIndicatorDefaultH = (int) ta.getDimension(R.styleable.Banner_indicator_default_height, dip2px(context, 8));
        mIndicatorSpacing = (int) ta.getDimension(R.styleable.Banner_indicator_space, dip2px(context, 6));
        mIndicatorMarginBottom = (int) ta.getDimension(R.styleable.Banner_indicator_margin_bottom, dip2px(context, 10));
        mIndicatorSelectD = ta.getResourceId(R.styleable.Banner_indicator_select_drawable, R.drawable.shape_banner_select_indicator);
        mIndicatorDefaultD = ta.getResourceId(R.styleable.Banner_indicator_default_drawable, R.drawable.shape_banner_default_indicator);
        mSingleTransform = ta.getInteger(R.styleable.Banner_banner_single_anim, BannerConfig.PAGER_TRANSFORM);
        mMultiTransform = ta.getInteger(R.styleable.Banner_banner_multi_anim, BannerConfig.PAGER_TRANSFORM);
        ta.recycle();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mViewPager = findViewById(R.id.banner_vp);
        mIndicatorLl = findViewById(R.id.indicator_ll);
        mDefaultImg = findViewById(R.id.default_iv);
        //Banner数据为空时默认显示图片
        mDefaultImg.setImageResource(mDefaultBanner);
        initIndicator();
        initMultiPage();
        initBannerTransformer();
    }

    /**
     * 切换多页
     */
    private void initMultiPage() {
        if (!mIsMultiPage) {
            //非一屏三页，直接return
            return;
        }
        //关键之处:
        // 1.当clipChildren为false时，超出View的子页面，不会被切掉，仍然可以显示。
        // 2.设置为true，那么不管你的子View设置为多大子View左右的View都不会显示，会用空白代替。
        // 3.设置setPagerMargin()
        setClipChildren(false);
        LayoutParams params = (LayoutParams) mViewPager.getLayoutParams();
        params.leftMargin = mExposeWidth + mPageSpacing;
        params.rightMargin = mExposeWidth + mPageSpacing;
        mViewPager.setLayoutParams(params);
        setPagerMargin(mPageSpacing);
        //左右预加载两页即可，太多会加大内存消耗
        setOffscreenPageLimit(2);
    }

    /**
     * 初始化指示器
     */
    private void initIndicator() {
        if (mShowIndicator) {
            mIndicatorLl.setVisibility(VISIBLE);
        }
        int defaultMargin = dip2px(getContext(), 16);
        LayoutParams params = (LayoutParams) mIndicatorLl.getLayoutParams();
        if (mIsMultiPage) {
            //指示器
            params.leftMargin = mExposeWidth + mPageSpacing + defaultMargin;
            params.rightMargin = mExposeWidth + mPageSpacing + defaultMargin;
            params.bottomMargin = mIndicatorMarginBottom;
        } else {
            params.leftMargin = defaultMargin;
            params.rightMargin = defaultMargin;
            params.bottomMargin = mIndicatorMarginBottom;
        }
        mIndicatorLl.setLayoutParams(params);
    }

    /**
     * 设置ViewPager翻页时长
     */
    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            BannerScroller bannerScroller = new BannerScroller(mViewPager.getContext());
            bannerScroller.setDuration(mPagerScrollTime);
            mField.set(mViewPager, bannerScroller);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 初始化Banner动画
     */
    private void initBannerTransformer() {
        if (!mIsMultiPage) {
            if (mSingleTransform == 0) {
                //无动画，正常滚动
                setBannerTransformer(new DefaultTransformer());
            } else if (mSingleTransform == 1) {
                //挤压
                setBannerTransformer(new AccordionTransformer());
            } else if (mSingleTransform == 2) {
                //由小变大
                setBannerTransformer(new BackgroundToForegroundTransformer());
            } else if (mSingleTransform == 3) {
                //有大变小
                setBannerTransformer(new ForegroundToBackgroundTransformer());
            } else if (mSingleTransform == 4) {
                //矩形翻转
                setBannerTransformer(new CubeInTransformer());
            } else if (mSingleTransform == 5) {
                //缩小的矩形翻转
                setBannerTransformer(new CubeOutTransformer());
            } else if (mSingleTransform == 6) {
                //由浅到深
                setBannerTransformer(new DepthPageTransformer());
            } else if (mSingleTransform == 7) {
                //水平旋转
                setBannerTransformer(new FlipHorizontalTransformer());
            } else if (mSingleTransform == 8) {
                //垂直旋转
                setBannerTransformer(new FlipVerticalTransformer());
            } else if (mSingleTransform == 9) {
                //大风车
                setBannerTransformer(new RotateDownTransformer());
            } else if (mSingleTransform == 10) {
                //大风车
                setBannerTransformer(new RotateUpTransformer());
            } else if (mSingleTransform == 11) {
                //一小一大
                setBannerTransformer(new ScaleInOutTransformer());
            } else if (mSingleTransform == 12) {
                //滑走
                setBannerTransformer(new StackTransformer());
            } else if (mSingleTransform == 13) {
                //矩形翻转
                setBannerTransformer(new TabletTransformer());
            } else if (mSingleTransform == 14) {
                //逐渐走远
                setBannerTransformer(new ZoomInTransformer());
            } else if (mSingleTransform == 15) {
                //逐渐靠近
                setBannerTransformer(new ZoomOutTranformer());
            } else {
                //连环画
                setBannerTransformer(new ZoomOutSlideTransformer());
            }
        } else {
            if (mMultiTransform == 0) {
                //左右略透明，中间正常，滑动透明
                setBannerTransformer(new AlphaPageTransformer());
            } else if (mMultiTransform == 1) {
                setBannerTransformer(new NonPageTransformer());
            } else if (mMultiTransform == 2) {
                setBannerTransformer(new RotateDownPageTransformer());
            } else if (mMultiTransform == 3) {
                setBannerTransformer(new RotateUpPageTransformer());
            } else if (mMultiTransform == 4) {
                setBannerTransformer(new RotateYTransformer());
            } else {
                //左右缩小，中间正常，滑动缩放
                setBannerTransformer(new ScaleInTransformer());
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mCanAutoPlay) {
            //按下时，结束轮播
            //其他情况，正常轮播
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP
                    || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
                Log.d(TAG, "dispatchTouchEvent: 开始轮播");
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay();
                Log.d(TAG, "dispatchTouchEvent: 取消轮播");
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置页面间距
     *
     * @param marginPixels
     */
    public void setPagerMargin(int marginPixels) {
        if (mViewPager != null && mIsMultiPage) {
            mViewPager.setPageMargin(marginPixels);
        }
    }

    /**
     * 设置自动滚动
     *
     * @param autoPlay
     * @return
     */
    public Banner setAutoPlay(boolean autoPlay) {
        mCanAutoPlay = autoPlay;
        return this;
    }

    /**
     * 设置轮播间隔时长
     *
     * @param intervalTime
     * @return
     */
    public Banner setIntervalTime(int intervalTime) {
        mIntervalTime = intervalTime;
        return this;
    }

    /**
     * 设置Banner加载器
     *
     * @param bannerLoader
     * @return
     */
    public Banner setBannerLoader(BannerLoader bannerLoader) {
        mBannerLoader = bannerLoader;
        return this;
    }


    /**
     * 设置Banner预加载页数
     *
     * @param limit
     * @return
     */
    public Banner setOffscreenPageLimit(int limit) {
        if (mViewPager != null) {
            mViewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }

    /**
     * 设置Banner翻页动画
     *
     * @param transformer
     * @return
     */
    private Banner setBannerTransformer(PageTransformer transformer) {
        try {
            if (mViewPager != null) {
                mViewPager.setPageTransformer(true, transformer);
            }
        } catch (Exception e) {
            Log.e(TAG, "Please set the PageTransformer class");
        }
        return this;
    }

    /**
     * 设置Banner需要的数据
     * 第一个元素放置最后一张图
     * 最后一个元素放置第一张图
     * 为了无限轮播在最后一张过渡到第一张（或者第一张过渡到最后一张）时
     * 有正常的翻页动画，达到更好的体验效果
     *
     * <p>
     * 一屏多页，就得在多加两张图
     * 防止过渡时出现左边或者右边空白
     *
     * @param imagePaths 图片URL/uri/res
     * @return
     */
    public void loadImagePaths(List<?> imagePaths) {
        if (imagePaths == null || imagePaths.isEmpty()) {
            mDefaultImg.setVisibility(VISIBLE);
            Log.e(TAG, "The image data set is empty.");
            return;
        }
        mDefaultImg.setVisibility(GONE);
        mImagePaths.clear();
        mBannerItems.clear();
        mRealPagers = imagePaths.size();
        //清除仅一页时，添加的View
        View lastOnePager = findViewWithTag("only_one_pager");
        if (lastOnePager != null) removeView(lastOnePager);
        if (imagePaths.size() == 1) {
            //仅有一页时
            createOnlyOnePager(imagePaths.get(0));
            return;
        }
        //创建指示器
        createDefaultIndicator(mRealPagers);
        //数据源处理
        if (mIsMultiPage) {
            //多添加4页
            mNeedPagers = mRealPagers + MULTIPAGE_EXTRA_NUM;
            //mImagePaths第1个元素,为Banner倒数第二张图
            mImagePaths.add(imagePaths.get(mRealPagers - 2));
            //mImagePaths第2个元素,为Banner最后一张图
            mImagePaths.add(imagePaths.get(mRealPagers - 1));
            mImagePaths.addAll(imagePaths);
            //mImagePaths倒数第2个元素,为Banner第一张图
            mImagePaths.add(imagePaths.get(0));
            //mImagePaths最后一个元素,为Banner第二张图
            mImagePaths.add(imagePaths.get(1));
        } else {
            //多添加2页
            mNeedPagers = mRealPagers + NORMAL_EXTRA_NUM;
            //mImagePaths第一个元素
            //为Banner最后一张图
            mImagePaths.add(imagePaths.get(mRealPagers - 1));
            mImagePaths.addAll(imagePaths);
            //mImagePaths最后一个元素
            //为Banner第一张图
            mImagePaths.add(imagePaths.get(0));
        }
        //通知更新数据
        notifyBannerData();
        Log.d(TAG, "loadImagePaths: banner所需元素：" + mImagePaths);
    }

    /**
     * 更新数据
     */
    private void notifyBannerData() {
        //Banner的起始位置
        if (mIsMultiPage)
            mCurrentIndex = 2;
        else
            mCurrentIndex = 1;
        //适配器创建
        if (mBannerPagerAdapter == null) {
            mBannerPagerAdapter = new BannerPagerAdapter();
            mViewPager.addOnPageChangeListener(this);
        }
        //此处做一个简单说明
        //mBannerPagerAdapter.notifyDataSetChanged()无法重置Vp的初始状态，他只是销毁当前页面
        //以及左右缓存的页面，然后再重新创建销毁的页面实例，但仍然显示当前页，因为 VP中mCurItem = 0;scrollTo(0, 0);
        //没法重置，所以直接设置适配器，重置VP状态

        //如果notifyDataSetChanged()，然后再setCurrentItem(mCurrentIndex, false)，
        //这样处理，会先实例当前页以及需要缓存的页面，然后再回到mCurrentIndex，再实例mCurrentIndex页以及其左右
        //缓存页，感觉有些得不偿失

        //所以不如直接设置一次适配器
        mViewPager.setAdapter(mBannerPagerAdapter);
        mViewPager.setFocusable(true);
        mViewPager.setCurrentItem(mCurrentIndex, false);
        //是否自行滚动
        if (mCanAutoPlay) startAutoPlay();
    }

    /**
     * 获取Banner当前下标
     *
     * @return
     */
    public int getBannerCurrentIndex() {
        return findRealPosition(mCurrentIndex);
    }

    /**
     * 获取真正的下标
     */
    private int findRealPosition(int position) {
        if (mIsMultiPage) {
            if (position == 1) {
                //Last Pager
                return mRealPagers - 1;
            } else if (position == 2 || mNeedPagers - 2 == position) {
                //First Pager
                return 0;
            } else {
                return position - 2;
            }
        } else {
            if (position == 0) {
                //Last Pager
                return mRealPagers - 1;
            } else if (position == mNeedPagers - 1) {
                //First Pager
                return 0;
            } else {
                return position - 1;
            }
        }
    }

    /**
     * 开始自动轮播
     * 用于onStart/onResume
     * 便于更好的用于体验
     */
    public void startAutoPlay() {
        if (mCanAutoPlay && mRealPagers > 1) {
            mWeakHandler.removeCallbacks(mBannerPlayRunnable);
            mWeakHandler.postDelayed(mBannerPlayRunnable, mIntervalTime);
        }
    }

    /**
     * 结束自动轮播
     * 用于onPause/onStop
     * 便于更好的用于体验
     */
    public void stopAutoPlay() {
        if (mCanAutoPlay && mRealPagers > 1) {
            mWeakHandler.removeCallbacks(mBannerPlayRunnable);
        }
    }

    /**
     * 轮播任务
     */
    private final Runnable mBannerPlayRunnable = new Runnable() {
        @Override
        public void run() {
            mCurrentIndex++;
            if (mIsMultiPage) {
                //一屏多页
                //b c a b c a b
                if (mCurrentIndex == mNeedPagers - 1) {
                    //当前处于倒数第二页，为Banner第一张图
                    //此时过渡到真实的第一页（即下标为2）
                    mViewPager.setCurrentItem(2, false);
                    mWeakHandler.post(mBannerPlayRunnable);
                } else {
                    mViewPager.setCurrentItem(mCurrentIndex);
                    mWeakHandler.postDelayed(mBannerPlayRunnable, mIntervalTime);
                }
            } else {
                //一屏一页
                if (mCurrentIndex == mNeedPagers) {
                    //当下标=页数,说明此时正在显示"第一页"
                    //即：处于最后一页,那么需要立即过渡到真实的第一页（即下标为1）
                    //过渡时间基本可以忽略，过渡完成，马上进入else，播放下一页
                    //禁用翻页动画,过渡不易察觉
                    mViewPager.setCurrentItem(1, false);
                    mWeakHandler.post(mBannerPlayRunnable);
                } else {
                    mViewPager.setCurrentItem(mCurrentIndex);
                    mWeakHandler.postDelayed(mBannerPlayRunnable, mIntervalTime);
                }
            }
            Log.d(TAG, "run: 下标：" + mCurrentIndex);
        }
    };

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mBannerPagerChangedListener != null) {
            mBannerPagerChangedListener.onPageScrollStateChanged(state);
        }
        // 从名字可以看出这个方法主要用来监测viewpager的滑动状态：
        // 当viewpager处于空闲状态时 state=0，
        // 当我们手指按下时 state=1，
        // 当我们手指抬起时 state=2，
        // 所以我们完全可以在state=0时 去加载或者处理我们的事情，因为这时候滑动已经结束。

        //在我们快速滑动过程中，state=0只有在空闲时才会调用
        //这样当我们滑到第一页或者最后一页时，没有及时的过渡，导致我们滑不动，
        //但是state=1，滑动过程中总有按下或者抬起，这样他会在每次翻页时触发
        //用来及时的处理我们的逻辑再好不过，这样就完美避免了滑不动的尴尬

        //多点触碰滑动，不做处理，你非要这样，我也没办法
        switch (state) {
            case 0:
                Log.d(TAG, "onPageScrollStateChanged: 空闲");
                break;
            case 1:
                if (mIsMultiPage) {
                    if (mCurrentIndex == 1) {
                        //过渡到真实的最后一页(即：倒数第三页)
                        mViewPager.setCurrentItem(mNeedPagers - 3, false);
                    } else if (mCurrentIndex == mNeedPagers - 2) {
                        //处于倒数第二张，为Banner第一张图
                        //过渡到真实第一张(即：下标为2)
                        mViewPager.setCurrentItem(2, false);
                    }
                } else {
                    if (mCurrentIndex == 0) {
                        //处于第一页，为Banner最后一张图
                        //过渡到真实的最后一页(即：倒数第二页)
                        mViewPager.setCurrentItem(mNeedPagers - 2, false);
                    } else if (mCurrentIndex == mNeedPagers - 1) {
                        //处于最后一页，为banner第一张图
                        //过渡到真实的第一页(即：下标为1)
                        mViewPager.setCurrentItem(1, false);
                    }
                }
                Log.d(TAG, "onPageScrollStateChanged: 按下拖拽：" + mCurrentIndex);
                break;
            case 2:
                // TODO: 2018/12/3
                Log.d(TAG, "onPageScrollStateChanged: 抬起");
                break;
            default:
                //default
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // TODO: 2018/12/3
        if (mBannerPagerChangedListener != null) {
            mBannerPagerChangedListener.onPageScrolled(findRealPosition(position),
                    positionOffset,
                    positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentIndex = position;
        setSelectedIndicator(findRealPosition(position));
        if (mBannerPagerChangedListener != null) {
            mBannerPagerChangedListener.onPageSelected(findRealPosition(position));
        }
        Log.d(TAG, "onPageSelected: 当前位置：" + mCurrentIndex
                + "\n"
                + "实际位置：" + findRealPosition(position));
    }

    /**
     * Banner适配器
     */
    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImagePaths.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            if (mBannerLoader == null) {
                throw new RuntimeException("[Banner] --> The mBannerLoader is not null");
            }
            while (mBannerItems.size() <= position) {
                mBannerItems.add(null);
            }
            View bannerPager = mBannerItems.get(position);
            if (bannerPager == null) {
                bannerPager = mBannerLoader.createView(mViewPager.getContext());
                mBannerLoader.loadView(mViewPager.getContext(), mImagePaths.get(position), bannerPager);
                mBannerItems.set(position, bannerPager);
                bannerPager.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnBannerClickListener != null) {
                            mOnBannerClickListener.onBannerClick(findRealPosition(position));
                        }
                    }
                });
            }
            container.addView(bannerPager);
            return bannerPager;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 创建指示器
     *
     * @param size
     */
    private void createDefaultIndicator(int size) {
        if (!mShowIndicator) return;
        mIndicatorLl.removeAllViews();
        while (size > 0) {
            View view = new View(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorDefaultW, mIndicatorDefaultH);
            params.setMargins(mIndicatorSpacing, 0, 0, 0);
            view.setLayoutParams(params);
            view.setBackgroundResource(mIndicatorDefaultD);
            mIndicatorLl.addView(view);
            size--;
        }
    }

    /**
     * 设置选中指示器
     *
     * @param aimsPosition
     */
    private void setSelectedIndicator(int aimsPosition) {
        if (!mShowIndicator) return;
        int childCount = mIndicatorLl.getChildCount();
        if (aimsPosition > childCount - 1) return;
        View beforeChild = mIndicatorLl.getChildAt(mCurIndicatorIndex);
        ViewGroup.LayoutParams lastChildLayoutParams = beforeChild.getLayoutParams();
        lastChildLayoutParams.width = mIndicatorDefaultW;
        lastChildLayoutParams.height = mIndicatorDefaultH;
        beforeChild.setLayoutParams(lastChildLayoutParams);
        beforeChild.setBackgroundResource(mIndicatorDefaultD);
        View selectChild = mIndicatorLl.getChildAt(aimsPosition);
        ViewGroup.LayoutParams selectChildLayoutParams = selectChild.getLayoutParams();
        selectChildLayoutParams.width = mIndicatorSelectedW;
        selectChildLayoutParams.height = mIndicatorSelectedH;
        selectChild.setLayoutParams(selectChildLayoutParams);
        selectChild.setBackgroundResource(mIndicatorSelectD);
        mCurIndicatorIndex = aimsPosition;
    }

    /**
     * 当仅有一页时
     *
     * @param path
     */
    private void createOnlyOnePager(Object path) {
        View pagerOne = mBannerLoader.createView(mViewPager.getContext());
        mBannerLoader.loadView(mViewPager.getContext(), path, pagerOne);
        pagerOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBannerClickListener != null) {
                    mOnBannerClickListener.onBannerClick(0);
                }
            }
        });
        pagerOne.setTag("only_one_pager");
        //添加
        LayoutParams onePagerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(pagerOne, onePagerParams);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素)的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * INTERFACE
     * 处理Banner点击事件
     */
    public interface OnBannerClickListener {
        void onBannerClick(int position);
    }

    private OnBannerClickListener mOnBannerClickListener;

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        mOnBannerClickListener = onBannerClickListener;
    }

    /**
     * INTERFACE
     * banner点击
     */
    public interface OnBannerPagerChangedListener {

        void onPageScrollStateChanged(int state);

        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);
    }

    private OnBannerPagerChangedListener mBannerPagerChangedListener;

    public void setBannerPagerChangedListener(OnBannerPagerChangedListener bannerPagerChangedListener) {
        mBannerPagerChangedListener = bannerPagerChangedListener;
    }

}
