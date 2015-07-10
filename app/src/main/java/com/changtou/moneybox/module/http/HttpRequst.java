package com.changtou.moneybox.module.http;

import com.changtou.moneybox.common.http.base.BaseEntity;
import com.changtou.moneybox.common.http.base.BaseHttpRequest;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.FlowEntity;
import com.changtou.moneybox.module.entity.InvestListEntity;
import com.changtou.moneybox.module.entity.InvestorEntity;
import com.changtou.moneybox.module.entity.ProductContractEntity;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.entity.PromotionEntity;
import com.changtou.moneybox.module.entity.RewardsEntity;
import com.changtou.moneybox.module.entity.TradeEntity;
import com.changtou.moneybox.module.entity.TransferListEntity;
import com.changtou.moneybox.module.entity.UserEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;

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

    // get请求的reqType<1000;
    public final static int REQ_TYPE_PRODUCT_TYPE = 17;


    //app banner 图接口
    public final static int REQ_TYPE_PRODUCT_BANNER = 10;

    public final static int REQ_TYPE_PRODUCT_HOME = 21;
    public final static int REQ_TYPE_PRODUCT_LIST = 22;
    public final static int REQ_TYPE_PRODUCT_DETAILS = 1023;
    public final static int REQ_TYPE_PRODUCT_CONTRACT = 24;
    public final static int REQ_TYPE_PRODUCT_INVESTOR = 25;

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

    //banner
    public final String REQ_URL_PRODUCT_BANNER = BASE_URL + "product/GetFocusImg";

    //产品
    public static String REQ_URL_PRODUCT_TYPE = BASE_URL + "product/GetProductType";
    public static String REQ_URL_PRODUCT_LIST = BASE_URL + "product/GetProductList?";
    public static String REQ_URL_PRODUCT_DETAILS = BASE_URL + "product/PostProductDetail";

    public static String REQ_URL_INVEST = BASE_URL + "Invest/PostInvest?";

    //投资post
    public static String REQ_URL_PRODUCT_CONTRACT = BASE_URL + "products";
    public static String REQ_URL_PRODUCT_INVESTOR = BASE_URL + "products";

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
    public static String REQ_URL_INVEST_LIST = BASE_URL + "bankcard/getbanklistinfo?";

    //获取站内转让列表
    public static String REQ_URL_TRANSFER_LIST = BASE_URL + "products";

    //资金流接口
    public static String FEQ_URL_FLOW = BASE_URL + "products";

    //推荐好友列表接口
    public static String FEQ_URL_REWARDS_LIST = BASE_URL + "products";

    //交易记录
    public static String REQ_URL_TRADE_LIST = BASE_URL + "investrecord/getinvestlist?";

    //发送短信
    public static String REQ_URL_SENDMSG = BASE_URL + "usertoken/postsendmsg?";

    //校验验证码
    public static String REQ_URL_CHECKCODE = BASE_URL + "usertoken/postcheckcode?";

    //注册用户
    public static String REQ_URL_POSTREG = BASE_URL + "usertoken/postreg?";

    //更改密码
    public static String REQ_URL_NEWPWD = BASE_URL + "usertoken/postnewpwd?";

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
        switch (reqType)
        {
            case REQ_TYPE_PRODUCT_BANNER:
                return REQ_URL_PRODUCT_BANNER;
            case REQ_TYPE_PRODUCT_TYPE:
                return REQ_URL_PRODUCT_TYPE;
            case REQ_TYPE_PRODUCT_HOME:
                return "http://autoapp.hsxiang.com/wp-admin/admin-ajax.php?action=";
            case REQ_TYPE_PRODUCT_LIST:
                return REQ_URL_PRODUCT_LIST;
            case REQ_TYPE_PRODUCT_DETAILS:
                return REQ_URL_PRODUCT_DETAILS;
            case REQ_TYPE_PRODUCT_CONTRACT:
                return REQ_URL_PRODUCT_CONTRACT;
            case REQ_TYPE_PRODUCT_INVESTOR:
                return REQ_URL_PRODUCT_INVESTOR;
            case REQ_TYPE_LOGIN:
                return REQ_URL_LOGIN;
            case REQ_TYPE_BANKCARD:
                return REQ_URL_BANKCARD;
            case REQ_TYPE_INVEST_LIST:
                return REQ_URL_INVEST_LIST;
            case REQ_TYPE_TRANSFER_LIST:
                return REQ_URL_TRANSFER_LIST;
            case REQ_TYPE_TRADE_LIST:
                return REQ_URL_TRADE_LIST;
            case REQ_TYPE_FLOW:
                return FEQ_URL_FLOW;
            case REQ_TYPE_REWARDS_LIST:
                return FEQ_URL_REWARDS_LIST;
            case REQ_TYPE_USERINFO:
                return FEQ_URL_USERINFO;
            case REQ_TYPE_ADDBANK:
                return REQ_URL_ADDBANK;
            case REQ_TYPE_DELBANK:
                return REQ_URL_DELBANK;
            case REQ_TYPE_CHANGEBANK:
                return REQ_URL_CHANGEBANK;
            case REQ_TYPE_INVEST:
                return REQ_URL_INVEST;
            case REQ_TYPE_WITHDRAW:
                return REQ_URL_WITHDRAW;
            case REQ_TYPE_WITHDRAWINFO:
                return REQ_URL_WITHDRAWINFO;
            case REQ_TYPE_SENDMSG:
                return REQ_URL_SENDMSG;
            case REQ_TYPE_CHECKCODE:
                return REQ_URL_CHECKCODE;
            case REQ_TYPE_POSTREG:
                return REQ_URL_POSTREG;
            case REQ_TYPE_NEWPWD:
                return REQ_URL_NEWPWD;
            default:
                break;
        }

        return null;
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
            case REQ_TYPE_PRODUCT_CONTRACT:
                paser = new ProductContractEntity();
                break;
            case REQ_TYPE_PRODUCT_INVESTOR:
                paser = new InvestorEntity();
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
            default:
                break;
        }
        return paser;
    }
}
