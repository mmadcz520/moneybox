package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        JSONArray array = new JSONArray(data);

        //解析json 数据
        mMonth = new ArrayList<>();
        MonthEntity entity;
        int size = array.length();

        for (int i = 0; i < size; i++) {
            entity = new MonthEntity();
            entity.paser(array.getJSONObject(i));
            mMonth.add(entity);
        }
    }

    public ArrayList<MonthEntity> getMonth()
    {
        return mMonth;
    }


    /**
     * 月份实体类
     */
    public class MonthEntity implements Serializable
    {
        public int index;
        public String dueIn = null;
        public String received = null;
        public String time = null;

        public ArrayList<DayEntity> mDay = null;

        public void paser(JSONObject json) throws Exception
        {
            index = json.getInt("index");
            dueIn = json.getString("dueIn");
            received = json.getString("received");
            time = json.getString("time");

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
        public ArrayList<Map<String, Object>> item = null;

        private Map<String, Object> mItemArray = null;

        public void paser(JSONObject json) throws Exception
        {
            dayNum = json.getString("dayNum");


            JSONArray items = json.getJSONArray("item");
            item = new ArrayList<>();
            int size = items.length();


            JSONObject array;
            for (int i = 0; i < size; i++)
            {
                mItemArray = new HashMap<>();
                array = items.getJSONObject(i);

                mItemArray.put("type", array.getString("type"));
                mItemArray.put("time", array.getString("time"));
                mItemArray.put("name", array.getString("name"));
                mItemArray.put("num", array.getString("num"));
                mItemArray.put("account", array.getBoolean("account"));

                item.add(i, mItemArray);
            }
        }
    }
}
