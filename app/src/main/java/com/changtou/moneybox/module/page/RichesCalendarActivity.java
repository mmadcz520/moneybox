package com.changtou.moneybox.module.page;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.adapter.FlowItemAdapter;
import com.changtou.moneybox.module.entity.FlowEntity;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.decorator.EventDecorator;
import com.prolificinteractive.materialcalendarview.decorator.OneDayDecorator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/25.
 * 现金流界面
 */
public class RichesCalendarActivity extends CTBaseActivity implements OnDateChangedListener, OnMonthChangedListener
{
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private MaterialCalendarView widget;

    private FlowEntity mFlowEntity = null;
    private int mSelectMonth = 0;
    private List<FlowEntity.DayEntity> mDayList = null;

    private Map<CalendarDay, ArrayList<Map<String, Object>>> mLabelData = null;

    private ListView mItemListView = null;

    private FlowItemAdapter mFlowItemAdapter = null;

    private TextView mZsyTextView = null;
    private TextView mZhbTextView = null;

    protected void initView(Bundle bundle)
    {
        super.setContentView(R.layout.riches_datapicker);

        ACache cache = ACache.get(this);
        mSelectMonth = (int)cache.getAsObject("selected_month");
        mFlowEntity = (FlowEntity)cache.getAsObject("flow");

        widget = (MaterialCalendarView) findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(false);
        widget.setArrowColor(getResources().getColor(R.color.ct_blue));
        widget.setSelectionColor(getResources().getColor(R.color.ct_blue));
        widget.addDecorators(
                oneDayDecorator
        );
        widget.setSelectedDate(Calendar.getInstance());
        widget.setOnMonthChangedListener(this);


        mFlowItemAdapter = new FlowItemAdapter(this);
        mItemListView = (ListView)findViewById(R.id.riches_flow_day_listview);
        mItemListView.setAdapter(mFlowItemAdapter);

        mZsyTextView = (TextView)findViewById(R.id.riches_calemdar_zsy);
        mZhbTextView = (TextView)findViewById(R.id.riches_calemdar_zhb);

//        setLabelDays();
//        setReData();
    }

    protected void initData() {
        super.initData();
        setPageTitle("还款日历");

        sendRequest(HttpRequst.REQ_TYPE_CALENDER,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_CALENDER),
                mParams,
                getAsyncClient(), false);
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        if(reqType == HttpRequst.REQ_TYPE_CALENDER)
        {
            try {
                JSONObject obj = new JSONObject(content);
                mFlowEntity = new FlowEntity();
                mFlowEntity.paser(obj.getJSONArray("data").toString());

                setLabelDays();
                setReData();
                super.onSuccess(content,object, reqType);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Throwable error, String content, int reqType) {
        super.onFailure(error, content, reqType);
    }

    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    private void setReData()
    {
        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar);

        widget.invalidateDecorators();

        CalendarDay day = new CalendarDay(calendar);
        ArrayList<Map<String, Object>> item = mLabelData.get(day);
        mFlowItemAdapter.setData(item);
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

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date)
    {
        ArrayList<FlowEntity.MonthEntity> monthList = mFlowEntity.getMonth();
        Calendar calendar = date.getCalendar();
        String str = (new SimpleDateFormat("yyyy-M")).format(calendar.getTime());
        String dayNum = "1";

        for(int i = 0; i < monthList.size(); i++)
        {
            String time = monthList.get(i).time;
            if(time.equals(str))
            {
                ArrayList<FlowEntity.DayEntity> days = monthList.get(i).mDay;
                dayNum = days.get(0).dayNum;

                //M
                FlowEntity.MonthEntity month = monthList.get(i);
                mZsyTextView.setText(month.zsy);
                mZhbTextView.setText(month.zhb);
            }
            else
            {
                mZsyTextView.setText("0.0");
                mZhbTextView.setText("0.0");
            }
        }

        calendar.set(Calendar.DATE, Integer.parseInt(dayNum));
        widget.setSelectedDate(calendar);
        widget.invalidateDecorators();

        CalendarDay day = new CalendarDay(calendar);
        ArrayList<Map<String, Object>> item = mLabelData.get(day);
        mFlowItemAdapter.setData(item);
    }
}
