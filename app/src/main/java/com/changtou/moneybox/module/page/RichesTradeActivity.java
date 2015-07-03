package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.adapter.ProductDetailsAdapter;
import com.changtou.moneybox.module.adapter.TradeAdapter;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;
import com.changtou.moneybox.module.entity.TradeEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.NumberWheel;

/**
 * 用户交易记录页面
 *
 * Created by Administrator on 2015/6/2.
 */
public class RichesTradeActivity extends CTBaseActivity
{
    private ListView mTradeListView = null;
    private TradeAdapter mAdapter = null;

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_trade_layout);

        mTradeListView = (ListView)findViewById(R.id.riches_trade_listview);
    }

    protected void initData()
    {
        setPageTitle("交易记录");

        mAdapter = new TradeAdapter(this);
        mTradeListView.setAdapter(mAdapter);

        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_TRADE_LIST) +
                "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

        sendRequest(HttpRequst.REQ_TYPE_TRADE_LIST,
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
        if (reqType == HttpRequst.REQ_TYPE_TRADE_LIST)
        {
            printLog(content);

            super.onSuccess(content, object, reqType);
            TradeEntity entity = (TradeEntity) object;
            mAdapter.setData(entity);
        }
    }
}
