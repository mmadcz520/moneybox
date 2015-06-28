package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 描述:产品页
 *     1. tab view
 *
 * @author zhoulongfei
 * @since 2015-3-26
 */
public class ProductFragment extends BaseFragment{

    private ViewPager mViewPager = null;

    private List<BaseFragment> mViewList = new ArrayList<>();

    private SlidingTabLayout mSlidingTabLayout;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mView = inflater.inflate(R.layout.product_fragment, container, false);

        mSlidingTabLayout = (SlidingTabLayout) mView.findViewById(R.id.sliding_tabs);
        Resources res = getResources();
        mSlidingTabLayout.setCustomTabView(R.layout.product_tabpage_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.ct_blue));

        mViewPager = (ViewPager) mView.findViewById(R.id.pager);
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(mPageChangeListener);

        return mView;
    }

    protected void initListener() {

    }

    protected void initData(Bundle savedInstanceState)
    {

        mZProgressHUD.show();

        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_TYPE,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_TYPE),
                mParams,
                mAct.getAsyncClient(), false);
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        if(reqType == HttpRequst.REQ_TYPE_PRODUCT_TYPE)
        {
            mZProgressHUD.cancel();

            try
            {
                JSONObject json = new JSONObject(content);
                JSONArray array = json.getJSONArray("productType");

                int len = array.length();
                String[] titles = new String[len];
                for(int i = 0; i < array.length();i++)
                {
                    titles[i] = array.getString(i);
                    mViewList.add(SubPage.create(i));
                }

                ExFPAdapter pagerAdapter = new ExFPAdapter(getChildFragmentManager(), mViewList);
                pagerAdapter.setTitles(titles);
                mViewPager.setAdapter(pagerAdapter);
                mViewPager.setOffscreenPageLimit(mViewList.size());
                mSlidingTabLayout.setViewPager(mViewPager);

            }
            catch (Exception e)
            {

            }

        }
    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        mZProgressHUD.cancel();
    }

    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener()
    {
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
    public static class SubPage extends BaseFragment implements PullToRefreshBase.OnRefreshListener<ListView>
    {
        private ProductListAdapter mAdapter = null;
        private Context mContext = null;

        private PullToRefreshListView mPullRefreshListView;

        private ListView actualListView;
        private MultiStateView mMultiStateView;

        private int mProductType = 0;
        private String mSelectedProdId   = null;

        private LinkedList mData = null;

        public static SubPage create(int type)
        {
            SubPage f = new SubPage();
            Bundle b = new Bundle();
            b.putInt("productType", type);
            f.setArguments(b);
            return f;
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
            actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    ProductEntity.ItemEntity item = (ProductEntity.ItemEntity)mData.get(position-1);
                    mSelectedProdId = item.id;
                    goToProductDetails(mSelectedProdId);
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

                mProductType = getArguments().getInt("productType");
                int len = entity.getProductTypeList().size();
                if(mProductType > (len -1)) return;
                mData = (LinkedList)entity.getProductTypeList().get(mProductType);
                mAdapter.setData(mData);

                if(mData.size() == 0)
                {
                    mMultiStateView.setViewState(MultiStateView.ViewState.EMPTY);
                }

                mPullRefreshListView.onRefreshComplete();
            }
        }

        public void onFailure(Throwable error, String content, int reqType)
        {
            mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
        }

        /**
         * 刷新函数
         * @param refreshView 刷新控件
         */
        public void onRefresh(PullToRefreshBase refreshView)
        {
            sendRequest(HttpRequst.REQ_TYPE_PRODUCT_LIST,
                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_LIST),
                    mParams,
                    mAct.getAsyncClient(), true);
        }

        /**
         * 跳转到详情页
         */
        private void goToProductDetails(String id)
        {
            Intent intent = new Intent(this.getActivity(), ProductDetailsActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("type", mProductType);
            startActivity(intent);
        }
    }
}
