package com.changtou.moneybox.module.widget;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.changtou.moneybox.common.activity.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2015/3/23.
 */
public class ExFPAdapter extends FragmentPagerAdapter{

    private List<BaseFragment> mBaseFragmentList;

    private String[] mTitles = null;

    public ExFPAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.mBaseFragmentList = list;
    }

    /**
     * @see FragmentPagerAdapter#getItem(int)
     * @param position
     * @return
     */
    public Fragment getItem(int position) {
        return mBaseFragmentList.get(position);
    }

    /**
     * @see FragmentPagerAdapter#getCount()
     * @return
     */
    public int getCount() {
        return mBaseFragmentList.size();
    }

    public CharSequence getPageTitle(int position)
    {
        if(mTitles != null && position < mTitles.length)
        {
            return mTitles[position];
        }
        return "page 1";
    }

    public void setTitles(String[] titles)
    {
        this.mTitles = titles;
    }
}
