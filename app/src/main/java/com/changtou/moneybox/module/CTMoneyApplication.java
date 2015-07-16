package com.changtou.moneybox.module;

import android.content.Intent;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.base.BaseHttpRequest;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.service.PingService;


/**
 * 描述: ChangTou Money App 基类
 *
 * @author zhoulongfei
 * @since 2015-3-13
 *
 * //版本分支 初始化时获取产品信息， 缓存机制
 *
 */
public class CTMoneyApplication extends BaseApplication
{
    public BaseApplication app;

    public void onCreate()
    {
        super.onCreate();

        initAppData();
    }

    /**
     * @see BaseApplication#initBaseHttpRequest()
     * @return
     */
    protected BaseHttpRequest initBaseHttpRequest()
    {
        return HttpRequst.getInstance();
    }

    /**
     * 初始化app 信息
     *
     * 首页推荐产品 产品类型 及 产品列表
     *
     */
    private void initAppData()
    {
        Intent intent = new Intent();
        intent.setClass(this, PingService.class);
        startService(intent);

        //
    }

}
