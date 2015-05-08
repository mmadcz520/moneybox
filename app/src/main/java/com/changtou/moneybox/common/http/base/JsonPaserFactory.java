package com.changtou.moneybox.common.http.base;

import android.util.Log;

import com.changtou.moneybox.common.activity.BaseApplication;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

/**
 * 描述:Json解析， 工程模式
 *
 * @author zhoulongfei
 * @since 2015-3-15
 */
public class JsonPaserFactory
{
    /**
     * 描述: 根据type，把Obj解析成具体的实体类
     *
     * @param data      解析数据
     * @param reqType   请求type
     * @return  实体类
     */
    public static BaseEntity paserObj(String data, int reqType)
    {
        Log.e("CT_MONEY", "paserObj" + data);
        Log.e("CT_MONEY", "paserObj" + reqType);

        if ((data == null) || data.trim().toString().equals("")) return null;
        BaseEntity entity = (BaseEntity) BaseApplication.mHttpRequest.getPaser(reqType);
        try
        {
            entity.paser(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return entity;
    }

    public static BaseEntity paserObj(HttpEntity httpEntity, int reqType)
    {
        BaseEntity entity = null;
        if (httpEntity != null)
        {
            try
            {
                entity = paserObj(EntityUtils.toString(httpEntity), reqType);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return entity;
    }
}
