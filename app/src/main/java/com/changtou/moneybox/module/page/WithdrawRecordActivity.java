package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.module.adapter.WithdrawAdapter;
import com.changtou.moneybox.module.entity.WithdrawEntity;
import com.changtou.moneybox.module.http.HttpRequst;

/**
 * 提现记录
 *
 * {"tradeNum":"4618195187897624475","payamount":0.01,"status":"成功","memo":"连连充值","type":"连连支付","creatime":"2015-11-03"}
 *
 * Created by Jone on 2015/11/10.
 */
public class WithdrawRecordActivity extends CTBaseActivity
{
    private ListView mTradeListView = null;
    private WithdrawAdapter mAdapter = null;

    private int mGoPageCode = 0;

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_trade_layout);

        mTradeListView = (ListView)findViewById(R.id.riches_trade_listview);

        mGoPageCode = this.getIntent().getIntExtra("goPageCode", 0);
    }

    protected void initData()
    {
        setPageTitle("提现记录");

        setLeftBtnOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mGoPageCode == 1) {
                    backToMainActivity();
                } else {
                    finish();
                }
            }
        });

        mAdapter = new WithdrawAdapter(this);
        mTradeListView.setAdapter(mAdapter);

        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_WITHDRAW_REC);

        sendRequest(HttpRequst.REQ_TYPE_WITHDRAW_REC,
                url,
                mParams,
                getAsyncClient(), false);
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        if (reqType == HttpRequst.REQ_TYPE_WITHDRAW_REC)
        {
            try {
                super.onSuccess(content, object, reqType);
                WithdrawEntity entity = (WithdrawEntity) object;
                mAdapter.setData(entity);
            }
            catch (Exception e)
            {
                BaseApplication.getInstance().backToLoginPage();
                Toast.makeText(BaseApplication.getInstance(), "账号在其他设备登陆,请重新登录", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }

    /**
     * back键跳转
     */
    public void onBackPressed()
    {
        if(mGoPageCode == 1)
        {
            backToMainActivity();
        }
        else
        {
            finish();
        }
    }

    private void backToMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("login_state", 1);
        startActivity(intent);
    }

}
