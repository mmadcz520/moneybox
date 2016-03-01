package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 礼金记录实体类
 *
 * Created by Jone on 2016/1/12.
 */
public class LiJinEntity extends BaseEntity
{
    private int errcode = 0;

    private HashMap<String, LinkedList> mLiJinData = new HashMap<>();

//    {"errcode":0,"data":[{"time":"2016年1月","data":[{"memo":"app注册送礼金","money":20,"date":"01-06","hour":"17:56:05"},{"memo":"实名认证奖励10礼金","money":10,"date":"01-06","hour":"17:56:47"}]}],"msg":"获取成功"}
    @Override
    public void paser(String data) throws Exception
    {
        JSONObject object = new JSONObject(data);
        JSONArray dataArray = object.getJSONArray("data");

        for(int i = 0; i < dataArray.length(); i++)
        {
            JSONObject monthObject = dataArray.getJSONObject(i);
            String time = monthObject.getString("time");
            JSONArray itemArray = monthObject.getJSONArray("data");
            LinkedList itemList = new LinkedList();

            for(int m = 0; m < itemArray.length(); m++)
            {
                JSONObject item = itemArray.getJSONObject(m);
                ItemEntity itemEntity = new ItemEntity();

                itemEntity.paser(item);
                itemList.add(itemEntity);
            }
            mLiJinData.put(time, itemList);
        }
    }

    public HashMap getLiJinData()
    {
        return mLiJinData;
    }

    public  class ItemEntity
    {
        public String memo;
        public int money;
        public String date;
        public String hour;

        public void paser(JSONObject json) throws Exception {
            memo = json.optString("memo");
            money = json.optInt("money");
            date = json.optString("date");
            hour = json.optString("hour");
        }
    }
}
