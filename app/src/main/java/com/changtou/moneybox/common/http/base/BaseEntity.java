package com.changtou.moneybox.common.http.base;

import org.json.JSONArray;

import java.util.LinkedHashMap;

/**
 * 描述：解析实体类， 基类
 *
 * @since 2015-3-15
 * @author zhoulongfei
 */
public abstract class BaseEntity {

    public final static String KEY_RESULT="result";
    public final static String KEY_LIST="list";
    public final static String KEY_DATE="date";
    public final static String KEY_NAME="name";
    public final static String KEY_DESC="desc";
    public final static String KEY_DATA="data";

    public abstract void paser(String data) throws Exception;

    public String err;

    public static void paserKeys(JSONArray arr, LinkedHashMap<String, String> map) throws Exception{

        //todo 解析Json数据
        JSONArray keys = arr.getJSONArray(0);
        JSONArray values = arr.getJSONArray(1);

        int size = keys.length();
        for(int i = 0; i<size; i++)
        {
            map.put(keys.getString(i), values.getString(i));
        }
    }
}
