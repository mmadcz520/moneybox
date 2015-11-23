package com.changtou.moneybox.module.http;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.base.BaseEntity;
import com.changtou.moneybox.common.http.base.BaseHttpRequest;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.FlowEntity;
import com.changtou.moneybox.module.entity.InvestListEntity;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.entity.PromotionEntity;
import com.changtou.moneybox.module.entity.RechargeEntity;
import com.changtou.moneybox.module.entity.RewardsEntity;
import com.changtou.moneybox.module.entity.TradeEntity;
import com.changtou.moneybox.module.entity.TransferListEntity;
import com.changtou.moneybox.module.entity.UserEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.entity.WithdrawEntity;

/**
 * 描述:http Api 工具类
 *
 * 封装各种Http 请求
 *
 * @since 2015-04-10
 * @author zhoulongfei
 */
public class HttpRequst extends BaseHttpRequest
{
    private static HttpRequst instance = null;

    public static String BASE_URL = "http://appt.changtounet.com/api/";

//    public static int REQ_METHOD_POST = 10000;
    public final static int REQ_TYPE_PING = 1;

    // get请求的reqType<1000;
    public final static int REQ_TYPE_PRODUCT_TYPE = 17;

    //app banner 图接口
    public final static int REQ_TYPE_PRODUCT_BANNER = 10;

//    public final static int REQ_TYPE_HOMEPAGE = 11;

    public final static int REQ_TYPE_PRODUCT_HOME = 21;
    public final static int REQ_TYPE_PRODUCT_LIST = 22;
    public final static int REQ_TYPE_PRODUCT_DETAILS = 1023;
//    public final static int REQ_TYPE_PRODUCT_CONTRACT = 24;
//    public final static int REQ_TYPE_PRODUCT_INVESTOR = 25;

    //投资
    public final static int REQ_TYPE_INVEST = 1030;

    //获取提现信息
    public final static int REQ_TYPE_WITHDRAWINFO = 50;

    public final static int REQ_TYPE_WITHDRAW = 1050;

    public final static int REQ_TYPE_BANKCARD = 26;
    public final static int REQ_TYPE_INVEST_LIST = 27;
    public final static int REQ_TYPE_TRANSFER_LIST = 28;

    //交易记录
    public final static int REQ_TYPE_TRADE_LIST = 29;

    public final static int REQ_TYPE_FLOW = 30;
    public final static int REQ_TYPE_REWARDS_LIST= 31;
    public final static int REQ_TYPE_USERINFO = 32;

    public final static int REQ_TYPE_LOGIN = 1001;
    public final static int REQ_TYPE_ADDBANK = 1010;
    public final static int REQ_TYPE_DELBANK = 1011;
    public final static int REQ_TYPE_CHANGEBANK = 1012;

    //注册相关
    public final static int REQ_TYPE_SENDMSG = 1080;
    public final static int REQ_TYPE_CHECKCODE = 1081;
    public final static int REQ_TYPE_POSTREG = 1082;
    public final static int REQ_TYPE_NEWPWD = 1083;   //更改新密码

//    //手机号码认证
//    public final static int REQ_TYPE_PHONE_CHECK = 1100;

    //实名认证
    public final static int REQ_TYPE_CERTIFY = 1101;

    //签到
    public final static int REQ_TYPE_SIGN = 80;
    public final static int REQ_TYPE_ISSIGN = 81;

    public final static int REQ_TYPE_RECOMMENDLIST = 35;

    public final static int REQ_TYPE_RECOMMEND_SENDSMS = 1300;

    public final static int REQ_TYPE_MOBILE_LIST = 36;

    public final static int REQ_TYPE_ISREG = 1818;

    public final static int REQ_TYPE_DEAL = 2011;

    public final String REQ_URL_PING = BASE_URL + "usertoken/getconnection";

    //banner
    public final String REQ_URL_PRODUCT_BANNER = BASE_URL + "product/GetFocusImg";

    public final String REQ_URL_HOMEPAGE = BASE_URL + "AppRecommend/getAppRecomm";

    //产品
    public static String REQ_URL_PRODUCT_TYPE = BASE_URL + "product/GetProductType";
    public static String REQ_URL_PRODUCT_LIST = BASE_URL + "product/GetProductList?";
    public static String REQ_URL_PRODUCT_DETAILS = BASE_URL + "product/PostProductDetail";

    public static String REQ_URL_INVEST = BASE_URL + "Invest/PostInvest?";

    //投资post
//    public static String REQ_URL_PRODUCT_CONTRACT = BASE_URL + "products";
//    public static String REQ_URL_PRODUCT_INVESTOR = BASE_URL + "products";

    //获取提现信息
    public static String REQ_URL_WITHDRAWINFO = BASE_URL + "Withdraw/GetWithdrawDataBinds?";

    //提现操作
    public static String REQ_URL_WITHDRAW = BASE_URL + "Withdraw/PostWithdrawInfo?";

    //银行卡列表
    public static String REQ_URL_BANKCARD = BASE_URL + "bankcard/getbanklistinfo?";

    //添加银行卡
    public static String REQ_URL_ADDBANK = BASE_URL + "bankcard/postaddbankcard?";

    //删除银行卡
    public static String REQ_URL_DELBANK = BASE_URL + "bankcard/postdeletebankcard?";

    //更换默认取现银行卡
    public static String REQ_URL_CHANGEBANK = BASE_URL + "bankcard/postsetbankcard?";

    //登陆相关接口
    public static final String REQ_URL_LOGIN = BASE_URL + "usertoken/postlogin";

    public static final String FEQ_URL_USERINFO = BASE_URL + "usertoken/GetUserInfo?";

    //获取投资列表
    public static String REQ_URL_INVEST_LIST = BASE_URL + "investrecord/getinvestlist?";

    //获取站内转让列表
//    public static String REQ_URL_TRANSFER_LIST = BASE_URL + "products";

    //资金流接口
//    public static String FEQ_URL_FLOW = BASE_URL + "products";

    //推荐好友列表接口
    public static String FEQ_URL_REWARDS_LIST = BASE_URL + "products";

    //交易记录
    public static String REQ_URL_TRADE_LIST = BASE_URL + "DealRecord/GetDealDatabinds?";

    //发送短信
    public static String REQ_URL_SENDMSG = BASE_URL + "usertoken/postsendmsg?";

    //校验验证码
    public static String REQ_URL_CHECKCODE = BASE_URL + "usertoken/postcheckcode?";

    //注册用户
    public static String REQ_URL_POSTREG = BASE_URL + "usertoken/postreg?";

    //更改密码
    public static String REQ_URL_NEWPWD = BASE_URL + "usertoken/postnewpwd?";

    public static String REQ_URL_CERTIFY = BASE_URL + "certification/PostCertiIdcard?";

    //手机号码认证
//    public static String REQ_URL_PHONE_CHECK = BASE_URL + "usertoken/postvalidmobile?";

    public static String REQ_URL_SIGN = BASE_URL + "usertoken/getsign?";
    public static String REQ_URL_ISSIGN = BASE_URL + "usertoken/getissign?";

    //推荐好友列表
    public static String REQ_URL_RECOMMENDLIST = BASE_URL + "AppRecommend/getTuijian?";

    //发送推荐好友短信
    public static String REQ_URL_RECOMMEND_SENDSMS = BASE_URL + "AppRecommend/PostSendSms?";

    //获取今日已推荐列表
    public static String REQ_URL_MOBILE_LIST = BASE_URL + "AppRecommend/GetMobileList?";

    //手机是否注册
    public static String REQ_URL_ISREG = BASE_URL + "usertoken/postisreg?";

    public static String REQ_URL_DEAL = BASE_URL + "dealrecord/postdealdatabindsbytype?";

    public static String TOKEN = "";

    public final static int REQ_TYPE_RECHARGE = 2001;
    public static String REQ_URL_RECHARGE = BASE_URL + "quickpay/postprepay?";

    public final static int REQ_TYPE_GIFTEXCHANGE = 2108;
    public static String REQ_URL_GIFTEXCHANGE = BASE_URL + "UserToken/postTouyuanLijinExchange?";


    //充值成功回调
    public final static int REQ_TYPE_QUICKPAY_SUCC = 2308;
    public static String REQ_URL_QUICKPAY_SUCC = BASE_URL + "QuickPay/PostPayResult?";

    //充值记录
    public final static int REQ_TYPE_QUICKPAY_REC = 90;
    public static String REQ_URL_QUICKPAY_REC = BASE_URL + "QuickPay/GetChargeRecords?";

    //提现记录
    public final static int REQ_TYPE_WITHDRAW_REC = 91;
    public static String REQ_URL_WITHDRAWE_REC = BASE_URL + "QuickPay/GetTxRecords?";

    public static synchronized HttpRequst getInstance()
    {
        if (instance == null)
            instance = new HttpRequst();
        return instance;
    }

    /**
     *描述: 根据Type 返回请求类型
     *
     * @param reqType 请求类型
     * @return URL
     */
    public String getUrl(int reqType)
    {
        String url = null;

        TOKEN =  "userid=" + BaseApplication.getInstance().getUserId() +
                "&token=" + BaseApplication.getInstance().getToken();

        switch (reqType)
        {
            case REQ_TYPE_PING:
                url= REQ_URL_PING;
                break;
            case REQ_TYPE_PRODUCT_BANNER:
                url = REQ_URL_PRODUCT_BANNER;
                break;
            case REQ_TYPE_PRODUCT_TYPE:
                url = REQ_URL_PRODUCT_TYPE;
                break;
            case REQ_TYPE_PRODUCT_HOME:
                url = REQ_URL_HOMEPAGE;
                break;
            case REQ_TYPE_PRODUCT_LIST:
                url = REQ_URL_PRODUCT_LIST;
                break;
            case REQ_TYPE_PRODUCT_DETAILS:
                url = REQ_URL_PRODUCT_DETAILS;
                break;
            case REQ_TYPE_LOGIN:
                url =  REQ_URL_LOGIN;
                break;
            case REQ_TYPE_BANKCARD:
                url =  REQ_URL_BANKCARD + TOKEN;
                break;
            case REQ_TYPE_INVEST_LIST:
                url =  REQ_URL_INVEST_LIST + TOKEN;
                break;
//            case REQ_TYPE_TRANSFER_LIST:
//                url = REQ_URL_TRANSFER_LIST + TOKEN;
//                break;
            case REQ_TYPE_TRADE_LIST:
                url = REQ_URL_TRADE_LIST + TOKEN;
                break;
//            case REQ_TYPE_FLOW:
//                url = FEQ_URL_FLOW + TOKEN;
//                break;
            case REQ_TYPE_REWARDS_LIST:
                url = FEQ_URL_REWARDS_LIST + TOKEN;
                break;
            case REQ_TYPE_USERINFO:
                url = FEQ_URL_USERINFO + TOKEN;
                break;
            case REQ_TYPE_ADDBANK:
                url = REQ_URL_ADDBANK + TOKEN;
                break;
            case REQ_TYPE_DELBANK:
                url =  REQ_URL_DELBANK + TOKEN;
                break;
            case REQ_TYPE_CHANGEBANK:
                url = REQ_URL_CHANGEBANK + TOKEN;
                break;
            case REQ_TYPE_INVEST:
                url = REQ_URL_INVEST + TOKEN;
                break;
            case REQ_TYPE_WITHDRAW:
                url =  REQ_URL_WITHDRAW + TOKEN;
                break;
            case REQ_TYPE_WITHDRAWINFO:
                url = REQ_URL_WITHDRAWINFO + TOKEN;
                break;
            case REQ_TYPE_SENDMSG:
                url = REQ_URL_SENDMSG + TOKEN;
                break;
            case REQ_TYPE_CHECKCODE:
                url = REQ_URL_CHECKCODE + TOKEN;
                break;
            case REQ_TYPE_POSTREG:
                url = REQ_URL_POSTREG + TOKEN;
                break;
            case REQ_TYPE_NEWPWD:
                url =  REQ_URL_NEWPWD + TOKEN;
                break;
            case REQ_TYPE_CERTIFY:
                url =  REQ_URL_CERTIFY + TOKEN;
                break;
//            case REQ_TYPE_PHONE_CHECK:
//                url =  REQ_URL_PHONE_CHECK + TOKEN;
//                break;
            case REQ_TYPE_SIGN:
                url = REQ_URL_SIGN + TOKEN;
                break;
            case REQ_TYPE_ISSIGN:
                url =  REQ_URL_ISSIGN + TOKEN;
                break;
            case REQ_TYPE_RECOMMENDLIST:
                url = REQ_URL_RECOMMENDLIST + TOKEN;
                break;
            case REQ_TYPE_RECOMMEND_SENDSMS:
                url = REQ_URL_RECOMMEND_SENDSMS + TOKEN;
                break;
            case REQ_TYPE_MOBILE_LIST:
                url = REQ_URL_MOBILE_LIST + TOKEN;
                break;
            case REQ_TYPE_ISREG:
                url = REQ_URL_ISREG + TOKEN;
                break;
            case REQ_TYPE_RECHARGE:
                url = REQ_URL_RECHARGE + TOKEN;
                break;
            case REQ_TYPE_DEAL:
                url = REQ_URL_DEAL + TOKEN;
                break;
            case REQ_TYPE_GIFTEXCHANGE:
                url = REQ_URL_GIFTEXCHANGE + TOKEN;
                break;
            case REQ_TYPE_QUICKPAY_SUCC:
                url = REQ_URL_QUICKPAY_SUCC + TOKEN;
                break;
            case REQ_TYPE_QUICKPAY_REC:
                url = REQ_URL_QUICKPAY_REC + TOKEN;
                break;
            case REQ_TYPE_WITHDRAW_REC:
                url = REQ_URL_WITHDRAWE_REC + TOKEN;
                break;
            default:
                break;
        }

        return url;
    }

    /**
     * 请求地址需要格式化的
     */
    public String getUrl(int mId, String format)
    {
        switch (mId)
        {
            // case REQ_METHOD_GET_MORE_REPLY:
            // return String.format(REQ_URL_GET_REPLY, format);
            default:
                return format + getUrl(mId);
        }
    }

    /**
     * 描述:返回解析对象
     *
     * @param type 请求类型
     * @return 解析实体对象
     */
    public BaseEntity getPaser(int type)
    {
        BaseEntity paser = null;
        switch (type)
        {
            case REQ_TYPE_PRODUCT_HOME:
                paser = new PromotionEntity();
                break;
            case REQ_TYPE_PRODUCT_LIST:
                paser = new ProductEntity();
                break;
            case REQ_TYPE_PRODUCT_DETAILS:
                paser = new ProductDetailsEntity();
                break;
            case REQ_TYPE_LOGIN:
                paser = new UserEntity();
                break;
            case REQ_TYPE_BANKCARD:
                paser = new BankCardEntity();
                break;
            case REQ_TYPE_INVEST_LIST:
                paser = new InvestListEntity();
                break;
            case REQ_TYPE_TRANSFER_LIST:
                paser = new TransferListEntity();
                break;
            case REQ_TYPE_TRADE_LIST:
                paser = new TradeEntity();
                break;
            case REQ_TYPE_FLOW:
                paser = new FlowEntity();
                break;
            case REQ_TYPE_REWARDS_LIST:
                paser = new RewardsEntity();
                break;
            case REQ_TYPE_USERINFO:
                paser = UserInfoEntity.getInstance();
                break;
            case REQ_TYPE_DEAL:
                paser = new TradeEntity();
                break;
            case REQ_TYPE_QUICKPAY_REC:
                paser = new RechargeEntity();
                break;
            case REQ_TYPE_WITHDRAW_REC:
                paser = new WithdrawEntity();
                break;
            default:
                break;
        }
        return paser;
    }
}
