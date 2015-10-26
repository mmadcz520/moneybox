package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话号码认证页面
 *
 * Created by Administrator on 2015/7/10.
 */
public class PhoneAuthActivity extends CTBaseActivity
{
    private Button mCountBtn = null;
    private EditText mCodeText = null;
    private EditText mPhoneNumText = null;

    private Counter mCounter = null;

    protected void initView(Bundle bundle)
    {
        super.initView(bundle);
        setContentView(R.layout.riches_safe_phoneauth);

        mCountBtn = (Button)findViewById(R.id.phoneauth_checksum_send);
        mCodeText = (EditText)findViewById(R.id.phoneauth_checksum);
        mPhoneNumText = (EditText)findViewById(R.id.phoneauth_phonenum);

        mCounter = new Counter(60*1000, 1000);    //第一个参数是倒计时时间，后者为计时间隔，单位毫秒，这里是倒计时 5 分钟，间隔1秒
        mCounter.start();
    }

    @Override
    protected void initListener()
    {
        setOnClickListener(R.id.phoneauth_checksum_send);
        setOnClickListener(R.id.phoneauth_submit);
    }

    @Override
    protected void initData()
    {
        super.initData();
        setPageTitle("手机认证");
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    @Override
    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.phoneauth_submit:
            {
                String code = mCodeText.getText().toString();
                String phoneNum = mPhoneNumText.getText().toString();
                checkCodeRequest(phoneNum, code);
                break;
            }

            case R.id.phoneauth_checksum_send:
            {
                String phoneNum = mPhoneNumText.getText().toString();

                mCounter.start();
                mCountBtn.setEnabled(false);
                sendMsgRequest(phoneNum);
                break;
            }
        }
    }

    /**
     * 校验验证码
     */
    private void checkCodeRequest(String phone, String code)
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_CHECKCODE);

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", phone);
            jsonObject.put("code", code);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_CHECKCODE, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static boolean isMobileNO(String mobiles)
    {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    public class Counter extends CountDownTimer {

        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated method stub
        }

        @Override
        public void onFinish()
        {
            mCountBtn.setEnabled(true);
            mCountBtn.setText("发送验证码");
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            mCountBtn.setText("" + millisUntilFinished/1000 + "秒后重新发送");
        }
    }

    /**
     * 发送验证码短信
     */
    private void sendMsgRequest(String mobiles)
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_SENDMSG);

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobiles);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_SENDMSG, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
