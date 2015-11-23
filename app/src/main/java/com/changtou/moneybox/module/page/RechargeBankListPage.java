package com.changtou.moneybox.module.page;

import android.os.Bundle;

import com.changtou.moneybox.R;


/**
 * 支持银行列表页面
 *
 * Created by Jone on 2015/11/19.
 */
public class RechargeBankListPage extends CTBaseActivity
{
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_recharge_banklist);
    }

    protected void initData()
    {
        setPageTitle("银行列表");
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }
}
