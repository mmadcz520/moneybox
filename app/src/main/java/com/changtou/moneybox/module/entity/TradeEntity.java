package com.changtou.moneybox.module.entity;

import android.util.Log;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * ���׼�¼ʵ����
 *
 * Created by Administrator on 2015/6/2.
 */
public class TradeEntity extends BaseEntity
{
    public LinkedList mList = null;

    /**
     * @see BaseEntity#paser(String)
     * @param data
     * @throws Exception
     */
    public void paser(String data) throws Exception {

        Log.e("CT_MONEY", "fffffffffffffffffffffffffffffffffffffffffff");

//        JSONArray array = new JSONArray(data);
        mList = new LinkedList();
        ProListEntity entity;
//        int size = array.length();
        for (int i = 0; i < 10; i++) {
            Log.e("CT_MONEY", "--------------------------------------");
            entity = new ProListEntity();
//            entity.paser(array.getJSONObject(i));
            mList.add(entity);
        }
    }

    public  class ProListEntity {
        public String id;
        public String name;

        public void paser(JSONObject json) throws Exception {
//            id = json.optString("reply_count");
//            name = json.optString("post_status");
        }
    }
}
