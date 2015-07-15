package com.changtou.moneybox.common.activity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.changtou.moneybox.common.http.base.BaseHttpRequest;
import com.changtou.moneybox.common.http.impl.AsyncHttpClientImpl;
import com.changtou.moneybox.common.utils.DeviceInfo;
import com.changtou.moneybox.common.utils.MySharedPreferencesMgr;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.page.GesturePWActivity;
import com.changtou.moneybox.module.service.BankParserHandler;
import com.changtou.moneybox.module.usermodule.UserManager;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 描述:全局Application
 * 
 * @author zhoulongfei
 * @since 2015-3-13
 */
public abstract class BaseApplication extends Application implements UncaughtExceptionHandler
{
	private static BaseApplication mApplication = null;

	// 应用全局变量存储在这里
	private static Hashtable<String, Object> mAppParamsHolder = null;

	private static final boolean IS_STATISTIC = true;

    //异步网络链接接口
    public AsyncHttpClientImpl mAsyncClient = null;

    //网络请求
    public static BaseHttpRequest mHttpRequest = null;

    //Activity列表, 全局退出App
    private static Stack<Activity> activityStack;

    //设备编号
    public static String mDeviceId = null;

    //程序意外终止异常
    private UncaughtExceptionHandler mDefaultHandler = null;

    private UserManager mUserManager = null;

    private boolean isBack = false;

    private SharedPreferencesHelper sph = null;

    /**
     * 银行列表
     */
    protected String[] mBankDatas;

    /**
     * 银行基本信列表
     */
    protected Map<String, String> mBankInfoList = null;

    public BaseApplication()
    {
        DeviceInfo.init(this);
        mHttpRequest = initBaseHttpRequest();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

        sph = SharedPreferencesHelper.getInstance(this);

        initBankDatas();
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

        Log.e("CT_MONEY", " ----------------- " + getDeviceInfo(mApplication));

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

    /**
     * 获取app 版本号
     *
     * @return
     */
    public String getVersionName()
    {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionName;
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

        isBack = false;
    }

    /**
     * App 回到前台
     * 1. 手势密码
     *
     */
    public void onForeground()
    {
        if(!isBack && (sph.getString(AppCfg.CFG_LOGIN, "").equals(AppCfg.LOGIN_STATE.LOGIN.toString())))
        {
            Intent intent = new Intent(this, GesturePWActivity.class);
            intent.putExtra("action", "login");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            isBack = true;
        }
    }


    /**
     * 获取用户模块
     * @return mUserManager
     */
    public UserManager getUserModule() {
        return mUserManager;
    }

    public void resetBackFlag()
    {
        isBack = true;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity){
        if(activityStack ==null){
            activityStack =new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }
    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
        System.exit(0);
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }


    /**
     * 解析银行卡信息
     */
    protected void initBankDatas()
    {
        List<String> bankList;

        try {
            InputStream input = getClass().getResourceAsStream("/assets/bank_data.xml");
//                    new FileInputStream("assets/bank_data.xml");

            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            BankParserHandler handler = new BankParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            bankList = handler.getDataList();
            mBankInfoList = handler.getBankInfoList();
            mBankDatas = new String[bankList.size()];

            for(int i = 0; i < bankList.size(); i++)
            {
                mBankDatas[i] = bankList.get(i);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Map<String, String> getBankInfoList()
    {
        return mBankInfoList;
    }

    public boolean isUserLogin()
    {
        return !((sph.getString(AppCfg.CFG_LOGIN, "").equals(AppCfg.LOGIN_STATE.EN_LOGIN.toString()))
                || (sph.getString(AppCfg.CFG_LOGIN, "").equals("")));
    }


    public static String getDeviceInfo(Context context) {
        try{
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if( TextUtils.isEmpty(device_id) ){
                device_id = mac;
            }

            if( TextUtils.isEmpty(device_id) ){
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
