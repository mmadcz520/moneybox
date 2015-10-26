package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView
{
    private int lastY = 0;

    private boolean isDown = false;

    private EventChanageListener mEventChanageListener = null;

    public interface EventChanageListener
    {
        void change();
    }

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
                break;
            case MotionEvent.ACTION_UP:
                if(getScrollY() + getHeight() >= computeVerticalScrollRange())
                {
                    isDown = true;
                }
                else
                {
                    isDown = false;
                }
                break;
        }

        // 滑动到底部
        if(isDown && (offset > 0))
        {
            if(mEventChanageListener != null)
            {
                mEventChanageListener.change();
            }
        }

        //滑动到顶部
        if(this.getScrollY() == 0  && (offset < 0))
        {

        }
        return true;
    }

    public void setEventChanageListener(EventChanageListener eventChanageListener)
    {
        this.mEventChanageListener = eventChanageListener;
    }
}