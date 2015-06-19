package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;

import com.changtou.R;
import com.changtou.moneybox.module.phonebook.RichesPhoneBookActivity;

/**
 * ÍÆ¼öºÃÓÑÒ³Ãæ
 *
 * Created by Administrator on 2015/6/10.
 */
public class RichesPromotionActivity extends CTBaseActivity
{
    @Override
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_promotion_layout);

    }

    @Override
    protected void initListener()
    {
        setOnClickListener(R.id.riches_pro_phonenum);
        setOnClickListener(R.id.riches_rewards);
    }

    @Override
    protected int setPageType() {
        return 0;
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.riches_pro_phonenum:
                final Intent intent1 = new Intent(this, RichesPhoneBookActivity.class);
                startActivity(intent1);
                break;

            case R.id.riches_rewards:
                final Intent intent2 = new Intent(this, RichesRewardsActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
