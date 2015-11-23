package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.changtou.moneybox.R;

/**
 * 充值成功页面
 *
 * Created by Jone on 2015/11/10.
 */
public class WithdrawSuccPage extends CTBaseActivity
{
    private Button mSuccButton1 = null;

    private Button mSuccButton2 = null;

    @Override
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.withdraw_succ_layout);
        mSuccButton1 = (Button)findViewById(R.id.withdraw_succ_btn_1);
        mSuccButton2 = (Button)findViewById(R.id.withdraw_succ_btn_2);

    }

    @Override
    protected void initListener()
    {
        super.initListener();
        setOnClickListener(R.id.withdraw_succ_btn_1);
        setOnClickListener(R.id.withdraw_succ_btn_2);
    }

    protected void initData()
    {
        setPageTitle("提现成功");
        super.initData();
    }

    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }


    public void treatClickEvent(int id)
    {
        switch (id) {
            case R.id.withdraw_succ_btn_1:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("login_state", 1);
                startActivity(intent);

                break;

            case R.id.withdraw_succ_btn_2:
                Intent intent1 = new Intent(this, WithdrawRecordActivity.class);
                intent1.putExtra("goPageCode", 1);
                startActivity(intent1);
                break;
        }
    }
}
