package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.changtou.R;
import com.changtou.moneybox.common.utils.AppUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 *
 * @author xiaanming
 */
public class RoundProgressBar extends LinearLayout
{
    /**
     * 画笔对象的引用
     */
    private Paint paint;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    private int textColor;

    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private int max;

    /**
     * 当前进度
     */
    private int progress;

    /**
     * 是否显示中间的进度
     */
    private boolean textIsDisplayable;

    /**
     * 进度的风格，实心或者空心
     */
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    //折线路径
    private final Path mPath1 = new Path();
    private final Paint mGesturePaint = new Paint();
    int width = getWidth();
    int height = getHeight();

    private ViewGroup mThisView = this;

    private int mPercent = 0;

    //开始绘制折线
    private boolean isDrawLine = false;

    private Context mContext = null;

    Timer timer = new Timer();

    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
//                playHeartbeatAnimation();
            }
            super.handleMessage(msg);
        }

        ;
    };


    public RoundProgressBar(Context context)
    {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;

        paint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

        mTypedArray.recycle();
        this.setWillNotDraw(false);

        //路径绘制动画
        mGesturePaint.setAntiAlias(true);
        mGesturePaint.setStyle(Paint.Style.STROKE);
        mGesturePaint.setColor(Color.BLACK);

        timer.schedule(task, 1000, 5000);
    }

    /**
     * 初始化画笔
     */
    private void initPaint()
    {

    }

    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
    }

    /**
     * 绘制图形
     * @param canvas
     */
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();

        /**
         * 画最外层的大圆环
         */
        int centre = height / 2; //获取圆心的x坐标
        int radius = (int) (centre - roundWidth / 2); //圆环的半径

        /**
         * 画圆弧 ，画圆环的进度
         */
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setColor(roundProgressColor);  //设置进度的颜色
        RectF oval = new RectF(width/2 - radius, centre - radius, width/2
                + radius, centre + radius);  //用于定义的圆弧的形状和大小的界限

        switch (style)
        {
            case FILL:
            {
                paint.setStyle(Paint.Style.STROKE);
                paint.setAntiAlias(true);
                paint.setDither(true);
                paint.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawArc(oval, 270,(360 * mPercent + 270) / max, false, paint);  //根据进度画圆弧

                //内圆
                paint.setStrokeWidth(0);
                paint.setColor(textColor);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setDither(true);
                canvas.drawArc(new RectF(width/2 - (radius - roundWidth / 2), centre - (radius - roundWidth / 2), width/2 + (radius - roundWidth / 2), centre + (radius - roundWidth / 2)), 0, 360, true, paint);
                break;
            }
            case STROKE:
            {
                paint.setColor(roundColor); //设置圆环的颜色
                paint.setStyle(Paint.Style.STROKE); //设置空心
                paint.setStrokeWidth(roundWidth); //设置圆环的宽度
                paint.setAntiAlias(true);  //消除锯齿
                canvas.drawCircle(width / 2, width / 2, radius, paint); //画出圆环

                paint.setColor(textColor);
                paint.setStrokeCap(Paint.Cap.ROUND);
//                if (progress != 0)
                    canvas.drawArc(oval, 270, (360 * 80 + 270)  / max, false, paint);
                break;
            }
        }

//        if (mPercent != 0)
//        {
//            float textWidth = paint.measureText(mPercent + "%");
//            paint.setTextSize(AppUtil.dip2px(mContext, 18));
//            paint.setStrokeWidth(0.5f);
//            paint.setColor(Color.WHITE);
//            canvas.drawText(mPercent + "%", width / 2 - textWidth / 2, centre, paint); //画出进度百分比
//        }

        if(isDrawLine) drawLine(canvas);
    }

    /**
     * 绘制折线
     */
    private void drawLine(Canvas canvas)
    {
        int startX = width/2 -AppUtil.dip2px(mContext,70);
        int startY = height/2 + AppUtil.dip2px(mContext, 10);
        int startX1 = width/2 + AppUtil.dip2px(mContext,70);

        //绘制折线
        mGesturePaint.setStrokeWidth(AppUtil.dip2px(mContext, 1));
        mPath1.moveTo(startX, startY);
        mPath1.lineTo(startX - AppUtil.dip2px(mContext, 10), startY - AppUtil.dip2px(mContext, 16));
        mPath1.lineTo(0, startY - AppUtil.dip2px(mContext, 16));
        canvas.drawPath(mPath1, mGesturePaint);

        mPath1.moveTo(startX1, startY);
        mPath1.lineTo(startX1 + AppUtil.dip2px(mContext, 10), startY + AppUtil.dip2px(mContext, 16));
        mPath1.lineTo(width, startY + AppUtil.dip2px(mContext, 16));
        canvas.drawPath(mPath1, mGesturePaint);

        //绘制点
        paint.setStrokeWidth(AppUtil.dip2px(mContext, 5));
        canvas.drawPoint(startX, startY, paint);
        canvas.drawPoint(startX1,startY, paint);
    }

    public synchronized int getMax()
    {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max)
    {
        if (max < 0)
        {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress()
    {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress)
    {
        if (progress < 0)
        {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max)
        {
            progress = max;
        }
        if (progress <= max)
        {
            this.progress = progress;
        }
        //开始进度刷新动画
        startLoadingAnimation(progress);
    }


    public int getCricleColor()
    {
        return roundColor;
    }

    public void setCricleColor(int cricleColor)
    {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor()
    {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor)
    {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor()
    {
        return textColor;
    }

    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }

    public float getTextSize()
    {
        return textSize;
    }

    public void setTextSize(float textSize)
    {
        this.textSize = textSize;
    }

    public float getRoundWidth()
    {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth)
    {
        this.roundWidth = roundWidth;
    }


    /**
     * 开始进度动画
     */
    public void startLoadingAnimation(int progress)
    {
        //Loading 动画
        ObjectAnimator loadingAnim = ObjectAnimator.ofInt(this, "loading", 0, progress);
        loadingAnim.setDuration(1500);
        loadingAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        loadingAnim.start();

//        ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(this, "path", 0, 200);
//        objectAnimator.setDuration(8000);
//        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        objectAnimator.start();

        //动画监听事件
        loadingAnim.addListener(new AnimatorListenerAdapter()
        {
            public void onAnimationEnd(Animator animation)
            {
                isDrawLine = true;
                postInvalidate();
                playHeartbeatAnimation();
            }
        });
    }

    /**
     * 页面载入时loading动画
     */
    private void setLoading(int loading)
    {
        setPercent(loading);
        postInvalidate();
    }


    //动画效果
    private void playHeartbeatAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.8f));

        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(1.2f, 1.0f, 1.2f,
                        1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.addAnimation(new AlphaAnimation(0.8f, 1.0f));

                animationSet.setDuration(600);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(false);

                // 实现心跳的View
                LinearLayout ll =  (LinearLayout)mThisView.getChildAt(0);
                ll.getChildAt(0).startAnimation(animationSet);
            }
        });

        // 实现心跳的View
        LinearLayout ll =  (LinearLayout)mThisView.getChildAt(0);
        ll.getChildAt(0).startAnimation(animationSet);
    }

    private synchronized void setPercent(int progress)
    {
        this.mPercent = (int) (((float) progress / (float) max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
    }

}
