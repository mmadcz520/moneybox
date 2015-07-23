package com.changtou.moneybox.module.page;

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
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.adapter.InvestListAdapter;
import com.changtou.moneybox.module.entity.InvestListEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExFPAdapter;
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

    private LinkedList list1 = null;
    private LinkedList list2 = null;
    private LinkedList list3 = null;

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
        pagerAdapter.setTitles(new String[]{"还款中", "已结清", "已退出"});

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

            list1 = investMap.get("1");
            list2 = investMap.get("2");
            list3 = investMap.get("3");

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

        private LinkedList mData = null;

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
//            mPullRefreshListView.setOnRefreshListener(this);

            return mView;
        }

        protected void initListener()
        {
            actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    InvestListEntity.ItemEntity item = ( InvestListEntity.ItemEntity)mData.get(position-1);
                    String pid = item.id;
                    int type = item.type;

                    goToProductDetails(pid, type);
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
            initInvestList(mData);
            mPullRefreshListView.onRefreshComplete();
        }

        public void initInvestList(LinkedList data) {
            ((InvestListAdapter) mAdapter).setData(data);
            this.mData = data;
        }

        /**
         * 跳转到详情页
         */
        private void goToProductDetails(String id, int type)
        {
            Intent intent = new Intent(this.getActivity(), ProductDetailsActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("type", type);
            startActivity(intent);
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
