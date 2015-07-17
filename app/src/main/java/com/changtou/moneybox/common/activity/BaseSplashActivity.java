package com.changtou.moneybox.common.activity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.http.base.BaseHttpClient;
import com.changtou.moneybox.common.http.base.HttpCallback;
import com.changtou.moneybox.common.logger.Logger;
import com.changtou.moneybox.common.preference.DefaultPreference;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 描述:闪屏界面
 *
 * @author zhoulongfei
 * @since 2015-3-12
 */
public abstract class BaseSplashActivity extends BaseFragmentActivity implements HttpCallback {

    //渐变动画效果
    public ScaleAnimation mAnimation = null;

    //图片背景
    public ImageView mImagebackgruand = null;


    private RequestParams mParams = new RequestParams();

    @Override
    protected void onResume() {
        super.onResume();

//        sendEmptyUiMessageDelayed(MSG_UI_GOTO_MAIN_ACTIVITY, 5000);
        requestHomePage();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        BaseApplication.getInstance().addActivity(this);

        // mAsyncImageLoader = AsyncImageLoader.getImageLoader();
        mImagebackgruand = (ImageView) findViewById(R.id.image_backgruand);
        setSplashImage();
        if (IsUseAnimation()) {
            mAnimation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            mAnimation.setDuration(2000);// 设置动画持续时间
            /** 常用方法 */
            // animation.setRepeatCount(int repeatCount);//设置重复次数
            mAnimation.setFillAfter(true);// 动画执行完后是否停留在执行完的状�?
            // animation.setStartOffset(long startOffset);//执行前的等待时间
            mImagebackgruand.setAnimation(mAnimation);
        }

    }

    private final int MSG_UI_GOTO_MAIN_ACTIVITY = 1;

    private final int MSG_UI_GOTO_EXITAPP = 2;

    @Override
    protected void handleUiMessage(Message msg) {
        super.handleUiMessage(msg);
        switch (msg.what) {
            case MSG_UI_GOTO_MAIN_ACTIVITY:
                if (DefaultPreference.getInstance().IsFirstTimeOpenApp()) {

                    gotoBootActivity();
                    DefaultPreference.getInstance().setIsFirstTimeOpenApp(false);

                } else {
                    gotoMainActivity();
                    DefaultPreference.getInstance().setIsFirstTimeOpenApp(false);
                }

                break;

            case MSG_UI_GOTO_EXITAPP:
            {
                BaseApplication.getInstance().AppExit();
                break;
            }
        }
    }

    public abstract void gotoBootActivity();

    public abstract void gotoMainActivity();

    public abstract void setSplashImage();

    public abstract Boolean IsUseAnimation();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImagebackgruand != null)
            mImagebackgruand.clearAnimation();
        if (mAnimation != null)
            mAnimation.cancel();
    }

    @Override
    public void onSuccess(String content, Object object, int reqType)
    {
        if(reqType == HttpRequst.REQ_TYPE_PRODUCT_TYPE)
        {
            sendEmptyUiMessage(MSG_UI_GOTO_MAIN_ACTIVITY);
        }

        if(reqType == HttpRequst.REQ_TYPE_PRODUCT_BANNER)
        {
            try {
                JSONObject data = new JSONObject(content);
                JSONArray array = data.getJSONArray("imglist");

                int len = array.length();
                String[] imgs = new String[len];

                for (int i = 0; i < len; i++) {
                    JSONObject j = array.getJSONObject(i);
                    String id = j.getString("id");
                    String url = j.getString("url");
                    String title = j.getString("title");

                    Logger.d(title);

                    imgs[i] = j.getString("img");

                }
            }
            catch (Exception e)
            {

            }
        }
    }

    @Override
    public void onFailure(Throwable error, String content, int reqType)
    {
        Toast.makeText(BaseApplication.getInstance(),"网络错误", Toast.LENGTH_LONG).show();
        sendEmptyUiMessageDelayed(MSG_UI_GOTO_EXITAPP, 2000);
    }

    public void requestHomePage()
    {
        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_TYPE,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_TYPE),
                mParams,
                BaseApplication.getInstance().getAsyncClient(), false);

        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_HOME,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_HOME),
                mParams,
                BaseApplication.getInstance().getAsyncClient(), false);

        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_BANNER,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_BANNER),
                mParams,
                BaseApplication.getInstance().getAsyncClient(), false);
    }

    public void sendRequest(int reqType, String url, RequestParams params,
                            BaseHttpClient baseHttpClient, boolean showDialog) {
        if (baseHttpClient != null) {
            if (reqType > 1000) {
                baseHttpClient.post(reqType, this, url, params, this);
            } else {
                baseHttpClient.get(reqType, url, params, this);
            }
        }
    }
}
