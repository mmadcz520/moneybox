package com.changtou.moneybox.module.usermodule;

import android.content.Context;
import android.util.Log;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.http.base.HttpCallback;
import com.changtou.moneybox.common.http.impl.AsyncHttpClientImpl;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述: 用户信息管理模块
 *
* @author zhoulongfei
* @since 2015-5-19
*/
public class UserManager implements HttpCallback {

	private List<LoginNotifier> mNotifierContainer = null;

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

		mNotifierContainer = new ArrayList<>();
	}

	// 登陆
	public void logIn(String username, String password)
    {
        AsyncHttpClientImpl client = mBaseApp.getAsyncClient();
        String loginUrl = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_LOGIN);
        RequestParams params = new RequestParams();
        params.put("username", username);
		params.put("pwd", password);
        client.post(HttpRequst.REQ_TYPE_LOGIN, mBaseApp, loginUrl, params, this);
	}

	// 设置通知响应事件
	public void setLoginNotifier(LoginNotifier loginNotifier) {

		this.mLoginNotifier = loginNotifier;
	}

    /**
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType)
    {
		ACache mCache = ACache.get(mContext);  //缓存类
        JSONObject jb;
        int error;
        try
        {
            jb = new JSONObject(content);
            error = jb.getInt("errcode");

			switch (error)
			{
				case 0:
				{
//					for (int i = 0; i < mNotifierContainer.size(); i++)
					mCache.put("userid", jb.getString("userid"));
					mCache.put("token", jb.getString("token"));
//					{
						mLoginNotifier.loginSucNotify();
//					}

					break;
				}

				default:
				{
//					for (int i = 0; i < mNotifierContainer.size(); i++)
//					{
						mLoginNotifier.loginErrNotify(error);
//					}
					break;
				}
			}

        }
		catch (JSONException e)
		{
			e.printStackTrace();
		}
    }

    /**
     * \
     * @param error    错误
     * @param content   返回值
     * @param reqType  请求的唯一识别
     */
    public void onFailure(Throwable error, String content, int reqType)
    {
		for (int i = 0; i < mNotifierContainer.size(); i++)
		{
			mNotifierContainer.get(i).loginErrNotify(6);
		}
    }
}
