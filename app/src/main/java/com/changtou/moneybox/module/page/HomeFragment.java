package com.changtou.moneybox.module.page;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.logger.Logger;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.adapter.ProductListAdapter;
import com.changtou.moneybox.module.entity.PromotionEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.service.NetReceiver;
import com.changtou.moneybox.module.service.NetStateListener;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.ExEditView;
import com.changtou.moneybox.module.widget.ExImageSwitcher;
import com.changtou.moneybox.module.widget.RoundProgressBar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

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

    private String[] mImgs = {"http://www.changtounet.com/manage/news/uploadimages/50758017-9051-4838-ac09-f764bb5bfa4b.png.png",
            "http://www.changtounet.com/manage/news/uploadimages/50758017-9051-4838-ac09-f764bb5bfa4b.png.png" };

    private String productId = "0";
    private double jd = 0;
    private String maturity = "";
    private String interest = "";
    private String projectname = "";
    private String syje = "";
    private String amount = "";
    private String minamount = "";

    private double allInvest = 0;
    private double leiji = 0;

    private TextView mQtjeTextView = null;

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
        mInvestNum = (TextView)mView.findViewById(R.id.invest_progress_num);

        mQtjeTextView = (TextView)mView.findViewById(R.id.invest_progress_qtje);

        mContext = this.getActivity();
        mAdapter = new ProductListAdapter(mContext);

        mPullToRefreshScrollView.setOnRefreshListener(this);

        sph = SharedPreferencesHelper.getInstance(this.getActivity());

        BaseApplication.getInstance().setNetStateListener(this);

        mBannerSwitcher.setImage(mImgs);

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
        initParam();
        requestHomePage();
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
            Logger.json(content);

            try
            {
                JSONArray array = new JSONArray(content);

                JSONObject obj1 = array.getJSONObject(0);
                JSONArray appRecomm = obj1.getJSONArray("AppRecomm");
                JSONObject appObject = appRecomm.getJSONObject(0);

                productId = appObject.getString("id");
                jd = appObject.getDouble("jd") * 100;
                projectname = appObject.getString("projectname");
                maturity = appObject.getString("maturity");
                interest = appObject.getString("interest");
                syje = appObject.getString("syje");
                amount = appObject.getString("amount");
                minamount = appObject.getString("minamount");

                JSONObject obj2 = array.getJSONObject(1);
                allInvest = obj2.getDouble("allInvest");

                JSONObject obj3 = array.getJSONObject(3);
                leiji = obj3.getDouble("leiji");
            }
            catch (Exception e)
            {

            }


            mInvestProgress.setProgress((int)jd);

            mInvestSum.showNumberWithAnimation((float) allInvest);
            mMakeMoney.showNumberWithAnimation((float)leiji);

            mProductTitle.setText(projectname);
            mIcomeText.setText(interest);
            mTagTextView.setText("%");
            mTimeLimit.setText(maturity);
            mInvestPercent.showPercentWithAnimation((float)jd);
            mInvestNum.setText(syje + "/" + amount);
            mQtjeTextView.setText(minamount + "起投 | " + "每人限购100万元");
            mInvestBtn.setEnabled(true);

            mPullToRefreshScrollView.onRefreshComplete();
        }

        if(reqType == HttpRequst.REQ_TYPE_PRODUCT_BANNER)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                JSONArray array = data.getJSONArray("imglist");

                int len = array.length();
                String[] imgs = new String[len];

                for(int i = 0; i < len; i++)
                {
                    JSONObject j = array.getJSONObject(i);
                    String id = j.getString("id");
                    String url = j.getString("url");
                    String title = j.getString("title");

                    Logger.d(url);

                    imgs[i] = j.getString("img");
                }

//                mBannerSwitcher.setImage(imgs);
            }
            catch (Exception e)
            {

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
        Toast.makeText(this.getActivity(), "网络连接超时", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this.getActivity(), ProductDetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", 0);
        startActivity(intent);
    }

    public void requestHomePage()
    {
//        mZProgressHUD.show();

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
