package com.changtou.moneybox.module.entity;

import android.util.Log;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/25.
 */
public class InvestListEntity extends BaseEntity{

    private Map<String, LinkedList> mInvestMap = null;
    public LinkedList<ItemEntity> mDataList = null;

    /**
     * @see BaseEntity#paser(String)
     * @throws Exception
     */
    public void paser(String data) throws Exception
    {
        JSONArray array = new JSONArray(data);
        int len = array.length();

        mInvestMap = new HashMap<>();

        for(int i = 0; i < len; i++)
        {
            JSONObject typeObject = array.getJSONObject(i);

            String type = typeObject.getString("status");
            JSONArray itemData = typeObject.getJSONArray("data");

            int data_len = itemData.length();
            mDataList = new LinkedList<>();
            ItemEntity itemEntity;

            for(int m = 0; m < data_len; m++)
            {
                JSONObject itemJson = itemData.getJSONObject(m);
                itemEntity = new ItemEntity();
                itemEntity.paser(itemJson);
                mDataList.add(itemEntity);
            }

            mInvestMap.put(type, mDataList);
        }

    }

    public  class ItemEntity
    {
        public String id;
        public int type;              //1:还款中 2:已结清 3:已退出
        public String projectname;
        public String withdrawamount;    //投资金额
        public String rate;              //利率
        public String maturity;          //期限
        public String starttime;         //开始时间
        public String endtime;           //结束时间
        public String expectin;          //预期收益

        public void paser(JSONObject json) throws Exception
        {
            id = json.optString("productid");
            type = json.optInt("type");
            projectname = json.optString("projectname");
            withdrawamount = json.optString("withdrawamount");
            rate = json.optString("rate");
            maturity = json.optString("maturity");
            starttime = json.optString("starttime");
            endtime = json.optString("endtime");
            expectin = json.optString("expectin");
        }
    }

    public Map<String, LinkedList> getInvestMap()
    {
        return mInvestMap;
    }
}
