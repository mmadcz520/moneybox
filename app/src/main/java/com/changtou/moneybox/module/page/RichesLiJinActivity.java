package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.module.adapter.LiJinAdapter;
import com.changtou.moneybox.module.entity.LiJinEntity;
import com.changtou.moneybox.module.http.HttpRequst;

/**
 * 用户礼金记录
 * <p/>
 * Created by Administrator on 2015/6/2.
 */
public class RichesLiJinActivity extends CTBaseActivity {
    private ListView mTradeListView = null;
    private LiJinAdapter mAdapter = null;

    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_trade_layout);

        mTradeListView = (ListView) findViewById(R.id.riches_trade_listview);
    }

    protected void initData() {
        setPageTitle("交易记录");

        mAdapter = new LiJinAdapter(this);
        mTradeListView.setAdapter(mAdapter);

        String url = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_LIJIN);

        sendRequest(HttpRequst.REQ_TYPE_LIJIN,
                url,
                mParams,
                getAsyncClient(), false);
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    public void onSuccess(String content, Object object, int reqType) {
        super.onSuccess(content, object, reqType);

        if (reqType == HttpRequst.REQ_TYPE_LIJIN) {
            try {

                Log.e("CT_MONEY", content);
                LiJinEntity entity = (LiJinEntity) object;
                mAdapter.setData(entity);

            } catch (Exception e) {
                e.printStackTrace();
                BaseApplication.getInstance().backToLoginPage();
                Toast.makeText(BaseApplication.getInstance(), "账号在其他设备登陆,请重新登录", Toast.LENGTH_LONG).show();
            }

        }
    }
}
