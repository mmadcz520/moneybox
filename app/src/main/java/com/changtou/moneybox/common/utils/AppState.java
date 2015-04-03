
package com.changtou.moneybox.common.utils;

/**
 * 描述:应用当前状态
 * 
 * @author chenys
 * @since 2013-10-28 下午3:22:36
 */
public interface AppState {

    /**
     * 未知状态
     */
    public static final int NONE = -1;

    /**
     * 未安装
     */
    public static final int NORMAL = 0;

    /**
     * 正在下载
     */
    public static final int DOWNLOADING = 1;

    /**
     * 下载完成
     */
    public static final int DOWNLOADED = 2;

    /**
     * 正在安装
     */
    public static final int INSTALLING = 3;

    /**
     * 已安装
     */
    public static final int INSTALLED = 4;
}
