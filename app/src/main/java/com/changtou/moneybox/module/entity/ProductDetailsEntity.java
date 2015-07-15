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

    //项目合同图片列表
    public LinkedList mImgList = null;

    //长投宝项目详情
    public DetailsEntityCTB mDetailsCTB = null;

    //普通项目详情
    public DetailsEntityOther mDetailsOther = null;


    public String nhsy = ""; //年化收益
    public String jd = "";   //还款进度
    public String rzje = ""; //融资金额
    public String syje = ""; //剩余金额
    public String cpqx = ""; //项目期限

    public String qtje = ""; //起投金额

    public String projectname = ""; //项目名称
    public String hkfs = "";        //还款方式
    public String hksj = "";        //还款时间

    public int productType = 0;       //项目类型

    /**
     * @see BaseEntity#paser(String)
     * @param data 1
     * @throws Exception
     */
    public void paser(String data) throws Exception
    {
        Log.e("CT_MONEY", "productDetail = " + data);

        JSONObject object = new JSONObject(data);
        JSONObject productDetail = object.getJSONObject("productDetail");

        productType = object.getInt("type");
        mDetailsCTB = new DetailsEntityCTB();
        mDetailsOther = new DetailsEntityOther();

        if(productType == 0)
        {
            mDetailsCTB.paser(productDetail);

            nhsy = mDetailsCTB.nhsy;
            jd = mDetailsCTB.jd;
            rzje = mDetailsCTB.rzje;
            syje = mDetailsCTB.syje;
            cpqx = mDetailsCTB.cpqx;
            qtje = mDetailsCTB.qtje;
            projectname = mDetailsCTB.projectname;
            hkfs = mDetailsCTB.hkfs;
            hksj = mDetailsCTB.hksj;
        }
        else
        {
            mDetailsOther.paser(productDetail);

            nhsy = mDetailsOther.nhsy;
            jd = mDetailsOther.jd;
            rzje = mDetailsOther.rzje;
            syje = mDetailsOther.syje;
            cpqx = mDetailsOther.cpqx;
            qtje = mDetailsOther.qtje;
            projectname = mDetailsOther.projectname;
            hkfs = mDetailsOther.hkfs;
            hksj = mDetailsOther.hksj;

            JSONArray imgList = object.getJSONArray("imglist");
            mImgList = new LinkedList();

            int len = imgList.length();
            for(int i = 0; i < len; i++)
            {
                String imgSrc = imgList.getString(i);
                mImgList.add(imgSrc);
            }
        }

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

    /**
     * 长投宝项目详情
     */
    public class DetailsEntityCTB
    {
        public String nhsy = ""; //年化收益
        public String jd = "";   //还款进度
        public String rzje = ""; //融资金额
        public String syje = ""; //剩余金额
        public String cpqx = ""; //项目期限

        public String qtje = ""; //起投金额

        public String projectname = ""; //项目名称
        public String hkfs = "";        //还款方式
        public String hksj = "";        //还款时间

        public String tbfw = "";        //投标范围
        public String jrtj = "";        //加入条件
        public String jrsx = "";        //加入上限
        public String sdjzrq = "";      //锁定截止日期
        public String tcfs = "";        //退出方式
        public String tqtcfs = "";      //提前退出方式
        public String glfy = "";         //管理费用
        public String jrfwf = "" ;       //金额服务费
        public String tqtcfy = "";      //提前退出费用
        public String bzfs = "";       //保障方式


        public void paser(JSONObject json) throws Exception
        {
            nhsy = json.getString("nhsy");
            jd = json.getString("jd");
            rzje = json.getString("rzje");
            syje = json.getString("syje");
            cpqx = json.getString("cpqx");

            qtje = json.getString("qtje");

            projectname = json.getString("projectname");
            hkfs = json.getString("hkfs");
            hksj = json.getString("hksj");

            tbfw = json.getString("tbfw");
            jrtj = json.getString("jrtj");
            jrsx = json.getString("jrsx");
            sdjzrq = json.getString("sdjzrq");
            tcfs = json.getString("tcfs");
            tqtcfs = json.getString("tqtcfs");
            glfy = json.getString("glfy");
            jrfwf = json.getString("jrfwf");
            tqtcfy = json.getString("tqtcfy");
            bzfs = json.getString("bzfs");
        }
    }

    /**
     * 其他项目的项目详情
     */
    public class DetailsEntityOther
    {
        public String nhsy = ""; //年化收益
        public String jd = "";   //还款进度
        public String rzje = ""; //融资金额
        public String syje = ""; //剩余金额
        public String cpqx = ""; //项目期限

        public String qtje = ""; //起投金额

        public String projectname = ""; //项目名称
        public String hkfs = "";        //还款方式
        public String hksj = "";        //还款时间

        public String xmqk = "";       //项目合同情况

        public void paser(JSONObject json) throws Exception
        {
            nhsy = json.getString("nhsy");
            jd = json.getString("jd");
            rzje = json.getString("rzje");
            syje = json.getString("syje");
            cpqx = json.getString("cpqx");

            qtje = json.getString("qtje");

            projectname = json.getString("projectname");
            hkfs = json.getString("hkfs");
            hksj = json.getString("hksj");

            xmqk = json.getString("xmqk");
        }
    }
}
