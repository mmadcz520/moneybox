package com.changtou.moneybox.module.entity;

import android.util.Log;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 奖励记录实体类
 */
public class JiangLiInfoEntity extends BaseEntity implements Serializable
{
//    {"errcode":0,"data":[{"type":0,"total":"9.94","detail":[{"money":"9.94","interest":"2%","status":"成功","memo":"2016老推新加息","repaytime":"2016-01-07"}]},{"type":1,"total":"0.00","detail":[]}],"msg":""}

    public LinkedList mYiShouList = null;
    public LinkedList mDaiShouList = null;

    //已收总额
    public String mYiShouTot = "0";

    //待收总额
    public String mDaiShouTot = "0";

    private int errCode = 0;


    /**
     * @see BaseEntity#paser(String)
     * @param data
     * @throws Exception
     *
     * 0- 待收  1-已收
     */
    public void paser(String data) throws Exception
    {
        JSONObject object = new JSONObject(data);
        errCode = object.getInt("errcode");

        JSONArray allData = object.getJSONArray("data");
        mYiShouList = new LinkedList();
        mDaiShouList = new LinkedList();

        for(int i = 0; i < allData.length(); i++)
        {
            JSONObject typeObject = allData.getJSONObject(i);
            int type = typeObject.getInt("type");
            JSONArray detailArray = typeObject.getJSONArray("detail");

            for(int m = 0; m < detailArray.length(); m++)
            {
                ItemEntity itemEntity = new ItemEntity();
                JSONObject itemData = detailArray.getJSONObject(m);
                if(type == 0)
                {
                    itemEntity.paser(itemData);
                    mDaiShouList.add(m,itemEntity);
                    mDaiShouTot = typeObject.getString("total");
                }
                else
                {
                    itemEntity.paser(itemData);
                    mYiShouList.add(m,itemEntity);
                    mYiShouTot = typeObject.getString("total");
                }
            }
        }
    }

    public  class ItemEntity
    {
        public String money;
        public String interest;
        public String status;
        public String memo;
        public String repaytime;

        public void paser(JSONObject json) throws Exception {
            money = json.optString("money");
            interest = json.optString("interest");
            status = json.optString("status");
            memo = json.optString("memo");
            repaytime = json.optString("repaytime");
        }
    }
}
