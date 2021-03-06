package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.CornerTabWidget;
import com.changtou.moneybox.module.widget.DemoAdapter;
import com.changtou.moneybox.module.widget.ProductListAdapter;

/**
 * 描述:产品页
 *     1. tab view
 *
 * @author zhoulongfei
 * @since 2015-3-26
 */
public class ProductFragment extends BaseFragment{

    private Context mContext = null;
    private ListView xList = null;
    private DemoAdapter adapter = null;

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private CornerTabWidget mTabWidget;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_product, null);
        mContext = this.getActivity();

        mTabWidget = (CornerTabWidget) mView.findViewById(R.id.tabWidget1);
        mTabWidget.setTabs(new String[]{"长投宝", "ZAMA宝", "精选债权", "转让专区"});
        mTabWidget.setTabListener(new CornerTabWidget.TabListener()
        {
            public void changePage(int pageId)
            {
                mViewPager.setCurrentItem(pageId);
            }
        });

        mViewPager = (ViewPager) mView.findViewById(R.id.pager);
        mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(mPageChangeListener);

//        mTabWidget.setCurrentTab(0);

        return mView;
    }

    protected void initLisener() {

    }

    protected void initData(Bundle savedInstanceState) {
        //adapter=new DemoAdapter(mContext);
        //xList.setAdapter(adapter);
        //onInitList();
    }

    public void onSuccess(String content, Object object, int reqType) {

    }

    public void onFailure(Throwable error, String content, int reqType) {

    }

    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0)
        {
            mTabWidget.setCurrentTab(arg0);
        }

        public void onPageScrolled(int arg0, float arg1, int arg2)
        {

        }

        public void onPageScrollStateChanged(int arg0)
        {

        }
    };
    private class MyPagerAdapter extends FragmentStatePagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        /**
         * @see FragmentPagerAdapter#getItem(int)
         * @param position 第几项
         * @return
         */
        public Fragment getItem( int position)
        {
            return SubPage.create(position);
        }
        /**
         * @see FragmentPagerAdapter#getCount()
         * @return
         */
        public int getCount()
        {
            return 3;
        }
    }

    /**
     * 描述: 产品分类子页面
     * @author zhoulongfei
     */
    public static class SubPage extends BaseFragment
    {
        private ProductListAdapter mAdapter = null;
        private Context mContext = null;
        private ListView mList = null;

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
            View mView = inflater.inflate(R.layout.tab_product, null);
            mList = (ListView) mView.findViewById(R.id.product_list);
            return mView;
        }

        protected void initLisener()
        {
            final Activity activity = this.getActivity();

            mList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                Intent intent = new Intent(activity, ProductDetailsActivity.class);
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    startActivity(intent);
                }
            });


            mList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                Intent intent = new Intent(activity, ProductDetailsActivity.class);
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    startActivity(intent);
                }

                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }

        protected void initData(Bundle savedInstanceState)
        {
            mContext = this.getActivity();
            mAdapter = new ProductListAdapter(mContext);
            mList.setAdapter(mAdapter);

            sendRequest(HttpRequst.REQ_TYPE_PRODUCT_HOME,
                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_HOME),
                    mParams,
                    mAct.getAsyncClient(), false);
        }

        public void onSuccess(String content, Object object, int reqType)
        {
            if (reqType == HttpRequst.REQ_TYPE_PRODUCT_HOME)
            {
                ProductEntity entity = (ProductEntity) object;
                mAdapter.setData(entity);
            }
        }

        public void onFailure(Throwable error, String content, int reqType)
        {

        }
    }
}
