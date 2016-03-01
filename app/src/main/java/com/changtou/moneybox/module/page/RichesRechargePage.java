package com.changtou.moneybox.module.page;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.SlidingTabLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yintong.pay.utils.BaseHelper;
import com.yintong.pay.utils.Constants;
import com.yintong.pay.utils.MobileSecurePayer;
import com.yintong.pay.utils.PayOrder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 充值页面
 * <p/>
 * Created by Jone on 2015/9/2.
 */
public class RichesRechargePage extends CTBaseActivity
{
    private ViewPager mViewPager = null;
    private SlidingTabLayout mSlidingTabLayout;

    private QuickRechargePage mSubPage1 = null;
    private SubPage mSubPage2 = null;

    private int mUrlType = 0;

    private static String[] mTypeName = {"快捷充值", "充值卡"};

    protected void initView(Bundle bundle) {

        setContentView(R.layout.riches_recharge_layout);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.riches_recharge_tabs);
        Resources res = getResources();
        mSlidingTabLayout.setCustomTabView(R.layout.product_tabpage_indicator, android.R.id.text1);
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.ct_blue));
        mSlidingTabLayout.setTabCount(2);

        List<BaseFragment> viewList = new ArrayList<>();
        mSubPage1 = QuickRechargePage.create();
        mSubPage2 = SubPage.create(1);

        viewList.add(mSubPage1);
        viewList.add(mSubPage2);

        mViewPager = (ViewPager) findViewById(R.id.riches_recharge_pager);
        ExFPAdapter pagerAdapter = new ExFPAdapter(getSupportFragmentManager(), viewList);
        pagerAdapter.setTitles(mTypeName);

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(viewList.size());
        mSlidingTabLayout.setViewPager(mViewPager);

        Intent intent = getIntent();
        mUrlType = intent.getIntExtra("urlType", 0);
    }

    protected void initData() {
        showRightBtn(true);
        setRightBtnOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(RichesRechargePage.this, RechargeRecordActivity.class );
                intent.putExtra("goPageCode", 0);
                startActivity(intent);
            }
        });
        setPageTitle("充值");
        super.initData();
    }

    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    /**
     * 充值卡充值页面
     */
    public static class SubPage extends BaseFragment implements PullToRefreshBase.OnRefreshListener {
        private BaseAdapter mAdapter = null;
        private Context mContext = null;

        private PullToRefreshListView mPullRefreshListView;

        private ListView actualListView;

        private LinkedList mData = null;

        private int mType = 0;

        public static SubPage create(int type) {
            SubPage subPage = new SubPage();
            Bundle bundle = new Bundle();
            bundle.putInt("invest", type);
            subPage.setArguments(bundle);
            return subPage;
        }

        protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View mView = inflater.inflate(R.layout.state_layout_empty, container, false);

            return mView;
        }

        protected void initListener() {

        }

        protected void initData(Bundle savedInstanceState) {

        }

        public void onSuccess(String content, Object object, int reqType) {

        }

        public void onFailure(Throwable error, String content, int reqType) {

        }

        /**
         * 刷新函数
         *
         * @param refreshView
         */
        public void onRefresh(PullToRefreshBase refreshView) {

        }

        public void initInvestList(LinkedList data) {

        }
    }

    /**
     * 快捷充值页面
     */
    public static class QuickRechargePage extends BaseFragment implements PullToRefreshBase.OnRefreshListener, View.OnClickListener {
        private Button mQuickReButton = null;
        private EditText mRechargeText = null;

        private EditText mRechargeIdCodeText = null;

        private EditText mRechargeNameText = null;

        private boolean is_preauth = false;

        private String mRechargeName = "";
        private String mRechargeCard = "";
        private String mRechargeNum = "0";

        private String mTradno = "";

        private UserInfoEntity userInfoEntity = null;

        private boolean isIdentycheck = false;

        private Map<String, String> mBankInfoList = null;
        private TextView mBankNoView = null;
        private ImageView mBankIconView = null;

        private UserInfoEntity mUserInfoEntity = null;

        //银行卡号
        private String mCardNum = null;

        //默认银行卡号
        private String mDefaultBankNo = "";

        //是否进行过首次充值
        private boolean isRecharged = false;

        public static QuickRechargePage create() {
            QuickRechargePage subPage = new QuickRechargePage();
            return subPage;
        }

        protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View mView = inflater.inflate(R.layout.riches_recharge_item3, container, false);
            userInfoEntity = (UserInfoEntity) ACache.get(this.getActivity()).getAsObject("userinfo");
            isIdentycheck = userInfoEntity.getIdentycheck();

            if (userInfoEntity != null && isIdentycheck) {
                mView = inflater.inflate(R.layout.riches_recharge_item1, container, false);
            } else {
                mRechargeIdCodeText = (EditText) mView.findViewById(R.id.riches_recharge_idcode);
                mRechargeNameText = (EditText) mView.findViewById(R.id.riches_recharge_name);
            }

            mRechargeText = (EditText) mView.findViewById(R.id.riches_recharge_number);
            mQuickReButton = (Button) mView.findViewById(R.id.quick_recharge_btn);

            //初始化银行卡信息
            mBankNoView = (TextView) mView.findViewById(R.id.riches_bank_item_no);
            mBankIconView = (ImageView) mView.findViewById(R.id.riches_bank_item_logo);
            mBankInfoList = BaseApplication.getInstance().getBankInfoList();

            final Intent intent1 = new Intent(this.getActivity(), RechargeBankListPage.class);
            ImageView banklistImg = (ImageView) mView.findViewById(R.id.riches_recharge_banklist);
            banklistImg.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    startActivity(intent1);
                }
            });

            final Intent intent = new Intent(this.getActivity(), RichesBankActivity.class);
            RelativeLayout ll = (RelativeLayout) mView.findViewById(R.id.riches_bank_item);
            intent.putExtra("URLType",1);
            ll.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(intent);
                }
            });

            return mView;
        }

        //初设化银行卡信息
        public void initBankInfo() {
            mUserInfoEntity = (UserInfoEntity) ACache.get(this.getActivity()).getAsObject("userinfo");

            if(mUserInfoEntity == null) return;

            isRecharged = mUserInfoEntity.getIsRecharged();
            BankCardEntity bank = mUserInfoEntity.getBankCardEntity();

            if(bank == null) return;

            int len = bank.mList.size();
            BankCardEntity.BankListEntity bList;

            for (int i = 0; i < len; i++) {
                bList = (BankCardEntity.BankListEntity) bank.mList.get(i);
                String account = bList.account;
                if (account.length() > 10) {
                    account = account.substring(0, 4) + " **** " + account.substring(account.length() - 4, account.length());
                }
                if (bList.isdefault.equals("是")) {
                    mDefaultBankNo = bList.account;
                    mBankNoView.setText(account);
                    setBankIcon(mBankIconView, bList.bank);

                    mCardNum = mDefaultBankNo;
                }
            }
        }

        private void setBankIcon(ImageView imageView, String bankName) {
            InputStream open = null;
            try {
                if (mBankInfoList == null) return;
                String temp = mBankInfoList.get(bankName);
                if (temp == null || !temp.startsWith("bank_icon")) return;

                open = this.getActivity().getAssets().open(temp);
                Bitmap bitmap = BitmapFactory.decodeStream(open);

                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (open != null) {
                    try {
                        open.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        protected void initListener() {
            mQuickReButton.setOnClickListener(this);
        }

        @Override
        public void onResume() {
            initBankInfo();
            super.onResume();
        }

        protected void initData(Bundle savedInstanceState) {
//            initBankInfo();
        }

        public void onSuccess(String content, Object object, int reqType) {

            if(reqType == HttpRequst.REQ_TYPE_QUICKPAY_SUCC)
            {
                try {
                    JSONObject paydata = new JSONObject(content);
                    Intent intent = new Intent(BaseApplication.getInstance(), RichesRechargeSuccPage.class);
                    intent.putExtra("paynum",paydata.optString("money"));
                    intent.putExtra("yue",paydata.optString("yue"));
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else {
                try {

                    JSONObject jsObj = new JSONObject(content);
                    JSONObject data = jsObj.getJSONObject("data");
                    int errcode = jsObj.getInt("errcode");

                    if (errcode == 0) {
                        is_preauth = false;

                        PayOrder order = null;
                        // 标准模式
                        order = constructGesturePayOrder(data);

                        String content4Pay = BaseHelper.toJSONString(order);

                        // 关键 content4Pay 用于提交到支付SDK的订单支付串，如遇到签名错误的情况，请将该信息帖给我们的技术支持
                        MobileSecurePayer msp = new MobileSecurePayer();
                        if (is_preauth) {
                            boolean bRet = msp.pay(content4Pay, mHandler,
                                    Constants.RQF_PAY, this.getActivity(), false);
                        } else {
                            boolean bRet = msp.pay(content4Pay, mHandler,
                                    Constants.RQF_PAY, this.getActivity(), false);
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void onFailure(Throwable error, String content, int reqType) {
        }

        /**
         * 刷新函数
         *
         * @param refreshView
         */
        public void onRefresh(PullToRefreshBase refreshView) {

        }

        public void initInvestList(LinkedList data) {

        }

        /**
         * 充值 按钮
         *
         * @param v
         */
        public void onClick(View v) {

//            Intent intent = new Intent(BaseApplication.getInstance(), RichesRechargeSuccPage.class);
//            startActivity(intent);

            String rechargeStr = mRechargeText.getEditableText().toString();
            if (rechargeStr != null && !rechargeStr.equals("")) {
                try {
                    Float recharge = Float.parseFloat(rechargeStr);
                    if (recharge > 0) {
                        if(!isRecharged)
                        {
                            popuDailog();
                        }
                        else
                        {
                            sumbitRechargeRequest();
                        }
                    } else {
                        Toast.makeText(this.getActivity(), "请输入正确金额", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this.getActivity(), "请输入正确金额", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this.getActivity(), "请输入正确金额", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * 创建订单
         *
         * @return
         */
        private PayOrder constructGesturePayOrder(JSONObject object) throws Exception {

            SimpleDateFormat dataFormat = new SimpleDateFormat(
                    "yyyyMMddHHmmss");
            Date date = new Date();
            String timeString = dataFormat.format(date);

            PayOrder order = new PayOrder();
            order.setBusi_partner(object.getString("busi_partner"));
            mTradno = object.getString("no_order");
            order.setNo_order(object.getString("no_order"));
            order.setDt_order(object.getString("dt_order"));
            order.setName_goods(object.getString("name_goods"));
            order.setNotify_url(object.getString("notify_url"));
            // TODO MD5 签名方式
            order.setSign_type(PayOrder.SIGN_TYPE_MD5);
            // TODO RSA 签名方式
            // order.setSign_type(PayOrder.SIGN_TYPE_RSA);
            order.setValid_order(object.getString("valid_order"));

            //填写基本参数
            order.setUser_id(object.getString("user_id"));
            order.setId_no(object.getString("id_no"));
            order.setAcct_name(object.getString("acct_name"));
            order.setMoney_order(object.getString("money_order"));

            order.setInfo_order(object.getString("info_order"));
            order.setCard_no(object.getString("card_no"));

            order.setFlag_modify("0");

            // 风险控制参数
            order.setRisk_item(object.getString("risk_item"));

            String sign = "";
//            if (is_preauth) {
            order.setOid_partner(object.getString("oid_partner"));
//            } else {
//                order.setOid_partner(EnvConstants.PARTNER);
//            }
            String content = BaseHelper.sortParam(order);
            // TODO MD5 签名方式, 签名方式包括两种，一种是MD5，一种是RSA 这个在商户站管理里有对验签方式和签名Key的配置。
//            if (is_preauth) {
//                sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
//            } else {
//                sign = Md5Algorithm.getInstance().sign(content, EnvConstants.MD5_KEY);
//            }
            // RSA 签名方式
            // sign = Rsa.sign(content, EnvConstants.RSA_PRIVATE);
            order.setSign(object.getString("sign"));
            return order;
        }

        private Handler mHandler = createHandler();

        private Handler createHandler() {
            return new Handler() {
                public void handleMessage(Message msg) {
                    String strRet = (String) msg.obj;
                    switch (msg.what) {
                        case Constants.RQF_PAY: {
                            JSONObject objContent = BaseHelper.string2JSON(strRet);
                            String retCode = objContent.optString("ret_code");
                            String retMsg = objContent.optString("ret_msg");

                            // 成功
                            if (Constants.RET_CODE_SUCCESS.equals(retCode)) {
                                // TODO 卡前置模式返回的银行卡绑定协议号，用来下次支付时使用，此处仅作为示例使用。正式接入时去掉

                                paySuccessRequest();

                                mUserInfoEntity.setIsRecharged(true);
                                ACache.get(BaseApplication.getInstance()).put("userinfo", mUserInfoEntity);

//                                BaseHelper.showDialog(QuickRechargePage.this.getActivity(), "提示",
//                                        "支付成功，交易状态码：" + retCode,
//                                        android.R.drawable.ic_dialog_alert);

                                //页面跳转处理
//                                Intent intent = new Intent(BaseApplication.getInstance(), RichesRechargeSuccPage.class);
//                                intent.putExtra("paynum",20);
//                                intent.putExtra("yue",30);
//                                startActivity(intent);

                            } else if (Constants.RET_CODE_PROCESS.equals(retCode)) {
                                // TODO 处理中，掉单的情形
                                String resulPay = objContent.optString("result_pay");
                                if (Constants.RESULT_PAY_PROCESSING
                                        .equalsIgnoreCase(resulPay)) {
                                    BaseHelper.showDialog(QuickRechargePage.this.getActivity(), "提示",
                                            objContent.optString("ret_msg") + "交易状态码："
                                                    + retCode,
                                            android.R.drawable.ic_dialog_alert);
                                }

                            } else {
                                // TODO 失败
                                BaseHelper.showDialog(QuickRechargePage.this.getActivity(), "提示", retMsg
                                                + "，交易状态码:" + retCode,
                                        android.R.drawable.ic_dialog_alert);
                            }
                        }
                        break;
                    }
                    super.handleMessage(msg);
                }
            };
        }


        private String constructRiskItem() {
            JSONObject mRiskItem = new JSONObject();
            try {
                mRiskItem.put("user_info_bind_phone", "13958069593");
                mRiskItem.put("user_info_dt_register", "201407251110120");
                mRiskItem.put("frms_ware_category", "4.0");
                mRiskItem.put("request_imei", "1122111221");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return mRiskItem.toString();
        }


        private void initRechargeRequest() {
            try {

                RequestParams params = new RequestParams();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("idcard", mRechargeCard.trim());
                jsonObject.put("fullname", mRechargeName.trim());
                jsonObject.put("money", mRechargeNum.trim());
                jsonObject.put("card_no", mCardNum.trim());
                jsonObject.put("type", 1);
                params.put("data", jsonObject.toString());

                sendRequest(HttpRequst.REQ_TYPE_RECHARGE,
                        HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_RECHARGE),
                        params,
                        mAct.getAsyncClient(), false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private void paySuccessRequest() {

            try {
                RequestParams params = new RequestParams();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tradno", mTradno);
                jsonObject.put("money", mRechargeNum);
                params.put("data", jsonObject.toString());

                sendRequest(HttpRequst.REQ_TYPE_QUICKPAY_SUCC,
                        HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_QUICKPAY_SUCC),
                        params,
                        mAct.getAsyncClient(), false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private void popuDailog() {
            final SweetAlertDialog sad = new SweetAlertDialog(this.getActivity(), SweetAlertDialog.WARNING_TYPE);
            String str =
                    "为保障用户资金安全，本次充值成功后，该卡即为用户默认充值及提现银行卡，不可更改、删除。\n\n详情请致电客服：400-880-6270\n";
            sad.setTitleText(str);
            sad.setCancelText("取消");
            sad.setConfirmText("确认");
            sad.show();

            sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sad.cancel();
                }
            });

            sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sad.cancel();
                    sumbitRechargeRequest();
                }
            });
        }

        public static String ToDBC(String input) {
            char[] c = input.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (c[i] == 12288) {
                    c[i] = (char) 32;
                    continue;
                }
                if (c[i] > 65280 && c[i] < 65375)
                    c[i] = (char) (c[i] - 65248);
            }
            return new String(c);
        }

        public static String StringFilter(String str) throws PatternSyntaxException {
            str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!");//替换中文标号
            String regEx = "[『』]"; // 清除掉特殊字符
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("").trim();
        }


        private void sumbitRechargeRequest() {
            if (isIdentycheck) {
                String rechargeStr = mRechargeText.getEditableText().toString();
                mRechargeNum = rechargeStr;
                if (rechargeStr == null && rechargeStr.equals("")) {
                    Toast.makeText(this.getActivity(), "请输入正确的金额", Toast.LENGTH_LONG).show();
                    return;
                }

                if (Float.parseFloat(rechargeStr) > 0) {
                    initRechargeRequest();
                }
            } else {
                String rechargeStr = mRechargeText.getEditableText().toString().trim();
                mRechargeName = mRechargeNameText.getEditableText().toString().trim();
                mRechargeCard = mRechargeIdCodeText.getEditableText().toString().trim();
                mRechargeNum = rechargeStr;
                if (rechargeStr.equals("")) {
                    Toast.makeText(this.getActivity(), "请输入正确的金额", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mRechargeName.equals("")) {
                    Toast.makeText(this.getActivity(), "请输入姓名", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mRechargeCard.equals("")) {
                    Toast.makeText(this.getActivity(), "请输入身份证号码", Toast.LENGTH_LONG).show();
                    return;
                }

                if (Float.parseFloat(rechargeStr) > 0) {
                    initRechargeRequest();
                }
            }
        }
    }

    protected void onBackIcon()
    {
        super.onBackIcon();

        if(mUrlType == 0)
        {
            final Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("login_state", 1);
            startActivity(intent);
        }
        else if(mUrlType == 1)
        {
           finish();
        }
   ;
    }

    public void onBackPressed() {
        super.onBackPressed();
        if(mUrlType == 0)
        {
            final Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("login_state", 1);
            startActivity(intent);
        }
        else if(mUrlType == 1)
        {
            finish();
        }
    }
}
