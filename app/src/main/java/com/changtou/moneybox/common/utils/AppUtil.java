package com.changtou.moneybox.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.preference.DefaultPreference;
import com.framework.http.HttpResponse;
import com.framework.utils.LogUtil;

/**
 * 描述:APP工具类
 * 
 * @author chenys
 * @since 2013-9-26 下午3:22:57
 */
public class AppUtil {

	static BroadcastReceiver mBatteryInfoReceiver;

	/**
	 * 获取屏幕分辨率
	 * 
	 * @return
	 */
	public static Point getDisplayScreenResolution(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		android.view.Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		display.getMetrics(dm);

		int screen_h = 0, screen_w = 0;
		screen_w = dm.widthPixels;
		screen_h = dm.heightPixels;
		return new Point(screen_w, screen_h);
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 *（DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 *（DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 *（DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 *（DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 单位换算
	 * 
	 * @param fileSize
	 * @return
	 */
	public static String getSizeStr(long fileSize) {
		if (fileSize <= 0) {
			return "0M";
		}
		float result = fileSize;
		String suffix = "M";
		result = result / 1024 / 1024;
		return String.format("%.1f", result) + suffix;
	}

	/**
	 * 从下载url中截取文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileNameFromUrl(String url) {
		if (TextUtils.isEmpty(url) || url.lastIndexOf("/") == -1) {
			return "";
		}
		String name = MD5Util.getMd5(url) + ".png";
		return name;
	}

	/**
	 * 初始化一个空{@link Menu}
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Menu newInstanceMenu(Context context) {
		try {
			Class menuBuilder = Class
					.forName("com.android.internal.view.menu.MenuBuilder");
			Constructor constructor = menuBuilder.getConstructor(Context.class);
			return (Menu) constructor.newInstance(context);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 把Bitmap转化成byte的数据流； 还是通过Bitmap.compress()函数转化
	 * 
	 * @param photo
	 * @return
	 */
	public static byte[] getBitmap2Bytes(Bitmap photo) {
		if (photo == null)
			return null;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 卸载Android应用程序
	 * 
	 * @param packageName
	 */
	public static boolean uninstallApk(String packageName, Context context) {
		if (packageName == null)
			return false;

		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(uninstallIntent);

		return true;
	}

	public static String getDeviceID(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	/**
	 * 将数字大小转换成“MB"、“KB”、"GB"格式
	 * 
	 * @param size
	 * @return
	 */
	public static String getSize(long size) {
		if (size < 0)
			return null;

		String result = null;
		if (size > 1024 * 1024 * 1024) {
			float f = (float) size / (1024 * 1024 * 1024);
			String s = String.valueOf(f);
			if (s.length() - s.indexOf(".") > 2)
				result = s.substring(0, s.indexOf(".") + 3);
			else
				result = s;
			return result + "GB";
		} else if (size > 1024 * 1024) {
			float f = (float) size / (1024 * 1024);
			String s = String.valueOf(f);
			if (s.length() - s.indexOf(".") > 2)
				result = s.substring(0, s.indexOf(".") + 3);
			else
				result = s;
			return result + "MB";
		} else if (size > 1024) {
			float f = (float) size / 1024;
			String s = String.valueOf(f);
			if (s.length() - s.indexOf(".") > 2)
				result = s.substring(0, s.indexOf(".") + 3);
			else
				result = s;
			return result + "KB";
		} else if (size < 1024) {
			return String.valueOf(size) + "B";
		} else
			return null;
	}

	/**
	 * judge whether <code>str</code> is equal one of <code>strArray</code>.
	 * 
	 * @param str
	 * @param strArray
	 * @return if include, return index of strArray. Else return -1
	 */
	public static int AincludeB(String str, String[] strArray) {
		if (str == null || strArray == null)
			return -1;
		for (int i = 0; i < strArray.length; i++) {
			if (str.equals(strArray[i]))
				return i;
		}
		return -1;
	}

	/**
	 * 将数字大小转换成“MB"、“KB”、"GB"格式
	 * 
	 * @param size
	 * @return
	 */
	public static String getSize(int size) {
		if (size < 0)
			return null;

		String result = null;
		if (size > 1024 * 1024 * 1024) {
			float f = (float) size / (1024 * 1024 * 1024);
			String s = String.valueOf(f);
			if (s.length() - s.indexOf(".") > 2)
				result = s.substring(0, s.indexOf(".") + 3);
			return result + "GB";
		} else if (size > 1024 * 1024) {
			float f = (float) size / (1024 * 1024);
			String s = String.valueOf(f);
			if (s.length() - s.indexOf(".") > 2)
				result = s.substring(0, s.indexOf(".") + 3);
			return result + "MB";
		} else if (size > 1024) {
			float f = (float) size / 1024;
			String s = String.valueOf(f);
			if (s.length() - s.indexOf(".") > 2)
				result = s.substring(0, s.indexOf(".") + 3);
			return result + "KB";
		} else if (size < 1024) {
			return String.valueOf(size) + "B";
		} else
			return null;
	}

	/**
	 * 如果是文件夹就删除文件下所有文件，然后删除文件夹，如果是文件就直接删除文件
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public static long del(String filepath) {

		if (filepath == null)
			return -1;

		long total = 0;
		try {

			File f = new File(filepath);// 定义文件路径
			if (!f.exists())
				return -1;
			if (f.isDirectory()) {// 目录
				int i = f.listFiles().length;
				if (i > 0) {
					File delFile[] = f.listFiles();
					for (int j = 0; j < i; j++) {
						if (delFile[j].isDirectory()) {
							// 递归调用del方法并取得子目录路径
							total = total + del(delFile[j].getAbsolutePath());
						}
						total += delFile[j].length();
						delFile[j].delete();// 删除文件
					}
				}
				f.delete();
			} else
				total += f.length();
			if (f.exists())
				f.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 删除文件夹下临时.tmp文件
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public static long delTmpFile(String filepath) {

		if (filepath == null)
			return -1;

		long total = 0;
		try {

			File f = new File(filepath);// 定义文件路径
			if (!f.exists())
				return -1;
			if (f.isDirectory()) {// 目录
				int i = f.listFiles().length;
				if (i > 0) {
					File delFile[] = f.listFiles();

					for (int j = 0; j < i; j++) {
						String filename = delFile[j].getAbsolutePath();
						if (delFile[j].isDirectory()) {
							// 递归调用del方法并取得子目录路径
							total = total + del(delFile[j].getAbsolutePath());
						}
						if (delFile[j].isFile()
								&& delFile[j].getAbsolutePath()
										.endsWith(".tmp")) {
							total += delFile[j].length();
							delFile[j].delete();// 删除文件
						}

					}
				}
				// f.delete();
			} else
				total += f.length();
			if (f.exists() && f.isFile()
					&& f.getAbsolutePath().endsWith(".tmp"))
				f.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 通过网址打开一个链接
	 * 
	 * @param url
	 */
	public static void openNetUrl(String url, Context context) {
		Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(it);
	}

	/**
	 * 打开应用
	 * 
	 * @param context
	 * @param packageName
	 */
	public static void openApp(Context context, String packageName) {
		try {
			if (!TextUtils.isEmpty(packageName)) {
				if (checkAppExistSimple(context, packageName)) {
					PackageManager pm = context.getPackageManager();
					Intent intent = pm.getLaunchIntentForPackage(packageName);
					if (intent != null) {
						List<ResolveInfo> list = context.getPackageManager()
								.queryIntentActivities(intent, 0);
						if (list != null) {
							// 如果这个Intent有1个及以上应用可以匹配处理，则选择第一个匹配的处理，防止选择处理类ResolverActivity缺失导致异常崩溃
							if (list.size() > 0) {
								ResolveInfo ri = list.iterator().next();
								if (ri != null) {
									ComponentName cn = new ComponentName(
											ri.activityInfo.packageName,
											ri.activityInfo.name);
									Intent launchIntent = new Intent();
									launchIntent.setComponent(cn);
									launchIntent
											.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									if (BaseApplication.getInstance().isStat()) {
										TrackUtil.trackEvent(context,
												"Play_AppGame", 38,
												ri.activityInfo.packageName, 0,
												"Manager");
									}
									context.startActivity(launchIntent);
								}
							}
						}
						// 记录应用启动时间
						// AppOpenTimeManager.excute(context, packageName);
						// // 启动应用统计
						// StatisticsManager.sendStatistics(new
						// StartGameTask(context, packageName));
						return;
					}
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 检查应用是否已安装（包名一致即可）
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean checkAppExistSimple(Context context,
			String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			if (pi != null) {
				return packageName.equals(pi.packageName);
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检查应用是否已安装（包名与版本号要一致）
	 * 
	 * @param context
	 * @param packageName
	 * @param versionCode
	 * @return
	 */
	public static boolean checkAppExist(Context context, String packageName,
			int versionCode) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			if (pi != null) {
				return packageName.equals(pi.packageName)
						&& versionCode == pi.versionCode;
			} else {
				return false;
			}
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 设置淡入动画
	 * 
	 * @param view
	 *            控件
	 * @param startOffset
	 *            　廷迟毫秒数
     *
	 */
	public static void setFadeIn(Context context, View view, long startOffset) {
        //todo
//		Animation fade_in = AnimationUtils.loadAnimation(context,R.anim.fade_in);
//		fade_in.setDuration(600);
//		fade_in.setStartOffset(startOffset);
//		fade_in.setInterpolator(new DecelerateInterpolator());
//		view.startAnimation(fade_in);
	}

	/**
	 * 是否新安装
	 * 
	 * @return
	 */
	public static boolean isNewInstall() {
		int oldVersionCode = DefaultPreference.getInstance().getOldVersionCode();
		return oldVersionCode == 0;
	}

	/**
	 * 是否覆盖安装
	 * 
	 * @return
	 */
	public static boolean isCoverInstall() {
		int oldVersionCode = DefaultPreference.getInstance().getOldVersionCode();
		if (oldVersionCode != 0) {
			// 覆盖安装
			PackageInfo pi = BaseApplication.getInstance()
					.getLocalPackageInfo();
			if (pi != null) {
				return oldVersionCode != pi.versionCode;
			} else {
				return false;
			}
		} else {
			// 属于真正新安装
			return false;
		}
	}

	/**
	 * 判断手机号码是否格式正确
	 * 
	 * @return boolean
	 */
	public static boolean matcherPhoneNum(String telNum) {
		// 匹配11数字，并且13-19开头
		String regex = "^1[3-9]\\d{9}$";
		Pattern pt = Pattern.compile(regex);
		Matcher mc = pt.matcher(telNum);
		return mc.matches();
	}

	/**
	 * 判断密码格式正确
	 * 
	 * @return boolean
	 */
	public static boolean matcherPassword(String psd) {
		// (6-16位字母或数字)
		String regex = "^[a-zA-Z0-9]{6,16}$";
		Pattern pt = Pattern.compile(regex);
		Matcher mc = pt.matcher(psd);
		return mc.matches();
	}

	/**
	 * 判断注册账号格式正确
	 * 
	 * @return boolean
	 */
	public static boolean matcherAccount(String account) {
		// （4-20位字符）
		String regex = "[\\u4e00-\\u9fa5a-zA-Z0-9\\-]{4,20}";
		Pattern pt = Pattern.compile(regex);
		Matcher mc = pt.matcher(account);
		return mc.matches();
	}

	/**
	 * 判断邮箱格式正确
	 * 
	 * @return boolean
	 */
	public static boolean matcherEmail(String email) {
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern pt = Pattern.compile(regex);
		Matcher mc = pt.matcher(email);
		return mc.matches();
	}

	/**
	 * 对外分享内容
	 * 
	 * @param context
	 * @param title
	 * @param text
	 * @param appId
	 */
	public static void shareText(Context context, String title, String text,
			long appId) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.putExtra(Intent.EXTRA_TEXT, text);

	}

	/**
	 * 手机里具有"分享"功能的所有应用
	 * 
	 * @param context
	 * @return
	 */
	public static List<ResolveInfo> getShareTargets(Context context) {
		List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
		Intent intent = new Intent(Intent.ACTION_SEND, null);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("text/plain");
		PackageManager pm = context.getPackageManager();
		mApps = pm.queryIntentActivities(intent,
				PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
		return mApps;
	}

	/**
	 * 获取当前日期字符串（格式yyyy-MM-dd）
	 */
	public static String getTodayDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date());
		return date;
	}

	public enum NetworkConnectedState {
		wifi, mobile, disconnect, other
	}

	/**
	 * 更新网络连接状态，是3g、wifi还是未连接
	 */
	public static NetworkConnectedState getNetworkState(Context context) {

		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();

		// ------------------------- 未连接
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return NetworkConnectedState.disconnect;
		}

		// ------------------------- wifi连接
		State state = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (state == State.CONNECTED) {
			return NetworkConnectedState.wifi;
		}

		// ------------------------- mobile连接
		state = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		if (state == State.CONNECTED) {
			return NetworkConnectedState.mobile;
		}

		return NetworkConnectedState.other;

	};

	/**
	 * 获取手机串号(IMEI)
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		if (imei == null) {
			return "";
		} else {
			return imei;
		}
	}

	/**
	 * 获取用户识别码（IMSI）
	 * 
	 * @param context
	 * @return
	 */
	public static String getSubscriberId(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String tel = tm.getSubscriberId();
		return TextUtils.isEmpty(tel) ? "" : tel;
	}

	/**
	 * 获取手机号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getPhoneModel() {
		return Build.MODEL;
	}

	/**
	 * 获取渠道号
	 * 
	 * @param context
	 * @return
	 */
	public static String getChannelId(Context context) {
		// return getRawFileContent(context, R.raw.channel, "utf-8");
		// return "gp";
		return getStringMetaData(context, "UMENG_CHANNEL");
	}

	/**
	 * 获取平台
	 * 
	 * @param context
	 * @return
	 */
	public static String getPlatform(Context context) {
        //todo
//		return getRawFileContent(context, R.raw.platform, "utf-8");
        return null;
	}

	/**
	 * 获取运营商<br>
	 * 其中46000、46002和46007标识中国移动，46001标识中国联通，46003标识中国电信
	 * 
	 * @param context
	 * @return
	 */
	public static String getMNC(Context context) {
		String providersName = "";
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
			providersName = telephonyManager.getSimOperator();
			providersName = providersName == null ? "" : providersName;
		}
		return providersName;
	}

	/**
	 * 读取RAW文件内容
	 * 
	 * @param context
	 * @param resid
	 * @param encoding
	 * @return
	 */
	public static String getRawFileContent(Context context, int resid,
			String encoding) {
		InputStream is = context.getResources().openRawResource(resid);
		if (is != null) {
			ByteArrayBuffer bab = new ByteArrayBuffer(1024);
			int read;
			try {
				while ((read = is.read()) != -1) {
					bab.append(read);
				}
				return EncodingUtils.getString(bab.toByteArray(), encoding);
			} catch (UnsupportedEncodingException e) {
			} catch (IOException e) {
			} finally {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return "";
	}

	/**
	 * 获取系统版本，如1.5,2.1
	 * 
	 * @return　SDK版本号
	 */
	public static String getSysVersionName() {
		return Build.VERSION.RELEASE;
	}

	/**
	 * 获取SDK版本号
	 * 
	 * @return
	 */
	public static int getSdkInt() {
		return Build.VERSION.SDK_INT;
	}

	/**
	 * 获取包名
	 * 
	 * @return
	 */
	public static String getPackageName(Context context) {
		final String packageName = context.getPackageName();
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, 0);
			return info.packageName;
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	/**
	 * 获取版本名称
	 * 
	 * @return
	 */
	public static String getVersionName(Context context) {
		final String packageName = context.getPackageName();
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return
	 */
	public static int getVersionCode(Context context) {
		final String packageName = context.getPackageName();
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			return 0;
		}
	}

	/**
	 * MAC地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getWifiMacAddr(Context context) {
		String macAddr = "";
		try {
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			macAddr = wifi.getConnectionInfo().getMacAddress();
			if (macAddr == null) {
				macAddr = "";
			}
		} catch (Exception e) {
		}
		return macAddr;
	}

	/**
	 * 抽样（PC使用的抽样算法，精确，但会使固定用户永久抽中）
	 * 
	 * @param imei
	 *            唯一标识符
	 * @param percent
	 *            百分比
	 * @return
	 */
	public static boolean isPicked(String imei, float percent) {
		if (percent <= 0) {
			return false;
		} else if (percent >= 100) {
			return true;
		}
		String md5 = MD5Util.getMd5(imei);
		if (md5.length() == 32) {
			long sum = 0;
			for (int i = 0; i < 4; i++) {
				sum += Long.parseLong(md5.substring(i * 8, (i + 1) * 8), 16);
			}
			sum = sum % 1000;
			if (sum % (Math.round(100.0f / percent)) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 抽样（约等于抽样比例，有误差）
	 * 
	 * @param percent
	 *            百分比
	 * @return
	 */
	public static boolean isPicked(float percent) {
		if (percent <= 0) {
			return false;
		} else if (percent >= 100) {
			return true;
		}
		float n = new Random().nextFloat();
		return n < percent / 100.0f;
	}

	/**
	 * 屏幕宽高
	 * 
	 * @param context
	 * @return
	 */
	public static int[] getScreenSize(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getApplicationContext().getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;

		return new int[] { screenWidth, screenHeight };
	}

	/**
	 * 屏幕宽高，字符串形式
	 * 
	 * @param context
	 * @return
	 */
	public static String getScreenSizeStr(Context context) {
		int[] screenSize = getScreenSize(context);
		return screenSize[0] + "*" + screenSize[1];
	}

	/**
	 * 创建快捷方式
	 * 
	 * @param context
	 * @param shortCutName
	 * @param iconId
	 * @param presentIntent
	 */
	public static void createShortcut(Context context, String shortCutName,
			int iconId, Intent presentIntent) {
		Intent shortcutIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		shortcutIntent.putExtra("duplicate", false);
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortCutName);
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(context, iconId));
		shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, presentIntent);

		context.sendBroadcast(shortcutIntent);
	}

	/**
	 * 创建我的游戏快捷方式
	 * 
	 * @param context
	 */
	public static void createGameShortCut(Context context) {

        //todo

//		String shortCutName = context.getResources().getString(
//				R.string.appcenter_mygame);
//		int iconId = R.drawable.icon_mygames_shortcut;
//		Intent presentIntent = new Intent();
//		presentIntent.setAction(MarketIntents.ACTION_MYGAMES_SHORT);
//		presentIntent.addCategory(".MyGamesActivity");
//		presentIntent.addCategory("android.intent.category.DEFAULT");
//		presentIntent.addFlags(0x10000000);
//
//		createShortcut(context, shortCutName, iconId, presentIntent);
	}

	/**
	 * 创建酷游游戏快捷方式
	 * 
	 * @param context
	 */
	// public static void createKYGameShortCut(Context context) {
	// String shortCutName = null;
	// String packageName = AppUtil.getPackageName(context);
	// String packageNamelowcase = packageName.toLowerCase();
	// if (packageNamelowcase.contains("lite")) {
	// shortCutName = context.getString(R.string.app_name_gp);
	// } else {
	// if (packageName.equals("com.fjoys.gamecenter")) {
	// // shortCutName = context.getString(R.string.app_name_other);
	// } else {
	//
	// shortCutName = context.getString(R.string.app_name);
	// }
	//
	// }
	// //
	// (getChannelId(context).equals(BaseApplication.GOOGLE_PLAY_CHANNEL))
	// // shortCutName = context.getString(R.string.app_name_gp);
	// // else
	// // shortCutName = context.getString(R.string.app_name);
	// int iconId = R.drawable.icon_app;
	// Intent presentIntent = new Intent();
	// ComponentName com = new ComponentName(context.getPackageName(),
	// SplashActivity.class.getName());
	// presentIntent.setComponent(com);
	// presentIntent.setAction("android.intent.action.MAIN");
	// presentIntent.addCategory("android.intent.category.LAUNCHER");
	// presentIntent.addFlags(0x10200000);
	//
	// createShortcut(context, shortCutName, iconId, presentIntent);
	// }

	/**
	 * 根据上次玩的日期返回提示
	 * 
	 * @param playDate
	 * @return
	 */
	public static String getPlayDateHint(Long playDate) {
		if (playDate == null || playDate == 0) {
			return "还没有";
		}
		// 距离此刻多久
		long margin = (System.currentTimeMillis() - playDate) / 1000;
		if (0 <= margin && margin < 1 * 60 * 60) {// 1小时之内
			return "刚刚";
		} else if (1 * 60 * 60 <= margin && margin < 24 * 60 * 60) {// 1天之内
			return margin / 3600 + "小时前";
		} else if (24 * 60 * 60 <= margin && margin < 30 * 24 * 60 * 60) {// 1个月之内
			return margin / (24 * 3600) + "天前";
		} else if (30 * 24 * 60 * 60 <= margin) {// 1个月之后
			return "很久没";
		}
		return "";
	}

	public static ArrayList<AppInfo> getAppInfoList(byte[] data,
			HttpResponse appsHttpResponse) throws JSONException {
		String respStr = new String(data);
		LogUtil.d("test", "respStr=" + respStr);
		// 解析json
		JSONObject jsonObj = new JSONObject(respStr);
		if (!"1".equalsIgnoreCase(jsonObj.getString("status"))) {
			return null;
		}
		JSONObject dataObj = jsonObj.getJSONObject("data");
		appsHttpResponse.setTotal(dataObj.optString("total"));
		appsHttpResponse.setPages(dataObj.optString("pages"));
		JSONArray appsArray = dataObj.getJSONArray("rows");
		int size = appsArray.length();
		ArrayList<AppInfo> apps = new ArrayList<AppInfo>(size);
		for (int i = 0; i < size; i++) {
			JSONObject appObj = appsArray.getJSONObject(i);
			AppInfo appInfo = new AppInfo();
			AppUtil.getAppInfo(appObj, appInfo);
			apps.add(appInfo);
		}

		return apps;
	}

	/**
	 * 从appObj中解析出AppInfo对象
	 * {"detail":"","appId":"180","packageName":"com.kiloo.subwaysurf"
	 * ,"iconUrl": <<<<<<< .mine
	 * "http:\/\/imge.kugou.com\/pgame\/20130830\/20130830164618261678.png","rel
	 * i a b i l i t y " : " 腾 讯 安 全 管 家 认 证 " , " v e r s i o n C o d e " : " 3
	 * 2 " , " s o u r c e U r l " : " h t t p : \ / \ / s y d a t a . k u g o u
	 * . c o m \ / p g a m e \ / a p k \ / 5 6 \ / 5 6 e a f 6 7 2 d d 5 2 c c 6
	 * 8 b 4 e 4 0 b c 9 7 e 9 8 9 e 1 d . a p k " , " g o n g L u e N u m " : "
	 * 0 " , " b o n u s N u m " : " 0 " , " f i l e S i z e " : " 2 7 5 3 5 0 1
	 * 1 " , " s o u r c e H a s h " : " 5 6 e a f 6 7 2 d d 5 2 c c 6 8 b 4 e 4
	 * 0 b c 9 7 e 9 8 9 e 1 d " , " d e s c r i p t i o n " : " 《 地 铁 跑 酷
	 * Subway Surfers》 =======
	 * "http:\/\/imge.kugou.com\/pgame\/20130830\/20130830164618261678.png","rel
	 * i a b i l i t y " : " 腾 讯 安 全 管 家 认 证 " , " v e r s i o n C o d e " : " 3
	 * 2 " , " s o u r c e U r l " : " h t t p : \ / \ / s y d a t a . k u g o u
	 * . c o m \ / p g a m e \ / a p k \ / 5 6 \ / 5 6 e a f 6 7 2 d d 5 2 c c 6
	 * 8 b 4 e 4 0 b c 9 7 e 9 8 9 e 1 d . a p k " , " g o n g L u e N u m " : "
	 * 0 " , " b o n u s N u m " : " 0 " , " f i l e S i z e " : " 2 7 5 3 5 0 1
	 * 1 " , " s o u r c e H a s h " : " 5 6 e a f 6 7 2 d d 5 2 c c 6 8 b 4 e 4
	 * 0 b c 9 7 e 9 8 9 e 1 d " , " d e s c r i p t i o n " : " 《 地 铁 跑 酷
	 * Subway Surfers》 >>>>>>> .r807
	 * 是一款带有美式街头风格的跑酷游戏，游戏采用3D立体视角，在充满动感和色彩艳丽的画面中奔跑、跳跃、翻滚，每天都会即时刷新三项任务供玩家去挑战
	 * ，同时还有丰富多彩的道具使用与升级
	 * ，众多隐藏角色等待你来开启！","permissions":"访问网络\r\n写入外部存储\r\n获取网络状态\r
	 * \n获取WiFi状态\r\n读取电话状态
	 * \r\n访问账户Gmail列表\r\n唤醒锁定\r\n获取任务信息\r\n使用振动","grade":"8","
	 * adType":0,"appType":"","downloadNum":"211242","updateTime":"2013-04-13
	 * 19:
	 * 10:29","versionName":"1.13.0","canUse":"0","commentNum":"0","appName":"
	 * 地铁跑酷
	 * 无限金币版","imageList":[{"type":"1","imageUrl":"http:\/\/imge.kugou.com\/
	 * pgame
	 * \/20130703\/20130703103451857238.jpg"},{"type":"1","imageUrl":"http:\
	 * /\/imge
	 * .kugou.com\/pgame\/20130703\/20130703103449165908.jpg"},{"type":"1","
	 * imageUrl
	 * ":"http:\/\/imge.kugou.com\/pgame\/20130703\/20130703103448354515.
	 * jpg"},{"type":"1","imageUrl":"http:\/\/imge.kugou.com\/pgame\/20130703\/
	 * 20130703103446534993.
	 * jpg"},{"type":"1","imageUrl":"http:\/\/imge.kugou.com
	 * \/pgame\/20130703\/20130703103445645837.
	 * jpg"},{"type":"1","imageUrl":"http
	 * :\/\/imge.kugou.com\/pgame\/20130830\/20130830161223146990.
	 * jpg"}],"source":"
	 * 0","favorited":0,"company":"","language":"2","patchs":[]}
	 * 
	 * @param appObj
	 * @return
	 * @throws JSONException
	 * @throws NumberFormatException
	 */
	public static void getAppInfo(JSONObject appObj, AppInfo appInfo)
			throws NumberFormatException, JSONException {
		appInfo.setAppId(Long.parseLong(appObj.getString("appId")));
		appInfo.setAppName(appObj.getString("appName"));
		appInfo.setGrade(Integer.parseInt(appObj.getString("grade")));
		appInfo.setDownloadNum(Integer.parseInt(appObj.getString("downloadNum")));
		appInfo.setFileSize(Long.parseLong(appObj.getString("fileSize")));
		appInfo.setPackageName(appObj.getString("packageName"));
		appInfo.setDownType(appObj.optInt("downType"));
		if (!appObj.isNull("description"))
			appInfo.setDescription(appObj.getString("description"));
		if (!appObj.isNull("canUse"))
			appInfo.setCanUse(Integer.parseInt(appObj.getString("canUse")));
		if (!appObj.isNull("adType"))
			appInfo.setAdType(Integer.parseInt(appObj.getString("adType")));
		// 解释增量包集合
		if (!appObj.isNull("patchs")) {
			JSONArray patchArray = appObj.getJSONArray("patchs");
			if (patchArray != null && patchArray.length() > 0) {
				int patchSize = patchArray.length();
				ArrayList<PatchInfo> patchs = new ArrayList<PatchInfo>();
				for (int j = 0; j < patchSize; j++) {
					PatchInfo patch = new PatchInfo();
					patch.setFromVersion(Integer.parseInt(appObj
							.getString("fromVersion")));
					patch.setPatchDownloadUrl(appObj.getString("patchUrl"));
					patch.setPatchHash(appObj.getString("patchHash"));
					patchs.add(patch);
				}
				appInfo.setPatchs(patchs);
			}
		}
		appInfo.setSourceUrl(appObj.getString("sourceUrl"));
		appInfo.setSourceHash(appObj.getString("sourceHash"));
		appInfo.setIconUrl(appObj.getString("iconUrl"));
		appInfo.setIconUrl1(appObj.optString("iconUrl1"));
		if (!appObj.isNull("img")) {
			appInfo.setImgUrl(appObj.getString("img"));
		}
		appInfo.setSource(Integer.parseInt(appObj.getString("source")));
		appInfo.setVersionCode(appObj.getInt("versionCode"));
		appInfo.setVersionName(appObj.getString("versionName"));
		if (!appObj.isNull("isGame")) {
			appInfo.setAppType("1".equals(appObj.getString("isGame")) ? LocalAppInfo.TYPE_GAME
					: LocalAppInfo.TYPE_APP);
		}
		if (!appObj.isNull("gpkPath")) {
			appInfo.setGpkPath(appObj.getString("gpkPath"));
		}
	}

	/**
	 * 从appObj中解析出AppInfo对象
	 * {"detail":"","appId":"180","packageName":"com.kiloo.subwaysurf"
	 * ,"iconUrl": <<<<<<< .mine
	 * "http:\/\/imge.kugou.com\/pgame\/20130830\/20130830164618261678.png","rel
	 * i a b i l i t y " : " 腾 讯 安 全 管 家 认 证 " , " v e r s i o n C o d e " : " 3
	 * 2 " , " s o u r c e U r l " : " h t t p : \ / \ / s y d a t a . k u g o u
	 * . c o m \ / p g a m e \ / a p k \ / 5 6 \ / 5 6 e a f 6 7 2 d d 5 2 c c 6
	 * 8 b 4 e 4 0 b c 9 7 e 9 8 9 e 1 d . a p k " , " g o n g L u e N u m " : "
	 * 0 " , " b o n u s N u m " : " 0 " , " f i l e S i z e " : " 2 7 5 3 5 0 1
	 * 1 " , " s o u r c e H a s h " : " 5 6 e a f 6 7 2 d d 5 2 c c 6 8 b 4 e 4
	 * 0 b c 9 7 e 9 8 9 e 1 d " , " d e s c r i p t i o n " : " 《 地 铁 跑 酷
	 * Subway Surfers》 =======
	 * "http:\/\/imge.kugou.com\/pgame\/20130830\/20130830164618261678.png","rel
	 * i a b i l i t y " : " 腾 讯 安 全 管 家 认 证 " , " v e r s i o n C o d e " : " 3
	 * 2 " , " s o u r c e U r l " : " h t t p : \ / \ / s y d a t a . k u g o u
	 * . c o m \ / p g a m e \ / a p k \ / 5 6 \ / 5 6 e a f 6 7 2 d d 5 2 c c 6
	 * 8 b 4 e 4 0 b c 9 7 e 9 8 9 e 1 d . a p k " , " g o n g L u e N u m " : "
	 * 0 " , " b o n u s N u m " : " 0 " , " f i l e S i z e " : " 2 7 5 3 5 0 1
	 * 1 " , " s o u r c e H a s h " : " 5 6 e a f 6 7 2 d d 5 2 c c 6 8 b 4 e 4
	 * 0 b c 9 7 e 9 8 9 e 1 d " , " d e s c r i p t i o n " : " 《 地 铁 跑 酷
	 * Subway Surfers》 >>>>>>> .r807
	 * 是一款带有美式街头风格的跑酷游戏，游戏采用3D立体视角，在充满动感和色彩艳丽的画面中奔跑、跳跃、翻滚，每天都会即时刷新三项任务供玩家去挑战
	 * ，同时还有丰富多彩的道具使用与升级
	 * ，众多隐藏角色等待你来开启！","permissions":"访问网络\r\n写入外部存储\r\n获取网络状态\r
	 * \n获取WiFi状态\r\n读取电话状态
	 * \r\n访问账户Gmail列表\r\n唤醒锁定\r\n获取任务信息\r\n使用振动","grade":"8","
	 * adType":0,"appType":"","downloadNum":"211242","updateTime":"2013-04-13
	 * 19:
	 * 10:29","versionName":"1.13.0","canUse":"0","commentNum":"0","appName":"
	 * 地铁跑酷
	 * 无限金币版","imageList":[{"type":"1","imageUrl":"http:\/\/imge.kugou.com\/
	 * pgame
	 * \/20130703\/20130703103451857238.jpg"},{"type":"1","imageUrl":"http:\
	 * /\/imge
	 * .kugou.com\/pgame\/20130703\/20130703103449165908.jpg"},{"type":"1","
	 * imageUrl
	 * ":"http:\/\/imge.kugou.com\/pgame\/20130703\/20130703103448354515.
	 * jpg"},{"type":"1","imageUrl":"http:\/\/imge.kugou.com\/pgame\/20130703\/
	 * 20130703103446534993.
	 * jpg"},{"type":"1","imageUrl":"http:\/\/imge.kugou.com
	 * \/pgame\/20130703\/20130703103445645837.
	 * jpg"},{"type":"1","imageUrl":"http
	 * :\/\/imge.kugou.com\/pgame\/20130830\/20130830161223146990.
	 * jpg"}],"source":"
	 * 0","favorited":0,"company":"","language":"2","patchs":[]}
	 * 
	 * @param appObj
	 * @return
	 * @throws JSONException
	 * @throws NumberFormatException
	 */
	public static void getAppDownloadInfo(JSONObject appObj, AppInfo appInfo)
			throws NumberFormatException, JSONException {
		appInfo.setAppId(Long.parseLong(appObj.getString("appId")));
		appInfo.setAppName(appObj.getString("appName"));
		appInfo.setGrade(Integer.parseInt(appObj.getString("grade")));
		appInfo.setFileSize(Long.parseLong(appObj.getString("fileSize")));
		appInfo.setPackageName(appObj.getString("packageName"));
		appInfo.setSourceUrl(appObj.getString("sourceUrl"));
		appInfo.setSourceHash(appObj.getString("sourceHash"));
		appInfo.setIconUrl(appObj.getString("iconUrl"));
		appInfo.setIconUrl(appObj.optString("iconUrl1"));
		if (!appObj.isNull("img")) {
			appInfo.setImgUrl(appObj.getString("img"));
		}
		appInfo.setSource(Integer.parseInt(appObj.getString("source")));
		appInfo.setVersionCode(Integer.parseInt(appObj.getString("versionCode")));
		appInfo.setVersionName(appObj.getString("versionName"));
		appInfo.setCanUse(Integer.parseInt(appObj.getString("canUse")));
		appInfo.setAdType(Integer.parseInt(appObj.getString("adType")));
		// 解释增量包集合
		JSONArray patchArray = appObj.getJSONArray("patchs");
		if (patchArray != null && patchArray.length() > 0) {
			int patchSize = patchArray.length();
			ArrayList<PatchInfo> patchs = new ArrayList<PatchInfo>();
			for (int j = 0; j < patchSize; j++) {
				PatchInfo patch = new PatchInfo();
				patch.setFromVersion(Integer.parseInt(appObj
						.getString("fromVersion")));
				patch.setPatchDownloadUrl(appObj.getString("patchUrl"));
				patch.setPatchHash(appObj.getString("patchHash"));
				patchs.add(patch);
			}
			appInfo.setPatchs(patchs);
		}
		if (!appObj.isNull("isGame")) {
			appInfo.setAppType("1".equals(appObj.getString("isGame")) ? LocalAppInfo.TYPE_GAME
					: LocalAppInfo.TYPE_APP);
		}
		if (!appObj.isNull("gpkPath")) {
			appInfo.setGpkPath(appObj.getString("gpkPath"));
		}
	}

	/**
	 * 获取安装包信息
	 * 
	 * @param context
	 * @param filePath
	 * @return
	 */
	public static PackageInfo getPackageInfo(Context context, String filePath) {
		if (!TextUtils.isEmpty(filePath)) {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageArchiveInfo(filePath, 0);
			return pi;
		} else {
			return null;
		}
	}

	/**
	 * 获取应用名称
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getAppName(Context context, String packageName) {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}

	/**
	 * 类似在游戏详情里面下载app，有些地方不能使用，例如MagicButton
	 * 
	 * @param appInfo
	 * @param c
	 */
	public static void startDownloadApp(AppInfo appInfo, Context c) {
		// 打开google play等平台进行下载
		String subUrl = appInfo.getSourceUrl().substring(0, 7);
		if (subUrl.equals("market:")) {
			Uri uri = null;
			if (subUrl.equals("market:"))
				uri = Uri.parse(appInfo.getSourceUrl());
			else {
				uri = Uri.parse("market://details?id="
						+ appInfo.getPackageName() + "");
			}
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			try {
				c.startActivity(it);
			} catch (Exception e) {
			}

			// 统计
			if (BaseApplication.getInstance().isStat()) {
				TrackUtil
						.trackEvent(BaseApplication.getInstance(),
								"Start_Download_GooglePlay", 30,
								appInfo.getPackageName(), appInfo.getAppId(),
								"Manager");
			}
		} else {
//			ParamsWrapper pw = DownloadHelper
//					.getParamsWrapperFromAppinfo(appInfo);
//			String key = pw.getKey();
//			boolean isDownloadFinish = false;
//			DownloadFile file = DownloadOperator.getInstance().query(key);
//			if (file != null) {
//				int state = file.getState();
//				if (state == FileDownloader.FINISH) {
//					isDownloadFinish = FileUtil.isFileExist(file.getFilePath());
//				}
//			}
//			if (isDownloadFinish) {
//				// 已下载
//				String filePath = file.getFilePath();
//				// InstallHelper.getInstance().install(filePath);
//			} else {
//				// 未下载
//				DownloadHelper.download(pw, null);
//			}
		}
	}

	public static String getLanguage(Context c) {
		if (c == null)
			c = BaseApplication.getInstance();
		return c.getResources().getConfiguration().locale.getLanguage();
	}

	/**
	 * 获取Mac 地址
	 * 
	 * @return
	 */
	public static String getMacAddress(Context c) {
		WifiManager wifi = (WifiManager) c
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		String mac = info.getMacAddress();
		if (mac == null) {
			return "";
		}
		mac = mac.replaceAll(":", "");

		return mac.toLowerCase();
	}

	public static String getStringMetaData(Context context, String key) {
		Bundle metaData = getMetaData(context);
		String strVal = metaData != null ? metaData.getString(key) : null;
		if (strVal == null || "".equals(strVal)) {
			throw new IllegalArgumentException("please define " + key
					+ " in your AndroidManifest.xml");
		}
		return strVal != null ? strVal : "";
	}

	private static Bundle getMetaData(Context context) {
		if (context == null) {
			return null;
		}

		PackageManager pm = context.getPackageManager();
		try {
			ApplicationInfo appInfo = pm.getApplicationInfo(
					context.getPackageName(), 128);

			if (appInfo != null)
				return appInfo.metaData;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressLint("DefaultLocale")
	public static boolean isLiteVersion(Context context) {
		try {
			boolean bversion = false;
			final String packageName = context.getPackageName();
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, 0);
			String version = info.packageName.toLowerCase();
			if (version.contains("lite")) {
				bversion = true;
			}
			return bversion;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isProVersion(Context context) {
		boolean bversion = false;
		final String packageName = context.getPackageName();
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, 0);
			String version = info.packageName.toLowerCase();
			if (version.contains("pro")) {
				bversion = true;
			}
			return bversion;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isLcVersion(Context context) {
		boolean bversion = false;
		final String packageName = context.getPackageName();
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, 0);
			String version = info.packageName.toLowerCase();
			if (version.contains("lc")) {
				bversion = true;
			}
			return bversion;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean checkApkInstall(Context context, String packageName) {
		boolean bInstall = false;
		if (packageName == null || "".equals(packageName))
			return bInstall;
		try {
			ApplicationInfo info = null;
			info = context.getPackageManager().getApplicationInfo(packageName,
					0);
			if (info != null) {
				bInstall = true;
			}
			return bInstall;

		} catch (NameNotFoundException e) {
			return bInstall;
		}
	}

	public static String getGooglePlayPackageName() {
		String GooglePlayPackageName = "com.android.vending";
		return GooglePlayPackageName;
	}

	public static String getAndroidId(Context context) {
		String androidId = android.provider.Settings.Secure.getString(
				context.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		return androidId;
	}

	public static boolean isChannelTracemobi() {
		// ApplicationInfo info;
		// try {
		// info = BaseApplication
		// .getInstance()
		// .getPackageManager()
		// .getApplicationInfo(BaseApplication.getInstance().getPackageName(),
		// PackageManager.GET_META_DATA);
		// String msg = info.metaData.getString("UMENG_CHANNEL");
		// if (msg.equalsIgnoreCase("tracemobi"))
		// return true;
		//
		// } catch (NameNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return false;

	}

	public final static boolean isScreenLocked(Context c) {
		boolean ScreenLocked = false;
		KeyguardManager mKeyguardManager = (KeyguardManager) c
				.getSystemService(c.KEYGUARD_SERVICE);
		ScreenLocked = !mKeyguardManager.inKeyguardRestrictedInputMode();
		return ScreenLocked;

	}

	/*
	 * 判断是否是小米系统
	 */
	public static boolean isMIUI(Context context) {
		boolean result = false;
		Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
		i.setClassName("com.android.settings",
				"com.miui.securitycenter.permission.AppPermissionsEditor");
		if (isIntentAvailable(context, i)) {
			result = true;
		}
		return result;
	}

	public static boolean isIntentAvailable(Context context, Intent intent) {
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.GET_ACTIVITIES);
		return list.size() > 0;
	}

	/*
	 * 小米系统跳转到本应用权限设置界面
	 */
	public static void setMIUIPermissionWhiteList(Context context) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = pm.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
		i.setClassName("com.android.settings",
				"com.miui.securitycenter.permission.AppPermissionsEditor");
		i.putExtra("extra_package_uid", info.applicationInfo.uid);
		try {
			context.startActivity(i);
		} catch (Exception e) {
			Toast.makeText(context, "只有MIUI才可以设置哦", Toast.LENGTH_SHORT).show();
		}
	}

	public static String getMetaData(Context context, String metadatename) {
		ApplicationInfo applicationInfo;
		String metadata = null;
		try {
			applicationInfo = context.getPackageManager().getApplicationInfo(
					getPackageName(context), PackageManager.GET_META_DATA);
			metadata = applicationInfo.metaData.getString(metadatename);
			// if (fluryApiKey == null)

			// else

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metadata;

	}

	/*
	 * 统计部分
	 */

	public static String getFlurryAppKey(Context context) {
		ApplicationInfo applicationInfo;
		String fluryAppKey = null;
		try {
			applicationInfo = context.getPackageManager().getApplicationInfo(
					getPackageName(context), PackageManager.GET_META_DATA);
			fluryAppKey = applicationInfo.metaData.getString("FLURRY_APPY");
			// if (fluryApiKey == null)

			// else

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fluryAppKey;

	}

	public static String getBaiduPushAppKey(Context context) {
		ApplicationInfo applicationInfo;
		String baiduPushAppKey = null;
		try {
			applicationInfo = context.getPackageManager().getApplicationInfo(
					getPackageName(context), PackageManager.GET_META_DATA);
			baiduPushAppKey = applicationInfo.metaData
					.getString("BAIDU_PUSH_APPKEY");
			// if (fluryApiKey == null)

			// else

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baiduPushAppKey;

	}

	/*
	 * 配置应用的主文件夹
	 */
	public static String getAppMainFolder() {
		final String packageName = BaseApplication.getInstance()
				.getPackageName();
		int index = packageName.lastIndexOf(".");
		String lastName = packageName
				.substring(index + 1, packageName.length());
		String first = lastName.substring(0, 1).toUpperCase();
		lastName = lastName.substring(1);
		String mainFolder = first + lastName;
		return mainFolder;

	}

	/*
	 * 获取在前台显示的Activity的SimpleName
	 */
	public static String getOnForegroundActivitySimpleName() {
		ActivityManager activityManager = (ActivityManager) BaseApplication
				.getInstance().getSystemService(
						BaseApplication.getInstance().ACTIVITY_SERVICE);
		String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity
				.getClassName();
		String runningActivity1 = activityManager.getRunningTasks(1).get(0).topActivity
				.getClass().getSimpleName().toString();
		int index = runningActivity.lastIndexOf(".");
		String runningActivitySimpleName = runningActivity.substring(index + 1,
				runningActivity.length());
		LogUtil.d("Front Display Activity Name Is: "
				+ runningActivitySimpleName);
		return runningActivitySimpleName;

	}

	/*
	 * 判断Activity是否在前台显示
	 */
	public static boolean IsActivityOnForeground(String activitySimpleName) {
		String name = getOnForegroundActivitySimpleName();
		if (name.equals(activitySimpleName)) {
			LogUtil.d(activitySimpleName + "is display front");
			return true;
		} else {
			LogUtil.d(activitySimpleName + "is not display front");
			return false;
		}

	}

	/**
	 * 程序是否在前台运行
	 * 
	 * @return
	 */
	public boolean isAppOnForeground() {
		// Returns a list of application processes that are running on the
		// device
		final String packageName = BaseApplication.getInstance()
				.getPackageName();
		ActivityManager activityManager = (ActivityManager) BaseApplication
				.getInstance().getSystemService(
						BaseApplication.getInstance().ACTIVITY_SERVICE);
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

	public static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日HH时mm分ss秒");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

//	public static void registerDoubleClickListener(View view,
//			final OnDoubleClickListener listener) {
//		if (listener == null)
//			return;
//		view.setOnClickListener(new View.OnClickListener() {
//			private static final int DOUBLE_CLICK_TIME = 350; // 双击间隔时间350毫秒
//
//			private boolean waitDouble = true;
//
//			private Handler handlerClick = new Handler() {
//				@Override
//				public void handleMessage(Message msg) {
//					listener.OnSingleClick((View) msg.obj);
//				}
//
//			};
//
//			// 等待双击
//			public void onClick(final View v) {
//				if (waitDouble) {
//					waitDouble = false; // 与执行双击事件
//					new Thread() {
//
//						public void run() {
//							try {
//								Thread.sleep(DOUBLE_CLICK_TIME);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} // 等待双击时间，否则执行单击事件
//							if (!waitDouble) {
//								// 如果过了等待事件还是预执行双击状态，则视为单击
//								waitDouble = true;
//								Message msg = handlerClick.obtainMessage();
//								msg.obj = v;
//								handlerClick.sendMessage(msg);
//							}
//						}
//
//					}.start();
//				} else {
//					waitDouble = true;
//					listener.OnDoubleClick(v); // 执行双击
//				}
//			}
//		});
//	}
}
