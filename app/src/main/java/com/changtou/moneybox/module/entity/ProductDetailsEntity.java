package com.changtou.moneybox.module.entity;

import android.util.Log;

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
    //投资列表
    public LinkedList mTzList = null;

    public String nhsy = ""; //年化收益
    public String jd = "";   //还款进度
    public String rzje = ""; //融资金额
    public String syje = ""; //剩余金额
    public String cpqx = ""; //项目期限

    public String qtje = ""; //起投金额

    public String projectname = ""; //项目名称
    public String hkfs = "";        //还款方式
    public String hksj = "";        //还款时间


    public String yqsy = "";

    public String jrtj = "";
    public String yzje = "";

    /**
     * @see BaseEntity#paser(String)
     * @param data
     * @throws Exception
     */
    public void paser(String data) throws Exception
    {
        Log.e("CT_MONEY", "productDetail = " + data);

        JSONObject object = new JSONObject(data);
        JSONObject productDetail = object.getJSONObject("productDetail");

        nhsy = productDetail.getString("nhsy");
        jd = productDetail.getString("jd");
        rzje = productDetail.getString("rzje");
        syje = productDetail.getString("syje");
        cpqx = productDetail.getString("cpqx");

        qtje = productDetail.getString("qtje");

        projectname = productDetail.getString("projectname");
        hkfs = productDetail.getString("hkfs");
        hksj = productDetail.getString("hksj");

//        Log.e("CT_MONEY", nhsy + "-" + jd + "-"+ rzje + "-" + syje + "-" +  cpqx + "-" + qtje
//        + "-" + projectname + "-" + hkfs + "-" + hksj);

        JSONArray tzlist = object.getJSONArray("tzlist");
        int  count = tzlist.length();

        mTzList = new LinkedList();
        TzListEntity entity;

        for (int i = 0; i < count; i++)
        {
            entity = new TzListEntity();
            entity.paser(tzlist.getJSONObject(i));
            mTzList.add(entity);
        }
    }

    /**
     * 投资列表实体类
     *
     * [{"id":206747,"username":"359239537@qq.com","withdrawAmount":10000,"createTime":"\/Date(1434269638433)\/"},
     */
    public  class TzListEntity
    {
        public String id;
        public String username;
        public String withdrawAmount;
        public String createTime;

        public void paser(JSONObject json) throws Exception
        {
            id = json.optString("id");
            username = json.optString("username");
            withdrawAmount = json.optString("withdrawAmount");
            createTime = json.optString("createTime");
        }
    }
}
