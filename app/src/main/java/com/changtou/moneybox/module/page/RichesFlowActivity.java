package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.changtou.R;
import com.changtou.moneybox.module.adapter.FlowAdapter;
import com.changtou.moneybox.module.entity.FlowEntity;
import com.changtou.moneybox.module.http.HttpRequst;

/**
 * Created by Administrator on 2015/5/25.
 * 现金流界面
 */
public class RichesFlowActivity extends CTBaseActivity implements AdapterView.OnItemClickListener
{
    private ListView mFlowListView = null;
    private FlowAdapter mAdapter = null;

    private FlowEntity mEntity = null;

    protected void initView(Bundle bundle)
    {
        super.setContentView(R.layout.riches_flow_layout);
        mFlowListView = (ListView)findViewById(R.id.riches_flow_listview);
        mFlowListView.setOnItemClickListener(this);
    }

    protected void initData()
    {
        mAdapter = new FlowAdapter(this);
        mFlowListView.setAdapter(mAdapter);

        sendRequest(HttpRequst.REQ_TYPE_FLOW,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_FLOW),
                mParams,
                getAsyncClient(), false);
    }

    /**
     * http 请求成功回调
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType)
    {
        if(reqType == HttpRequst.REQ_TYPE_FLOW)
        {
            mEntity = (FlowEntity) object;
            mAdapter.setData(mEntity);
        }
    }

    /**
     * http 请求失败回调
     *
     * @param error      错误码
     * @param content    错误内容
     * @param reqType    请求类型
     */
    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        final Intent intent = new Intent(RichesFlowActivity.this, RichesCalendarActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("selected_month", "5");
        bundle.putSerializable("month", mEntity);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
