package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;

import com.changtou.R;

/**
 * 1. √‹¬Îπ‹¿Ì“≥√Ê
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
        return 0;
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.btn_pd_login:
            {

                break;
            }

            case R.id.btn_pd_gesture:
            {
                Intent intent = new Intent(this, GesturePWActivity.class);
                startActivity(intent);

                break;
            }
        }
    }
}
