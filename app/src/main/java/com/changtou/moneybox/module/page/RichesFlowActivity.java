package com.changtou.moneybox.module.page;

import android.graphics.Color;
import android.os.Bundle;

import com.changtou.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.decorator.HighlightWeekendsDecorator;
import com.prolificinteractive.materialcalendarview.decorator.OneDayDecorator;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/25.
 * 现金流界面
 */
public class RichesFlowActivity extends CTBaseActivity implements OnDateChangedListener
{
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private MaterialCalendarView widget;

    @Override
    protected void initView(Bundle bundle)
    {
        super.setContentView(R.layout.riches_datapicker);

        widget = (MaterialCalendarView) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(true);
        widget.setArrowColor(getResources().getColor(R.color.ct_blue));
        widget.setSelectionColor(getResources().getColor(R.color.ct_blue));

        widget.addDecorators(
                oneDayDecorator
        );

        //设置当日日期
        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());
    }

    public void onDateChanged(MaterialCalendarView widget, CalendarDay date)
    {

    }
}
