package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.changtou.R;
import com.changtou.moneybox.module.adapter.ProductDetailsAdapter;
import com.changtou.moneybox.module.adapter.TradeAdapter;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;
import com.changtou.moneybox.module.entity.TradeEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.NumberWheel;

/**
 * Created by Administrator on 2015/6/2.
 */
public class RichesTradeActivity extends CTBaseActivity
{
    private ListView mTradeListView = null;
    private TradeAdapter mAdapter = null;

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_trade_layout);

//        NumberWheel.NumberBean nBean=new NumberWheel.NumberBean();
//        nBean.setStartShowNumString("0000");
//        nBean.setEndShowNumString("1998");
//        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(338,130);
//        NumberWheel nw = new NumberWheel(this,nBean,rl,500);
//        RelativeLayout r = (RelativeLayout)findViewById(R.id.tredd);
//        r.addView(nw);

        mTradeListView = (ListView)findViewById(R.id.riches_trade_listview);
    }

    protected void initData()
    {
        mAdapter = new TradeAdapter(this);
        mTradeListView.setAdapter(mAdapter);

        sendRequest(HttpRequst.REQ_TYPE_TRADE_LIST,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_TRADE_LIST),
                mParams,
                getAsyncClient(), false);
    }

    @Override
    protected int setPageType() {
        return 0;
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        if (reqType == HttpRequst.REQ_TYPE_TRADE_LIST)
        {
            TradeEntity entity = (TradeEntity) object;
            mAdapter.setData(entity);
        }
    }
}
