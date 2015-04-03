package com.changtou.moneybox.module.entity;

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
public class UserEntity extends BaseEntity {

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
        for (int i = 0; i < size; i++) {
            entity = new ProListEntity();
            entity.paser(array.getJSONObject(i));
            mList.add(entity);
        }
    }

    public  class ProListEntity {
        public String id;
        public String name;
        public String time;
        public String address;
        public String category;
        public String updatetime;

        public String creatorid;
        public String intro;
        public String picture;
        public String status;
        public String htmlpath;

        public void paser(JSONObject json) throws Exception {
            id = json.optString("id");
            name = json.optString("name");
            time = json.optString("time");
            address = json.optString("address");
            category = json.optString("category");
            updatetime = json.optString("updatetime");
            creatorid = json.optString("creatorid");
            intro = json.optString("intro");
            picture = json.optString("picture");
            status = json.optString("status");
            htmlpath = json.optString(htmlpath);
        }
    }
}
