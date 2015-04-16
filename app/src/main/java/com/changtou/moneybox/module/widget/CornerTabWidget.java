package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changtou.R;

/**
 * 描述： 自定义圆角TableWiget
 *        提供addTab方法
 *
 */
public class CornerTabWidget extends LinearLayout implements View.OnClickListener
{
    private Context mContext = null;

    private String[] mTabs = null;

    private CornerTabWidget mCornerTabWidget = this;

    public interface TabListener
    {
        void changePage(int pageId);
    }

    private TabListener mTabListener = null;

    public CornerTabWidget(Context context)
    {
        super(context);
        this.setBackgroundResource(R.drawable.icon_more);
        this.setOrientation(LinearLayout.HORIZONTAL);

    }

    public CornerTabWidget(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;
        this.setBackgroundResource(R.drawable.tabwidget_bg_corner);
        this.setOrientation(LinearLayout.HORIZONTAL);

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            public void onGlobalLayout()
            {
                int width = mCornerTabWidget.getWidth();
                int height = mCornerTabWidget.getHeight();

                Log.e("width", "         " + width);

                for (int i = 0; i < mTabs.length; i++)
                {
                    final TextView t = new TextView(mContext);

                    t.setWidth(width / mTabs.length);
                    t.setHeight(height);
                    t.setText(mTabs[i]);
                    LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    t.setLayoutParams(layoutParams);
                    t.setGravity(Gravity.CENTER);
                    t.setFocusable(true);
                    t.setOnClickListener(mCornerTabWidget);

                    if (i == 0)
                    {
                        t.setBackgroundResource(R.drawable.tabwidget_item_left);
                    }
                    else if (i == (mTabs.length - 1))
                    {
                        t.setBackgroundResource(R.drawable.tabwidget_item_right);
                    }
                    else
                    {
                        t.setBackgroundResource(R.drawable.tabwidget_item_center);
                    }

                    mCornerTabWidget.addView(t);
                }
                mCornerTabWidget.getChildAt(0).setSelected(true);
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }


    protected void dispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);
    }

    /**
     * 在LinearLayout 上绘制分割线
     * @param canvas
     */
    protected void onDraw(Canvas canvas)
    {
        int width = this.getWidth();
        int height = this.getHeight();
        int count = mTabs.length-1;

        Paint localPaint;
        localPaint = new Paint();
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(getContext().getResources().getColor(R.color.ct_blue));
        localPaint.setStrokeWidth((float) 4.0);

        for(int i = 1; i < count+1; i++)
        {
            canvas.drawLine((width/(count+1))*(count+1-i), 0, (width/(count+1))*(count+1-i), height, localPaint);
        }
    }

    public void setTabs(String[] tabs)
    {
        this.mTabs = tabs;
    }

    /**
     * Tab 页切换事件向阳
     * @param v
     */
    public void onClick(View v)
    {
        TextView tv = (TextView)v;
        String content = tv.getText().toString();

        for(int i = 0; i < mTabs.length; i++)
        {
            if(mTabs[i].equals(content))
            {
                this.getChildAt(i).setSelected(true);
                mTabListener.changePage(i);
            }
            else
            {
                this.getChildAt(i).setSelected(false);
            }
        }
    }

    /**
     * 设置页面切换监听
     *
     * @param tabListener 监听回调
     */
    public void setTabListener(TabListener tabListener)
    {
        this.mTabListener = tabListener;
    }

    public void setCurrentTab(int index)
    {
        for(int i = 0; i < mTabs.length; i++)
        {
            if(i==index)
            {
                this.getChildAt(i).setSelected(true);
            }
            else
            {
                this.getChildAt(i).setSelected(false);
            }
        }
    }
}
