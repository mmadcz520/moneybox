
package com.changtou.moneybox.common.manager;

import android.os.Environment;

import com.changtou.moneybox.common.utils.FileUtil;
import com.changtou.moneybox.common.utils.SDCardUtil;

/**
 * 系统文件夹管理
 * 
 * @author chenys
 */
public class FolderManager {

    /**
     * 初始化文件系统
     */
    public static void initSystemFolder() {
        boolean isSDCardAvailable = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!isSDCardAvailable) {
            // 存储卡不可用，返回
            return;
        }

        checkFolder(SDCardUtil.ROOT_FOLDER);
        checkFolder(SDCardUtil.CACHE_FOLDER);
        checkFolder(SDCardUtil.IMAGE_CACHE_FOLDER);
        checkFolder(SDCardUtil.OTHER_CACHE_FOLDER);
        checkFolder(SDCardUtil.APP_FOLDER);
        checkFolder(SDCardUtil.LOG_FOLDER);
        checkFolder(SDCardUtil.CONFIG_FOLDER);
        checkFolder(SDCardUtil.SPLASH_FOLDER);
        checkFolder(SDCardUtil.CROP_IMAGE_CACHE_FOLDER);

    }

    // 检查文件夹，不存在或文件夹版本号不同时，会重新创建
    private static void checkFolder(String folder) {
        if (!FileUtil.isFileExist(folder)) {
            FileUtil.createFolder(folder, false);
        }
    }

}
