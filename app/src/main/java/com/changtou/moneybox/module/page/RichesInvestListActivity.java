package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.adapter.InvestListAdapter;
import com.changtou.moneybox.module.adapter.TransferListAdapter;
import com.changtou.moneybox.module.entity.InvestListEntity;
import com.changtou.moneybox.module.entity.TransferListEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.MultiStateView;
import com.changtou.moneybox.module.widget.SlidingTabLayout;
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
    private SlidingTabLayout mSlidingTabLayout;

    protected void initView(Bundle bundle) {

        setContentView(R.layout.riches_invest_layout);

        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.riches_invest_sliding_tabs);
        Resources res = getResources();
        mSlidingTabLayout.setCustomTabView(R.layout.product_tabpage_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.ct_blue));

        List<BaseFragment> viewList = new ArrayList<>();
        viewList.add(SubPage.create(0));
        viewList.add(SubPage.create(1));
        viewList.add(SubPage.create(2));
        viewList.add(SubPage.create(3));

        mViewPager = (ViewPager)findViewById(R.id.riches_invest_pager);
        ExFPAdapter pagerAdapter = new ExFPAdapter(getSupportFragmentManager(), viewList);
        pagerAdapter.setTitles(new String[]{"还款中", "已结束", "转让中", "已转让"});

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(viewList.size());
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    /**
     * 描述: 产品分类子页面
     * @author zhoulongfei
     */
    public static class SubPage extends BaseFragment implements PullToRefreshBase.OnRefreshListener
    {
        private BaseAdapter mAdapter = null;
        private Context mContext = null;

        private PullToRefreshListView mPullRefreshListView;

        private ListView actualListView;
        private MultiStateView mMultiStateView;

        private int[] mType = {HttpRequst.REQ_TYPE_INVEST_LIST,
                0 ,
                HttpRequst.REQ_TYPE_TRANSFER_LIST ,
                0};

        private String[] mUrl = { HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_INVEST_LIST),
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_INVEST_LIST) ,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_TRANSFER_LIST) ,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_INVEST_LIST)};

        public static SubPage create(int type)
        {
            SubPage subPage = new SubPage();
            Bundle bundle = new Bundle();
            bundle.putInt("invest", type);
            subPage.setArguments(bundle);
            return subPage;
        }

        protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View mView = inflater.inflate(R.layout.product_tabpage_layout, container, false);

            mContext = this.getActivity();
            mMultiStateView = (MultiStateView) mView.findViewById(R.id.multiStateView);
            mPullRefreshListView = (PullToRefreshListView) mView.findViewById(R.id.product_list);
            actualListView = mPullRefreshListView.getRefreshableView();
            actualListView.setEnabled(true);
            mPullRefreshListView.setOnRefreshListener(this);

            return mView;
        }

        protected void initListener()
        {
            final Activity activity = this.getActivity();

            actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                Intent intent = new Intent(activity, ProductDetailsActivity.class);

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(intent);
                }
            });

            actualListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                Intent intent = new Intent(activity, ProductDetailsActivity.class);

                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // 跳转到详情页
                    startActivity(intent);
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        protected void initData(Bundle savedInstanceState)
        {
            int page = getArguments().getInt("invest");
            sendRequest(mType[page], mUrl[page], mParams, mAct.getAsyncClient(), false);
            switch (page)
            {
                case 0:
                    mAdapter = new InvestListAdapter(mContext);
                    actualListView.setAdapter(mAdapter);
                    break;
                case 1:
                    mAdapter = new InvestListAdapter(mContext);
                    break;
                case 2:
                    mAdapter = new TransferListAdapter(mContext);
                    actualListView.setAdapter(mAdapter);
                    break;
                case 3:
                    mAdapter = new InvestListAdapter(mContext);
                    break;
            }
        }

        public void onSuccess(String content, Object object, int reqType)
        {
            if (reqType == HttpRequst.REQ_TYPE_INVEST_LIST)
            {
                mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                InvestListEntity entity = (InvestListEntity) object;
                ((InvestListAdapter)mAdapter).setData(entity);

                mPullRefreshListView.onRefreshComplete();
            }
            else if(reqType == HttpRequst.REQ_TYPE_TRANSFER_LIST)
            {
                mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
                TransferListEntity entity = (TransferListEntity) object;
                ((TransferListAdapter)mAdapter).setData(entity);
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
            sendRequest(HttpRequst.REQ_TYPE_INVEST_LIST,
                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_INVEST_LIST),
                    mParams,
                    mAct.getAsyncClient(), true);
        }
    }
}
