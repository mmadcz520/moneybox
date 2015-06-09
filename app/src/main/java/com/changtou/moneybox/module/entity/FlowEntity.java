package com.changtou.moneybox.module.entity;


import android.util.Log;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 现金流实体类
 *
 * Created by Administrator on 2015/6/2.
 */
public class FlowEntity extends BaseEntity implements Serializable
{
    public ArrayList<MonthEntity> mMonth = null;

    /**
     * @see BaseEntity#paser(String)
     * @param data        日期
     * @throws Exception  抛出异常
     */
    public void paser(String data) throws Exception {
//        JSONArray array = new JSONArray(data);

        /*** 测试数据 ***/
        JSONObject details = new JSONObject();
        details.put("type", "0");
        details.put("time", "2015-04-10");
        details.put("name", "[长投宝] 上手易第251期");
        details.put("num", "1819.00");
        details.put("account", "false");

        JSONObject details2 = new JSONObject();
        details2.put("type", "0");
        details2.put("time", "2015-04-10");
        details2.put("name", "[长投宝] 上手易第248期");
        details2.put("num", "289.00");
        details2.put("account", "true");

        JSONObject details3 = new JSONObject();
        details3.put("type", "1");
        details3.put("time", "2015-04-10");
        details3.put("name", "[长投宝] 上手易第251期");
        details3.put("num", "25000.00");
        details3.put("account", "true");
        JSONArray item = new JSONArray();
        item.put(details);
        item.put(details2);
        item.put(details3);


        JSONArray days = new JSONArray();
        JSONObject day = new JSONObject();
        day.put("dayNum", "5");
        day.put("item", item);
        days.put(0, day);

        JSONObject month1 = new JSONObject();
        month1.put("month", "5");
        month1.put("dueIn", "1900.00");
        month1.put("received", "2500.00");
        month1.put("days", days);

        JSONObject month2 = new JSONObject();
        month2.put("month", "6");
        month2.put("dueIn", "1900.00");
        month2.put("received", "2600.00");
        month2.put("days", new JSONArray());

        JSONObject month3 = new JSONObject();
        month3.put("month", "7");
        month3.put("dueIn", "1900.00");
        month3.put("received", "2600.00");
        month3.put("days", new JSONArray());

        JSONArray jdata = new JSONArray();
        jdata.put(0,month1);
        jdata.put(1,month2);
        jdata.put(2,month3);

        //解析json 数据
        mMonth = new ArrayList<>();
        MonthEntity entity;
        int size = jdata.length();

        for (int i = 0; i < size; i++) {

            entity = new MonthEntity();
            entity.paser(jdata.getJSONObject(i));
            mMonth.add(entity);
        }

    }

    /**
     * 月份实体类
     */
    public class MonthEntity implements Serializable
    {
        public String month = null;
        public String dueIn = null;
        public String received = null;

        public ArrayList<DayEntity> mDay = null;

        public void paser(JSONObject json) throws Exception
        {
            month = json.getString("month");
            dueIn = json.getString("dueIn");
            received = json.getString("received");

            JSONArray days = json.getJSONArray("days");
            mDay = new ArrayList<>();
            int size = days.length();
            DayEntity entity;
            for (int i = 0; i < size; i++)
            {
                entity = new DayEntity();
                entity.paser(days.getJSONObject(i));
                mDay.add(entity);
            }
        }
    }

    /**
     * 日期实体类
     */
    public class DayEntity implements Serializable
    {
        public String dayNum = null;
        public ArrayList<String[]> item = null;

        private String[] mItemArray = null;

        public void paser(JSONObject json) throws Exception
        {
            dayNum = json.getString("dayNum");

            JSONArray items = json.getJSONArray("item");
            item = new ArrayList<>();
            int size = items.length();

            JSONObject array;
            for (int i = 0; i < size; i++)
            {
                mItemArray = new String[5];
                array = items.getJSONObject(i);

                mItemArray[0] = array.getString("type");
                mItemArray[1] = array.getString("time");
                mItemArray[2] = array.getString("name");
                mItemArray[3] = array.getString("num");
                mItemArray[4] = array.getString("account");

                item.add(mItemArray);
            }
        }
    }
}
