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
public class InvestmentSuccPage extends CTBaseActivity
{
    private Button mSuccButton1 = null;

    private Button mSuccButton2 = null;

    @Override
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.investment_succ_layout);
        mSuccButton1 = (Button)findViewById(R.id.invest_succ_btn_1);
        mSuccButton2 = (Button)findViewById(R.id.invest_succ_btn_2);

        TextView touyuanView = (TextView)findViewById(R.id.invest_succ_touyuan);
        Intent intent = getIntent();

        String touyuan = intent.getStringExtra("touyuan");
        if(touyuan!= null && !touyuan.equals(""))
        {
            touyuan = "恭喜您投资成功, 并获得" + touyuan + "个投圆。";
            touyuanView.setText(touyuan);
        }

    }

    @Override
    protected void initListener()
    {
        super.initListener();
        setOnClickListener(R.id.invest_succ_btn_1);
        setOnClickListener(R.id.invest_succ_btn_2);
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
            case R.id.invest_succ_btn_1:
                Intent intent1 = new Intent(this, RichesInvestListActivity.class);
                intent1.putExtra("login_state", 1);
                startActivity(intent1);

                break;

            case R.id.invest_succ_btn_2:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("login_state", 2);
                startActivity(intent);
                break;
        }
    }
}
