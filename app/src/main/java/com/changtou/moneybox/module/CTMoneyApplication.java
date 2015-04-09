package com.changtou.moneybox.module;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.base.BaseHttpRequest;
import com.changtou.moneybox.module.http.HttpRequst;
//import com.changtou.moneybox.module.usermodule.UserManager;

import java.io.IOException;

/**
 * 描述: ChangTou Money App 基类
 *
 * @author zhoulongfei
 * @since 2015-3-13
 */
public class CTMoneyApplication extends BaseApplication {

    //用户模块
//    private UserManager mUserManager = null;

    public void onCreate() {
        super.onCreate();

//        try {
//            mUserManager = new UserManager();
//            mUserManager.init(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 获取用户模块
     * @return mUserManager
     */
//    public UserManager getUserModule() {
//        return mUserManager;
//    }

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

    private void tes22()
    {

    }
}
