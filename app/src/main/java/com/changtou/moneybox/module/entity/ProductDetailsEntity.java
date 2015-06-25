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

    private String yqsy = null;
    private String cpqx = null;
    private String jd = null;
    private String jrtj = null;
    private String syje = null;
    private String yzje = null;

    /**
     * @see BaseEntity#paser(String)
     * @param data
     * @throws Exception
     */
    public void paser(String data) throws Exception
    {
        JSONObject object = new JSONObject(data);
        JSONObject productDetail = object.getJSONObject("productDetail");

        yqsy = productDetail.getString("yqsy");
        cpqx = productDetail.getString("cpqx");
        jd = productDetail.getString("jd");
        jrtj = productDetail.getString("jrtj");
        syje = productDetail.getString("syje");
        yzje = productDetail.getString("yzje");

//        JSONArray array = new JSONArray(data);
//        mList = new LinkedList();
//        ProListEntity entity;
//        int size = array.length();
//        for (int i = 0; i < 3; i++) {
//            entity = new ProListEntity();
//            entity.paser(array.getJSONObject(i));
//            mList.add(entity);
//        }
    }

    public  class ProListEntity
    {
        public String id;
        public String name;

        public void paser(JSONObject json) throws Exception {
            id = json.optString("reply_count");
            name = json.optString("post_status");
        }
    }
}
