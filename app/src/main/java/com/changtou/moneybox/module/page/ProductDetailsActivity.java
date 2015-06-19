package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.module.adapter.ProductDetailsAdapter;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.OnItemSelectListener;
import com.changtou.moneybox.module.widget.PullToNextAdapter;
import com.changtou.moneybox.module.widget.PullToNextLayout;
import com.changtou.moneybox.module.widget.RoundProgressBar;

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
    public ProductDetailsMorePage mAgreementPage = null;

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
        mAgreementPage = new ProductDetailsMorePage();
        mPageList.add(mDetailsPage);
        mPageList.add(mAgreementPage);
        pullToNextLayout.setAdapter(new PullToNextAdapter(getSupportFragmentManager(), mPageList));

        pullToNextLayout.setOnItemSelectListener(new OnItemSelectListener()
        {
            public void onSelectItem(int position, View view)
            {
                mAgreementPage.initScroll();
            }
        });
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
            HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_DETAILS),
            mParams,
            getAsyncClient(), false);
    }

    @Override
    protected int setPageType() {
        return 0;
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
            mDetailsPage.getInvestPercent().showPercentWithAnimation(50);
            mDetailsPage.getIcomeText().setText("14");
            mDetailsPage.getTagTextView().setText("%");
            mDetailsPage.getInvestNum().setText("25.5万/100万");
            mDetailsPage.getTimeLimit().setText("三个月");
        }
    }

    /**
     * 描述: 产品详情页
     * @author zhoulongfei
     */
    public static class  DetailsPage extends Fragment
    {
        private ListView mListView = null;

        private RoundProgressBar mProgressBar = null;

        private TextView  mTimeLimit = null;
        private TextView  mIcomeText = null;
        private TextView mTagTextView = null;
        private CountView mInvestPercent = null;
        private TextView mInvestNum = null;      //投资进度数值


        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.product_details_sub1, container, false);
            mProgressBar = (RoundProgressBar)v.findViewById(R.id.homepage_invest_progress);
            mListView = (ListView)v.findViewById(R.id.lv_details);

            mTimeLimit = (TextView)v.findViewById(R.id.homepage_timelimit);
            mIcomeText = (TextView)v.findViewById(R.id.homepage_income_percent);
            mTagTextView = (TextView)v.findViewById(R.id.homepage_income_percenttag);
            mInvestPercent = (CountView)v.findViewById(R.id.invest_progress_percent);
            mInvestNum = (TextView)v.findViewById(R.id.invest_progress_num);

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

        public TextView getIcomeText()
        {
            return mIcomeText;
        }

        public TextView getTagTextView()
        {
            return mTagTextView;
        }

        public CountView getInvestPercent()
        {
            return mInvestPercent;
        }

        public TextView getInvestNum()
        {
            return mInvestNum;
        }

        public TextView getTimeLimit()
        {
            return mTimeLimit;
        }
    }
}
