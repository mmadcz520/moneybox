package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

import com.changtou.R;

/**
 * 描述:自定义圆角ListView
 *
 * @author zhoulongfei
 * @since 2015-3-25
 */
public class ExCornerListView extends ListView{

    public ExCornerListView(Context context) {
        super(context);
    }

    public ExCornerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ExCornerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写此方式实现不同行的样式不一样
     *
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            //
            case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                //返回记录数据行数
                int itemnum = pointToPosition(x, y);

                if (itemnum == AdapterView.INVALID_POSITION)
                    break;
                else{
//                    setSelector(R.drawable.app_list_corner_round_top); //多行且第一行的样式
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
