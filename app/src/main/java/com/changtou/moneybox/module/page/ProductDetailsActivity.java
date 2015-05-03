package com.changtou.moneybox.module.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.changtou.R;
import com.changtou.moneybox.module.adapter.ProductDetailsAdapter;
import com.changtou.moneybox.module.adapter.ProductListAdapter;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.MultiStateView;
import com.changtou.moneybox.module.widget.RoundProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述： 产品详情页
 *
 * @author zhoulongfei
 * @since 2015-4-13
 */
public class ProductDetailsActivity extends CTBaseActivity
{
    RelativeLayout.LayoutParams parentParams;
    RelativeLayout.LayoutParams paneBomParams;

    private ProductDetailsAdapter mAdapter = null;
    private ListView mProdList = null;


    /**
     * @see CTBaseActivity#initView(Bundle)
     */
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.product_details);

        Button button = (Button)findViewById(R.id.confirm_button);

        final Intent intent = new Intent(this, ConfirmActivity.class);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent);
            }
        });

        mProdList = (ListView)findViewById(R.id.lv_details);
        ViewStub viewStub= new ViewStub(this);
        mProdList.addHeaderView(viewStub);

        RoundProgressBar rpb = (RoundProgressBar)findViewById(R.id.details_progressbar);
        rpb.setProgress(60);
    }


    /**
     * 网络数据请求， 初始化数据
     *
     * @param bundle
     */
    protected void initData(Bundle bundle)
    {
        mAdapter = new ProductDetailsAdapter(this);
        mProdList.setAdapter(mAdapter);

        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_DETAILS,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_HOME),
                mParams,
                getAsyncClient(), false);
    }

    /**
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType)
    {
        if (reqType == HttpRequst.REQ_TYPE_PRODUCT_DETAILS)
        {
            ProductDetailsEntity entity = (ProductDetailsEntity) object;
            mAdapter.setData(entity);
        }
    }
}
