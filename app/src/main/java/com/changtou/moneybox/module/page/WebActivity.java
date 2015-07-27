package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2015/7/8.
 */
public class WebActivity extends CTBaseActivity
{
    private WebView myWebView = null;

    private String mUrl = "";

    @Override
    protected void initView(Bundle bundle) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_web_layout);

        Intent intent = this.getIntent();
        mUrl = intent.getStringExtra("url");

        myWebView = (WebView) findViewById(R.id.ddwebview);
        myWebView.loadUrl(mUrl);
        myWebView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }

        });

        WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        myWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    // 网页加载完成

                } else {

                }
            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        setPageTitle("关于我们");
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            if(myWebView.canGoBack())
            {
                myWebView.goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
