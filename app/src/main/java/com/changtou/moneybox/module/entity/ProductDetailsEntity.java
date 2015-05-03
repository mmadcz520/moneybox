package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * 产品详细信息实体类
 *
 * Created by Administrator on 2015/5/3 0003.
 */
public class ProductDetailsEntity extends BaseEntity
{
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
        }
    }
}
