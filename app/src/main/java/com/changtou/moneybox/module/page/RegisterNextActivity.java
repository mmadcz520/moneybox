package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.changtou.R;

/**
 * 注册页面
 * Created by Administrator on 2015/5/18.
 */
public class RegisterNextActivity extends CTBaseActivity
{
    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_register_next_layout);

        Button button = (Button)findViewById(R.id.register_next_btn);
        final Intent intent = new Intent(this, RegisterPasswordActivity.class);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent);
            }
        });
    }
}
