package com.changtou.moneybox.common.http.base;

import android.util.Log;

import com.changtou.moneybox.common.activity.BaseApplication;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

/**
 * 描述:Json解析， 工程模式
 *
 * @since 2015-3-15
 * @author zhoulongfei
 */
public class JsonPaserFactory {

    public static BaseEntity paserObj(String data, int reqType){

        if((data == null) || data.trim().toString().equals("")) return null;

        BaseEntity entity = (BaseEntity)BaseApplication.mHttpRequest.getPaser(reqType);
        try{

            Log.e("aaaaaaaaaaaaaaa", data);

            entity.paser(data);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return entity;
    }

    public static BaseEntity paserObj(HttpEntity httpEntity, int reqType){
        BaseEntity entity = null;
        if(httpEntity != null){
            try{
                entity = paserObj(EntityUtils.toString(httpEntity), reqType);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return entity;
    }
}
