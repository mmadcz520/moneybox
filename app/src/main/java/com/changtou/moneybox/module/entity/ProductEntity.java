package com.changtou.moneybox.module.entity;

import android.util.Log;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * 描述: 产品实体类
 *
 * @since 2015-3-30
 * @author zhoulongfei
 */
public class ProductEntity extends BaseEntity {

    public LinkedList mList = null;

    /**
     * @see BaseEntity#paser(String)
     * @param data
     * @throws Exception
     */
    public void paser(String data) throws Exception {

        JSONArray array = new JSONArray(data);
        mList = new LinkedList();
        ProListEntity entity;
        int size = array.length();
        for (int i = 0; i < 3; i++) {
            entity = new ProListEntity();
            entity.paser(array.getJSONObject(i));
            mList.add(entity);
        }
    }

    public  class ProListEntity {
        public String id;
        public String name;

        public void paser(JSONObject json) throws Exception {
            id = json.optString("reply_count");
            name = json.optString("post_status");

            Log.e("paser","ddddddddddddddddd" + id);
        }
    }
}
