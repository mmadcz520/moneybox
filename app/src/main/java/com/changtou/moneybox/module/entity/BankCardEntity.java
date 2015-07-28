package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * "bankinfo":[{"id":240,"account":"6217857600003237158","bank":"中国银行","branch":"民族大道支行","isdefault":"是","place":"湖北武汉","type":"","createtime":"2015-06-08"}],
 *
 * Created by Administrator on 2015/5/21.
 */
public class BankCardEntity extends BaseEntity implements Serializable
{
    public ArrayList mList = null;

    public void paser(String data) throws Exception
    {
        JSONArray array = new JSONArray(data);
        mList = new ArrayList<>();
        BankListEntity entity;
        int size = array.length();
        for (int i = 0; i < size; i++) {
            entity = new BankListEntity();
            entity.paser(array.getJSONObject(i));
            mList.add(entity);
        }
    }

    public class BankListEntity implements Serializable{
        public String account = "";
        public String bank = "";
        public String branch = "";
        public String isdefault = "";
        public String place = "";
        public String type = "";
        public String createtime = "";

        public void paser(JSONObject json) throws Exception {
            account = json.optString("account");
            bank = json.optString("bank");
            branch = json.optString("branch");
            isdefault = json.optString("isdefault");
            place = json.optString("place");
            type = json.optString("type");
            createtime = json.optString("createtime");
        }
    }
}
