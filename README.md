# Android轮播图 - Banner

纵观Android古今，轮播图已然泛滥成灾！大神们各显神通大兴土木，所起楼台之高让后来者心生膜拜,纷纷Star！但不法分子也是层出不穷，为求"大神"的桂冠，挂羊头卖狗肉，一个镜像就试图去名满天下，鄙人正是此等不法分子！虽有点盗窃[youth5201314](https://github.com/youth5201314/banner)前辈的劳动成果，但也算是实实在在在前辈的肩膀上干了点实在事——帮前辈梳了梳头，整理了一下发型。毕竟盲仔说过：头可断，发型不能乱。但是在下还是得由衷感谢[youth5201314](https://github.com/youth5201314/banner)前辈，您造的法拉利我至少给您保养了一番！

## 功能特点
- 支持一屏三页
- 支持任意View显示在Banner上
- 支持自定义显示器
- 支持多种Banner动画
- 支持仿魅族Banner
- 创建的View内部已做缓存，性能更佳
- 一句话：更加精简，更加智能，更加好用！！！

## 有图有真相
![Banner1](https://github.com/AlpsDog/Banner/blob/master/banner1.png)
![Banner2](https://github.com/AlpsDog/Banner/blob/master/banner2.png)
![Banner3](https://github.com/AlpsDog/Banner/blob/master/banner3.png)
![Banner4](https://github.com/AlpsDog/Banner/blob/master/banner4.png)

### 新增仿魅族Banner
![Banner5](https://github.com/AlpsDog/Banner/blob/master/banner5.png)

## 简单用法
#### 1.添加依赖：
```
1.1 工程的build.gradle下添加：
    maven { url 'https://jitpack.io' }
1.2 模块的build.gradle下添加：
    implementation 'com.github.AlpsDog:Banner:v1.0.2'
```
#### 2.XML布局文件
```
 <com.geek.banner.Banner
            android:id="@+id/banner"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:layout_marginTop="20dp"
            app:banner_auto_play="true"
            app:banner_default_image="@drawable/banner_default"
            app:banner_expose_width="20dp"
            app:banner_interval_time="5000"
            app:banner_multi_anim="ScaleIn"
            app:banner_page_spacing="14dp"
            app:banner_scroll_time="1000"
            app:banner_show_indicator="true"
            app:banner_show_model="Multi"
            app:layout_scrollFlags="scroll" />
```
#### 3.在Activity/Fragment中
```
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
                Toast.makeText(BaseUseActivity.this, "点击了：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        // 3 翻页事件
        banner.setBannerPagerChangedListener(new Banner.OnBannerSimplePagerListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("hsl777", "onPageSelected: ==>" + position);
            }
        });

        // 4 最重要一步，加载数据
        banner.loadImagePaths(mData);
```

#### 4.数据源说明
```
// Banner内部定义的数据接口
public interface BannerEntry<T> {

    /**
     * 获取Banner数据源
     *
     * @return
     */
    T getBannerPath();

    /**
     * 获取指示器文本
     *
     * @return
     */
    String getIndicatorText();
}

// 实体类继承此接口
// getBannerPath()返回图片加载的源
// getIndicatorText()返回Banner摘要,非必须
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
        //此处返回图片加载的源，可以是 URL RES FILE...
        return path;
    }

    @Override
    public String getIndicatorText() {
        //此处返回Banner摘要,非必须
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
```
#### 5.加载器说明
```
//自定义Banner显示的View时，可用此加载器
public interface BannerLoader<T, V extends View> {

    /**
     * 加载VIEW
     *
     * @param context
     * @param entry
     * @param position  显示的位置
     * @param imageView
     */
    void loadView(Context context, BannerEntry entry, int position, V imageView);

    /**
     * 创建View
     *
     * @param context
     * @param position
     * @return
     */
    V createView(Context context, int position);
}

//简化版加载器
public abstract class ImageLoader<T> implements BannerLoader<T, ImageView> {

    @Override
    public ImageView createView(Context context, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}

//关于怎样自定义Banner显示的View，可参考Demo
```
#### 6.自定义属性说明
```
banner_auto_play  :  能否自动轮播
banner_show_model  :  显示模式（一屏一页、一屏三页、仿魅族Banner）
banner_interval_time  :  轮播间隔
banner_scroll_time  :  翻页时长（决定翻页的速度）
banner_single_anim  :  一屏一页动画
banner_default_image  :  数据为空时，默认显示图
banner_expose_width  :  一屏三页，左右两页露出的宽度
banner_page_spacing  :  一屏三页，页间距
banner_multi_anim  :  一屏三页动画
banner_mz_overlap  :  仿魅族Banner时，中间Page压着左右两边page的宽度
banner_mz_anim  :  仿魅族Banner动画
banner_show_indicator  :  是否显示指示器
indicator_select_width  :  选中的指示器宽度
indicator_select_height  :  选中的指示器高度
indicator_default_width  :  未选中指示器宽度
indicator_default_height  :  未选中指示器高度
indicator_space  :  指示器间隔
indicator_margin_bottom  :  指示器距底部距离
indicator_select_drawable  :  指示器选中的样式
indicator_default_drawable  :  指示器未选中样式
```
## 版本说明
##### v1.0.2正式版
```
新增仿魅族Banner
```
##### v1.0.1正式版
```
1.对内部结构进行了优化
2.增加数据源接口，规范使用
```
##### V1.0.0正式版
```
1.轮播图使用示例
2.轮播图LIB源码
```
## 鸣谢
感谢[youth5201314](https://github.com/youth5201314/banner)前辈的Banner

感谢[张鸿洋](https://github.com/hongyangAndroid/MagicViewPager)大师的ViewPager一屏三页动画

**感谢大家对鄙人的支持**

![HEAD](https://github.com/AlpsDog/Banner/blob/master/linshao.png)




