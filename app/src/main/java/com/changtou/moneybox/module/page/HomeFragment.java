package com.changtou.moneybox.module.page;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExImageSwitcher;
import com.changtou.moneybox.module.widget.ExLoadingDialog;
import com.changtou.moneybox.module.widget.ExScrollView;
import com.changtou.moneybox.module.widget.ProductListAdapter;
import com.changtou.moneybox.module.widget.RoundProgressBar;

/**
 * 主页面Fragment
 *
 * @since 2015-3-20
 * @author zhoulongfei
 */
public class HomeFragment extends BaseFragment{

    private Context mContext = null;
    private ListView mList = null;
    private ProductListAdapter mAdapter = null;

    private ExLoadingDialog mExLoading = null;

    private ExScrollView mExScrollView = null;

    private SwipeRefreshLayout mSwipeRefreshLayout = null;

    /**
     * 初始化信息列表
     */
    public void onInitList(){
        initParam();
        mParams.put("action", "app_article_list");
        mParams.put("forum_id", "57,287,59,61");// 1表示资讯 2表示攻略
        mParams.put("page", 1 + "");
        mParams.put("per_page", 10 + "");// 1表示资讯 2表示攻略
        sendRequest(HttpRequst.REQ_METHOD_GET_DEMO_HOME,
                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_METHOD_GET_DEMO_HOME),
                    mParams,
                    mAct.getAsyncClient(), false);
    }

    /**
     *
     * @param inflater               1
     * @param container              2
     * @param savedInstanceState     3
     * @return view
     */
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_home, null);
        ExImageSwitcher exImage = (ExImageSwitcher) mView.findViewById(R.id.hp_ad);
        exImage.setImage(new int[] { R.drawable.homepage_kv1});

        mList = (ListView) mView.findViewById(R.id.homeListview);

        //loading
        mExLoading= new ExLoadingDialog(getActivity(), R.style.dialog_loading);
        mExLoading.show();

        //下拉刷新控件
        mSwipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swipe_container);

        mExScrollView = (ExScrollView) mView.findViewById(R.id.homepage_scrollview);
        mExScrollView.setRefreshAbleListener(new ExScrollView.RefreshAbleListener() {
            public void refreshAble() {
                if(!mSwipeRefreshLayout.isEnabled()) {
                    mSwipeRefreshLayout.setEnabled(true);
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }
        });

        return mView;
    }

    /**
     * 初始化控件监听
     */
    protected void initLisener() {

    }

    /**
     *
     * @param savedInstanceState 状态信息
     */
    protected void initData(Bundle savedInstanceState) {
        mContext=this.getActivity();
        mAdapter = new ProductListAdapter(mContext);
        mList.setAdapter(mAdapter);
        onInitList();
    }

    /**
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType) {

        mExLoading.cancel();

        if(reqType== HttpRequst.REQ_METHOD_GET_DEMO_HOME){
            ProductEntity entity=(ProductEntity)object;
            Log.i("CT", "onSuccess==>");
            mAdapter.setData(entity);
        }
    }

    /**
     *
     * @param error    错误
     * @param content   返回值
     * @param reqType  请求的唯一识别
     */
    public void onFailure(Throwable error, String content, int reqType) {

        Log.e("http failure ","***********************" + content);
    }

}
