package com.changtou.moneybox.module.widget;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.changtou.moneybox.common.activity.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2015/3/23.
 */
public class ExFPAdapter extends FragmentPagerAdapter{

    private List<BaseFragment> mBaseFragmentList;

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

        Log.e("get item", "+++++++++++++++ == "+position);
        return mBaseFragmentList.get(position);
    }

    /**
     * @see FragmentPagerAdapter#getCount()
     * @return
     */
    public int getCount() {
        return mBaseFragmentList.size();
    }
}
