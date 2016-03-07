package com.changtou.moneybox.module.http;

import com.changtou.moneybox.common.activity.BaseApplication;


/**
 * 描述:http Api 工具类
 * <p/>
 * 封装各种Http 请求
 *
 * @author zhoulongfei
 * @since 2015-04-10
 */
public enum HttpRequst {

    REQ_TYPE_PING("usertoken/getconnection", Type.GET),
    REQ_TYPE_PRODUCT_TYPE("product/GetProductType", Type.GET),
    REQ_TYPE_PRODUCT_BANNER("product/GetFocusImg", Type.GET),
    REQ_TYPE_PRODUCT_LIST("product/GetProductList?", Type.GET),
    REQ_TYPE_WITHDRAWINFO("Withdraw/GetWithdrawDataBinds?", Type.GET),
    REQ_TYPE_BANKCARD("product/GetFocusImg", Type.GET),
    REQ_TYPE_INVEST_LIST("product/GetFocusImg", Type.GET),
    REQ_TYPE_TRANSFER_LIST("product/GetFocusImg", Type.GET),
    REQ_TYPE_TRADE_LIST("product/GetFocusImg", Type.GET),
    REQ_TYPE_FLOW("product/GetFocusImg", Type.GET),
    REQ_TYPE_REWARDS_LIST("product/GetFocusImg", Type.GET),
    REQ_TYPE_USERINFO("product/GetFocusImg", Type.GET),
    REQ_TYPE_SIGN("product/GetFocusImg", Type.GET),
    REQ_TYPE_ISSIGN("product/GetFocusImg", Type.GET),
    REQ_TYPE_RECOMMENDLIST("product/GetFocusImg", Type.GET),
    REQ_TYPE_MOBILE_LIST("product/GetFocusImg", Type.GET),
    REQ_TYPE_CALENDER("product/GetFocusImg", Type.GET);

    //post
//        REQ_TYPE_PRODUCT_DETAILS,
//        REQ_TYPE_INVEST,
//        REQ_TYPE_WITHDRAW,
//        REQ_TYPE_LOGIN,
//        REQ_TYPE_ADDBANK,
//        REQ_TYPE_DELBANK,
//        REQ_TYPE_CHANGEBANK,
//        REQ_TYPE_SENDMSG,
//        REQ_TYPE_CHECKCODE,
//        REQ_TYPE_POSTREG,
//        REQ_TYPE_NEWPWD,
//        REQ_TYPE_PROMO,
//        REQ_TYPE_CERTIFY,
//        REQ_TYPE_RECOMMEND_SENDSMS,
//        REQ_TYPE_ISREG,
//        REQ_TYPE_DEAL,


    public String BASE_URL = "http://appt.changtounet.com/api/";

    private String Url;
    private Type reqType;

    enum Type {
        GET, POST
    }

    // 构造方法
    private HttpRequst(String url, Type type) {
        this.Url = BASE_URL + url + "userid=" + BaseApplication.getInstance().getUserId() +
                "&token=" + BaseApplication.getInstance().getToken()+ url;
        this.reqType = type;
    }

    public final String REQ_URL_PING = BASE_URL + "usertoken/getconnection";

    //banner
    public final String REQ_URL_PRODUCT_BANNER = BASE_URL + "product/GetFocusImg";

    public final String REQ_URL_HOMEPAGE = BASE_URL + "AppRecommend/getAppRecomm";

    //产品
    public static String REQ_URL_PRODUCT_TYPE = BASE_URL + "product/GetProductType";
    public static String REQ_URL_PRODUCT_LIST = BASE_URL + "product/GetProductList?";
    public static String REQ_URL_PRODUCT_DETAILS = BASE_URL + "product/PostProductDetail";

    public static String REQ_URL_INVEST = BASE_URL + "Invest/PostInvest?";


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

    public static final String FEQ_URL_USERINFO = BASE_URL + "usertoken/GetUserInfo_2?";

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

    public static String REQ_URL_CALENDER = BASE_URL + "UserToken/GetCalenderData?";

    public static String REQ_URL_PROMO = BASE_URL + "UserToken/PostRecom?";

    public final static int REQ_TYPE_ISHASJIAXI = 1908;
    public static String REQ_URL_ISHASJIAXI = BASE_URL + "invest/postIsJX?";

    public final static int REQ_TYPE_TUIJIANREN = 95;
    public static String REQ_URL_TUIJIANREN = BASE_URL + "usertoken/GetTuiMobile?";

    public final static int REQ_TYPE_JIANGLI = 96;
    public static String REQ_URL_JIANGLI = BASE_URL + "usertoken/GetRewardRecord?";

    public final static int REQ_TYPE_LIJIN = 97;
    public static String REQ_URL_LIJIN = BASE_URL + "usertoken/GetLijinRecord?";

    public static synchronized HttpRequst getInstance()
    {
        if (instance == null)
            instance = new HttpRequst();
        return instance;
    }

    public String getUrl() {
        return Url;
    }

//    /**
//     *描述: 根据Type 返回请求类型
//     *
//     * @param reqType 请求类型
//     * @return URL
//     */
//    public String getUrl(HttpGet reqType)
//    {
//        String url = null;
//
//        TOKEN =  "userid=" + BaseApplication.getInstance().getUserId() +
//                "&token=" + BaseApplication.getInstance().getToken();
//
//        switch (reqType)
//        {
//            case REQ_TYPE_PING:
//                url= REQ_URL_PING;
//                break;
//            case REQ_TYPE_PRODUCT_BANNER:
//                url = REQ_URL_PRODUCT_BANNER;
//                break;
//            case REQ_TYPE_PRODUCT_TYPE:
//                url = REQ_URL_PRODUCT_TYPE;
//                break;
//            case REQ_TYPE_PRODUCT_HOME:
//                url = PRODUCT_HOMEPRODUCT_HOME;
//                break;
//            case REQ_TYPE_PRODUCT_LIST:
//                url = REQ_URL_PRODUCT_LIST;
//                break;
//            case REQ_TYPE_PRODUCT_DETAILS:
//                url = REQ_URL_PRODUCT_DETAILS;
//                break;
//            case REQ_TYPE_LOGIN:
//                url =  REQ_URL_LOGIN;
//                break;
//            case REQ_TYPE_BANKCARD:
//                url =  REQ_URL_BANKCARD + TOKEN;
//                break;
//            case REQ_TYPE_INVEST_LIST:
//                url =  REQ_URL_INVEST_LIST + TOKEN;
//                break;
////            case REQ_TYPE_TRANSFER_LIST:
////                url = REQ_URL_TRANSFER_LIST + TOKEN;
////                break;
//            case REQ_TYPE_TRADE_LIST:
//                url = REQ_URL_TRADE_LIST + TOKEN;
//                break;
////            case REQ_TYPE_FLOW:
////                url = FEQ_URL_FLOW + TOKEN;
////                break;
//            case REQ_TYPE_REWARDS_LIST:
//                url = FEQ_URL_REWARDS_LIST + TOKEN;
//                break;
//            case REQ_TYPE_USERINFO:
//                url = FEQ_URL_USERINFO + TOKEN;
//                break;
//            case REQ_TYPE_ADDBANK:
//                url = REQ_URL_ADDBANK + TOKEN;
//                break;
//            case REQ_TYPE_DELBANK:
//                url =  REQ_URL_DELBANK + TOKEN;
//                break;
//            case REQ_TYPE_CHANGEBANK:
//                url = REQ_URL_CHANGEBANK + TOKEN;
//                break;
//            case REQ_TYPE_INVEST:
//                url = REQ_URL_INVEST + TOKEN;
//                break;
//            case REQ_TYPE_WITHDRAW:
//                url =  REQ_URL_WITHDRAW + TOKEN;
//                break;
//            case REQ_TYPE_WITHDRAWINFO:
//                url = REQ_URL_WITHDRAWINFO + TOKEN;
//                break;
//            case REQ_TYPE_SENDMSG:
//                url = REQ_URL_SENDMSG + TOKEN;
//                break;
//            case REQ_TYPE_CHECKCODE:
//                url = REQ_URL_CHECKCODE + TOKEN;
//                break;
//            case REQ_TYPE_POSTREG:
//                url = REQ_URL_POSTREG + TOKEN;
//                break;
//            case REQ_TYPE_NEWPWD:
//                url =  REQ_URL_NEWPWD + TOKEN;
//                break;
//            case REQ_TYPE_CERTIFY:
//                url =  REQ_URL_CERTIFY + TOKEN;
//                break;
////            case REQ_TYPE_PHONE_CHECK:
////                url =  REQ_URL_PHONE_CHECK + TOKEN;
////                break;
//            case REQ_TYPE_SIGN:
//                url = REQ_URL_SIGN + TOKEN;
//                break;
//            case REQ_TYPE_ISSIGN:
//                url =  REQ_URL_ISSIGN + TOKEN;
//                break;
//            case REQ_TYPE_RECOMMENDLIST:
//                url = REQ_URL_RECOMMENDLIST + TOKEN;
//                break;
//            case REQ_TYPE_RECOMMEND_SENDSMS:
//                url = REQ_URL_RECOMMEND_SENDSMS + TOKEN;
//                break;
//            case REQ_TYPE_MOBILE_LIST:
//                url = REQ_URL_MOBILE_LIST + TOKEN;
//                break;
//            case REQ_TYPE_ISREG:
//                url = REQ_URL_ISREG + TOKEN;
//                break;
//            case REQ_TYPE_RECHARGE:
//                url = REQ_URL_RECHARGE + TOKEN;
//                break;
//            case REQ_TYPE_DEAL:
//                url = REQ_URL_DEAL + TOKEN;
//                break;
//            case REQ_TYPE_GIFTEXCHANGE:
//                url = REQ_URL_GIFTEXCHANGE + TOKEN;
//                break;
//            case REQ_TYPE_QUICKPAY_SUCC:
//                url = REQ_URL_QUICKPAY_SUCC + TOKEN;
//                break;
//            case REQ_TYPE_QUICKPAY_REC:
//                url = REQ_URL_QUICKPAY_REC + TOKEN;
//                break;
//            case REQ_TYPE_WITHDRAW_REC:
//                url = REQ_URL_WITHDRAWE_REC + TOKEN;
//                break;
//            case REQ_TYPE_CALENDER:
//                url = REQ_URL_CALENDER + TOKEN;
//                break;
//            case REQ_TYPE_PROMO:
//                url = REQ_URL_PROMO + TOKEN;
//                break;
//            case REQ_TYPE_ISHASJIAXI:
//                url = REQ_URL_ISHASJIAXI + TOKEN;
//                break;
//            case REQ_TYPE_TUIJIANREN:
//                url = REQ_URL_TUIJIANREN + TOKEN;
//                break;
//            case REQ_TYPE_JIANGLI:
//                url = REQ_URL_JIANGLI + TOKEN;
//                break;
//            case REQ_TYPE_LIJIN:
//                url = REQ_URL_LIJIN + TOKEN;
//                break;
//            default:
//                break;
//        }
//
//        return url;
//    }
//
//    @Override
//    public String getUrl(int reqType) {
//        return null;
//    }

    /**
     * 请求地址需要格式化的
     */
//    public String getUrl(int mId, String format)
//    {
//        switch (mId)
//        {
//            // case REQ_METHOD_GET_MORE_REPLY:
//            // return String.format(REQ_URL_GET_REPLY, format);
//            default:
//                return format + getUrl(mId);
//        }
//    }
//
//    /**
//     * 描述:返回解析对象
//     *
//     * @param type 请求类型
//     * @return 解析实体对象
//     */
//    public BaseEntity getPaser(int type)
//    {
//        BaseEntity paser = null;
//        switch (type)
//        {
//            case REQ_TYPE_PRODUCT_HOME:
//                paser = new PromotionEntity();
//                break;
//            case REQ_TYPE_PRODUCT_LIST:
//                paser = new ProductEntity();
//                break;
//            case REQ_TYPE_PRODUCT_DETAILS:
//                paser = new ProductDetailsEntity();
//                break;
//            case REQ_TYPE_LOGIN:
//                paser = new UserEntity();
//                break;
//            case REQ_TYPE_BANKCARD:
//                paser = new BankCardEntity();
//                break;
//            case REQ_TYPE_INVEST_LIST:
//                paser = new InvestListEntity();
//                break;
//            case REQ_TYPE_TRANSFER_LIST:
//                paser = new TransferListEntity();
//                break;
//            case REQ_TYPE_TRADE_LIST:
//                paser = new TradeEntity();
//                break;
//            case REQ_TYPE_FLOW:
//                paser = new FlowEntity();
//                break;
//            case REQ_TYPE_REWARDS_LIST:
//                paser = new RewardsEntity();
//                break;
//            case REQ_TYPE_USERINFO:
//                paser = UserInfoEntity.getInstance();
//                break;
//            case REQ_TYPE_DEAL:
//                paser = new TradeEntity();
//                break;
//            case REQ_TYPE_QUICKPAY_REC:
//                paser = new RechargeEntity();
//                break;
//            case REQ_TYPE_WITHDRAW_REC:
//                paser = new WithdrawEntity();
//                break;
//            case REQ_TYPE_CALENDER:
//                paser = new FlowEntity();
//                break;
//            case REQ_TYPE_JIANGLI:
//                paser = new JiangLiInfoEntity();
//                break;
//            case REQ_TYPE_LIJIN:
//                paser = new LiJinEntity();
//           default:
//                break;
//        }
//        return paser;
//    }
}
