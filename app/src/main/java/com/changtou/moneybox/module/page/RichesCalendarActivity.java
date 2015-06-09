package com.changtou.moneybox.module.page;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.changtou.R;
import com.changtou.moneybox.module.entity.FlowEntity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;
import com.prolificinteractive.materialcalendarview.decorator.EventDecorator;
import com.prolificinteractive.materialcalendarview.decorator.OneDayDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/5/25.
 * 现金流界面
 */
public class RichesCalendarActivity extends CTBaseActivity implements OnDateChangedListener
{
    private OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private MaterialCalendarView widget;

    protected void initView(Bundle bundle)
    {
        super.setContentView(R.layout.riches_datapicker);

        FlowEntity data = (FlowEntity)getIntent().getSerializableExtra("month");
        String dueIn = data.mMonth.get(0).mDay.get(0).item.get(0)[0];
        Log.e("CT_MONEY", "+++++++++++++++++++++++++++++dueIn" + dueIn);

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

        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    public void onDateChanged(MaterialCalendarView widget, CalendarDay date)
    {
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        protected List<CalendarDay> doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
//            for (int i = 0; i < 30; i++) {
            calendar.set(Calendar.MONTH, 5);
            calendar.set(Calendar.DATE, 8);
            CalendarDay day = new CalendarDay(calendar);
            dates.add(day);
//                calendar.add(Calendar.DATE, 5);
//            }
            return dates;
        }

        @Override
        protected void onPostExecute(List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if(isFinishing()) {
                return;
            }

            widget.addDecorator(new EventDecorator(Color.BLUE, calendarDays));
            widget.addDecorators();
        }
    }
}
