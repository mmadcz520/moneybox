package com.changtou.moneybox.common.activity;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.changtou.moneybox.common.http.base.BaseHttpRequest;
import com.changtou.moneybox.common.http.impl.AsyncHttpClientImpl;
import com.changtou.moneybox.common.utils.DeviceInfo;
import com.changtou.moneybox.common.utils.MySharedPreferencesMgr;
import com.changtou.moneybox.module.usermodule.UserManager;

/**
 * 描述:全局Application
 * 
 * @author zhoulongfei
 * @since 2015-3-13
 */
public abstract class BaseApplication extends Application implements UncaughtExceptionHandler {

	private static BaseApplication mApplication = null;

	// 应用全局变量存储在这里
	private static Hashtable<String, Object> mAppParamsHolder = null;

	private static final boolean IS_STATISTIC = true;

    //异步网络链接接口
    public AsyncHttpClientImpl mAsyncClient = null;

    //网络请求
    public static BaseHttpRequest mHttpRequest = null;

    //Activity列表, 全局退出App
    private ArrayList<Activity> mActivityList = null;

    //设备编号
    public static String mDeviceId = null;

    //程序意外终止异常
    private UncaughtExceptionHandler mDefaultHandler = null;

    private UserManager mUserManager = null;

    public BaseApplication()
    {
        DeviceInfo.init(this);
        mHttpRequest = initBaseHttpRequest();
        mActivityList = new ArrayList<Activity>();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 推送， track代码的初始化
     * (友盟))
     */
	public void onCreate() {
        //0315
        mDeviceId=getDeviceID(this.getApplicationContext());
        mAsyncClient = getAsyncClient();

		super.onCreate();
		mApplication = this;
//        FolderManager.initSystemFolder();
		mAppParamsHolder = new Hashtable<String, Object>();

        try {
            mUserManager = new UserManager();
            mUserManager.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static BaseApplication getInstance() {
		if (mApplication == null) {
			throw new IllegalStateException("Application is not created.");
		}
		return mApplication;
	}

    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();

        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

	/**
	 * 存储全局数据
	 * 
	 * @param key
	 * @param value
	 */
	public static void putValue(String key, Object value) {
		mAppParamsHolder.put(key, value);
	}

	/**
	 * 获取全局数据
	 * 
	 * @param key
	 * @return
	 */
	public static Object getValue(String key) {
		return mAppParamsHolder.get(key);
	}

	/**
	 * 是否已存放
	 * 
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String key) {
		return mAppParamsHolder.containsKey(key);
	}

	/**
	 * 获取自身App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getLocalPackageInfo() {
		return getPackageInfo(getPackageName());
	}

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo(String packageName) {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info;
	}

	public boolean isStat() {
		return IS_STATISTIC;
	}

    protected abstract BaseHttpRequest initBaseHttpRequest();

    /**
     * 获取设备id
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        if (mDeviceId == null) {
            mDeviceId = DeviceInfo.getDeviceInfo(context);
            if (mDeviceId == null || mDeviceId.length() < 2) {
                mDeviceId = MySharedPreferencesMgr.getString("clientid", null);
                if (mDeviceId == null) {
                    mDeviceId = DeviceInfo.getUUID();
                    MySharedPreferencesMgr.setString("clientid", mDeviceId);
                }
            }
        }
        return mDeviceId;
    }

    /**
     * 得到 http aynsc客户端 实例对象
     * @return
     */
    public AsyncHttpClientImpl getAsyncClient(){
        return AsyncHttpClientImpl.getHupuHttpClient();
    }

    /**
     * App 异常退出处理
     *
     * @see UncaughtExceptionHandler#uncaughtException(Thread, Throwable)
     * @param thread
     * @param ex
     */
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }else{
            quitApp();
        }
    }

    /**
     * 控制应用退出操作
     */
    @SuppressLint("NewApi")
    public void quitApp() {
        int version = android.os.Build.VERSION.SDK_INT;
//		for (Activity act : actList) {
//			if (!act.isFinishing())
//				act.finish();
//		}
//		actList.clear();
        if (version <= 7) {
            System.out.println("   version  < 7");
            ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            manager.restartPackage(getPackageName());
        } else {
            ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(getPackageName());
        }
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
    }

    /**
     * App 切换到后台
     */
    public void onBackground(){
        //todo
    }

    /**
     * App 回到前台
     */
    public void onForeground(){
        //todo
    }


    /**
     * 获取用户模块
     * @return mUserManager
     */
    public UserManager getUserModule() {
        return mUserManager;
    }
}
