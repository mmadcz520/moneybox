package com.changtou.moneybox.common.http.base;

/**
 *描述: Http 请求基类
 *
 *@since 2015-3-15
 *@author zhoulongfei
 */
public abstract class BaseHttpRequest {

    /**
     * 根据reqType 获取URL
     *
     * @param reqType
     * @return
     */
    public abstract String getUrl(int reqType);

    /**
     * 格式化请求地址
     *
     * @param mId
     * @param format
     * @return
     */
    public String getUrl(int mId, String format){
        switch (mId){
            //todo
            default:
                return format + getUrl(mId);
        }
    }

    /**
     * 根据reqType 获取返回对象
     *
     * @param reqType
     * @return
     */
    public abstract Object getPaser(int reqType);
}
