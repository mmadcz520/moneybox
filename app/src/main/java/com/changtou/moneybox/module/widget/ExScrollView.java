package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 描述: 有弹性的ScrollView
 *
 * Created by Administrator on 2015/4/2.
 */
public class ExScrollView extends ScrollView{

    private View inner;
    private float y;
    private Rect normal = new Rect();
    private boolean animationFinish = true;

    private float start_y;
    private boolean refresh = true;

    RefreshAbleListener mRefreshAbleListener = null;

    public ExScrollView(Context context) {
        super(context);
    }

    public ExScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent ev) {
        if (animationFinish) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    y = ev.getY();
                    super.onTouchEvent(ev);
                    break;
                case MotionEvent.ACTION_UP:
                    y = 0;
                    refresh = true;
                    if (isNeedAnimation()) {
                        animation();
                    }
                    super.onTouchEvent(ev);
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float preY = y == 0 ? ev.getY() : y;
                    float nowY = ev.getY();
                    int deltaY = (int) (preY - nowY);
                    // 滚动
//              scrollBy(0, deltaY);

                    y = nowY;
                    // 当滚动到最上或者最下时就不会再滚动，这时移动布局

                    refresh();
                    if (isNeedMove()) {
                        if (normal.isEmpty()) {
                            // 保存正常的布局位置
                            normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                        }
                        // 移动布局
                        inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2, inner.getRight(), inner.getBottom() - deltaY / 2);
                    } else {
                        super.onTouchEvent(ev);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    // 开启动画移动

    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, normal.top - inner.getTop());
        ta.setDuration(200);
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationFinish = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                inner.clearAnimation();
                // 设置回到正常的布局位置
                inner.layout(normal.left, normal.top, normal.right, normal.bottom);
                normal.setEmpty();
                animationFinish = true;
            }
        });
        inner.startAnimation(ta);
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    // 是否需要移动布局
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();

        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    // 调用刷新控件
    public void refresh()
    {
        int offset = (int)y - (int)start_y;
        if(refresh)
        {
            start_y = y;
            refresh = false;
        }

        if(offset > 200)
        {
            mRefreshAbleListener.refreshAble();
        }
    }

    /**
     * 是否开启下拉刷新控件
     */
    public interface RefreshAbleListener{
        void refreshAble();
    }

    /**
     * 设置下拉刷新监听
     */
    public void setRefreshAbleListener(RefreshAbleListener refreshAbleListener){
        this.mRefreshAbleListener = refreshAbleListener;
    }
}
