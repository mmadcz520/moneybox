package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/16.
 */
public class UserInfoEntity extends BaseEntity implements Serializable
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
    private String mGifts = "0.00";
    private int mTouYuan = 0;

    //是否实名认证   是否手机认证  是否首次投资
    private boolean identycheck = false;
    private boolean mobilecheck = false;
    private boolean hasinvestrecords = false;

    private FlowEntity mFlowEntity = null;
    private BankCardEntity mBankCardEntity = null;

    private static UserInfoEntity single = null;

    public static UserInfoEntity getInstance() {
        if (single == null) {
            single = new UserInfoEntity();
        }
        return single;
    }

    public void paser(String data) throws Exception
    {
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
        mTouYuan = wealthinfo.getInt("TouYuan");

        //现金流
        JSONArray flowInfo = jsonObject.getJSONArray("calender");
        mFlowEntity = new FlowEntity();
        mFlowEntity.paser(flowInfo.toString());

        //银行卡
        JSONArray bankInfo = jsonObject.getJSONArray("bankinfo");
        mBankCardEntity = new BankCardEntity();
        mBankCardEntity.paser(bankInfo.toString());

        identycheck = jsonObject.getBoolean("identycheck");
        mobilecheck = jsonObject.getBoolean("mobilecheck");

        hasinvestrecords = jsonObject.getBoolean("hasinvestrecords");
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

    public int getTouYuan()
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

    public boolean getIdentycheck()
    {
        return identycheck;
    }

    public boolean getMobilecheck()
    {
        return mobilecheck;
    }

    public boolean getHasinvestrecords()
    {
        return hasinvestrecords;
    }

    public void setTouyuan(int touyuan)
    {
        this.mTouYuan = touyuan;
    }
}
