package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/3/23.
 */
public class ExViewPager extends ViewPager{

    private boolean isCanScroll = true;

    public ExViewPager(Context context) {
        super(context);
    }

    public ExViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        if (isCanScroll)
            return super.onInterceptTouchEvent(arg0);
        return false;
    }

    public boolean onTouchEvent(MotionEvent arg0) {
        return false;
    }
}
