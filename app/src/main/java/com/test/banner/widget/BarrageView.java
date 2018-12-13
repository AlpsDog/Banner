package com.test.banner.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: HSL
 * @Time: 2018/12/12 14:53
 * @E-mail: xxx@163.com
 * @Description: 弹幕~
 */
public class BarrageView extends View implements Runnable {

    //弹幕集合
    private List<TextItem> items = new ArrayList<TextItem>();
    //随机数,用于取得x,y坐标,颜色值，以及移动速度
    private Random random = new Random();
    //画笔
    private Paint paint;


    public BarrageView(Context context) {
        this(context, null);
    }

    public BarrageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        //开启死循环线程让弹幕动起来
        new Thread(this).start();
    }

    /**
     * 添加弹幕
     *
     * @param "文本内容"
     */
    public void addTextItem(final String content) {
        post(new Runnable() {
            @Override
            public void run() {
                //随机x
                float x = random.nextFloat() * getWidth() - 30;
                //稍微处理了一下y坐标，让字幕不紧贴边缘
                float y = Math.abs(random.nextFloat() * (getHeight() - 50)) + 40;
                float step = random.nextFloat() * 50;
                int r = random.nextInt(255);
                int g = random.nextInt(255);
                int b = random.nextInt(255);
                TextItem textItem = new TextItem(content, x, y, step, Color.rgb(r, g, b));
                items.add(textItem);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //把文字画出来
        for (TextItem item : items) {
            paint.setColor(item.getTextColor());
            canvas.drawText(item.getContent(), item.getFx(), item.getFy(), paint);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                for (TextItem item : items) {
                    item.setPerStep();
                }
                postInvalidate();//每隔500毫秒重绘视图
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 弹幕类
     */
    class TextItem {
        private String content;//文本内容
        private float fx;//x坐标
        private float fy;//y坐标
        private float perStep;//移动速度
        private int textColor;//字体颜色

        public TextItem(String content, float fx, float fy, float perStep, int textColor) {
            this.content = content;
            this.fx = fx;
            this.fy = fy;
            this.perStep = perStep;
            this.textColor = textColor;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public float getFx() {
            return fx;
        }

        public void setFx(float fx) {
            this.fx = fx;
        }

        public int getTextColor() {
            return textColor;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;
        }

        public float getFy() {
            return fy;
        }

        public void setFy(float fy) {
            this.fy = fy;
        }

        public float getPerStep() {
            return perStep;
        }

        public void setPerStep() {
            fx -= perStep;//调用此方法实现x坐标向左移动
        }
    }
}
