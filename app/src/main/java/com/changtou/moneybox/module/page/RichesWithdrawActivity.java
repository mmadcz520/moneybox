package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/21.
 * 提现页面
 */
public class RichesWithdrawActivity extends CTBaseActivity
{
    private TextView mBankNoView = null;
    private ImageView mBankIconView = null;

    private Map<String, String> mBankInfoList = null;

    private EditText mWithdrawNum = null;
    private TextView mWithdrawMoney = null;
    private TextView mWithdrawTotle = null;
    private TextView mWithdrawHandling = null;

    private String mOverage = "";
    private String mHandling = "5";

    private Button mSubmitBtn = null;

    //默认银行卡号
    private String mDefaultBankNo = "";

    private String[] mError = {"取现成功", "取现失败", "没有绑定银行卡", "没有绑定支行信息", "用户设置银行卡有问题", "没有实名认证", "余额不足"};

    private UserInfoEntity mUserInfoEntity = null;

    @Override
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_withdraw_layout);

        final Intent intent3 = new Intent(this, RichesBankActivity.class);

        RelativeLayout ll = (RelativeLayout)findViewById(R.id.riches_bank_item);
        ll.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent3);
            }
        });

        mWithdrawNum = (EditText)findViewById(R.id.riches_withdraw_number);
        mWithdrawNum.addTextChangedListener(mTextWatcher);

        mWithdrawMoney = (TextView)findViewById(R.id.riches_withdraw_money);
        mWithdrawTotle = (TextView)findViewById(R.id.riches_withdraw_total);
        mWithdrawHandling = (TextView)findViewById(R.id.riches_withdraw_handling);

        mBankNoView = (TextView)findViewById(R.id.riches_bank_item_no);
        mBankIconView = (ImageView)findViewById(R.id.riches_bank_item_logo);

        mSubmitBtn = (Button)findViewById(R.id.affirm_withdraw_btn);

        mBankInfoList = BaseApplication.getInstance().getBankInfoList();
    }

    @Override
    protected void initData()
    {
        mUserInfoEntity = (UserInfoEntity)ACache.get(this).getAsObject("userinfo");

        initBankInfo();
        setPageTitle("提现");

        mOverage = mUserInfoEntity.getOverage();
//        mWithdrawTotle.setText(customFormat(mOverage));
//        mWithdrawHandling.setText("-" + customFormat("5"));

        makeWithdrawInfoRequest();

        super.initData();
    }

    private void initBankInfo()
    {
        BankCardEntity bank = mUserInfoEntity.getBankCardEntity();

        int len = bank.mList.size();
        BankCardEntity.BankListEntity bList ;

        for(int i = 0; i < len; i++)
        {
            bList = (BankCardEntity.BankListEntity)bank.mList.get(i);
            String account = bList.account;
            if(account.length()>10)
            {
                account = account.substring(0,4) + " **** " + account.substring(account.length()-4,account.length());
            }
            if(bList.isdefault.equals("是"))
            {
                mDefaultBankNo = bList.account;

                mBankNoView.setText(account);
                setBankIcon(mBankIconView, bList.bank);
            }
        }
    }

    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    private void setBankIcon(ImageView imageView, String bankName) {
        InputStream open = null;
        try {
            if (mBankInfoList == null) return;
            String temp = mBankInfoList.get(bankName);
            if (temp == null || !temp.startsWith("bank_icon")) return;

            open = this.getAssets().open(temp);
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

    /**
     * 输入取现金额时监听函数
     */
    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        public void afterTextChanged(Editable s)
        {
            Float input_f;
            Float money_f;

            try {
                mHandling = mHandling.replace(",", "");
                money_f = Float.parseFloat(mHandling);

                String input = s.toString();
                input = input.replace(",", "");
                input_f = Float.parseFloat(input);
            }
            catch (Exception e)
            {
                money_f = 0f;
                input_f = 0f;
            }

            Float m = input_f - money_f;
            if(m > 0)
            {
                mSubmitBtn.setEnabled(true);
                mWithdrawMoney.setText(customFormat(m.toString()));
            }
            else
            {
                mSubmitBtn.setEnabled(false);
                mWithdrawMoney.setText("");
            }
        }
    };

    public String customFormat(String value) {
        try
        {
            value = value.replace(",","");
            double f = Double.parseDouble(value);

            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumFractionDigits(2);
            nf.setMaximumFractionDigits(2);
            String output =  nf.format(f);
            return output;
        }
        catch (Exception e)
        {
            Toast.makeText(RichesWithdrawActivity.this,"请输入正确格式", Toast.LENGTH_LONG).show();
            return "";
        }
    }

    @Override
    protected void initListener()
    {
        setOnClickListener(R.id.affirm_withdraw_btn);
    }

    @Override
    public void treatClickEvent(int id)
    {
        makeWithdrawRequest();
    }

    /**
     * id
     * token
     * 投资金额
     * 银行卡号
     *
     * 提现请求
     */
    private void makeWithdrawRequest()
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_WITHDRAW) +
                    "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                    "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

            String num = mWithdrawNum.getEditableText().toString();

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("num", num);
            jsonObject.put("account", mDefaultBankNo);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_WITHDRAW, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void  makeWithdrawInfoRequest()
    {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_WITHDRAWINFO) +
                "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

        sendRequest(HttpRequst.REQ_TYPE_WITHDRAWINFO, url, mParams, getAsyncClient(), false);
    }

    /**
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType)
    {
        super.onSuccess(content, object, reqType);

        if(reqType == HttpRequst.REQ_TYPE_WITHDRAW)
        {
            try
            {
                JSONObject json = new JSONObject(content);
                int errcode = json.getInt("errorcode");

                int code = (errcode < mError.length) ? errcode : (mError.length - 1);
                Toast toast = Toast.makeText(getApplicationContext(),
                        mError[code], Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                makeWithdrawInfoRequest();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(reqType == HttpRequst.REQ_TYPE_WITHDRAWINFO)
        {
            try
            {
                JSONObject json = new JSONObject(content);

                mHandling = json.getString("fee");
                mOverage = json.getString("yue");

                mWithdrawTotle.setText(customFormat(mOverage));
                mWithdrawHandling.setText("-" + customFormat(mHandling));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param error
     * @param content
     * @param reqType
     */
    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }
}
