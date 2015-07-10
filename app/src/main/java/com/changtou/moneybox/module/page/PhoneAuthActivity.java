package com.changtou.moneybox.module.page;

import android.os.Bundle;

import com.changtou.R;

/**
 * Created by Administrator on 2015/7/10.
 */
public class PhoneAuthActivity extends CTBaseActivity
{

    @Override
    protected void initView(Bundle bundle) {
        super.initView(bundle);
        setContentView(R.layout.riches_safe_phoneauth);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void initData() {
        super.initData();
        setPageTitle("手机认证");
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }
}
