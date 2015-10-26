
package com.changtou.moneybox.common.utils;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

public class FileUtil
{
    private static final String TAG = "FileUtil";

    public static File getCacheFile(String imageUri){
        File cacheFile = null;
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                String fileName = getFileName(imageUri);
                File dir = new File(sdCardDir.getCanonicalPath()
                        + AsyncImageLoader.CACHE_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                cacheFile = new File(dir, fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cacheFile;
    }

    public static String getFileName(String path) {
        int index = path.lastIndexOf("/");
        return path.substring(index + 1);
    }
}
