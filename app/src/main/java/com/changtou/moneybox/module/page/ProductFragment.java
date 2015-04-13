package com.changtou.moneybox.module.page;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.widget.DemoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private TabWidget mTabWidget;
    private String[] addresses = { "first", "second", "third" };
    private Button[] mBtnTabs = new Button[addresses.length];

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_product, null);
        mContext = this.getActivity();

        mTabWidget = (TabWidget) mView.findViewById(R.id.tabWidget1);
        mTabWidget.setStripEnabled(false);
        mBtnTabs[0] = new Button(mContext);
        mBtnTabs[0].setFocusable(true);
        mBtnTabs[0].setText(addresses[0]);
//        mBtnTabs[0].setTextColor(getResources().getColorStateList(R.color.button_bg_color_selector));
        mTabWidget.addView(mBtnTabs[0]);
        /*
         * Listener必须在mTabWidget.addView()之后再加入，用于覆盖默认的Listener，
         * mTabWidget.addView()中默认的Listener没有NullPointer检测。
         */
        mBtnTabs[0].setOnClickListener(mTabClickListener);
        mBtnTabs[1] = new Button(mContext);
        mBtnTabs[1].setFocusable(true);
        mBtnTabs[1].setText(addresses[1]);
//        mBtnTabs[1].setTextColor(getResources().getColorStateList(R.color.button_bg_color_selector));
        mTabWidget.addView(mBtnTabs[1]);
        mBtnTabs[1].setOnClickListener(mTabClickListener);
        mBtnTabs[2] = new Button(mContext);
        mBtnTabs[2].setFocusable(true);
        mBtnTabs[2].setText(addresses[2]);
//        mBtnTabs[2].setTextColor(getResources().getColorStateList(R.color.button_bg_color_selector));
        mTabWidget.addView(mBtnTabs[2]);
        mBtnTabs[2].setOnClickListener(mTabClickListener);

        mViewPager = (ViewPager) mView.findViewById(R.id.pager);
        mPagerAdapter = new MyPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
      //  mViewPager.setOnPageChangeListener(mPageChangeListener);

//        mTabWidget.setCurrentTab(0);

        return mView;
    }

    @Override
    protected void initLisener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //adapter=new DemoAdapter(mContext);
        //xList.setAdapter(adapter);
        //onInitList();
    }

    @Override
    public void onSuccess(String content, Object object, int reqType) {

    }

    @Override
    public void onFailure(Throwable error, String content, int reqType) {

    }

    private OnClickListener mTabClickListener = new OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if (v == mBtnTabs[0])
            {
                mViewPager.setCurrentItem(0);
            } else if (v == mBtnTabs[1])
            {
                mViewPager.setCurrentItem(1);
            } else if (v == mBtnTabs[2])
            {
                mViewPager.setCurrentItem(2);
            }
        }
    };

    private OnPageChangeListener mPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0)
        {
            Log.e("OnPageChangeListener", "in " + arg0);
            mTabWidget.setCurrentTab(arg0);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {

        }

        @Override
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
     *
     */
    public static class SubPage extends BaseFragment
    {
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
            TextView convertView = new TextView(getActivity());
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            convertView.setGravity(Gravity.CENTER);
            convertView.setTextSize(30);
            convertView.setTextColor(Color.BLACK);
            Bundle b = getArguments();
            convertView.setText("Pagedddd " + b.getInt("productType"));
            return convertView;
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
