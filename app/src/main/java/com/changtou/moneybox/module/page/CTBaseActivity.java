package com.changtou.moneybox.module.page;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseActivity;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.module.CTMoneyApplication;

/**
 * 描述: CT钱包app 页面基类
 *
 * @author zhoulongfei
 * @since 2015-3-23
 */
abstract public class CTBaseActivity extends BaseActivity{

    public CTMoneyApplication mCtApp = null;
    public CTBaseActivity mBaseAct = null;

    private TextView mPageTitleView = null;

    public static final int PAGE_TYPE_HOME = 0;
    public static final int PAGE_TYPE_SUB  = 1;
    private int mPageType = 0;

    private boolean mShowRightBtn = false;

    private LinearLayout mRightTouc = null;

    LinearLayout mLefttouc = null;

    private TextView mRightTextView = null;

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
        mPageType = setPageType();
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
    protected void onResume()
    {
        if (mCtApp.isAppOnForeground())
        {
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
     * @see BaseActivity#initListener()
     */
    protected void initListener() {

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
        ActionBar actionBar = this.getActionBar();

        if( null != actionBar)
        {
            int layoutId = R.layout.common_actionbar;
            actionBar.setDisplayShowHomeEnabled( false );
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(layoutId, null);
            ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(v,layout);

            mPageTitleView = (TextView)v.findViewById(R.id.page_title);
            mLefttouc = (LinearLayout) findViewById(R.id.touchLeft);
            ImageView leftBtn = (ImageView)findViewById(R.id.menuBtn);

            mRightTouc = (LinearLayout) findViewById(R.id.touchRight);
            mRightTextView = (TextView)findViewById(R.id.menuBtnRight);

            if(mPageType == PAGE_TYPE_SUB)
            {
                leftBtn.setVisibility(View.VISIBLE);
                mLefttouc.setOnClickListener(new View.OnClickListener()
                {
                    /*
                     * (non-Javadoc)
                     * @see android.view.View.OnClickListener#onClick(android.view.View)
                     */
                    public void onClick(View arg0)
                    {
                        CTBaseActivity.this.finish();
                        onBackIcon();
                    }
                });
            }
        }
    }

    /**
     * 设置页面标题
     */
    public void setPageTitle(String pageTitle)
    {
        mPageTitleView.setText(pageTitle);
    }

    /**
     * 设置页面类型
     */
    abstract protected int setPageType();

    protected void onDestroy()
    {
        if(mPageType != PAGE_TYPE_SUB)
        {
            BaseApplication.getInstance().AppExit();
        }

        super.onDestroy();
    }

    protected void onBackIcon()
    {

    }

    protected void showRightBtn(boolean isShow)
    {
        mShowRightBtn = isShow;
        mRightTouc.setVisibility(View.VISIBLE);
    }

    protected void setRightBtnName(String name)
    {
        if(mRightTextView != null)
        {
            mRightTextView.setText(name);
        }
    }

    protected void setRightBtnOnClickListener(View.OnClickListener listener)
    {
        mRightTouc.setOnClickListener(listener);
    }

    protected void setLeftBtnOnClickListener(View.OnClickListener listener)
    {
        mLefttouc.setOnClickListener(listener);
    }
}
