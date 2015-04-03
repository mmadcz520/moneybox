
package com.changtou.moneybox.common.utils;

import com.framework.common.base.IEntity;

/**
 * 描述:增量文件信息
 * 
 * @author chenys
 * @since 2013-10-7 下午5:23:22
 */
public class PatchInfo implements IEntity {

    private static final long serialVersionUID = -6994224114239392407L;

    // 哪个版本的增量包
    private int fromVersion;

    // 增量包下载地址
    private String patchDownloadUrl;

    // 文件hash
    private String patchHash;

    public int getFromVersion() {
        return fromVersion;
    }

    public void setFromVersion(int fromVersion) {
        this.fromVersion = fromVersion;
    }

    public String getPatchDownloadUrl() {
        return patchDownloadUrl;
    }

    public void setPatchDownloadUrl(String patchDownloadUrl) {
        this.patchDownloadUrl = patchDownloadUrl;
    }

    public String getPatchHash() {
        return patchHash;
    }

    public void setPatchHash(String patchHash) {
        this.patchHash = patchHash;
    }
}
