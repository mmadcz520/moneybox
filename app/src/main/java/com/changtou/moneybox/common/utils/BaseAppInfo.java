
package com.changtou.moneybox.common.utils;

import com.framework.common.db.BaseDbEntity;

/**
 * 描述:应用基本信息，只有包名一个字段
 * 
 * @author chenys
 * @since 2013-10-18 下午8:38:54
 */
public class BaseAppInfo extends BaseDbEntity {

    private static final long serialVersionUID = -6774432090493698029L;

    protected String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
