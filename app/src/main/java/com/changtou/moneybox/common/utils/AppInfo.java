
package com.changtou.moneybox.common.utils;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;


/**
 * 描述:应用实体类
 * 
 * @author chenys
 * @since 2013-9-24 下午7:34:15
 */
public class AppInfo extends LocalAppInfo implements Cloneable {

    private static final long serialVersionUID = -759603268018355368L;

    /**
     * 来源：默认0
     */
    public static final int SOURCE_NONE = 0;

    /**
     * 来源：官方
     */
    public static final int SOURCE_OFFICIAL = 1;

    /**
     * AD类型：默认0
     */
    public static final int AD_NONE = 0;

    /**
     * AD类型：首发
     */
    public static final int AD_FIRST_RELEASE = 1;

    /**
     * AD类型：推广
     */
    public static final int AD_RECOMMEND = 2;

    /**
     * AD类型：礼包
     */
    public static final int AD_GIFT = 3;

    /**
     * 已开放，默认0
     */
    public static final int USE_YES = 0;

    /**
     * 还没开放
     */
    public static final int USE_NO = 1;

    public static AppInfo cloneAppInfo(AppInfo ai) {
        AppInfo info = new AppInfo();
        info.adType = ai.adType;
        info.appClass = ai.appClass;
        info.appId = ai.appId;
        info.appName = ai.appName;
        info.canUse = ai.canUse;
        info.description = ai.description;
        info.detail = ai.detail;
        info.downloadNum = ai.downloadNum;
        info.drawable = ai.drawable;
        info.filePath = ai.filePath;
        info.fileSize = ai.fileSize;
        info.gpkPath = ai.gpkPath;
        info.grade = ai.grade;
        info.iconUrl = ai.iconUrl;
        info.imgUrl = ai.imgUrl;
        info.packageName = ai.packageName;
        info.patchs = ai.patchs;
        info.realVersionCode = ai.realVersionCode;
        info.source = ai.source;
        info.sourceHash = ai.sourceHash;
        info.sourceUrl = ai.sourceUrl;
        info.versionCode = ai.versionCode;
        info.versionName = ai.versionName;
        return info;
    }

    private long appId;

    private int appType;

    private long playDate;

    private long playDuration;

    private long bytes_2G;

    private long bytes_3G;

    private long bytes_Wifi;

    private String appName;

    private String versionName;

    // 服务端版本号
    private int versionCode;

    // 真实版本号
    private int realVersionCode;

    // 72x72
    private String iconUrl;

    // 144x144
    private String iconUrl1;

    /** 大图的url */
    private String imgUrl;

    private String filePath;

    private float grade;

    private int downloadNum;

    private long fileSize;

    private String description;

    private String appClass;// 应用分类（休闲；益智）

    // 来源，0默认，1官方
    private int source;

    // 推广类型，0默认，1首发，2推广，3礼包
    private int adType;

    // 是否开放，0开放，1即将开放
    private int canUse;

    // 完整安装包下载地址
    private String sourceUrl;

    // 完整安装包文件hash
    private String sourceHash;

    // 增量包集合
    private ArrayList<PatchInfo> patchs;

    private Drawable drawable;

    private String gpkPath;

    private String detail;

    private String packageName;

    private int downType;

    public int getDownType() {
        return downType;
    }

    public void setDownType(int downType) {
        this.downType = downType;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getRealVersionCode() {
        return realVersionCode;
    }

    public void setRealVersionCode(int realVersionCode) {
        this.realVersionCode = realVersionCode;
    }

    public String getAppClass() {
        return appClass;
    }

    public void setAppClass(String appClass) {
        this.appClass = appClass;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getIconUrl1() {
        return iconUrl1;
    }

    public void setIconUrl1(String iconUrl1) {
        this.iconUrl1 = iconUrl1;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        // 划分为５个等级
        if (grade <= 0) {
            this.grade = 0;
        } else if (grade <= 1) {
            this.grade = 0.5f;
        } else if (grade <= 2) {
            this.grade = 1;
        } else if (grade <= 3) {
            this.grade = 1.5f;
        } else if (grade <= 4) {
            this.grade = 2;
        } else if (grade <= 5) {
            this.grade = 2.5f;
        } else if (grade <= 6) {
            this.grade = 3;
        } else if (grade <= 7) {
            this.grade = 3.5f;
        } else if (grade <= 8) {
            this.grade = 4;
        } else if (grade <= 9) {
            this.grade = 4.5f;
        } else {
            this.grade = 5;
        }
    }

    public int getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(int downloadNum) {
        this.downloadNum = downloadNum;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public int getCanUse() {
        return canUse;
    }

    public void setCanUse(int canUse) {
        this.canUse = canUse;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceHash() {
        return sourceHash;
    }

    public void setSourceHash(String sourceHash) {
        this.sourceHash = sourceHash;
    }

    public ArrayList<PatchInfo> getPatchs() {
        return patchs;
    }

    public void setPatchs(ArrayList<PatchInfo> patchs) {
        this.patchs = patchs;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    /**
     * 获取最新的增量包信息
     * 
     * @return
     */
    public PatchInfo getLastestPatch() {
        // if (patchs != null && patchs.size() > 0) {
        // PatchInfo pi = patchs.get(patchs.size() - 1);
        // return pi;
        // } else {
        // return null;
        // }
        return null;
    }

    /**
     * 下载文件hash
     * 
     * @return
     */
    public String getDownloadFileKey() {
        PatchInfo pi = getLastestPatch();
        if (pi != null) {
            return pi.getPatchHash();
        } else {
            return sourceHash;
        }
    }

    /**
     * 下载地址
     * 
     * @return
     */
    public String getDownloadUrl() {
        PatchInfo pi = getLastestPatch();
        if (pi != null) {
            return pi.getPatchDownloadUrl();
        } else {
            return sourceUrl;
        }
    }

    /**
     * 是否增量包
     * 
     * @return
     */
    public boolean isPatchFile() {
        PatchInfo pi = getLastestPatch();
        if (pi != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getGpkPath() {
        return gpkPath;
    }

    public void setGpkPath(String gpkPath) {
        this.gpkPath = gpkPath;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public int hashCode() {
        if (packageName != null) {
            return packageName.hashCode() + versionCode;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != getClass()) {
            return false;
        }
        if (o instanceof AppInfo) {
            String inPackageName = ((AppInfo) o).getPackageName();
            int inVersionCode = ((AppInfo) o).getVersionCode();
            return inPackageName != null && inPackageName.equals(packageName)
                    && inVersionCode == versionCode;
        }
        return super.equals(o);
    }

    // public static final Parcelable.Creator<AppInfo> CREATOR = new
    // Creator<AppInfo>() {
    //
    // @SuppressWarnings("unchecked")
    // @Override
    // public AppInfo createFromParcel(Parcel source) {
    // AppInfo info = new AppInfo();
    // info.detail = source.readString();
    //
    // info.appName = source.readString();
    //
    // info.versionName = source.readString();
    //
    // info.versionCode = source.readInt();
    //
    // info.appId = source.readLong();
    //
    // info.iconUrl = source.readString();
    //
    // info.imgUrl = source.readString();
    //
    // info.filePath = source.readString();
    //
    // info.grade = source.readFloat();
    //
    // info.downloadNum = source.readInt();
    //
    // info.fileSize = source.readLong();
    //
    // info.description = source.readString();
    //
    // info.appClass = source.readString();
    //
    // info.source = source.readInt();
    //
    // info.adType = source.readInt();
    //
    // info.canUse = source.readInt();
    //
    // info.isDownloaded = (Boolean)
    // source.readValue(ClassLoader.getSystemClassLoader());
    //
    // info.isInstalled = (Boolean)
    // source.readValue(ClassLoader.getSystemClassLoader());
    //
    // info.sourceUrl = source.readString();
    //
    // info.sourceHash = source.readString();
    //
    // info.patchs = source.readArrayList(ClassLoader.getSystemClassLoader());
    //
    // info.drawable = (Bitmap)
    // source.readValue(ClassLoader.getSystemClassLoader());
    //
    // info.isGame = source.readInt();
    //
    // info.gpkPath = source.readString();
    //
    // return info;
    // }
    //
    // @Override
    // public AppInfo[] newArray(int size) {
    // return new AppInfo[size];
    // }
    //
    // };
    //
    // @Override
    // public int describeContents() {
    // return 0;
    // }
    //
    // @Override
    // public void writeToParcel(Parcel dest, int flags) {
    // dest.writeString(detail);
    //
    // dest.writeString(appName);
    //
    // dest.writeString(versionName);
    //
    // dest.writeInt(versionCode);
    //
    // dest.writeLong(appId);
    //
    // dest.writeString(iconUrl);
    //
    // dest.writeString(imgUrl);
    //
    // dest.writeString(filePath);
    //
    // dest.writeFloat(grade);
    //
    // dest.writeInt(downloadNum);
    //
    // dest.writeLong(fileSize);
    //
    // dest.writeString(description);
    //
    // dest.writeString(appClass);
    //
    // dest.writeInt(source);
    //
    // dest.writeInt(adType);
    //
    // dest.writeInt(canUse);
    //
    // dest.writeValue(isDownloaded);
    //
    // dest.writeValue(isInstalled);
    //
    // dest.writeString(sourceUrl);
    //
    // dest.writeString(sourceHash);
    //
    // dest.writeList(patchs);
    //
    // dest.writeValue(drawable);
    //
    // dest.writeInt(isGame);
    //
    // dest.writeString(gpkPath);
    //
    // }
}
