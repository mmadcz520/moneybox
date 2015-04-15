package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;

/**
 * Created by Administrator on 2015/3/26.
 */
public class RichesFragment extends BaseFragment{


    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_riches, null);
        return view;
    }

    @Override
    protected void initLisener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onSuccess(String content, Object object, int reqType) {

    }

    @Override
    public void onFailure(Throwable error, String content, int reqType) {

    }
}
