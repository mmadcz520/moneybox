package com.changtou.moneybox.module.page;

import android.os.Bundle;

import com.changtou.R;

/**
 * 实名认证页面
 *
 * Created by Administrator on 2015/6/11.
 */
public class RichesCertificationActivity extends CTBaseActivity
{
    /**
     * @see CTBaseActivity#initView(Bundle)
     */
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_safe_certification);
    }

    protected void initData()
    {
        setPageTitle("实名认证");
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }
}
