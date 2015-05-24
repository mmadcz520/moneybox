package com.changtou.moneybox.module.http;

import com.changtou.moneybox.common.http.base.BaseEntity;
import com.changtou.moneybox.common.http.base.BaseHttpRequest;
//import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.entity.PromotionEntity;
import com.changtou.moneybox.module.entity.UserEntity;

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
    public final static int REQ_TYPE_PRODUCT_HOME = 21;
    public final static int REQ_TYPE_PRODUCT_LIST = 22;
    public final static int REQ_TYPE_PRODUCT_DETAILS = 23;
    public final static int REQ_TYPE_BANKCARD = 24;

    public final static int REQ_TYPE_LOGIN = 1001;

    //主页推荐产品列表
    public static String REQ_URL_PRODUCT_LIST = BASE_URL + "products";
    public static String REQ_URL_PRODUCT_DETAILS = BASE_URL + "products";

    //银行卡列表
    public static String REQ_URL_BANKCARD = BASE_URL + "products";

    //登陆相关接口
    public static String REQ_URL_LOGIN = "http://autoapp.hsxiang.com/wp-admin/admin-ajax.php?action=app_user_login";

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
            case REQ_TYPE_PRODUCT_HOME:
                return "http://autoapp.hsxiang.com/wp-admin/admin-ajax.php?action=";
            case REQ_TYPE_PRODUCT_LIST:
                return REQ_URL_PRODUCT_LIST;
            case REQ_TYPE_PRODUCT_DETAILS:
                return REQ_URL_PRODUCT_DETAILS;
            case REQ_TYPE_LOGIN:
                return REQ_URL_LOGIN;
            case REQ_TYPE_BANKCARD:
                return REQ_URL_BANKCARD;
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
            case REQ_TYPE_LOGIN:
                paser = new UserEntity();
                break;
            case REQ_TYPE_BANKCARD:
//                paser = new BankCardEntity();
                break;
            default:
                break;
        }
        return paser;
    }
}
