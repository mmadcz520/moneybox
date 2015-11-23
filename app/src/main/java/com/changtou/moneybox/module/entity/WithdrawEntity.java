package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * 充值记录实体类
 * Created by Administrator on 2015/6/2.
 */
public class WithdrawEntity extends BaseEntity
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
        public String withdrawamount;
        public String status;
        public String bank;
        public String account;
        public String createtime;

        public void paser(JSONObject json) throws Exception
        {
            withdrawamount = json.optString("withdrawamount");
            status = json.optString("status");
            bank = json.optString("bank");
            account = json.optString("account");
            createtime = json.optString("createtime");
        }
    }
}
