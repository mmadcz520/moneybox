package com.changtou.moneybox.module.page;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.dialog.SpotsDialog;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.AppUtil;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.adapter.ExGridAdapter;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.FlowEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.usermodule.LoginNotifier;
import com.changtou.moneybox.module.usermodule.UserManager;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.SignInHUD;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class RichesFragment extends BaseFragment implements AdapterView.OnItemClickListener, LoginNotifier {

//    private TextView mMobileTextView = null;
    private CountView mTotalAssetsTextView = null;
    private TextView mInvestAssetsTextView = null;
    private TextView mProfitTextView = null;
    private TextView mOverageTextView = null;
    private TextView mGiftsTextView = null;
    private TextView mTouYuanTextView = null;

    private ImageView mAuthIdcodeIV = null;
    private ImageView mAuthPhoneIV = null;
    private ImageView mAuthCardIV = null;

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

    private ACache mAcache = null;
    private UserInfoEntity mUserInfoEntity = null;

    private UserManager mUserManager = null;

    private SpotsDialog mSpotsDialog = null;

    private View mExchangeGiftView = null;
    private View mExchangeGiftView1 = null;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sph = SharedPreferencesHelper.getInstance(this.getActivity().getApplicationContext());
        cache = ACache.get(this.getActivity());

        mUserManager = BaseApplication.getInstance().getUserModule();
        mUserManager.setLoginNotifier(this);

        mSpotsDialog = new SpotsDialog(this.getActivity());
        if(BaseApplication.getInstance().isUserLogin())
        {
            mSpotsDialog.show();
        }

        int[] mImgRes = {R.drawable.riches_btn_adf_selector,
                R.drawable.riches_btn_regcharge_selector,
                R.drawable.riches_btn_wd_selector,
                R.drawable.riches_btn_flow_selector,
                R.drawable.riches_btn_invest_selector,
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

                        //先验证实名认证，在添加银行卡
                        if(!mUserInfoEntity.getIdentycheck())
                        {
                            final Intent intent = new Intent(RichesFragment.this.getActivity(), RichesCertificationActivity.class);
                            intent.putExtra("URLType", 1);
                            startActivity(intent);
                        }
                        else
                        {
                            BankCardEntity bank = mUserInfoEntity.getBankCardEntity();

                            int len = bank.mList.size();
                            if (len == 0) {
                                final Intent intent = new Intent(RichesFragment.this.getActivity(), RichesBankAddActivity.class);
                                intent.putExtra("URLType", 1);
                                startActivity(intent);
                            } else {
                                final Intent intent = new Intent(RichesFragment.this.getActivity(), RichesRechargePage.class);
                                startActivity(intent);
                                //跳转到充值页面
                            }
                        }

                        break;
                    case 2:

                        //先验证实名认证，在添加银行卡
                        if(!mUserInfoEntity.getIdentycheck())
                        {
                            final Intent intent = new Intent(RichesFragment.this.getActivity(), RichesCertificationActivity.class);
                            intent.putExtra("URLType", 2);
                            startActivity(intent);
                        }
                        else
                        {
                            BankCardEntity bank = mUserInfoEntity.getBankCardEntity();

                            int len = bank.mList.size();
                            if (len == 0) {
                                final Intent intent = new Intent(RichesFragment.this.getActivity(), RichesBankAddActivity.class);
                                intent.putExtra("URLType", 2);
                                startActivity(intent);
                            } else {
                                final Intent intent = new Intent(RichesFragment.this.getActivity(), RichesWithdrawActivity.class);
                                startActivity(intent);
                                //跳转到充值页面
                            }
                        }

                        break;
                    case 3:
                        gotoCalendar();

                        break;
                    case 4: {

                        final Intent intent1 = new Intent(RichesFragment.this.getActivity(), RecordActivity.class);
                        startActivity(intent1);

//                        UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();

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

        //100

//        mMobileTextView = (TextView)view.findViewById(R.id.riches_text_mobile);
        mTotalAssetsTextView = (CountView)view.findViewById(R.id.riches_text_totalassets);
        mInvestAssetsTextView = (TextView)view.findViewById(R.id.riches_text_investassets);
        mProfitTextView = (TextView)view.findViewById(R.id.riches_text_profit);
        mOverageTextView = (TextView)view.findViewById(R.id.riches_text_overage);
        mGiftsTextView = (TextView)view.findViewById(R.id.riches_text_gifts);
        mTouYuanTextView = (TextView)view.findViewById(R.id.riches_text_touyuan);

        mAcache = ACache.get(BaseApplication.getInstance());
        mUserInfoEntity = (UserInfoEntity)mAcache.getAsObject("userinfo");

        mAuthIdcodeIV = (ImageView)view.findViewById(R.id.auth_idcode_img);
        mAuthPhoneIV = (ImageView)view.findViewById(R.id.auth_phone_img);
        mAuthCardIV = (ImageView)view.findViewById(R.id.auth_card_img);

        //添加认证按钮点击事件
        //// TODO: 2015/11/23
        mAuthIdcodeIV.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                final Intent intent = new Intent(RichesFragment.this.getActivity(), RichesCertificationActivity.class);
                intent.putExtra("isCerfy", mUserInfoEntity.getIdentycheck());
                startActivity(intent);
            }
        });

        mAuthPhoneIV.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                final Intent intent = new Intent(RichesFragment.this.getActivity(), PdFrogetActivity.class);
                intent.putExtra("pageType", 1);
                intent.putExtra("isPhoneauth", mUserInfoEntity.getMobilecheck());
                startActivity(intent);
            }
        });

        mAuthCardIV.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(mUserInfoEntity.getIdentycheck())
                {
                    final Intent intent = new Intent(RichesFragment.this.getActivity(), RichesBankActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RichesFragment.this.getActivity(), "请先进行实名认证！", Toast.LENGTH_LONG).show();
                }
            }
        });

        mExchangeGiftView = view.findViewById(R.id.gift_click_layout);
        mExchangeGiftView1 = view.findViewById(R.id.gift_click_layout1);

        if(mUserInfoEntity != null && !mUserInfoEntity.getCreatetime().equals(""))
        {
                initRichesPage();
        }

        sHUD = SignInHUD.getInstance(this.getActivity());
        sHUD.setCancelable(true);
        sHUD.setOwnerActivity(this.getActivity());
        sHUD.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                mQiandaoImage.setEnabled(false);
                mTouYuanTextView.setText("" + mTouyuan);
            }
        });
        sHUD.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                //签到
            }
        });

        mExchangeGiftView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RichesFragment.this.getActivity(), GiftExchangeActivity.class);
                startActivity(intent);
            }
        });

        mExchangeGiftView1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(RichesFragment.this.getActivity(), GiftExchangeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onResume()
    {
        //请求userinfo
        if(BaseApplication.getInstance().isUserLogin()) {
            getUserInfo();
            isSignRequest();
        }
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
//        if(!isUpdate) return;
//
//        isUpdate = false;

        try {

            mSpotsDialog.cancel();

//        mMobileTextView.setText(mUserInfoEntity.getMobile());
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

            if(mUserInfoEntity.getIdentycheck())
            {
                mAuthIdcodeIV.setImageResource(R.mipmap.auth_idcode_true);
            }

            if(mUserInfoEntity.getMobilecheck())
            {
                mAuthPhoneIV.setImageResource(R.mipmap.auth_phone_true);
            }

            if( mUserInfoEntity.getBankCardEntity() != null && mUserInfoEntity.getBankCardEntity().mList != null) {
                int cardSize = mUserInfoEntity.getBankCardEntity().mList.size();
                if (cardSize > 0) {
                    mAuthCardIV.setImageResource(R.mipmap.auth_card_true);
                }
            }

            cache.put("fullname", mUserInfoEntity.getFullName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
            BaseApplication.getInstance().backToLoginPage();
        }


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
                    Toast.makeText(this.getActivity(),"账号在其他设备登陆,请重新登录", Toast.LENGTH_LONG).show();
                    BaseApplication.getInstance().backToLoginPage();
//                    sph.putString(AppCfg.GSPD, "");
//                    ACache.get(this.getActivity()).clear();
                    mSpotsDialog.cancel();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(reqType == HttpRequst.REQ_TYPE_SIGN)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(content);

                int error = jsonObject.getInt("errcode");
                mTouyuan = jsonObject.getInt("touyuan");

                if(error == 0)
                {
                    mTouYuanTextView.setText("" + mTouyuan);

                    Toast toast = Toast.makeText(this.getActivity(), "", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    LinearLayout toastView = (LinearLayout) toast.getView();
                    ImageView imageCodeProject = new ImageView(this.getActivity());
                    imageCodeProject.setImageResource(R.mipmap.riches_touyuan_banner);
                    toastView.addView(imageCodeProject, 0);
                    int width = AppUtil.getScreenSize(BaseApplication.getInstance())[0];
                    toastView.setMinimumWidth(width);
                    toast.show();

                }
                else if(error == 1)
                {
                    Toast.makeText(this.getActivity(), "签到失败", Toast.LENGTH_LONG).show();
                    mQiandaoImage.setEnabled(true);
                }
                else if(error == 2)
                {
                    sHUD.show();
                    Toast.makeText(this.getActivity(), "您已签到", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
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

    public void onFailure(Throwable error, String content, int reqType)
    {
        mSpotsDialog.cancel();

        if(reqType == HttpRequst.REQ_TYPE_USERINFO)
        {
            Toast.makeText(this.getActivity(), "获取用户信息失败, 请重新登录", Toast.LENGTH_LONG).show();

            sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.EN_LOGIN.toString());
            sph.putString(AppCfg.GSPD, "");
            ACache.get(this.getActivity()).clear();
            BaseApplication.getInstance().AppExit();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }

    /**
     * 初始化用户信息
     */
    private void getUserInfo()
    {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_USERINFO);

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

    @Override
    public void loginSucNotify() {

    }

    @Override
    public void loginIngNotify() {

    }

    @Override
    public void loginErrNotify(int errcode) {

    }

    @Override
    public void logoutNotify() {

    }

    @Override
    public void loginUserInfo(Object object) {
        mUserInfoEntity = (UserInfoEntity)object;
        mAcache.put("userinfo", mUserInfoEntity);

        initRichesPage();
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
        if(mQiandaoImage!=null && isSign == 0 && mQiandaoImage.isEnabled())
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
        mQiandaoImage.setEnabled(false);

        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_SIGN);

        sendRequest(HttpRequst.REQ_TYPE_SIGN, url, mParams,
                mAct.getAsyncClient(), false);
    }

    /**
     * 是否签到接口
     */
    private void isSignRequest()
    {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_ISSIGN);

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

    /**
     * 跳转到还款日历
     */
    private void gotoCalendar()
    {
        FlowEntity mEntity = mUserInfoEntity.getFlowEntity();

        final Intent intent = new Intent(this.getActivity(), RichesCalendarActivity.class);

        ACache cache = ACache.get(BaseApplication.getInstance());
        cache.put("selected_month", mEntity.mMonth.size());
        cache.put("flow", mEntity);

        startActivity(intent);
    }

}
