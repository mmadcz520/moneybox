package com.changtou.moneybox.module.page;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseActivity;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.module.CTMoneyApplication;

/**
 * 描述: CT钱包app 页面基类
 *
 * @author zhoulongfei
 * @since 2015-3-23
 */
public class CTBaseActivity extends BaseActivity{

    public CTMoneyApplication mCtApp = null;

    private static String mToken = null;

    public CTBaseActivity mBaseAct = null;

    public int i_curReqTimes;

    /**
     * @see android.app.Activity#onCreate(Bundle)
     * @param bundle
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCtApp = (CTMoneyApplication)getApplication();

        if (mParams == null)
            mParams = new RequestParams();
        mBaseAct = this;
        initActionBarLayout();
    }

    /**
     * @see android.app.Activity#onStop()
     */
    protected void onStop() {
        if (!mCtApp.isAppOnForeground()) {
            bBackGround = true;
            onBackground();
        }
        //todo 友盟
        super.onStop();
    }

    /**
     * @see android.app.Activity#onResume()
     */
    protected void onResume() {
        if (mCtApp.isAppOnForeground()) {
            bBackGround = false;
            onForeground();
        }
        //todo
        super.onResume();
    }

    /**
     * @see BaseActivity#initView(Bundle)
     * @param bundle
     */
    protected void initView(Bundle bundle) {

    }


    /**
     * @see BaseActivity#initLisener()
     */
    protected void initLisener() {

    }

    /**
     * @see BaseActivity#initData()
     */
    protected void initData() {

    }

    /**
     * app 切换到后台
     */
    public void onBackground() {
        mCtApp.onBackground();
    }

    /**
     * app 回到前台
     */
    public void onForeground() {
        mCtApp.onForeground();
    }

    /**
     * app 退出
     */
    public void quitApp() {
        mCtApp.quitApp();
    }

    /**
     * 初始化请求参数
     * @return
     */
    public RequestParams initParameter() {
        if (mParams != null)
            mParams.clear();
        return mParams;
    }

    /**
     * 自定义Action Bar
     * */
    public void initActionBarLayout()
    {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setElevation(0);

        if( null != actionBar)
        {
            int layoutId = R.layout.common_actionbar;
            actionBar.setDisplayShowHomeEnabled( false );
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(layoutId, null);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(v,layout);
        }
    }
}
