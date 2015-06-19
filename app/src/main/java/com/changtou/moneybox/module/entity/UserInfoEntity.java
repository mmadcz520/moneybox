package com.changtou.moneybox.module.entity;

import android.util.Log;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/6/16.
 */
public class UserInfoEntity extends BaseEntity
{
    /** user info*/
    private String mEmail = "";
    private String mMobile = "";
    private String mIdCard = "";
    private String mFullName = "";
    private String mAutoinvest = "";
    private String mCreatetime = "";

    /** wealthinfo */
    private String mTotalAssets = "";
    private String mInvestAssets = "";
    private String mProfit = "";
    private String mOverage = "";
    private String mGifts = "";
    private String mTouYuan = "";

    private FlowEntity mFlowEntity = null;
    private BankCardEntity mBankCardEntity = null;

    private static UserInfoEntity single=null;
    //静态工厂方法
    public static UserInfoEntity getInstance() {
        if (single == null) {
            single = new UserInfoEntity();
        }
        return single;
    }

    public void paser(String data) throws Exception
    {
        Log.e("CT_MONEY", "----------------------------------" +data);

        JSONObject jsonObject = new JSONObject(data);
        JSONObject userinfo = jsonObject.getJSONObject("userinfo");
        mEmail = userinfo.getString("email");
        mMobile = userinfo.getString("mobile");
        mIdCard = userinfo.getString("idcard");
        mFullName = userinfo.getString("fullname");
        mAutoinvest = userinfo.getString("autoinvest");
        mCreatetime = userinfo.getString("createtime");

        JSONObject wealthinfo = jsonObject.getJSONObject("wealthinfo");
        mTotalAssets = wealthinfo.getString("TotalAssets");
        mInvestAssets = wealthinfo.getString("InvestAssets");
        mProfit = wealthinfo.getString("Profit");
        mOverage = wealthinfo.getString("Overage");
        mGifts = wealthinfo.getString("Gifts");
        mTouYuan = wealthinfo.getString("TouYuan");

        //现金流
        JSONArray flowInfo = jsonObject.getJSONArray("calender");
        mFlowEntity = new FlowEntity();
        mFlowEntity.paser(flowInfo.toString());

        //银行卡
        JSONArray bankInfo = jsonObject.getJSONArray("bankinfo");
        mBankCardEntity = new BankCardEntity();
        mBankCardEntity.paser(bankInfo.toString());
    }

    public String getEmail()
    {
        return mEmail;
    }

    public String getMobile()
    {
        return mMobile;
    }

    public String getIdCard()
    {
        return mIdCard;
    }

    public String getFullName()
    {
        return mFullName;
    }

    public String getAutoinvest()
    {
        return mAutoinvest;
    }

    public String getCreatetime()
    {
        return mCreatetime;
    }

    public String getTouYuan()
    {
        return mTouYuan;
    }

    public String getGifts()
    {
        return mGifts;
    }

    public String getOverage()
    {
        return mOverage;
    }

    public String getProfit()
    {
        return mProfit;
    }

    public String getInvestAssets()
    {
        return mInvestAssets;
    }

    public String getTotalAssets() {
        return mTotalAssets;
    }

    public FlowEntity getFlowEntity()
    {
        return mFlowEntity;
    }

    public BankCardEntity getBankCardEntity()
    {
        return mBankCardEntity;
    }

    public void setBankCardEntity(BankCardEntity bankCardEntity)
    {
        this.mBankCardEntity = bankCardEntity;
    }

}
