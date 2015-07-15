package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.adapter.InvestListAdapter;
import com.changtou.moneybox.module.adapter.TransferListAdapter;
import com.changtou.moneybox.module.entity.InvestListEntity;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.entity.TransferListEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.MultiStateView;
import com.changtou.moneybox.module.widget.SlidingTabLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    private SubPage mSubPage1 = null;
    private SubPage mSubPage2 = null;
    private SubPage mSubPage3 = null;

    protected void initView(Bundle bundle) {

        setContentView(R.layout.riches_invest_layout);

        mSlidingTabLayout = (SlidingTabLayout)findViewById(R.id.riches_invest_sliding_tabs);
        Resources res = getResources();
        mSlidingTabLayout.setCustomTabView(R.layout.product_tabpage_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.ct_blue));
        mSlidingTabLayout.setTabCount(3);

        List<BaseFragment> viewList = new ArrayList<>();
        mSubPage1 = SubPage.create(0);
        mSubPage2 = SubPage.create(1);
        mSubPage3 = SubPage.create(2);

        viewList.add(mSubPage1);
        viewList.add(mSubPage2);
        viewList.add(mSubPage3);

        mViewPager = (ViewPager)findViewById(R.id.riches_invest_pager);
        ExFPAdapter pagerAdapter = new ExFPAdapter(getSupportFragmentManager(), viewList);
        pagerAdapter.setTitles(new String[]{"还款中", "已结束", "已退出"});

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

        if(reqType == HttpRequst.REQ_TYPE_INVEST_LIST)
        {
            InvestListEntity entity = (InvestListEntity) object;
            Map<String, LinkedList> investMap = entity.getInvestMap();

            LinkedList list1 = investMap.get("1");
            LinkedList list2 = investMap.get("2");
            LinkedList list3 = investMap.get("3");

            mSubPage1.initInvestList(list1);
            mSubPage2.initInvestList(list2);
            mSubPage3.initInvestList(list3);
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
            mAdapter = new InvestListAdapter(mContext);
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
//            initInvestListRequest();
        }

        public void initInvestList(LinkedList data) {
            ((InvestListAdapter) mAdapter).setData(data);
        }
    }

    private void initInvestListRequest() {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_INVEST_LIST) +
                "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

        sendRequest(HttpRequst.REQ_TYPE_INVEST_LIST,
                url,
                mParams,
                getAsyncClient(), false);
    }
}
