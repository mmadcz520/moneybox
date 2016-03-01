package com.changtou.moneybox.module.usermodule;

import android.content.Context;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.http.base.HttpCallback;
import com.changtou.moneybox.common.http.impl.AsyncHttpClientImpl;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.AppUtil;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * 描述: 用户信息管理模块
 *
 * @author zhoulongfei
 * @since 2015-5-19
 */
public class UserManager implements HttpCallback {

    private LoginNotifier mLoginNotifier = null;

    public BaseApplication mBaseApp = null;

    private Context mContext = null;

    /**
     * 初始化登陆模块
     *
     * @throws java.io.IOException
     */
    public void init(Context context) throws IOException {

        mBaseApp = BaseApplication.getInstance();
        this.mContext = context;
    }

    // 登陆
    public void logIn(String username, String password) {
        AsyncHttpClientImpl client = mBaseApp.getAsyncClient();
        String loginUrl = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_LOGIN);
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("pwd", password);
        params.put("ly", "android");
        AppUtil.getVersionCode(BaseApplication.getInstance());
        params.put("version", BaseApplication.getInstance().getVersionName());
        client.post(HttpRequst.REQ_TYPE_LOGIN, mBaseApp, loginUrl, params, this);
    }


    // 设置通知响应事件
    public void setLoginNotifier(LoginNotifier loginNotifier) {
        this.mLoginNotifier = loginNotifier;
    }

    /**
     * @param content 返回值
     * @param object  返回的转化对象
     * @param reqType 请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType) {
        if (reqType == HttpRequst.REQ_TYPE_LOGIN) {
            ACache mCache = ACache.get(mContext);  //缓存类
            JSONObject jb;
            int error;
            try {
                jb = new JSONObject(content);
                error = jb.getInt("errcode");

                switch (error) {
                    case 0: {
                        BaseApplication.getInstance().setToken(jb.getString("token"));
                        BaseApplication.getInstance().setUserId(jb.getString("userid"));
                        mLoginNotifier.loginSucNotify();
                        getUserInfo();

                        break;
                    }

                    default: {
//                        for (int i = 0; i < mNotifierContainer.size(); i++)
//                        {
//                            if (mNotifierContainer.get(i) != null) {
                        mLoginNotifier.loginErrNotify(error);
//                            }
//                        }
                        break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (reqType == HttpRequst.REQ_TYPE_USERINFO) {
//            for (int i = 0; i < mNotifierContainer.size(); i++)
//            {
//                if (mNotifierContainer.get(i) != null)
//                {
            mLoginNotifier.loginUserInfo(object);
//                }
//            }
        }
    }

    /**
     * \
     *
     * @param error   错误
     * @param content 返回值
     * @param reqType 请求的唯一识别
     */
    public void onFailure(Throwable error, String content, int reqType) {
//        for (int i = 0; i < mNotifierContainer.size(); i++)
//        {
        mLoginNotifier.loginErrNotify(reqType);
//        }
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        AsyncHttpClientImpl client = mBaseApp.getAsyncClient();
        String url = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_USERINFO);
        RequestParams params = new RequestParams();

        client.get(HttpRequst.REQ_TYPE_USERINFO, mBaseApp, url, params, this);
    }
}
