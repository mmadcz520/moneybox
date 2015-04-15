package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView
{
    private int lastY = 0;

    public MyScrollView(Context context)
    {
        super(context);

    }

    public MyScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        super.onInterceptTouchEvent(event);
        return false;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);

        int action=event.getAction();

        int offset = 0;

        switch(action){
            case MotionEvent.ACTION_DOWN:
                lastY = (int)event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                offset = lastY - (int)event.getY();
                Log.e("offset", "--------------" + this.getScrollY() +"-------"+ getHeight() + "-------------------" + getMeasuredHeight());
                if(getScrollY() + getHeight() >=  computeVerticalScrollRange())
                {
                   return false;
                }
                break;

        }

        if(offset > 0)
        {
            Log.e("CT_DEMO", " 向上");
            return  false;
        }
        else
        {
            Log.e("CT_DEMO", " 向下");
        }

        return true;
    }

}