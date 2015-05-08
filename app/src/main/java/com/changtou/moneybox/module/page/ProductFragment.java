package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

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
import com.changtou.moneybox.module.widget.SlidingTabLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述:产品页
 *     1. tab view
 *
 * @author zhoulongfei
 * @since 2015-3-26
 */
public class ProductFragment extends BaseFragment{

    private ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.product_fragment, container, false);

        mSlidingTabLayout = (SlidingTabLayout) mView.findViewById(R.id.sliding_tabs);
        Resources res = getResources();
        mSlidingTabLayout.setCustomTabView(R.layout.product_tabpage_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.tab_indicator_color));

        //根据产品分类列表初始化界面
        List<BaseFragment> viewList = new ArrayList<>();
        viewList.add(new SubPage());
        viewList.add(new SubPage());
        viewList.add(new SubPage());
        viewList.add(new SubPage());

        mViewPager = (ViewPager) mView.findViewById(R.id.pager);
        ExFPAdapter pagerAdapter = new ExFPAdapter(getChildFragmentManager(), viewList);
        pagerAdapter.setTitles(new String[]{"长投宝", "ZAMA宝", "精选债权", "转让专区"});
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(mPageChangeListener);
        mViewPager.setOffscreenPageLimit(viewList.size());
        mSlidingTabLayout.setViewPager(mViewPager);

        return mView;
    }

    protected void initLisener() {

    }

    protected void initData(Bundle savedInstanceState) {

    }

    public void onSuccess(String content, Object object, int reqType) {

    }

    public void onFailure(Throwable error, String content, int reqType) {

    }

    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0)
        {
//            mSegmentControl.setSelectIndex(arg0);
        }

        public void onPageScrolled(int arg0, float arg1, int arg2)
        {

        }

        public void onPageScrollStateChanged(int arg0)
        {

        }
    };

    /**
     * 描述: 产品分类子页面
     * @author zhoulongfei
     */
    public class SubPage extends BaseFragment implements PullToRefreshBase.OnRefreshListener
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
            if (reqType == HttpRequst.REQ_TYPE_PRODUCT_HOME)
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
            sendRequest(HttpRequst.REQ_TYPE_PRODUCT_HOME,
                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_HOME),
                    mParams,
                    mAct.getAsyncClient(), false);
        }
    }
}
