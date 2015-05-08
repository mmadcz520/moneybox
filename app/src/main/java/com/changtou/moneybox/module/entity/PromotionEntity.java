package com.changtou.moneybox.module.entity;

import android.util.Log;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * 活动 实体类  解析实体
 */
public class PromotionEntity extends BaseEntity
{
    public String mImgUrl = null;

    /**
     * @see BaseEntity#paser(String)
     * @param data
     * @throws Exception
     */
    public void paser(String data) throws Exception {

        Log.e("CT_MONEY", "paser+++" + data);

        JSONObject json = new JSONObject(data);
        JSONObject dataObject = json.getJSONObject("data");
        JSONArray array = dataObject.getJSONArray("topics");
        JSONObject object = array.getJSONObject(0);
        mImgUrl = object.getString("author_avatar");
    }

    public String getImgUrl()
    {
        return mImgUrl;
    }
}
