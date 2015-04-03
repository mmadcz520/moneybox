package com.changtou.moneybox.common.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.changtou.R;
import com.changtou.moneybox.common.preference.DefaultPreference;

/**
 * 描述:闪屏界面
 *
 * @author zhoulongfei
 * @since 2015-3-12
 */
public abstract class BaseSplashActivity extends BaseFragmentActivity {

    //渐变动画效果
    public ScaleAnimation mAnimation = null;

    //图片背景
    public ImageView mImagebackgruand = null;

    @Override
    protected void onResume() {
        super.onResume();

        sendEmptyUiMessageDelayed(MSG_UI_GOTO_MAIN_ACTIVITY, 2000);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

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
}
