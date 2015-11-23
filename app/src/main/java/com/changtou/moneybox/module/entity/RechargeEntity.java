package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * 充值记录实体类
 *{"tradeNum":"4618195187897624475","payamount":0.01,"status":"成功","memo":"连连充值","type":"连连支付","creatime":"2015-11-03"}
 * Created by Administrator on 2015/6/2.
 */
public class RechargeEntity extends BaseEntity
{
    public LinkedList mList = null;

    /**
     * @see BaseEntity#paser(String)
     * @param data
     * @throws Exception
     */
    public void paser(String data) throws Exception
    {
        JSONArray array = new JSONArray(data);
        mList = new LinkedList();
        ItemEntity entity;
        int size = array.length();
        for (int i = 0; i < size; i++)
        {
            entity = new ItemEntity();
            entity.paser(array.getJSONObject(i));
            mList.add(entity);
        }
    }

    public  class ItemEntity
    {
        public String tradeNum;
        public String payamount;
        public String status;
        public String memo;
        public String creatime;

        public void paser(JSONObject json) throws Exception
        {
            tradeNum = json.optString("tradeNum");
            payamount = json.optString("payamount");
            status = json.optString("status");
            memo = json.optString("memo");
            creatime = json.optString("creatime");
        }
    }
}
