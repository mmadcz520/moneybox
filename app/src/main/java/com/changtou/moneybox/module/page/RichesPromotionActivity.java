package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;

import com.changtou.R;
import com.changtou.moneybox.module.phonebook.RichesPhoneBookActivity;

/**
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
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.riches_pro_phonenum:
                final Intent intent = new Intent(this, RichesPhoneBookActivity.class);
                startActivity(intent);
                break;
        }
    }
}
