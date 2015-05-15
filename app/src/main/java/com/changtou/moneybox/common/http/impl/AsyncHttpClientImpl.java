package com.changtou.moneybox.common.http.impl;

import android.content.Context;
import android.util.Log;

import com.changtou.moneybox.common.http.base.BaseHttpClient;
import com.changtou.moneybox.common.http.base.BaseHttpHandler;
import com.changtou.moneybox.common.http.base.HttpCallback;
import com.changtou.moneybox.common.http.async.AsyncHttpClient;
import com.changtou.moneybox.common.http.async.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import java.util.Map;

/**
 * 描述: AsyncHttpClient async网络申请实现类
 *       单例模式
 *       适配器模式
 *       回调
 *
 * @since  2015/3/15 0015.
 * @author zhoulongfei
 */
public class AsyncHttpClientImpl implements BaseHttpClient {

    //异步连接客户端
    private  AsyncHttpClient client = null;

    //单例模式实现
    private static AsyncHttpClientImpl instance = null;

    public static AsyncHttpClientImpl getHupuHttpClient() {
        if (instance == null)
            instance = new AsyncHttpClientImpl();
        return instance;
    }

    private AsyncHttpClientImpl() {
        client = new AsyncHttpClient();
    }

    @Override
    public void get(int reqType, String url, RequestParams cacheParams,
                    HttpCallback callback) {
        client.get(url,cacheParams, new BaseHttpHandler(callback), reqType);
    }

    @Override
    public void get(int reqType, Context context, String url,
                    RequestParams cacheParams, HttpCallback callback) {
        // TODO Auto-generated method stub
        client.get(url, cacheParams,new BaseHttpHandler(callback), reqType);
    }

    @Override
    public void post(int reqType, Context context, String url,
                     RequestParams params, HttpCallback callback) {
        // TODO Auto-generated method stub
        client.post(url,params, new BaseHttpHandler(callback), reqType);
    }

    @Override
    public void post(int reqType, Context context, String url,
                     HttpEntity entity, String contentType, HttpCallback callback) {
        // TODO Auto-generated method stub
        client.post(context,url, entity,contentType,new BaseHttpHandler(callback), reqType);
    }

    @Override
    public void post(int reqType, Context context, String url,
                     Map<String, String> clientHeaderMap, RequestParams params,
                     String contentType, HttpCallback callback) {
        // TODO Auto-generated method stub
    }

    @Override
    public void post(int reqType, Context context, String url,
                     Header[] headers, HttpEntity entity, String contentType,
                     HttpCallback callback) {
        // TODO Auto-generated method stub
        client.post(context,url, headers,entity,contentType,new BaseHttpHandler(callback), reqType);
    }

}
