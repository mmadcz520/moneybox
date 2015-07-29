package com.changtou.moneybox.module.page;

import android.os.Bundle;

import com.changtou.R;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;

/**
 * 好友推荐列表
 *
 * Created by Administrator on 2015/6/2.
 */
public class RichesFriendActivity extends CTBaseActivity
{
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_friend_layout);
    }

    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }
}
