package com.changtou.moneybox.common.preference;

/**
 * 描述:普通的SharePreference工具
 * 
 * @author cjl
 * @since 2014-08-15 下午3:11:23
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	public static SharePreferenceUtil spu;

	private SharePreferenceUtil(Context context) {
		sp = context
				.getSharedPreferences("config_params", Context.MODE_PRIVATE);
	}

	// 单例
	public static SharePreferenceUtil getsp(Context context) {
		if (spu == null)
			spu = new SharePreferenceUtil(context);

		return spu;
	}

	/**
	 * 保存数据到sp
	 * 
	 * @param key
	 * @param value
	 */
	public void savePreference(String key, String value) {
		editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 从
	 * 
	 * @param mContext
	 * @param key
	 * @return
	 */
	public String getPreference(String key) {
		return sp.getString(key, "");
	}

	/**
	 * 保存数据到sp
	 * 
	 * @param key
	 * @param value
	 */
	public void savebooleanPreference(String key, boolean value) {
		editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 从
	 * 
	 * @param mContext
	 * @param key
	 * @return
	 */
	public boolean getbooleanPreference(String key) {
		return sp.getBoolean(key, false);
	}
}
