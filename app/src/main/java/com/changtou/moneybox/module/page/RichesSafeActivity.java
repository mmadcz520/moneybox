package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.widget.ExEditView;

/**
 * Created by Administrator on 2015/5/22.
 *
 * 用户中心  安全管理
 */
public class RichesSafeActivity extends CTBaseActivity
{
    private ExEditView mExEditView = null;

    private SharedPreferencesHelper sph = null;

    protected void initView(Bundle bundle) {
        sph = SharedPreferencesHelper.getInstance(getApplicationContext());
        setContentView(R.layout.riches_safe_layout);
    }

    protected void initData() {
        setOnClickListener(R.id.safe_page_quit);
        setOnClickListener(R.id.btn_pd_manager);
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.btn_pd_manager:
            {
                final Intent intent0 = new Intent(this, PdManagerActivity.class);
                startActivity(intent0);
                break;
            }

            // 退出app
            case R.id.safe_page_quit:
            {
                sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.EN_LOGIN.toString());
                final Intent intent0 = new Intent(this, MainActivity.class);
                startActivity(intent0);
                BaseApplication.getInstance().resetBackFlag();
                break;
            }
        }
    }
}
