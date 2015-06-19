package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.changtou.R;

/**
 * 注册页面
 * Created by Administrator on 2015/5/18.
 */
public class RegisterActivity extends CTBaseActivity
{
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_register_layout);

        Button button = (Button)findViewById(R.id.register_btn);
        final Intent intent = new Intent(this, RegisterNextActivity.class);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setPageType() {
        return 0;
    }
}
