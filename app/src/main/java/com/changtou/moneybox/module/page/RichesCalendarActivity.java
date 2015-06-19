package com.changtou.moneybox.module.page;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.changtou.R;
import com.changtou.moneybox.module.adapter.FlowItemAdapter;
import com.changtou.moneybox.module.entity.FlowEntity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.decorator.EventDecorator;
import com.prolificinteractive.materialcalendarview.decorator.OneDayDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/25.
 * 现金流界面
 */
public class RichesCalendarActivity extends CTBaseActivity implements OnDateChangedListener
{
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private MaterialCalendarView widget;

    private FlowEntity mFlowEntity = null;
    private int mSelectMonth = 0;
    private List<FlowEntity.DayEntity> mDayList = null;

    private Map<CalendarDay, ArrayList<Map<String, Object>>> mLabelData = null;

    private ListView mItemListView = null;

    private FlowItemAdapter mFlowItemAdapter = null;

    protected void initView(Bundle bundle)
    {
        super.setContentView(R.layout.riches_datapicker);

        mFlowEntity = (FlowEntity)getIntent().getSerializableExtra("flow");
        mSelectMonth = getIntent().getIntExtra("selected_month",0);

        FlowEntity.MonthEntity sMonth = mFlowEntity.mMonth.get(mSelectMonth);
        String time = sMonth.time;

        widget = (MaterialCalendarView) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(true);
        widget.setArrowColor(getResources().getColor(R.color.ct_blue));
        widget.setSelectionColor(getResources().getColor(R.color.ct_blue));
        widget.addDecorators(
                oneDayDecorator
        );
        widget.setSelectedDate(Calendar.getInstance());


        //设置当日日期
        String[] time_arr = time.split("-");
        if(time_arr.length != 2) return;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(time_arr[0]));
        calendar.set(Calendar.MONTH, (Integer.parseInt(time_arr[1])-1));
        widget.setCurrentDate(calendar);

        mFlowItemAdapter = new FlowItemAdapter(this);
        mItemListView = (ListView)findViewById(R.id.riches_flow_day_listview);
        mItemListView.setAdapter(mFlowItemAdapter);

        setLabelDays();
    }

    protected void initData() {
        super.initData();
        setPageTitle("还款日历");
    }

    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    public void onDateChanged(MaterialCalendarView widget, CalendarDay date)
    {
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();

        //日历选择监听
        ArrayList<Map<String, Object>> item = mLabelData.get(date);
        mFlowItemAdapter.setData(item);
    }

    private void setLabelDays()
    {
        Calendar calendar = Calendar.getInstance();
        ArrayList<CalendarDay> dates = new ArrayList<>();
        int monthSize =  mFlowEntity.mMonth.size();

        mLabelData = new HashMap<>();

        for(int i = 0; i < monthSize; i++)
        {
            FlowEntity.MonthEntity monthEntity = mFlowEntity.mMonth.get(i);
            String time = monthEntity.time;
            mDayList = monthEntity.mDay;

            String[] timeArr = time.split("-");
            if(timeArr.length != 2) return;
            int year = Integer.parseInt(timeArr[0]);
            int month = Integer.parseInt(timeArr[1]) - 1;

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);

            int daySize = mDayList.size();
            for (int m = 0; m < daySize; m++)
            {
                FlowEntity.DayEntity dayEntity = mDayList.get(m);
                calendar.set(Calendar.DATE, Integer.parseInt(dayEntity.dayNum));
                CalendarDay day = new CalendarDay(calendar);
                dates.add(day);

                mLabelData.put(day, dayEntity.item);
            }
        }

        widget.addDecorator(new EventDecorator(Color.RED, dates));
        widget.addDecorators();
    }
}
