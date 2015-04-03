package com.changtou.moneybox.common.activity;

import java.lang.ref.WeakReference;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * 描述:抽象FragmentActivity，提供刷新UI的Handler
 *
 * @author zhoulongfei
 * @since 2015-3-12
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    protected Handler mUiHandler = new UiHandler(this);

    private static class UiHandler extends Handler {
        private final WeakReference<BaseFragmentActivity> mActivityReference;

        public UiHandler(BaseFragmentActivity activity) {
            mActivityReference = new WeakReference<>(activity);
        }

        public WeakReference<BaseFragmentActivity> getActivityReference() {
            return mActivityReference;
        }

        @Override
        public void handleMessage(Message msg) {
            if (getActivityReference() != null && getActivityReference().get() != null) {
                getActivityReference().get().handleUiMessage(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    /**
     * 处理更新UI任务
     * 
     * @param msg
     */
    protected void handleUiMessage(Message msg) {
    }

    /**
     * 发送UI更新操作
     *
     * @param msg
     */
    protected void sendUiMessage(Message msg) {
        mUiHandler.sendMessage(msg);
    }

    /**
     * 延迟发送UI更新操作
     *
     * @param msg
     * @param delayMillis
     */
    protected void sendUiMessageDelayed(Message msg, long delayMillis) {
        mUiHandler.sendMessageDelayed(msg, delayMillis);
    }

    /**
     * 发送UI更新操作
     * @param what
     */
    protected void sendEmptyUiMessage(int what) {
        mUiHandler.sendEmptyMessage(what);
    }

    protected void sendEmptyUiMessageDelayed(int what, long delayMillis) {
        mUiHandler.sendEmptyMessageDelayed(what, delayMillis);
    }

    /**
     * 显示一个Toast类型的消息
     * @param msg 显示的消息
     */
    public void showToast(final String msg) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseFragmentActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 显示{@link Toast}通知
     * @param strResId 字符串资源id
     */
    public void showToast(final int strResId) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseFragmentActivity.this, getResources().getString(strResId),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 隐藏软键盘
     */
    protected void hideSoftInput(Context context) {
        InputMethodManager manager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // manager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        if (getCurrentFocus() != null) {
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

//        FlurryAgent.onStartSession(this, AppUtil.getFlurryAppKey(this));

        // EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

//        TestinAgent.onResume(this);// 此行必须放在super.onResume后
//        // 友盟统计
//        MobclickAgent.openActivityDurationTrack(false);// 关闭默认的统计
//        String pagename = getPageName();
//        if (pagename != null && pagename.length() > 0) {
//            Log.d("pages", pagename);
//
//            MobclickAgent.onPageStart(pagename); // 统计页面
//
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("页面" + pagename, pagename);
//            FlurryAgent.onEvent(pagename + "(页面跟踪)", map);
//            FlurryAgent.onPageView();
//
//            // EasyTracker tracker =
//            // EasyTracker.getInstance(BaseApplication.getInstance());
//            Tracker tracker = GoogleAnalytics.getInstance(BaseApplication.getInstance())
//                    .getTracker(AppUtil.getMetaData(BaseApplication.getInstance(), "GA_APPKEY"));
//            tracker.set(Fields.customDimension(2),
//                    AppUtil.getChannelId(BaseApplication.getInstance()));
//            tracker.set(Fields.SCREEN_NAME, pagename);
//            tracker.send(MapBuilder.createAppView().build());
//        }
//
//        MobclickAgent.onResume(this); // 统计时长

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // 友盟统计
        String pagename = getPageName();
        if (pagename != null && pagename.length() > 0) {

//            MobclickAgent.onPageEnd(pagename); // 保证 onPageEnd 在onPause
            // 之前调用,因为 onPause 中会保存信息
        }

//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
//        TestinAgent.onStop(this);// 此行必须放在super.onStop后

//        FlurryAgent.onEndSession(this);

        // EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    public final static boolean isScreenLocked(Context c) {
        boolean ScreenLocked = false;
        KeyguardManager mKeyguardManager = (KeyguardManager) c
                .getSystemService(c.KEYGUARD_SERVICE);
        ScreenLocked = !mKeyguardManager.inKeyguardRestrictedInputMode();
        return ScreenLocked;

    }

    /**
     * 显示软键盘
     */
    protected void showSoftInput() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public abstract String getPageName();
}
