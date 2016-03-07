package com.changtou.moneybox.module.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.adapter.ProductListAdapter;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.service.NetStateListener;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.ExImageSwitcher;
import com.changtou.moneybox.module.widget.RoundProgressBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.tencent.weibo.sdk.android.network.HttpReq;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 描述： home 页面
 *
 * @author zhoulongfei
 * @since 2015-3-20
 */
public class HomeFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener, NetStateListener
{
    private Context mContext = null;
//    private ACache mCache = null;

    private ExImageSwitcher  mBannerSwitcher = null;
    private RoundProgressBar mInvestProgress = null;
    private CountView mInvestSum = null;
    private CountView mMakeMoney = null;
    private Button    mInvestBtn = null;
    private TextView  mIcomeText = null;
    private TextView mTagTextView = null;
    private TextView  mTimeLimit = null;
    private TextView  mProductTitle = null;
    private CountView mInvestPercent = null;
    private TextView mInvestNum = null;      //投资进度数值

    private SharedPreferencesHelper sph = null;

    private ProductListAdapter mAdapter = null;

    private PullToRefreshScrollView mPullToRefreshScrollView = null;

    private String productId = "0";
    private double jd = 0;
    private String maturity = "";
    private String interest = "";
    private String projectname = "";
    private String syje = "";
    private String amount = "";
    private String minamount = "";

    private String syje_money="";

    private String xg;

    private String allInvest = "";
    private String leiji = "";

    private TextView mQtjeTextView = null;

    private TextView mLijinInText = null;

    private FrameLayout mlijinlayout = null;

    private String[] mDetails = new String[6];

    private String mProType = "";

    private boolean isHasProd = false;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mView = inflater.inflate(R.layout.home_fragment, null);

        mBannerSwitcher = (ExImageSwitcher)mView.findViewById(R.id.homepage_banner);
        mInvestProgress = (RoundProgressBar)mView.findViewById(R.id.homepage_invest_progress);
        mInvestSum = (CountView)mView.findViewById(R.id.homepage_investsum);
        mMakeMoney = (CountView)mView.findViewById(R.id.homepage_makemoney);
        mInvestBtn = (Button)mView.findViewById(R.id.homepage_btn_invest);
        mPullToRefreshScrollView = (PullToRefreshScrollView)mView.findViewById(R.id.pull_refresh_scrollview);

        mIcomeText = (TextView)mView.findViewById(R.id.homepage_income_percent);
        mTagTextView = (TextView)mView.findViewById(R.id.homepage_income_percenttag);
        mTimeLimit = (TextView)mView.findViewById(R.id.homepage_timelimit);
        mProductTitle = (TextView)mView.findViewById(R.id.homepage_title_text);
        mInvestPercent = (CountView)mView.findViewById(R.id.invest_progress_percent);
        mLijinInText = (TextView)mView.findViewById(R.id.homepage_invest_lijininterest);
        mInvestNum = (TextView)mView.findViewById(R.id.invest_progress_num);

        mlijinlayout = (FrameLayout)mView.findViewById(R.id.homepage_invest_lijinlayout);

        mQtjeTextView = (TextView)mView.findViewById(R.id.invest_progress_qtje);

        mContext = this.getActivity();
        mAdapter = new ProductListAdapter(mContext);

        mPullToRefreshScrollView.setOnRefreshListener(this);

        mInvestProgress.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(!isHasProd)
                {
                    Toast.makeText(HomeFragment.this.getActivity(),"暂无可投产品,敬请期待!", Toast.LENGTH_LONG).show();
                    return;
                }

                //
                if(BaseApplication.getInstance().isUserLogin())
                {
                    Intent intent = new Intent(HomeFragment.this.getActivity(), ProductDetailsActivity.class);
                    intent.putExtra("id", productId);
                    if(mProType.equals("长投宝"))
                    {
                        intent.putExtra("type", 0);
                    }
                    else if(mProType.equals("站内转让"))
                    {
                        intent.putExtra("type", 1);
                    }
                    else if(mProType.equals("普通投资"))
                    {
                        intent.putExtra("type", 2);
                    }
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(HomeFragment.this.getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

        return mView;
    }

    protected void initListener()
    {
        setOnClickListener(R.id.homepage_btn_invest);
    }

    /**
     * @param savedInstanceState 状态信息
     */
    protected void initData(Bundle savedInstanceState) {

        sph = SharedPreferencesHelper.getInstance(this.getActivity());
        BaseApplication.getInstance().setNetStateListener(this);

        initParam();
        requestHomePage();
    }

    /**
     * @param content 返回值
     * @param object  返回的转化对象
     * @param reqType 请求的唯一识别
     */
    public void onSuccess(String content, Object object, HttpReq reqType)
    {
        if (reqType == HttpRequst.REQ_TYPE_PRODUCT_HOME)
        {
            try
            {
                JSONArray array = new JSONArray(content);

                JSONObject obj1 = array.getJSONObject(0);
                JSONArray appRecomm = obj1.getJSONArray("AppRecomm");

                Log.e("CT_MONEY", appRecomm.toString());

                if(appRecomm.length() > 0) {

                    isHasProd = true;
                    JSONObject appObject = appRecomm.getJSONObject(0);

                    productId = appObject.getString("id");
                    jd = appObject.getDouble("jd") * 100;
                    projectname = appObject.getString("projectname");
                    maturity = appObject.getString("maturity");
                    interest = appObject.getString("interest");
                    syje = appObject.getString("syje");
                    amount = appObject.getString("amount");
                    minamount = appObject.getString("minamount");
                    mProType = appObject.getString("type");

                    syje_money = appObject.optString("syje_money");
                    xg = appObject.optString("xg");

                    mInvestProgress.setProgress((int) jd);
                    double lijininterest = appObject.optDouble("lijininterest");
                    mLijinInText.setText(lijininterest + "%礼金变现");

                    if(lijininterest > 0)
                    {
                        mlijinlayout.setVisibility(View.VISIBLE);
                    }

//                    mInvestSum.setText(allInvest);
//                    mMakeMoney.setText(leiji);

                    Log.e("CT_MONEY", interest);

                    mProductTitle.setText(projectname);
                    mIcomeText.setText(interest);
                    mTagTextView.setText("%");
                    mTimeLimit.setText(maturity);
                    mInvestPercent.showPercentWithAnimation((float) jd);
                    mInvestNum.setText("￥" + syje + "/" + amount);
                    mQtjeTextView.setText(minamount + "起投 | " + "融资金额" + amount);
                    mInvestBtn.setEnabled(true);
                    mInvestBtn.setText("立即投资");

                    mDetails[0] = projectname;
                    mDetails[1] = maturity;
                    mDetails[2] = interest + "%";
                    mDetails[3] = syje_money;
                    mDetails[4] = minamount;
                    mDetails[5] = xg;
                }
                else
                {
                    mInvestBtn.setEnabled(false);
                    mInvestBtn.setText("敬请期待");
                    mQtjeTextView.setText("敬请期待");
                    mProductTitle.setText("敬请期待");

                    isHasProd = false;
                }

                JSONObject obj2 = array.getJSONObject(3);
                allInvest = obj2.getString("leiji");

                JSONObject obj3 = array.getJSONObject(2);
                leiji = obj3.getString("beifujin");

                mMakeMoney.setText(allInvest);
                mInvestSum.setText(leiji);

                mPullToRefreshScrollView.onRefreshComplete();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(reqType == HttpRequst.REQ_TYPE_PRODUCT_BANNER)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                JSONArray array = data.getJSONArray("imglist");

                int len = array.length();
                String[] imgs = new String[len];
                String[] urls = new String[len];

                for(int i = 0; i < len; i++)
                {
                    JSONObject j = array.getJSONObject(i);
                    String id = j.getString("id");
                    String url = j.getString("url");
                    String title = j.getString("title");

                    imgs[i] = j.getString("img");
                    urls[i] = url;
                }

                mBannerSwitcher.setImage(imgs, urls);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param error   错误
     * @param content 返回值
     * @param reqType 请求的唯一识别
     */
    public void onFailure(Throwable error, String content, int reqType)
    {
        Toast.makeText(this.getActivity(), "首页数据加载错误", Toast.LENGTH_LONG).show();
    }

    public void onRefresh(PullToRefreshBase refreshView) {

        requestHomePage();
    }

    public void treatClickEvent(int id)
    {
        //
        if(BaseApplication.getInstance().isUserLogin())
        {
            goToProductDetails(productId);
        }
        else
        {
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivityForResult(intent, 0);
        }
    }

    /**
     * 跳转到详情页
     */
    private void goToProductDetails(String id)
    {
//        Intent intent = new Intent(this.getActivity(), ProductDetailsActivity.class);
//        intent.putExtra("id", id);
//        intent.putExtra("type", 0);
//        startActivity(intent);

        Intent intent = new Intent(this.getActivity(), ConfirmActivity.class);
        intent.putExtra("details",mDetails);
        intent.putExtra("id",id);
        if(mProType.equals("长投宝"))
        {
            intent.putExtra("type", 0);
        }
        else if(mProType.equals("站内转让"))
        {
            intent.putExtra("type", 1);
        }
        else if(mProType.equals("普通投资"))
        {
            intent.putExtra("type", 2);
        }

        startActivity(intent);
    }

    public void requestHomePage()
    {
        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_HOME,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_HOME),
                mParams,
                mAct.getAsyncClient(), false);

        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_BANNER,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_BANNER),
                mParams,
                mAct.getAsyncClient(), false);
    }

    @Override
    public void connectNet() {
        requestHomePage();
    }

    @Override
    public void disconnectNet() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
