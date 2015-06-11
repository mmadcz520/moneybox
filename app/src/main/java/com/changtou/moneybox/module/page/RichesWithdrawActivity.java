package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.changtou.R;

/**
 * Created by Administrator on 2015/5/21.
 *
 */
public class RichesWithdrawActivity extends CTBaseActivity
{
    @Override
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_withdraw_layout);

        final Intent intent3 = new Intent(this, RichesBankActivity.class);

        RelativeLayout ll = (RelativeLayout)findViewById(R.id.riches_bank_item);
        ll.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent3);
            }
        });
    }
}
