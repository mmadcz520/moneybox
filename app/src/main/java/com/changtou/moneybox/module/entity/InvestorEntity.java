package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/5/28.
 * 1. 投资人列表
 * 2. 用户详情页 more
 */
public class InvestorEntity extends BaseEntity
{
    public LinkedList mList = null;

    public void paser(String data) throws Exception
    {
        JSONArray array = new JSONArray(data);
        mList = new LinkedList();
        ListEntity entity;
        int size = array.length();

        entity = new ListEntity();
        mList.add(entity);

        for (int i = 0; i < 20; i++)
        {
            entity = new ListEntity();
            entity.paser(array.getJSONObject(i));
            mList.add(entity);
        }
    }

    public  class ListEntity {
        // 投资人姓名
        public String mInvestorName = "用户";

        // 投资额
        public String mInvestorMoney = "投资金额";

        //投资时间
        public String mInvestorTime = "投资列表";

        public void paser(JSONObject json) throws Exception {
            mInvestorName = "4582***com";
            mInvestorMoney = "20000.00";
            mInvestorTime = "2015-3-5";
        }
    }
}
