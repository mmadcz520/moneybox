package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.module.adapter.ProductDetailsAdapter;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.OnItemSelectListener;
import com.changtou.moneybox.module.widget.PullToNextAdapter;
import com.changtou.moneybox.module.widget.PullToNextLayout;
import com.changtou.moneybox.module.widget.RoundProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 描述： 产品详情页
 *
 * @author zhoulongfei
 * @since 2015-4-13
 */
public class ProductDetailsActivity extends CTBaseActivity
{
    private ProductDetailsAdapter mAdapter = null;
    private ListView mProdList = null;

    //上下拉动布局
    public PullToNextLayout pullToNextLayout;
    private ArrayList<Fragment> mPageList = null;

    public DetailsPage mDetailsPage = null;
    public ProductDetailsMorePage mAgreementPage = null;

    public int    mProductType = 0;
    public String mProductId = "";

    public String[] mDetails = null;

    private Button mConfirmBtn = null;

    private String mState = null;

    /**
     * @see CTBaseActivity#initView(Bundle)
     */
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.product_details);

        pullToNextLayout = (PullToNextLayout) findViewById(R.id.pullToNextLayout);

        Intent pro_intent = getIntent();
        mProductType = pro_intent.getIntExtra("type", 0);
        mProductId = pro_intent.getStringExtra("id");
        mState = pro_intent.getStringExtra("state");

        if(mProductId.equals(""))
        {
            Toast.makeText(this, "找不到相应产品", Toast.LENGTH_LONG).show();
            return;
        }

        mPageList = new ArrayList<>();
        mDetailsPage = new DetailsPage();
        mAgreementPage = ProductDetailsMorePage.create(mProductType);
        mPageList.add(mDetailsPage);
        mPageList.add(mAgreementPage);
        pullToNextLayout.setAdapter(new PullToNextAdapter(getSupportFragmentManager(), mPageList));

        pullToNextLayout.setOnItemSelectListener(new OnItemSelectListener() {
            public void onSelectItem(int position, View view) {
                mAgreementPage.initScroll();
            }
        });

        mConfirmBtn = (Button)findViewById(R.id.confirm_button);

        if(mState != null)
        {
            mConfirmBtn.setEnabled(false);
            mConfirmBtn.setText(mState);
        }
    }

    /**
     * 网络数据请求， 初始化数据
     *
     */
    protected void initData()
    {
        setPageTitle("项目详情");

        mProdList = mDetailsPage.getListView();
        mAdapter = new ProductDetailsAdapter(this);
        mProdList.setAdapter(mAdapter);

        getProductDetailsRequest();
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    protected void onResume()
    {
        super.onResume();
    }

    /**
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType)
    {
        if (reqType == HttpRequst.REQ_TYPE_PRODUCT_DETAILS)
        {
            super.onSuccess(content, object, reqType);
            ProductDetailsEntity entity = (ProductDetailsEntity) object;
            String[] keys = {"项目名称", "还款方式", "还款时间"};
            String[] mValues = {entity.projectname, entity.hkfs, entity.hksj};
            mAdapter.setData(keys, mValues);

            float jd;
            try
            {
                jd = Float.parseFloat(entity.jd);
            }
            catch (Exception e)
            {
                jd = 0.0f;
            }
            mDetailsPage.getProgressBar().setProgress((int)jd);
            mDetailsPage.getInvestPercent().showPercentWithAnimation((int)jd);
            mDetailsPage.getIcomeText().setText(entity.nhsy);
            mDetailsPage.getTagTextView().setText("%");
            mDetailsPage.getInvestNum().setText("￥" + entity.syje + "/" + entity.rzje);
            mDetailsPage.getTimeLimit().setText(entity.cpqx);
            mDetailsPage.getQtjeTextView().setText(entity.qtje + "起投 | " + "每人限购100万元");

            mAgreementPage.initTzListData(entity.mTzList);
            if(mProductType == 2 )
            {
                mAgreementPage.initContractText(entity.mDetailsOther.xmqk);
                mAgreementPage.initContractImgList(mProductId);
            }
            else
            {
                String[] contractList = new String[14];
                contractList[0] = entity.mDetailsCTB.projectname;
                contractList[1] = entity.mDetailsCTB.tbfw;
                contractList[2] = entity.mDetailsCTB.cpqx;
                contractList[3] = entity.mDetailsCTB.hkfs;
                contractList[4] = entity.mDetailsCTB.nhsy + "%";
                contractList[5] = entity.mDetailsCTB.jrtj;
                contractList[6] = entity.mDetailsCTB.jrsx;
                contractList[7] = entity.mDetailsCTB.sdjzrq;
                contractList[8] = entity.mDetailsCTB.tcfs;
                contractList[9] = entity.mDetailsCTB.tqtcfs;
                contractList[10] = entity.mDetailsCTB.glfy;
                contractList[11] = entity.mDetailsCTB.jrfwf;
                contractList[12] = entity.mDetailsCTB.tqtcfy;
                contractList[13] = entity.mDetailsCTB.bzfs;

                mAgreementPage.initContractCTB(contractList, entity.mDetailsCTB.rzje, mProductId);
            }

            mDetails = new String[4];
            mDetails[0] = entity.projectname;
            mDetails[1] = entity.cpqx;
            mDetails[2] = entity.nhsy + "%";
            mDetails[3] = entity.syje;

            if(mState == null)
            mConfirmBtn.setEnabled(true);
        }
    }

    /**
     * 描述: 产品详情页
     * @author zhoulongfei
     */
    public static class  DetailsPage extends Fragment
    {
        private ListView mListView = null;

        private RoundProgressBar mProgressBar = null;

        private TextView  mTimeLimit = null;
        private TextView  mIcomeText = null;
        private TextView mTagTextView = null;
        private CountView mInvestPercent = null;
        private TextView mInvestNum = null;      //投资进度数值

        private TextView mQtjeTextView = null;   //起投金额

        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.product_details_sub1, container, false);
            mProgressBar = (RoundProgressBar)v.findViewById(R.id.homepage_invest_progress);
            mListView = (ListView)v.findViewById(R.id.lv_details);

            mTimeLimit = (TextView)v.findViewById(R.id.homepage_timelimit);
            mIcomeText = (TextView)v.findViewById(R.id.homepage_income_percent);
            mTagTextView = (TextView)v.findViewById(R.id.homepage_income_percenttag);
            mInvestPercent = (CountView)v.findViewById(R.id.invest_progress_percent);
            mInvestNum = (TextView)v.findViewById(R.id.invest_progress_num);

            mQtjeTextView = (TextView)v.findViewById(R.id.invest_progress_qtje);

            return v;
        }

        public ListView getListView()
        {
            return mListView;
        }

        public RoundProgressBar getProgressBar()
        {
            return mProgressBar;
        }

        public TextView getIcomeText()
        {
            return mIcomeText;
        }

        public TextView getTagTextView()
        {
            return mTagTextView;
        }

        public CountView getInvestPercent()
        {
            return mInvestPercent;
        }

        public TextView getInvestNum()
        {
            return mInvestNum;
        }

        public TextView getTimeLimit()
        {
            return mTimeLimit;
        }

        public TextView getQtjeTextView()
        {
            return mQtjeTextView;
        }
    }

    private void getProductDetailsRequest()
    {
        try {
            String url = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_DETAILS);

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", mProductId);
            jsonObject.put("type", mProductType);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_PRODUCT_DETAILS, url, params, getAsyncClient(), true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }

    @Override
    protected void initListener()
    {
        setOnClickListener(R.id.confirm_button);
    }

    public void treatClickEvent(int id)
    {
        if(BaseApplication.getInstance().isUserLogin())
        {
            Intent intent = new Intent(this, ConfirmActivity.class);
            intent.putExtra("details",mDetails);
            intent.putExtra("id",mProductId);
            intent.putExtra("type",mProductType);

            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
