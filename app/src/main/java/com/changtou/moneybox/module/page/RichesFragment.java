package com.changtou.moneybox.module.page;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.logger.Logger;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.adapter.ExGridAdapter;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.SignInHUD;
import com.changtou.moneybox.module.widget.ZProgressHUD;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


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

    private Button mQiandaoImage = null;

    private SignInHUD sHUD = null;

    private int isSign = 0;

    private int mTouyuan = 0;

    //是否实名认证
    private boolean isCertify = false;

    private boolean isPopu = false;

    private ACache cache = null;

    private ZProgressHUD mZProgressHUD = null;

    private ACache mAcache = null;
    private UserInfoEntity mUserInfoEntity = null;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sph = SharedPreferencesHelper.getInstance(this.getActivity().getApplicationContext());
        cache = ACache.get(this.getActivity());

        mZProgressHUD = ZProgressHUD.getInstance(this.getActivity());

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

        mQiandaoImage = (Button)view.findViewById(R.id.riches_signbar);

        mMobileTextView = (TextView)view.findViewById(R.id.riches_text_mobile);
        mTotalAssetsTextView = (CountView)view.findViewById(R.id.riches_text_totalassets);
        mInvestAssetsTextView = (TextView)view.findViewById(R.id.riches_text_investassets);
        mProfitTextView = (TextView)view.findViewById(R.id.riches_text_profit);
        mOverageTextView = (TextView)view.findViewById(R.id.riches_text_overage);
        mGiftsTextView = (TextView)view.findViewById(R.id.riches_text_gifts);
        mTouYuanTextView = (TextView)view.findViewById(R.id.riches_text_touyuan);

        mAcache = ACache.get(BaseApplication.getInstance());
        mUserInfoEntity = (UserInfoEntity)mAcache.getAsObject("userinfo");

        if(mUserInfoEntity != null)
        {
            initRichesPage();
        }

        sHUD = SignInHUD.getInstance(this.getActivity());
        sHUD.setCancelable(true);
        sHUD.setOwnerActivity(this.getActivity());
        sHUD.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog)
            {
                mQiandaoImage.setEnabled(false);
                mTouYuanTextView.setText("" + mTouyuan);
            }
        });
        sHUD.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                //签到
            }
        });

        return view;
    }

    public void onResume()
    {
        //请求userinfo
        getUserInfo();
        isSignRequest();
        super.onResume();
    }

    protected void initListener()
    {
        setOnClickListener(R.id.riches_signbar);
    }

    protected void initData(Bundle savedInstanceState)
    {

    }

    private void initRichesPage()
    {
        mMobileTextView.setText(mUserInfoEntity.getMobile());
        String total = mUserInfoEntity.getTotalAssets();
        total = total.replace(",", "");
        if(total.equals("")) return;

        mTouyuan = mUserInfoEntity.getTouYuan();

        mTotalAssetsTextView.showNumberWithAnimation(Float.parseFloat(total));
        mTotalAssetsTextView.setText(mUserInfoEntity.getTotalAssets());
        mInvestAssetsTextView.setText(mUserInfoEntity.getInvestAssets());
        mProfitTextView.setText(mUserInfoEntity.getProfit());
        mOverageTextView.setText(mUserInfoEntity.getOverage());
        mGiftsTextView.setText(mUserInfoEntity.getGifts());
        mTouYuanTextView.setText("" + mTouyuan);

        cache.put("fullname", mUserInfoEntity.getFullName());
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        if(reqType == HttpRequst.REQ_TYPE_USERINFO)
        {
            try {
                JSONObject jsonObject = new JSONObject(content);
                int err = jsonObject.getInt("err");
                if(err == 0)
                {
                    mUserInfoEntity = (UserInfoEntity)object;
                    mAcache.put("userinfo", mUserInfoEntity);
                    initRichesPage();

                    isPopu = (boolean)cache.getAsObject("isPopu");
                    isCertify = UserInfoEntity.getInstance().getIdentycheck();
                    if((!isCertify) && isPopu)
                    {
                        cache.put("isPopu", false);
                        popuAuthDailog();
                    }
                }
                else if(err == 2)
                {
                    sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.EN_LOGIN.toString());
                    sph.putString(AppCfg.GSPD, "");
                }
            }
            catch (Exception e)
            {

            }



        }

        if(reqType == HttpRequst.REQ_TYPE_SIGN)
        {
            try
            {
                mZProgressHUD.cancel();
                JSONObject jsonObject = new JSONObject(content);

                int error = jsonObject.getInt("errcode");
                mTouyuan = jsonObject.getInt("touyuan");

                if(error == 0)
                {
                    sHUD.show();
                }
                else if(error == 1)
                {
                    Toast.makeText(this.getActivity(), "签到失败", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {

            }
        }


        if(reqType == HttpRequst.REQ_TYPE_ISSIGN)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(content);

                int error = jsonObject.getInt("errcode");
                isSign = error;
                if(error == 0)
                {
                    mQiandaoImage.setEnabled(true);
                }
                else if(error == 1)
                {
                    mQiandaoImage.setEnabled(false);
                    mQiandaoImage.clearAnimation();
                }
            }
            catch (Exception e)
            {

            }
        }
    }

    public void onFailure(Throwable error, String content, int reqType) {


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

        Logger.e(url);

        sendRequest(HttpRequst.REQ_TYPE_USERINFO, url, mParams,
                mAct.getAsyncClient(), false);

    }

    public void treatClickEvent(int id)
    {
        signRequest();
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
        if(isSign == 0 && mQiandaoImage.isEnabled())
        {
            animator = nope(mQiandaoImage);
            animator.setRepeatCount(1);
            animator.start();

            mCounter = new Counter(10*1000, 1000);    //第一个参数是倒计时时间，后者为计时间隔，单位毫秒，这里是倒计时 5 分钟，间隔1秒
            mCounter.start();
        }
    }


    /**
     * 签到请求接口
     */
    private void signRequest()
    {
        mZProgressHUD.show();

        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_SIGN) +
                "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

        sendRequest(HttpRequst.REQ_TYPE_SIGN, url, mParams,
                mAct.getAsyncClient(), false);
    }

    /**
     * 是否签到接口
     */
    private void isSignRequest()
    {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_ISSIGN) +
                "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

        sendRequest(HttpRequst.REQ_TYPE_ISSIGN, url, mParams,
                mAct.getAsyncClient(), false);
    }

    private void popuAuthDailog()
    {
        ColorStateList redColors = ColorStateList.valueOf(0xffff0000);
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder("实名认证得10元礼金！");
        //style 为0 即是正常的，还有Typeface.BOLD(粗体) Typeface.ITALIC(斜体)等
        //size  为0 即采用原始的正常的 size大小
        spanBuilder.setSpan(new TextAppearanceSpan(null, 0, 40, redColors, null), 5, 7, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        spanBuilder.setSpan(new TextAppearanceSpan(null, 0, 60, redColors, null), 28, 30, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        SpannableStringBuilder spanBuilder1 = new SpannableStringBuilder("去认证");

        final SweetAlertDialog sad = new SweetAlertDialog(this.getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sad.setConfirmText(spanBuilder1) .setContentText(spanBuilder);
        sad.setCancelText("先逛逛");
        sad .show();

        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                sad.cancel();

            }
        });

        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                sad.cancel();
                Intent intent = new Intent(RichesFragment.this.getActivity(), RichesSafeActivity.class);
                startActivity(intent);
            }
        });
    }

}
