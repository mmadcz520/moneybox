package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.adapter.ExGridAdapter;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.MultiStateView;

public class RichesFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private TextView mMobileTextView = null;
    private CountView mTotalAssetsTextView = null;
    private TextView mInvestAssetsTextView = null;
    private TextView mProfitTextView = null;
    private TextView mOverageTextView = null;
    private TextView mGiftsTextView = null;
    private TextView mTouYuanTextView = null;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int[] mImgRes = {R.drawable.riches_btn_adf_selector,
                R.drawable.riches_btn_invest_selector,
                R.drawable.riches_btn_flow_selector,
                R.drawable.riches_btn_safe_selector,
                R.drawable.riches_btn_wd_selector,
                R.drawable.riches_btn_safe_selector};

        String[] titleList = this.getActivity().getResources().getStringArray(R.array.riches_modules);

        View view = inflater.inflate(R.layout.riches_fragment, container, false);
        GridView gv = (GridView) view.findViewById(R.id.brainheroall);
        ExGridAdapter sa = new ExGridAdapter(this.getActivity(), mImgRes, titleList);
        gv.setAdapter(sa);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
                        final Intent intent0 = new Intent(RichesFragment.this.getActivity(), RichesPromotionActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        final Intent intent1 = new Intent(RichesFragment.this.getActivity(), RichesInvestListActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        final Intent intent2= new Intent(RichesFragment.this.getActivity(), RichesTradeActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        final Intent intent3 = new Intent(RichesFragment.this.getActivity(), RichesFlowActivity.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        final Intent intent4 = new Intent(RichesFragment.this.getActivity(), RichesWithdrawActivity.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        final Intent intent5 = new Intent(RichesFragment.this.getActivity(), RichesSafeActivity.class);
                        startActivity(intent5);
                        break;
                }
            }
        });

        ImageView imageView = (ImageView)view.findViewById(R.id.riches_signbar);
        Animation translateAnimation = AnimationUtils.loadAnimation(this.getActivity(), R.anim.tip);
        translateAnimation.setDuration(200);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        imageView.setAnimation(translateAnimation); //这里iv就是我们要执行动画的item，例如一个imageView
        translateAnimation.start();

        mMobileTextView = (TextView)view.findViewById(R.id.riches_text_mobile);
        mTotalAssetsTextView = (CountView)view.findViewById(R.id.riches_text_totalassets);
        mInvestAssetsTextView = (TextView)view.findViewById(R.id.riches_text_investassets);
        mProfitTextView = (TextView)view.findViewById(R.id.riches_text_profit);
        mOverageTextView = (TextView)view.findViewById(R.id.riches_text_overage);
        mGiftsTextView = (TextView)view.findViewById(R.id.riches_text_gifts);
        mTouYuanTextView = (TextView)view.findViewById(R.id.riches_text_touyuan);

        return view;
    }

    public void onResume()
    {
        //请求userinfo
        getUserInfo();
        super.onResume();
    }

    protected void initListener()
    {

    }

    protected void initData(Bundle savedInstanceState)
    {

    }

    public void onSuccess(String content, Object object, int reqType)
    {
        if(reqType == HttpRequst.REQ_TYPE_USERINFO)
        {
//            mTotalAssetsTextView.showNumberWithAnimation(Integer.parseInt(userInfo.getTotalAssets()));

            UserInfoEntity userInfo = UserInfoEntity.getInstance();
            mMobileTextView.setText(userInfo.getMobile());
            String total = userInfo.getTotalAssets();
            total = total.replace(",","");
            mTotalAssetsTextView.showNumberWithAnimation(Float.parseFloat(total));
            mTotalAssetsTextView.setText(userInfo.getTotalAssets());
            mInvestAssetsTextView.setText(userInfo.getInvestAssets());
            mProfitTextView.setText(userInfo.getProfit());
            mOverageTextView.setText(userInfo.getOverage());
            mGiftsTextView.setText(userInfo.getGifts());
            mTouYuanTextView.setText(userInfo.getTouYuan());

            mMultiStateView.setViewForState(R.layout.state_layout_content, MultiStateView.ViewState.LOADING);
        }
    }

    public void onFailure(Throwable error, String content, int reqType)
    {
//        mMultiStateView.setViewState(MultiStateView.ViewState.ERROR);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }

    /**
     * 获取用户信息
     */
    private void getUserInfo()
    {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_USERINFO) +
                "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");


        sendRequest(HttpRequst.REQ_TYPE_USERINFO, url, mParams,
                mAct.getAsyncClient(), false);

        mMultiStateView.setViewForState(R.layout.state_layout_loading, MultiStateView.ViewState.LOADING);

    }
}
