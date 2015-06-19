package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;

import com.changtou.R;

/**
 * 确认密码页
 * Created by Administrator on 2015/5/18.
 */
public class RegisterPasswordActivity extends CTBaseActivity
{
    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_register_password_layout);
    }

    protected void initListener()
    {
        setOnClickListener(R.id.riches_register_do);
    }

    @Override
    protected int setPageType() {
        return 0;
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.riches_register_do:
                Intent intent = new Intent(this, GesturePWActivity.class);
                startActivity(intent);
                break;
        }
    }

}
