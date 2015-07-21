package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.widget.ExEditView;

/**
 * Created by Administrator on 2015/5/22.
 *
 * 用户中心  安全管理
 */
public class RichesSafeActivity extends CTBaseActivity
{
    private ExEditView mExEditView = null;

    private TextView mTitleView = null;

    private SharedPreferencesHelper sph = null;

    protected void initView(Bundle bundle) {
        sph = SharedPreferencesHelper.getInstance(getApplicationContext());
        setContentView(R.layout.riches_safe_layout);

        mTitleView = (TextView)findViewById(R.id.riches_safe_title);
    }

    protected void initListener()
    {
        setOnClickListener(R.id.btn_phoneauth);
        setOnClickListener(R.id.btn_certification_manager);
        setOnClickListener(R.id.safe_page_quit);
        setOnClickListener(R.id.btn_bank_manager);
        setOnClickListener(R.id.btn_pd_manager);
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    @Override
    protected void initData()
    {
        setPageTitle("安全设置");

        UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
        String fullname = userInfoEntity.getFullName();
        mTitleView.setText(fullname);
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.btn_phoneauth:
            {
                final Intent intent = new Intent(this, PdFrogetActivity.class);
                intent.putExtra("pageType", 1);
                startActivity(intent);
                break;
            }

            case R.id.btn_certification_manager:
            {
                final Intent intent = new Intent(this, RichesCertificationActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.btn_bank_manager:
            {
                final Intent intent = new Intent(this, RichesBankActivity.class);
                startActivity(intent);
                break;
            }

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
                sph.putString(AppCfg.GSPD, "");
//                final Intent intent0 = new Intent(this, MainActivity.class);
//                startActivity(intent0);
//                BaseApplication.getInstance().resetBackFlag();
                BaseApplication.getInstance().AppExit();
                break;
            }
        }
    }
}
