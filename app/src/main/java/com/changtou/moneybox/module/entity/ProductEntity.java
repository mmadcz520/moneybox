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
public class ProductEntity extends BaseEntity {

    public LinkedList mList = null;
    public LinkedList mProductTypeList = null;

    /**
     * @see BaseEntity#paser(String)
     * @param data
     * @throws Exception
     */
    public void paser(String data) throws Exception
    {
        JSONObject productList = new JSONObject(data);
        JSONArray allData = productList.getJSONArray("productList");

        JSONObject typeData;
        ItemEntity itemEntity;
        JSONArray listByType;

        mProductTypeList = new LinkedList();

        for(int i = 0; i < allData.length(); i++)
        {
            typeData = allData.getJSONObject(i);
            listByType = typeData.getJSONArray("data");

            mList = new LinkedList();

            for(int m = 0; m < listByType.length(); m++)
            {
                JSONObject itemData = listByType.getJSONObject(m);
                itemEntity = new ItemEntity();
                itemEntity.paser(itemData);
                mList.add(itemEntity);
            }
            mProductTypeList.add(mList);
        }
    }

    public LinkedList getProductTypeList()
    {
        return mProductTypeList;
    }

    public  class ItemEntity
    {
        public String id;
        public String projectname;
        public String amount;
        public String interest;
        public String maturity;
        public String minamount;
        public String type;
        public String repaytime;
        public String sold;
        public String createtime;
        public String jd;
        public String syje;

        public void paser(JSONObject json) throws Exception {
            id = json.optString("id");
            projectname = json.optString("projectname");
            amount = json.optString("amount");
            interest = json.optString("interest");
            maturity = json.optString("maturity");
            minamount = json.optString("minamount");
            type = json.optString("type");
            repaytime = json.optString("repaytime");
            sold = json.optString("sold");
            createtime = json.optString("createtime");
            jd = json.optString("jd");
            syje = json.optString("syje");
        }
    }
}
