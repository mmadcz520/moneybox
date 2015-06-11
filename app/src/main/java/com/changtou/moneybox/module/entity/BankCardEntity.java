package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/5/21.
 */
public class BankCardEntity extends BaseEntity
{
    public LinkedList mList = null;
    private int cardNo = 0;

    public void paser(String data) throws Exception
    {
        JSONArray array = new JSONArray(data);
        mList = new LinkedList();
        BankListEntity entity;
        int size = array.length();
        for (int i = 0; i < 3; i++) {
            entity = new BankListEntity();
            entity.paser(array.getJSONObject(i));
            mList.add(entity);
        }
    }

    public class BankListEntity {
        public String id;
        public String name;
        public int deFlag = -1;

        public void paser(JSONObject json) throws Exception {
            id = json.optString("reply_count");
            name = json.optString("post_status");
            deFlag = cardNo;
            cardNo ++;
        }
    }
}
