package com.changtou.moneybox.module.page;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.AsyncImageLoader;
import com.changtou.moneybox.module.adapter.ProductListAdapter;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.entity.PromotionEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.ExImageSwitcher;
import com.changtou.moneybox.module.widget.RoundProgressBar;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

/**
 * 描述： home 页面
 *
 * @author zhoulongfei
 * @since 2015-3-20
 */
public class HomeFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener
{
    private Context mContext = null;
    private ACache mCache = null;                    //app 缓存类

    private ExImageSwitcher  mBannerSwitcher = null;
    private RoundProgressBar mInvestProgress = null;
    private CountView mInvestSum = null;
    private CountView mMakeMoney = null;
    private Button    mInvestBtn = null;
    private TextView  mIcomeText = null;
    private TextView  mTimeLimit = null;
    private TextView  mProductTitle = null;
    private CountView mInvestPercent = null;

    private ProductListAdapter mAdapter = null;

    private PullToRefreshScrollView mPullToRefreshScrollView = null;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mView = inflater.inflate(R.layout.home_fragment, null);

        mBannerSwitcher = (ExImageSwitcher)mView.findViewById(R.id.homepage_banner);
        mInvestProgress = (RoundProgressBar)mView.findViewById(R.id.homepage_invest_progress);
        mInvestSum = (CountView)mView.findViewById(R.id.homepage_investsum);
        mMakeMoney = (CountView)mView.findViewById(R.id.homepage_makemoney);
        mInvestBtn = (Button)mView.findViewById(R.id.homepage_btn_invest);
        mPullToRefreshScrollView = (PullToRefreshScrollView)mView.findViewById(R.id.pull_refresh_scrollview);

        mIcomeText = (TextView)mView.findViewById(R.id.homepage_income_percentage);
        mTimeLimit = (TextView)mView.findViewById(R.id.homepage_timelimit);
        mProductTitle = (TextView)mView.findViewById(R.id.homepage_title_text);
        mInvestPercent = (CountView)mView.findViewById(R.id.invest_progress_percent);

        mContext = this.getActivity();
        mAdapter = new ProductListAdapter(mContext);

        mPullToRefreshScrollView.setOnRefreshListener(this);

        return mView;
    }

    protected void initLisener()
    {

    }

    /**
     * @param savedInstanceState 状态信息
     */
    protected void initData(Bundle savedInstanceState)
    {
        initParam();

        mParams.put("action", "app_article_list");
        mParams.put("forum_id", "57,287,59,61");// 1表示资讯 2表示攻略
        mParams.put("page", 1 + "");
        mParams.put("per_page", 10 + "");// 1表示资讯 2表示攻略

        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_HOME,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_HOME),
                mParams,
                mAct.getAsyncClient(), false);

    }

    /**
     * @param content 返回值
     * @param object  返回的转化对象
     * @param reqType 请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType)
    {
        if (reqType == HttpRequst.REQ_TYPE_PRODUCT_HOME)
        {
            PromotionEntity entity = (PromotionEntity)object;
            mBannerSwitcher.setImage(new String[]{"http://appt.changtounet.com/Img/index002_02.png", "http://appt.changtounet.com/Img/home_default_banner.png"});
            mInvestProgress.setProgress(60);

            mInvestSum.showNumberWithAnimation(251815);
            mMakeMoney.showNumberWithAnimation(881100);

            mProductTitle.setText("今日优选[上投宝]上手易第256期");
            mIcomeText.setText("14%");
            mTimeLimit.setText("三个月");
            mInvestPercent.showNumberWithAnimation(60);

            mPullToRefreshScrollView.onRefreshComplete();
        }
    }

    /**
     * @param error   错误
     * @param content 返回值
     * @param reqType 请求的唯一识别
     */
    public void onFailure(Throwable error, String content, int reqType)
    {

    }

    public void onRefresh(PullToRefreshBase refreshView) {

        mParams.put("action", "app_article_list");
        mParams.put("forum_id", "57,287,59,61");// 1表示资讯 2表示攻略
        mParams.put("page", 1 + "");
        mParams.put("per_page", 10 + "");// 1表示资讯 2表示攻略

        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_HOME,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_HOME),
                mParams,
                mAct.getAsyncClient(), false);

    }
}
