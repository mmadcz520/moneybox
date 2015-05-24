package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.adapter.ProductListAdapter;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.MultiStateView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/23 0023.
 *
 * 投资列表页面
 *
 */
public class RichesInvestListActivity extends CTBaseActivity
{
    private ViewPager mViewPager = null;

    protected void initView(Bundle bundle) {

        setContentView(R.layout.riches_invest_layout);

        List<BaseFragment> viewList = new ArrayList<>();
        viewList.add(new SubPage());
        viewList.add(new SubPage());
        viewList.add(new SubPage());
        viewList.add(new SubPage());

        mViewPager = (ViewPager)findViewById(R.id.riches_invest_pager);
        ExFPAdapter pagerAdapter = new ExFPAdapter(getSupportFragmentManager(), viewList);

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(viewList.size());

    }

    /**
     * 描述: 产品分类子页面
     * @author zhoulongfei
     */
    public static class SubPage extends BaseFragment implements PullToRefreshBase.OnRefreshListener
    {
        private ProductListAdapter mAdapter = null;
        private Context mContext = null;

        private PullToRefreshListView mPullRefreshListView;

        private ListView actualListView;
        private MultiStateView mMultiStateView;

        protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View mView = inflater.inflate(R.layout.product_tabpage_layout, null);

            mContext = this.getActivity();
            mMultiStateView = (MultiStateView) mView.findViewById(R.id.multiStateView);
            mPullRefreshListView = (PullToRefreshListView) mView.findViewById(R.id.product_list);
            actualListView = mPullRefreshListView.getRefreshableView();
            actualListView.setEnabled(true);
            mPullRefreshListView.setOnRefreshListener(this);

            return mView;
        }

        protected void initLisener()
        {
            final Activity activity = this.getActivity();

            actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                Intent intent = new Intent(activity, ProductDetailsActivity.class);
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    startActivity(intent);
                }
            });

            actualListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                Intent intent = new Intent(activity, ProductDetailsActivity.class);
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    // 跳转到详情页
                    startActivity(intent);
                }

                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }

        protected void initData(Bundle savedInstanceState)
        {
            mAdapter = new ProductListAdapter(mContext);
            actualListView.setAdapter(mAdapter);

            sendRequest(HttpRequst.REQ_TYPE_PRODUCT_LIST,
                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_LIST),
                    mParams,
                    mAct.getAsyncClient(), false);
        }

        public void onSuccess(String content, Object object, int reqType)
        {
            if (reqType == HttpRequst.REQ_TYPE_PRODUCT_LIST)
            {
                mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                ProductEntity entity = (ProductEntity) object;
                mAdapter.setData(entity);

                mPullRefreshListView.onRefreshComplete();
            }
        }

        public void onFailure(Throwable error, String content, int reqType)
        {
            mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
        }

        /**
         * 刷新函数
         * @param refreshView
         */
        public void onRefresh(PullToRefreshBase refreshView)
        {
            sendRequest(HttpRequst.REQ_TYPE_PRODUCT_LIST,
                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_LIST),
                    mParams,
                    mAct.getAsyncClient(), true);
        }
    }
}
