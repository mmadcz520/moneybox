package com.changtou.moneybox.module.page;

import android.os.Bundle;
import com.changtou.R;

/**
 * 推荐奖励页面
 *
 * Created by Administrator on 2015/6/11.
 */
public class RichesRewardsActivity extends CTBaseActivity
{
    /**
     * @see CTBaseActivity#initView(Bundle)
     */
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_rewards_layout);
    }

    @Override
    protected int setPageType() {
        return 0;
    }
}
