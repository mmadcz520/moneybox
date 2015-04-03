
package com.changtou.moneybox.common.preference;

import android.content.Context;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.framework.common.preference.PreferenceOpenHelper;

public class SettingPreference extends PreferenceOpenHelper {

    private static SettingPreference mSettingPref;

    public SettingPreference(Context context, String prefname) {
        super(context, prefname);
    }

    public synchronized static SettingPreference getInstance() {
        if (mSettingPref == null) {
            Context context = BaseApplication.getInstance();
            String name = "setting";
            mSettingPref = new SettingPreference(context, name);
        }
        return mSettingPref;
    }

    public boolean getShowImage() {
        return getBoolean(Keys.SHOW_IMAGE, true);
    }

    /**
     * 获取用户是否收藏过
     * 
     * @return
     */
    public boolean getHaveCollected() {
        return getBoolean(Keys.HAVE_COLLECTED, false);
    }

    public boolean getAutoInstall() {
        return getBoolean(Keys.AUTO_INSTALL, true);
    }

    public boolean getSilentInstall() {
        return getBoolean(Keys.SILENT_INSTALL, true);
    }

    public boolean getAutoDeleteInstallFile() {
        return getBoolean(Keys.AUTO_DELETE_INSTALL_FILE, true);
    }

    public boolean getNotifyBarDownloadTask() {
        return getBoolean(Keys.NOTIFYBAR_DOWNLOAD_TASK, true);
    }

    // public boolean getShowIconAndShot() {
    // return getBoolean(Keys.SHOW_ICON_AND_SHOT_ONLY_WIFI, true);
    // }

    public boolean getDownloadGame() {
        return getBoolean(Keys.DOWNLOAD_GAME_ONLY_WIFI, true);
    }

    public boolean getDownloadUpdataPackage() {
        return getBoolean(Keys.AUTO_DOWNLOAD_UPDATA_PACKAGE_ONLY_WIFI, false);
    }

    public String getSplashBgImageUrl() {
        return getString(Keys.SPLASHBGIMAGEURL, "");
    }

    public String getSplashBgImageSavePath() {
        return getString(Keys.SPLASHBGIMAGESAVEPATH, "");
    }

    /**
     * 设置用户是否收藏过
     */
    public void putHaveCollected(boolean b) {
        putBoolean(Keys.HAVE_COLLECTED, b);
    }

    public void putShowImage(boolean b) {
        putBoolean(Keys.SHOW_IMAGE, b);
    }

    public void putAutoInstall(boolean b) {
        putBoolean(Keys.AUTO_INSTALL, b);
    }

    public void putSilentInstall(boolean b) {
        putBoolean(Keys.SILENT_INSTALL, b);
    }

    public void putAutoDeleteInstallFile(boolean b) {
        putBoolean(Keys.AUTO_DELETE_INSTALL_FILE, b);
    }

    public void putNotifyBarDownloadTask(boolean b) {
        putBoolean(Keys.NOTIFYBAR_DOWNLOAD_TASK, b);
    }

    // public void putShowIconAndShot(boolean b) {
    // putBoolean(Keys.SHOW_ICON_AND_SHOT_ONLY_WIFI, b);
    // }

    public void putDownloadGame(boolean b) {
        putBoolean(Keys.DOWNLOAD_GAME_ONLY_WIFI, b);
    }

    public void putDownloadUpdataPackage(boolean b) {
        putBoolean(Keys.AUTO_DOWNLOAD_UPDATA_PACKAGE_ONLY_WIFI, b);
    }

    public void putSplashBgImageUrl(String url) {
        putString(Keys.SPLASHBGIMAGEURL, url);
    }

    public void putSplashBgImageSavePath(String savepath) {
        putString(Keys.SPLASHBGIMAGESAVEPATH, savepath);
    }

    public static interface Keys {

        public static final String HAVE_COLLECTED = "have_collected";

        public static final String SHOW_IMAGE = "show_image";

        public static final String AUTO_INSTALL = "auto_install";

        public static final String SILENT_INSTALL = "silent_install";

        public static final String AUTO_DELETE_INSTALL_FILE = "auto_delete_install_file";

        public static final String NOTIFYBAR_DOWNLOAD_TASK = "notifybar_download_task";

        // public static final String SHOW_ICON_AND_SHOT_ONLY_WIFI =
        // "show_icon_and_shot_only_wifi";

        public static final String DOWNLOAD_GAME_ONLY_WIFI = "download_game_only_wifi";

        public static final String AUTO_DOWNLOAD_UPDATA_PACKAGE_ONLY_WIFI = "auto_download_updata_package_only_wifi";

        public static final String SPLASHBGIMAGEURL = "splash_bg_image_url";

        public static final String SPLASHBGIMAGESAVEPATH = "splash_bg_image_savepath";
    }

}
