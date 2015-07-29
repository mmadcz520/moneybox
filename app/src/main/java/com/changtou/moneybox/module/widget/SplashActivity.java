package com.changtou.moneybox.module.widget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseSplashActivity;
import com.changtou.moneybox.module.page.BootActivity;
import com.changtou.moneybox.module.page.MainActivity;

/**
 * 1. app 启动页
 * 2. 闪屏效果
 * 3. 获取版本号
 * 4. 提醒是否更新
 *
 * @since 2015-3-16
 * @author zhoulongfei
 */
public class SplashActivity extends BaseSplashActivity {

    public static String PAGE_NAME = "splash_page";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @see BaseSplashActivity
     */
    protected void onResume() {
        super.onResume();
    }

    /**
     * @see BaseSplashActivity#getPageName()
     * @return
     */
    public String getPageName() {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseSplashActivity#gotoMainActivity()
     */
    public void gotoMainActivity() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, MainActivity.class);

        startActivity(intent);

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        finish();
    }

    /**
     * @see BaseSplashActivity#
     */
    public void gotoBootActivity() {
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, BootActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        finish();
    }

    /**
     * @see BaseSplashActivity#setSplashImage()
     */
    public void setSplashImage() {
        mImagebackgruand.setImageResource(R.mipmap.splash_page_new);
    }

    /**
     *
     * @return
     */
    public Boolean IsUseAnimation() {
        return false;
    }

}
