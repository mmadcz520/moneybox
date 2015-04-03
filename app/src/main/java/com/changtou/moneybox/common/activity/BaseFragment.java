package com.changtou.moneybox.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.changtou.moneybox.common.http.base.BaseHttpClient;
import com.changtou.moneybox.common.http.base.HttpCallback;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.DeviceInfo;

public abstract class BaseFragment extends Fragment implements
        HttpCallback {
    protected boolean bQuit;

    //httpClient
    protected BaseActivity mAct;

    public boolean bBlank;

    // 请求参数
    protected RequestParams mParams = null;

    public FragmentClick click;

    /**
     * @see BaseFragment#onAttach(Activity)
     * @param activity
     */
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mParams == null)
            mParams = new RequestParams();
        if (activity instanceof BaseActivity)
            mAct = (BaseActivity) activity;
        click = new FragmentClick();
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return initView(inflater,container,
                savedInstanceState);
    }



    /**
     * 初始化布局和控件
     *
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState);

    /**
     * 初始化监听
     */
    protected abstract void initLisener();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (bQuit)
            // ((BaseActivity)getActivity()).quit();
            bBlank = true;
        initData(savedInstanceState);
        initLisener();
    }

    public void entry() {

    }

    public void initParam() {
        this.mParams.clear();
    }


    //设置事件监听
    public void setOnClickListener(int id) {
        if (click == null)
            click = new FragmentClick();
        this.getView().findViewById(id).setOnClickListener(click);
    }

    private class FragmentClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            treatClickEvent(id);
            treatClickEvent(v);
        }
    }

    /**
     * 事件返回
     * @param id
     */
    public void treatClickEvent(int id) {

    }

    /**
     * 事件返回
     * @param v 视图
     */
    public void treatClickEvent(View v) {

    }

    /**
     *
     * @param reqType
     * @param url
     * @param params
     * @param baseHttpClient
     * @param showDialog
     */
    public void sendRequest(int reqType, String url, RequestParams params,
                            BaseHttpClient baseHttpClient, boolean showDialog) {
        if (DeviceInfo.isNetWorkEnable(this.getActivity())) {
            if(baseHttpClient!=null){
                if (reqType > 10000) {
                    baseHttpClient.post(reqType, this.getActivity(), url, params, this);
                } else {
                    baseHttpClient.get(reqType,this.getActivity(), url, params, this);
                }
            }
        }

    }

}
