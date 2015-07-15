package com.changtou.moneybox.module.page;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.utils.AppUtil;
import com.changtou.moneybox.module.CTMoneyApplication;
import com.changtou.moneybox.module.adapter.AuditAdapter;
import com.changtou.moneybox.module.adapter.ProductContractAdapter;
import com.changtou.moneybox.module.adapter.ProductInvestorAdapter;
import com.changtou.moneybox.module.entity.ProductContractEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.CustomViewPager;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.LinkedList;


public class ProductDetailsMorePage extends Fragment
{
    private SlidingTabLayout mSlidingTabLayout;

    private InvestorPage mInvestorPage;
    private ContractPage mContractPage;
    private ContractCTBPage mContractCTBPage;

    public static ProductDetailsMorePage create(int type)
    {
        ProductDetailsMorePage page = new ProductDetailsMorePage();
        Bundle b = new Bundle();
        b.putInt("productType", type);
        page.setArguments(b);
        return page;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.product_details_sub2, container, false);
        mSlidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.product_details_sliding_tabs);
        Resources res = getResources();
        mSlidingTabLayout.setCustomTabView(R.layout.product_tabpage_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.ct_blue));

        final ViewPager viewPage = (ViewPager)v.findViewById(R.id.product_introduction);
        ArrayList<BaseFragment> pageList = new ArrayList<>();

        ExFPAdapter pagerAdapter = new ExFPAdapter(this.getChildFragmentManager(), pageList);
        String[] titles = {"项目详情", "风险控制", "投资列表"};

        int type = getArguments().getInt("productType",0);
        if(type == 0)
        {
            viewPage.setOffscreenPageLimit(2);
            mSlidingTabLayout.setTabCount(2);
            titles = new String[]{"项目详情", "投资列表"};

            mContractCTBPage = new ContractCTBPage();
            mInvestorPage = new InvestorPage();
            pageList.add(mContractCTBPage);
            pageList.add(mInvestorPage);
        }
        else
        {
            viewPage.setOffscreenPageLimit(3);
            mSlidingTabLayout.setTabCount(3);

            mInvestorPage = new InvestorPage();
            mContractPage = new ContractPage();
            pageList.add(mContractPage);
            pageList.add(new RiskControlPage());
            pageList.add(mInvestorPage);
        }

        pagerAdapter.setTitles(titles);
        viewPage.setAdapter(pagerAdapter);

        mSlidingTabLayout.setViewPager(viewPage);

        final ScrollView scrollView = (ScrollView)v.findViewById(R.id.details_scrollView2);
        scrollView.scrollTo(0, 0);
        mSlidingTabLayout.setFocusable(true);
        mSlidingTabLayout.setFocusableInTouchMode(true);
        mSlidingTabLayout.requestFocus();

        viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position)
            {
                scrollView.scrollTo(0, 0);
                scrollView.computeScroll();

            }

            public void onPageScrollStateChanged(int state)
            {

            }
        });

        return v;
    }

    /**
     * 初始化更多页控件
     */
    private void initPageMore(int type)
    {

    }

    public void initScroll()
    {
        mSlidingTabLayout.setFocusable(true);
        mSlidingTabLayout.setFocusableInTouchMode(true);
        mSlidingTabLayout.requestFocus();
    }

    public void onResume()
    {
        super.onResume();
    }

    /**
     * 初始化联系人列表数据
     */
    public void initTzListData(LinkedList tzList)
    {
        mInvestorPage.initTzList(tzList);
    }

    /**
     * 初始化合同
     * @param text
     */
    public void initContractText(String text)
    {
        mContractPage.setContractText(text);
    }

    /**
     *初始化长投宝合同列表
     *@param data
     */
    public void initContractCTB(String[] data)
    {
        mContractCTBPage.setContractList(data);
    }

    public void initContractImgList(LinkedList list)
    {
        mContractPage.setContractImgList(list);
    }

    /**
     * 投资人列表
     */
    public static class InvestorPage extends BaseFragment {
        private ProductInvestorAdapter mAdapter = null;
        private Context mContext = null;
        private ListView actualListView;

        protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View mView = inflater.inflate(R.layout.product_details_investlist, container, false);
            mContext = this.getActivity();
            actualListView = (ListView) mView.findViewById(R.id.investlist_listview);
//            setListViewHeightBasedOnChildren(actualListView);
            return mView;
        }

        protected void initListener() {

        }

        protected void initData(Bundle savedInstanceState) {
            mAdapter = new ProductInvestorAdapter(mContext);
            actualListView.setAdapter(mAdapter);

//            sendRequest(HttpRequst.REQ_TYPE_PRODUCT_INVESTOR,
//                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_INVESTOR),
//                    mParams,
//                    mAct.getAsyncClient(), false);
        }

        public void onSuccess(String content, Object object, int reqType) {
            if (reqType == HttpRequst.REQ_TYPE_PRODUCT_INVESTOR) {
//                mMultiStateView.setViewState(MultiStateView.ViewState.CONTENT);
//                InvestorEntity entity = (InvestorEntity) object;
//                mAdapter.setData(entity);
//                setListViewHeightBasedOnChildren(actualListView);
//
//                mPullRefreshListView.onRefreshComplete();
            }
        }

        public void onFailure(Throwable error, String content, int reqType) {

        }

        public void initTzList(LinkedList data)
        {
            mAdapter.setData(data);
        }
    }


    /**
     * 合同
     */
    public static class ContractPage extends BaseFragment
    {
        private Context mContext = null;

        private TextView mContentView = null;
        private ListView mAuditImgListView = null;

        private AuditAdapter mAdapter = null;

        protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View mView = inflater.inflate(R.layout.product_details_contract, container, false);
            mContext = this.getActivity();

            mContentView = (TextView)mView.findViewById(R.id.product_details_content);
            mAuditImgListView = (ListView)mView.findViewById(R.id.product_details_contract_list);

            return mView;
        }

        protected void initListener()
        {

        }

        protected void initData(Bundle savedInstanceState)
        {
            mAdapter = new AuditAdapter(mContext);
            mAuditImgListView.setAdapter(mAdapter);

//            sendRequest(HttpRequst.REQ_TYPE_PRODUCT_CONTRACT,
//                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_CONTRACT),
//                    mParams,
//                    mAct.getAsyncClient(), false);
        }

        public void onSuccess(String content, Object object, int reqType)
        {

        }

        public void onFailure(Throwable error, String content, int reqType)
        {

        }

         public void setContractText(String text)
         {
            mContentView.setText(Html.fromHtml(text));
         }

        public void setContractImgList(LinkedList list)
        {
            mAdapter.setData(list);
            setListViewHeightBasedOnChildren(mAuditImgListView);
        }
    }


    /**
     * 长投宝
     * 合同页
     */
    public static class ContractCTBPage extends BaseFragment
    {
        private Context mContext = null;
        private ProductContractAdapter mAdapter = null;

        protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View mView = inflater.inflate(R.layout.product_details_contract_ctb, container, false);
            mContext = this.getActivity();

            ListView listview = (ListView)mView.findViewById(R.id.product_details_contract_ctb_list);

            mAdapter = new ProductContractAdapter(mContext);
            listview.setAdapter(mAdapter);

            return mView;
        }

        protected void initListener()
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

        public void setContractList(String[] data)
        {
            mAdapter.setData(data);
        }
    }

    /**
     * 风险控制
     */
    public static class RiskControlPage extends BaseFragment
    {
        protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View mView = inflater.inflate(R.layout.product_details_riskcontrol, container, false);

            return mView;
        }

        protected void initListener()
        {

        }

        protected void initData(Bundle savedInstanceState)
        {

        }

        public void onSuccess(String content, Object object, int reqType)
        {
//            if (reqType == HttpRequst.REQ_TYPE_PRODUCT_CONTRACT)
//            {
//                ProductContractEntity entity = (ProductContractEntity)object;
//                mContentView.setText(entity.getContent());
//                mRepayingSrcView.setText(entity.getRepayingSrc());
//                mSafeguardView.setText(entity.getSafeguard());
//                mAdapter.setData(entity.getAuditImg());
//            }
        }

        public void onFailure(Throwable error, String content, int reqType)
        {

        }
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度

            totalHeight += (listItem.getMeasuredHeight() + AppUtil.dip2px(CTMoneyApplication.getInstance(),0));
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() -1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
}
