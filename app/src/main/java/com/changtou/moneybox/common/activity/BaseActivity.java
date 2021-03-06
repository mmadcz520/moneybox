package com.changtou.moneybox.common.activity;

import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.changtou.moneybox.common.http.base.BaseHttpClient;
import com.changtou.moneybox.common.http.base.HttpCallback;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.http.impl.AsyncHttpClientImpl;

/**
 * 描述: Tab页，导航栏在底部
 *
 * @author zhoulongfei
 */
public abstract class BaseActivity extends FragmentActivity implements HttpCallback {

    public RequestParams mParams;
    public Click click;
    HashMap<String, String> UMENG_MAP = new HashMap<String, String>();
    private Context mcontext;
    protected boolean bBackGround;
    public BaseApplication app;
    public String mDeviceId;

    /**
     * @see android.app.Activity#onCreate(Bundle)
     * @param bundle
     */
    protected void onCreate(Bundle bundle) {

        //分支

        super.onCreate(bundle);
        click = new Click();
        mcontext=this.getApplicationContext();
        if (mParams == null)
            mParams = new RequestParams();
        app=(BaseApplication) getApplication();
        mDeviceId =app.mDeviceId;
        initView(bundle);
        initLisener();
        initData(bundle);
    }

    /**
     * @see HttpCallback#onSuccess(String, Object, int)
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType) {
        // TODO Auto-generated method stub
    }

    /**
     * 设置监听事件
     * @param id
     */
    public void setOnClickListener(int id) {
        if (click == null)
            click = new Click();
        findViewById(id).setOnClickListener(click);
    }

    private class Click implements OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            treatClickEvent(id);
            treatClickEvent(v);
        }
    }

    /**
     * @param categray
     *            eventID
     * @param key
     *            key
     * @param value
     *            value
     * */
    public void sendUmeng(String categray, String key, String value) {
        UMENG_MAP = new HashMap<String, String>();
        UMENG_MAP.put(key, value);
        //todo
       // MobclickAgent.onEvent(this, categray, UMENG_MAP);
    }

    public void sendUmeng(String categray, String key) {
       // MobclickAgent.onEvent(this, categray, key);
        //todo
    }

    public void showToast(String s) {
        Toast toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void treatClickEvent(int id) {

    }

    public void treatClickEvent(View v) {

    }

    /**
     * 初始化布局和控件
     *
     * @param bundle
     */
    protected abstract void initView(Bundle bundle);

    /**
     * 初始化监听
     */
    protected abstract void initLisener();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle bundle);



    @Override
    public void onFailure(Throwable error, String content, int reqType) {
        // TODO Auto-generated method stub

    }

    public void sendRequest(int reqType, String url, RequestParams params,
                            BaseHttpClient baseHttpClient, boolean showDialog) {
        // TODO Auto-generated method stub
        if(baseHttpClient!=null){
            if (reqType > 10000) {
                baseHttpClient.post(reqType, this, url, params, this);
            } else {
                baseHttpClient.get(reqType, url, params, this);
            }
        }
    }


    /**
     * 得到 http aynsc客户端 实例对象
     * @return
     */
    public AsyncHttpClientImpl getAsyncClient(){
        return app.mAsyncClient;
    }

    /**
     * 屏幕回到前台时要执行的操作
     *
     * @see android.app.Activity#onResume()
     */
    protected void onResume() {
        Log.e("ChangTou", "onResume++++++++++++++++++++++++++");
        super.onResume();
    }
}
