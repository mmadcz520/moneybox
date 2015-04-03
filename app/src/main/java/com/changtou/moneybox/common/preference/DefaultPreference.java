
package com.changtou.moneybox.common.preference;

import android.content.Context;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.framework.common.preference.PreferenceOpenHelper;

/**
 * 描述:默认预设文件，对应com.kuyou.market_preferences.xml
 * 
 * @author zhoulongfei
 * @since 2015-3-13
 */
public class DefaultPreference extends PreferenceOpenHelper {
    private static DefaultPreference mDefaultPref = null;

    public DefaultPreference(Context context, String prefname) {
        super(context, prefname);
    }

    public static synchronized DefaultPreference getInstance() {
        if (mDefaultPref == null) {
            Context context = BaseApplication.getInstance();
            String name = context.getPackageName() + "_preferences";
            mDefaultPref = new DefaultPreference(context, name);
        }
        return mDefaultPref;
    }

    /**
     * 获取旧版本号。注意：在版本覆盖安装后第一次启动时，拿到的versionCode才是真正的旧版本号，以后的拿到的都是当前版本号
     * 
     * @return
     */
    public int getOldVersionCode() {
        return getInt(Keys.OLD_VERSION_CODE, 0);
    }

    /**
     * 设置旧版本号
     * 
     * @param oldVersionCode
     */
    public void setOldVersionCode(int oldVersionCode) {
        putInt(Keys.OLD_VERSION_CODE, oldVersionCode);
    }

    /**
     * 获取上次检查更新时间
     *
     * @return
     */
    public long getCheckUpdateTime() {
        return getLong(Keys.CHECK_UPDATE_TIME, 0);
    }

    /**
     * 设置检查更新时间
     * 
     * @param time
     */
    public void setCheckUpdateTime(long time) {
        putLong(Keys.CHECK_UPDATE_TIME, time);
    }

    public String getPushSetting() {
        return getString(Keys.PUSH_SETTING_DATA, null);
    }

    public DefaultPreference setPushSetting(String setting) {
        putString(Keys.PUSH_SETTING_DATA, setting);
        return this;
    }

    public long getLastReplyTime() {
        return getLong(Keys.LAST_REPLY_TIME, 0);
    }

    public DefaultPreference setLastReplyTime(long time) {
        putLong(Keys.LAST_REPLY_TIME, time);
        return this;
    }

    public long getLastPushTime() {
        return getLong(Keys.LAST_PUSH_TIME, 0);
    }

    public DefaultPreference setLastPushTime(long time) {
        putLong(Keys.LAST_PUSH_TIME, time);
        return this;
    }

    public long getLastCheckUpdateTime() {
        return getLong(Keys.LAST_CHECKUPDATE_TIME, 0);
    }

    public DefaultPreference setLastCheckUpdateTime(long time) {
        putLong(Keys.LAST_CHECKUPDATE_TIME, time);
        return this;
    }

    public long getLastPushId() {
        return getLong(Keys.LAST_PUSH_ID, 0);
    }

    public DefaultPreference setLastPushId(long id) {
        putLong(Keys.LAST_PUSH_ID, id);
        return this;
    }

    public long getLastDownloadTime(String fileKey) {
        return getLong("download_time_" + fileKey, 0);
    }

    public int getLastDownloadProgress(String fileKey) {
        return getInt("download_progress_" + fileKey, 0);
    }

    public DefaultPreference setLastDownload(String fileKey, long time, int progress) {
        if (getLastDownloadTime(fileKey) != 0) {
            return this;
        }
        putLong("download_time_" + fileKey, time);
        putInt("download_progress_" + fileKey, progress);
        return this;
    }

    public void removeDownload(String fileKey) {
        removeKey("download_time_" + fileKey);
        removeKey("download_progress_" + fileKey);
    }

    public void removeCacheIP() {
        removeKey("api_cache_ip");
    }

    public void setCacheIP(String ip) {
        putString("api_cache_ip", ip);
    }

    public String getCacheIP() {
        return getString("api_cache_ip", null);
    }

    public void setDownloadGame(String packageName) {
        putBoolean("is_download_" + packageName.toLowerCase().trim(), true);
    }

    public boolean isDownloadGame(String packageName) {
        return getBoolean("is_download_" + packageName.toLowerCase().trim(), false);
    }

    public void setIsFirstTimeOpenApp(boolean isFirstTime) {
        putBoolean(Keys.IS_FIRST_OPEN_APP, isFirstTime);
    }

    public boolean IsFirstTimeOpenApp() {
        return getBoolean(Keys.IS_FIRST_OPEN_APP, true);
    }

    public static interface Keys {

        /**
         * 旧版本版本号
         */
        public static final String OLD_VERSION_CODE = "old_version_code";

        /**
         * 检查版本更新的时间
         */
        public static final String CHECK_UPDATE_TIME = "check_update_time";

        /**
         * 推送設置
         */
        public static final String PUSH_SETTING_DATA = "push_setting_data";

        public static final String LAST_PUSH_TIME = "last_push_time";

        public static final String LAST_REPLY_TIME = "last_reply_time";

        public static final String LAST_CHECKUPDATE_TIME = "last_checkupdate_time";

        public static final String LAST_PUSH_ID = "last_push_id";

        /*
         * 是不是第一次打开应用
         */

        public static final String IS_FIRST_OPEN_APP = "is_first_time_open_app";
    }

}
