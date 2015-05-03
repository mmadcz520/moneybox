package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

public class DemoEntity extends BaseEntity {

    public LinkedList mList;

    @SuppressWarnings("unchecked")
    public void paser(String data) throws Exception {
        JSONArray array = new JSONArray(data);
        mList = new LinkedList();
        DemoListEntity entity;
        int size = array.length();
        for (int i = 0; i < size; i++) {
            entity = new DemoListEntity();
            entity.paser(array.getJSONObject(i));
            mList.add(entity);
        }

    }

    public  class DemoListEntity {

        //mmm

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
