package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.adapter.FlowAdapter;
import com.changtou.moneybox.module.entity.FlowEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
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
        setPageTitle("我的现金流");

        mAdapter = new FlowAdapter(this);
        mFlowListView.setAdapter(mAdapter);

//        sendRequest(HttpRequst.REQ_TYPE_FLOW,
//                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_FLOW),
//                mParams,
//                getAsyncClient(), false);


        UserInfoEntity userInfoEntity = (UserInfoEntity)ACache.get(this).getAsObject("userinfo");
        mEntity = userInfoEntity.getFlowEntity();
        mAdapter.setData(mEntity);
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
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
            super.onSuccess(content, object, reqType);
            if(object != null)
            {
                mEntity = (FlowEntity) object;
            }
            UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
            mAdapter.setData(userInfoEntity.getFlowEntity());
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

//        Bundle bundle = new Bundle();
//        bundle.putInt("selected_month", position);
//        bundle.putSerializable("flow", mEntity);
//        intent.putExtras(bundle);

        ACache cache = ACache.get(this);
        cache.put("selected_month", position);

        cache.put("flow", mEntity);

        startActivity(intent);
    }

}
