package com.changtou.moneybox.module.page;

import android.os.Bundle;

import com.changtou.R;

/**
 * �����Ƽ��б�
 *
 * Created by Administrator on 2015/6/2.
 */
public class RichesFriendActivity extends CTBaseActivity
{
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_friend_layout);
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }
}
