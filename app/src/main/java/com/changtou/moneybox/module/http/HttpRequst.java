package com.changtou.moneybox.module.http;

import com.changtou.moneybox.common.http.base.BaseEntity;
import com.changtou.moneybox.common.http.base.BaseHttpRequest;
import com.changtou.moneybox.module.entity.DemoEntity;
import com.changtou.moneybox.module.entity.ProductEntity;

/**
 * Created by Administrator on 2015/3/15 0015.
 */
public class HttpRequst extends BaseHttpRequest{
    private static HttpRequst instance = null;
//    public static String BASE_URL = "http://autoapp.hsxiang.com/wp-admin/admin-ajax.php?";
    public static String BASE_URL = "http://023151.ichengyun.net/";
    public static int REQ_METHOD_POST = 10000;
    // get请求的reqType<1000;

    public final static int REQ_METHOD_GET_DEMO_HOME = 21;
    public static String REQ_URL_GET_DEMO_HOME = BASE_URL
            + "app/findAllHonor4app.action";



    public static synchronized HttpRequst getInstance() {
        if (instance == null)
            instance = new HttpRequst();
        return instance;
    }

    private HttpRequst() {
    }

    public String getUrl(int reqType) {
        switch (reqType) {
            case REQ_METHOD_GET_DEMO_HOME:
                return REQ_URL_GET_DEMO_HOME;
        }
        return null;
    }

    /**
     * 请求地址需要格式化的
     * */
    public String getUrl(int mId, String format) {
        switch (mId) {
            // case REQ_METHOD_GET_MORE_REPLY:
            // return String.format(REQ_URL_GET_REPLY, format);
            default:
                return format + getUrl(mId);
        }
    }


    public BaseEntity getPaser(int type) {
        BaseEntity paser = null;
        switch (type) {
            case REQ_METHOD_GET_DEMO_HOME:
                paser =  new ProductEntity();
                break;
        }
        return paser;
    }
}
