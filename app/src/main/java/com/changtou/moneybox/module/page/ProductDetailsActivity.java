package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.adapter.ProductDetailsAdapter;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.MultiStateView;
import com.changtou.moneybox.module.widget.PullToNextAdapter;
import com.changtou.moneybox.module.widget.PullToNextLayout;
import com.changtou.moneybox.module.widget.RoundProgressBar;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * 描述： 产品详情页
 *
 * @author zhoulongfei
 * @since 2015-4-13
 */
public class ProductDetailsActivity extends CTBaseActivity
{
    private ProductDetailsAdapter mAdapter = null;
    private ListView mProdList = null;

    //上下拉动布局
    public PullToNextLayout pullToNextLayout;
    private ArrayList<Fragment> mPageList = null;

    public DetailsPage mDetailsPage = null;
    public AgreementPage mAgreementPage = null;

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

        pullToNextLayout = (PullToNextLayout) findViewById(R.id.pullToNextLayout);

        mPageList = new ArrayList<>();
        mDetailsPage = new DetailsPage();
        mAgreementPage = new AgreementPage();
        mPageList.add(mDetailsPage);
        mPageList.add(mAgreementPage);
        pullToNextLayout.setAdapter(new PullToNextAdapter(getSupportFragmentManager(), mPageList));
    }


    /**
     * 网络数据请求， 初始化数据
     *
     */
    protected void initData()
    {
        mProdList = mDetailsPage.getListView();
        ViewStub viewStub = new ViewStub(this);
        mProdList.addHeaderView(viewStub);
        mAdapter = new ProductDetailsAdapter(this);
        mProdList.setAdapter(mAdapter);

        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_DETAILS,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_HOME),
                mParams,
                getAsyncClient(), false);
    }

    protected void onResume()
    {
        super.onResume();
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
            mDetailsPage.getProgressBar().setProgress(50);
        }
    }

    /**
     * 描述: 产品详情页
     * @author zhoulongfei
     */
    public class DetailsPage extends Fragment
    {
        private ListView mListView = null;

        private RoundProgressBar mProgressBar = null;

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.product_details_sub1, container, false);
            mProgressBar = (RoundProgressBar)v.findViewById(R.id.details_progressbar);
            mListView = (ListView)v.findViewById(R.id.lv_details);
            return v;
        }

        public ListView getListView()
        {
            return mListView;
        }

        public RoundProgressBar getProgressBar()
        {
            return mProgressBar;
        }
    }

    /**
     * 描述:产品协议页
     * @author zhoulongfei
     */
    public class AgreementPage extends Fragment
    {
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.product_details_sub2, container, false);

            ViewPager viewPage = (ViewPager)v.findViewById(R.id.product_introduction);
            ArrayList<BaseFragment> pageList = new ArrayList<>();
            pageList.add(new SubPage());
            ExFPAdapter pagerAdapter = new ExFPAdapter(this.getChildFragmentManager(), pageList);
            viewPage.setAdapter(pagerAdapter);

            return v;
        }

        /**
         * 产品协议子页面
         */
        public class SubPage extends BaseFragment
        {
            protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
            {
                View mView = inflater.inflate(R.layout.product_details_investlist, container, false);
                return mView;
            }

            protected void initLisener()
            {
            }

            protected void initData(Bundle savedInstanceState)
            {
            }

            public void onSuccess(String content, Object object, int reqType)
            {
            }

            public void onFailure(Throwable error, String content, int reqType)
            {
            }
        }
    }
}
