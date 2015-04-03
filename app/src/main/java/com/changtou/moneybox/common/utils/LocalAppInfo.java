
package com.changtou.moneybox.common.utils;

/**
 * 描述:应用基本信息
 * 
 * @author chenys
 * @since 2013-9-28 上午11:13:51
 */
public class LocalAppInfo extends BaseAppInfo {

    private static final long serialVersionUID = -8211472050476177328L;

    /**
     * 应用
     */
    public static final int TYPE_APP = 0;

    /**
     * 游戏
     */
    public static final int TYPE_GAME = 1;

    private long appId;

    private int appType;

    private long playDate;

    private long playDuration;

    private long bytes_2G;

    private long bytes_3G;

    private long bytes_Wifi;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public long getPlayDate() {
        return playDate;
    }

    public void setPlayDate(long playDate) {
        this.playDate = playDate;
    }

    public long getPlayDuration() {
        return playDuration;
    }

    public void setPlayDuration(long playDuration) {
        this.playDuration = playDuration;
    }

    public long getBytes_2G() {
        return bytes_2G;
    }

    public void setBytes_2G(long bytes_2g) {
        bytes_2G = bytes_2g;
    }

    public long getBytes_3G() {
        return bytes_3G;
    }

    public void setBytes_3G(long bytes_3g) {
        bytes_3G = bytes_3g;
    }

    public long getBytes_Wifi() {
        return bytes_Wifi;
    }

    public void setBytes_Wifi(long bytes_Wifi) {
        this.bytes_Wifi = bytes_Wifi;
    }

}
