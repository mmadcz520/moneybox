package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.ExEditView;

import org.json.JSONObject;

/**
 * 注册页面
 * Created by Administrator on 2015/5/18.
 */
public class RegisterNextActivity extends CTBaseActivity
{
    private ExEditView mCodeText = null;

    private Button mCountBtn = null;

    private String mPhoneNum = "";

    private Counter mCounter = null;

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_register_next_layout);

        mCodeText = (ExEditView)findViewById(R.id.register_code);

        mCountBtn = (Button)findViewById(R.id.register_count_btn);

        Intent intent = this.getIntent();
        mPhoneNum = intent.getStringExtra("phoneNum");

        mCounter = new Counter(60*1000, 1000);    //第一个参数是倒计时时间，后者为计时间隔，单位毫秒，这里是倒计时 5 分钟，间隔1秒
        mCounter.start();
    }

    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    protected void initListener()
    {
        setOnClickListener(R.id.register_next_btn);
        setOnClickListener(R.id.register_count_btn);
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.register_next_btn:
            {
                String code = mCodeText.getEditValue();
                checkCodeRequest(mPhoneNum, code);
                break;
            }

            case R.id.register_count_btn:
            {
                mCounter.start();
                mCountBtn.setEnabled(false);
                sendMsgRequest(mPhoneNum);
                break;
            }
        }
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        super.onSuccess(content, object, reqType);

        if(reqType == HttpRequst.REQ_TYPE_CHECKCODE)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                int error = data.getInt("errcode");

                if(error == 0)
                {
                    final Intent intent = new Intent(this, RegisterPasswordActivity.class);
                    intent.putExtra("phoneNum",mPhoneNum);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "验证码错误", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, "内部错误", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }

    /**
     * 校验验证码
     */
    private void checkCodeRequest(String phone, String code)
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_CHECKCODE) +
                    "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                    "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

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

    //
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
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_SENDMSG) +
                    "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                    "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

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
