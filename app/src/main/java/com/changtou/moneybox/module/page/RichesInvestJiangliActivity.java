package com.changtou.moneybox.module.page;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.adapter.JiangLiListAdapter;
import com.changtou.moneybox.module.entity.JiangLiInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.SlidingTabLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/23 0023.
 *
 * 投资列表页面
 *
 */
public class RichesInvestJiangliActivity extends CTBaseActivity
{
    private ViewPager mViewPager = null;
    private SlidingTabLayout mSlidingTabLayout;

    private SubPage mSubPage1 = null;
    private SubPage mSubPage2 = null;

    private static String[] mTypeName = {"已获奖励", "待收奖励"};

    private ExFPAdapter pagerAdapter = null;

    protected void initView(Bundle bundle) {

        setContentView(R.layout.riches_invest_layout);

        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.riches_invest_sliding_tabs);
        Resources res = getResources();
        mSlidingTabLayout.setCustomTabView(R.layout.product_tabpage_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.ct_blue));
        mSlidingTabLayout.setTabCount(2);

        List<BaseFragment> viewList = new ArrayList<>();
        mSubPage1 = SubPage.create(0);
        mSubPage2 = SubPage.create(1);

        viewList.add(mSubPage1);
        viewList.add(mSubPage2);

        mViewPager = (ViewPager)findViewById(R.id.riches_invest_pager);
        pagerAdapter = new ExFPAdapter(getSupportFragmentManager(), viewList);
        pagerAdapter.setTitles(mTypeName);

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(viewList.size());
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    protected void initData()
    {
        initInvestListRequest();
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        super.onSuccess(content, object, reqType);

        try {
            if(reqType == HttpRequst.REQ_TYPE_JIANGLI)
            {
                JiangLiInfoEntity entity = (JiangLiInfoEntity) object;
                LinkedList daiShouList = entity.mDaiShouList;
                LinkedList yiShouList = entity.mYiShouList;


                String[] title = new String[2];
                title[0] = "已获奖励(" + entity.mYiShouTot + "元)";
                title[1] = "待收奖励(" + entity.mDaiShouTot + "元)";
                pagerAdapter.setTitles(title);

                mViewPager.setAdapter(pagerAdapter);
                mSlidingTabLayout.setViewPager(mViewPager);

                mSubPage1.initInvestList(yiShouList);
                mSubPage2.initInvestList(daiShouList);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
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

        private LinkedList mData = null;

        private int mType = 0;

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
            mPullRefreshListView = (PullToRefreshListView) mView.findViewById(R.id.product_list);
            actualListView = mPullRefreshListView.getRefreshableView();
            actualListView.setEnabled(true);

            mType = getArguments().getInt("invest");

//            mPullRefreshListView.setOnRefreshListener(this);

            return mView;
        }

        protected void initListener()
        {

        }

        protected void initData(Bundle savedInstanceState)
        {
            mAdapter = new JiangLiListAdapter(mContext);
            actualListView.setAdapter(mAdapter);
        }

        public void onSuccess(String content, Object object, int reqType)
        {

        }

        public void onFailure(Throwable error, String content, int reqType)
        {
        }

        /**
         * 刷新函数
         * @param refreshView
         */
        public void onRefresh(PullToRefreshBase refreshView)
        {
            initInvestList(mData);
            mPullRefreshListView.onRefreshComplete();
        }

        public void initInvestList(LinkedList data) {
            ((JiangLiListAdapter) mAdapter).setData(data);
            this.mData = data;
        }

        /**
         * 跳转到详情页
         */
        private void goToProducDtetails(String id, int type, String state)
        {
            Intent intent = new Intent(this.getActivity(), ProductDetailsActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("type", type);
            intent.putExtra("state", state);
            startActivity(intent);
        }
    }

    private void initInvestListRequest() {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_JIANGLI);

        sendRequest(HttpRequst.REQ_TYPE_JIANGLI,
                url,
                mParams,
                getAsyncClient(), false);
    }

}

