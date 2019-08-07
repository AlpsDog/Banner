package com.test.banner.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.test.banner.R;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * @Project: Banner
 * @Package: com.test.banner.widget
 * @Author: HSL
 * @Time: 2019/08/07 14:19
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class BannerView extends RelativeLayout {

    private JzvdStd mVideo;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.merge_banner_view, this);
        mVideo = findViewById(R.id.jz_video);
        mVideo.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , "饺子闭眼睛");
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.banner_default)
                .error(R.drawable.banner_default)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(context)
                .load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
                .apply(requestOptions)
                .into(mVideo.thumbImageView);
    }

    public void startPlay() {
        if (mVideo != null) {
            mVideo.startVideo();
        }
    }

    public void stopPlay() {
        Jzvd.releaseAllVideos();
    }
}
