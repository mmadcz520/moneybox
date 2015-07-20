package com.changtou.moneybox.module.page;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.adapter.ExGridAdapter;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.SignInHUD;


public class RichesFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private TextView mMobileTextView = null;
    private CountView mTotalAssetsTextView = null;
    private TextView mInvestAssetsTextView = null;
    private TextView mProfitTextView = null;
    private TextView mOverageTextView = null;
    private TextView mGiftsTextView = null;
    private TextView mTouYuanTextView = null;

    private SharedPreferencesHelper sph = null;

    private ObjectAnimator animator = null;

    private Counter mCounter = null;

    private ImageView mQiandaoImage = null;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sph = SharedPreferencesHelper.getInstance(this.getActivity().getApplicationContext());

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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        final Intent intent0 = new Intent(RichesFragment.this.getActivity(), RichesPromotionActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        final Intent intent1 = new Intent(RichesFragment.this.getActivity(), RichesInvestListActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        final Intent intent2 = new Intent(RichesFragment.this.getActivity(), RichesTradeActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        final Intent intent3 = new Intent(RichesFragment.this.getActivity(), RichesFlowActivity.class);
                        startActivity(intent3);
                        break;
                    case 4: {

                        UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
                        BankCardEntity bank = userInfoEntity.getBankCardEntity();

                        int len = bank.mList.size();
                        if (len == 0) {
                            Toast.makeText(RichesFragment.this.getActivity(), "请先添加取现银行卡", Toast.LENGTH_LONG).show();
                            final Intent intent4 = new Intent(RichesFragment.this.getActivity(), RichesBankActivity.class);
                            startActivity(intent4);
                        } else {
                            final Intent intent4 = new Intent(RichesFragment.this.getActivity(), RichesWithdrawActivity.class);
                            startActivity(intent4);
                        }

                        break;
                    }
                    case 5:
                        final Intent intent5 = new Intent(RichesFragment.this.getActivity(), RichesSafeActivity.class);
                        startActivity(intent5);
                        break;
                }
            }
        });

        mQiandaoImage = (ImageView)view.findViewById(R.id.riches_signbar);
//        Animation translateAnimation = AnimationUtils.loadAnimation(this.getActivity(), R.anim.tip);
//        translateAnimation.setDuration(200);
//        translateAnimation.setRepeatCount(Animation.INFINITE);
//        translateAnimation.setRepeatMode(Animation.REVERSE);
//        imageView.setAnimation(translateAnimation); //这里iv就是我们要执行动画的item，例如一个imageView
//        translateAnimation.start();



//        ObjectAnimator nopeAnimator = nope(imageView);
//        nopeAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        nopeAnimator.start();

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
        setOnClickListener(R.id.riches_signbar);
    }

    protected void initData(Bundle savedInstanceState)
    {

    }

    public void onSuccess(String content, Object object, int reqType)
    {
//        Log.e("CT_MONEY", "content " + content);

        if(reqType == HttpRequst.REQ_TYPE_USERINFO)
        {
            UserInfoEntity userInfo = UserInfoEntity.getInstance();
            mMobileTextView.setText(userInfo.getMobile());
            String total = userInfo.getTotalAssets();
            total = total.replace(",","");
            if(total.equals("")) return;

            Log.e("CT_MONEY", "mMobileTextViewmMobileTextViewmMobileTextView" + userInfo.getFullName());

            mTotalAssetsTextView.showNumberWithAnimation(Float.parseFloat(total));
            mTotalAssetsTextView.setText(userInfo.getTotalAssets());
            mInvestAssetsTextView.setText(userInfo.getInvestAssets());
            mProfitTextView.setText(userInfo.getProfit());
            mOverageTextView.setText(userInfo.getOverage());
            mGiftsTextView.setText(userInfo.getGifts());
            mTouYuanTextView.setText(userInfo.getTouYuan());
        }
    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.EN_LOGIN.toString());
        sph.putString(AppCfg.GSPD, "");
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

//        mMultiStateView.setViewForState(R.layout.state_layout_loading, MultiStateView.ViewState.LOADING);
    }

    public void treatClickEvent(int id)
    {
        SignInHUD sHUD = SignInHUD.getInstance(this.getActivity());
        sHUD.show();
//        sHUD.changeNum();
    }


    public static ObjectAnimator tada(View view) {
        return tada(view, 1f);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static ObjectAnimator tada(View view, float shakeFactor) {

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(.1f, -3f * shakeFactor),
                Keyframe.ofFloat(.2f, -3f * shakeFactor),
                Keyframe.ofFloat(.3f, 3f * shakeFactor),
                Keyframe.ofFloat(.4f, -3f * shakeFactor),
                Keyframe.ofFloat(.5f, 3f * shakeFactor),
                Keyframe.ofFloat(.6f, -3f * shakeFactor),
                Keyframe.ofFloat(.7f, 3f * shakeFactor),
                Keyframe.ofFloat(.8f, -3f * shakeFactor),
                Keyframe.ofFloat(.9f, 3f * shakeFactor),
                Keyframe.ofFloat(1f, 0)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).
                setDuration(1000);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static ObjectAnimator nope(View view) {
        int delta = view.getResources().getDimensionPixelOffset(R.dimen.spacing_medium);

        PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(.10f, -delta),
                Keyframe.ofFloat(.26f, delta),
                Keyframe.ofFloat(.42f, -delta),
                Keyframe.ofFloat(.58f, delta),
                Keyframe.ofFloat(.74f, -delta),
                Keyframe.ofFloat(.90f, delta),
                Keyframe.ofFloat(1f, 0f)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX).
                setDuration(500);
    }

    public class Counter extends CountDownTimer {

        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated method stub
        }

        @Override
        public void onFinish()
        {
            animator.cancel();
        }

        @Override
        public void onTick(long millisUntilFinished)
        {

        }
    }

    /**
     * 切换页面是签到按钮动画效果
     */
    public void initAnim()
    {
        animator = tada(mQiandaoImage);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

        mCounter = new Counter(10*1000, 1000);    //第一个参数是倒计时时间，后者为计时间隔，单位毫秒，这里是倒计时 5 分钟，间隔1秒
        mCounter.start();
    }
}
