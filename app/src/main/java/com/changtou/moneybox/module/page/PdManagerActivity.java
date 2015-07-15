package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;

/**
 * 1. 密码管理页面
 *
 * Created by Administrator on 2015/6/1.
 */
public class PdManagerActivity extends CTBaseActivity
{
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.password_mananger_activity);
    }

    protected void initListener()
    {
        setOnClickListener(R.id.btn_pd_login);
        setOnClickListener(R.id.btn_pd_gesture);
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.btn_pd_login:
            {
                Intent intent = new Intent(this, PdFrogetActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.btn_pd_gesture:
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, 0);

                break;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            //设置手势密码
            BaseApplication.getInstance().onBackground();
        }
        else
        {

        }
    }
}
