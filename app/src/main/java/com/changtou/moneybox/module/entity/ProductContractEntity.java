package com.changtou.moneybox.module.entity;

import com.changtou.moneybox.common.http.base.BaseEntity;

import org.json.JSONArray;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/5/29.
 */
public class ProductContractEntity extends BaseEntity {
    private String mContent = "";

    //还款来源
    private String mRepayingSrc = "";

    //还款保障
    private String mSafeguard = "";

    //审核资料
    private LinkedList mAuditImg = null;

    /**
     * @param data
     * @throws Exception
     * @see BaseEntity#paser(String)
     */
    public void paser(String data) throws Exception {


//        JSONArray array = new JSONArray(data);
        mAuditImg = new LinkedList();

        mContent = "借款人***集团有限公司, 是一家专业从事房地产开发的公司," +
                "具备丰富的房地产开发经验及专业开发团队,因公司经验周转需要," +
                "特向xx个人申请贷款。xx个人现将部分债权向长投在线投资人进行" +
                "转让。同时,xx个人承若全额本息回购。";

        mRepayingSrc = "***集团有限公司经营收入";

        mSafeguard = "1.原债权人全额本息回购承若。\n2.项目公司关联企业承担连带责任担保。\n3.平台已对项目进行实地考察,通过专业尽调认为可保障本息按时还款。";

        mAuditImg = new LinkedList();
        mAuditImg.add("https://www.baidu.com/img/bd_logo1.png");
        mAuditImg.add("https://www.baidu.com/img/bd_logo1.png");
        mAuditImg.add("https://www.baidu.com/img/bd_logo1.png");
        mAuditImg.add("https://www.baidu.com/img/bd_logo1.png");
        mAuditImg.add("https://www.baidu.com/img/bd_logo1.png");
        mAuditImg.add("https://www.baidu.com/img/bd_logo1.png");
    }

    public String getRepayingSrc() {
        return mRepayingSrc;
    }

    public String getSafeguard() {
        return mSafeguard;
    }

    public LinkedList getAuditImg() {
        return mAuditImg;
    }

    public String getContent() {
        return mContent;
    }
}
