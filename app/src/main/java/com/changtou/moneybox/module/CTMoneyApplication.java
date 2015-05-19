package com.changtou.moneybox.module;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.base.BaseHttpRequest;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.usermodule.UserManager;
//import com.changtou.moneybox.module.usermodule.UserManager;

import java.io.IOException;

/**
 * 描述: ChangTou Money App 基类
 *
 * @author zhoulongfei
 * @since 2015-3-13
 *
 * //版本分支  developer zhou
 *
 */
public class CTMoneyApplication extends BaseApplication {

    public void onCreate() {
        super.onCreate();
    }

//    /**
//     *获取cookieStore
//     * @return mCookisStore
//     */
//    public RzAppCookieStore getCookieStore() {
//        return mCookisStore;
//    }

    /**
     * @see BaseApplication#initBaseHttpRequest()
     * @return
     */
    protected BaseHttpRequest initBaseHttpRequest() {
        return HttpRequst.getInstance();
    }

}
