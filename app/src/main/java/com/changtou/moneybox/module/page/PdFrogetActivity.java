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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 忘记密码
 *
 * Created by Administrator on 2015/6/1.
 */
public class PdFrogetActivity extends CTBaseActivity
{
    //发送验证码
    private Button mBtnSendCS = null;

    private Counter mCounter = null;

    private int restCnt = 0;

    private ACache mACache = null;

    private ExEditView mAccountEditView = null;
    private ExEditView mCodeText = null;

    private String mPhoneNum = null;
    private String mCheckSum = null;

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.password_forget_activity);
        mBtnSendCS = (Button)findViewById(R.id.btn_send_checksum);

        mAccountEditView = (ExEditView)findViewById(R.id.forget_pwd_account);
        mCodeText = (ExEditView)findViewById(R.id.forget_pwd_code);

        mACache = ACache.get(this);
        restTimeCount();
    }

    protected void initListener() {
        setOnClickListener(R.id.btn_send_checksum);
        setOnClickListener(R.id.password_forget_submit);
    }

    @Override
    protected void initData() {
        setPageTitle("忘记密码");
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.btn_send_checksum:
            {
                mPhoneNum = mAccountEditView.getEditValue();

                if(isMobileNO(mPhoneNum))
                {
//                    mCounter = new Counter(60*1000, 1000);
//                    restCnt = 60;
//                    mCounter.start();
                    sendMsgRequest(mPhoneNum);
                }
                else
                {
                    Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_LONG).show();
                }

                break;
            }

            case R.id.password_forget_submit:
            {
//                final Intent intent = new Intent(this, RegisterPasswordActivity.class);
//                startActivity(intent);

                mCheckSum = mCodeText.getEditValue();
                checkCodeRequest(mPhoneNum, mCheckSum);

                break;
            }

        }
    }


    public class Counter extends CountDownTimer {

        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

            mBtnSendCS.setEnabled(false);
        }

        @Override
        public void onFinish()
        {
            mBtnSendCS.setEnabled(true);
            mBtnSendCS.setText("发送验证码");
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            restCnt --;
            mBtnSendCS.setText("" + millisUntilFinished/1000 + "秒后重新发送");
        }
    }

    public void finish()
    {
        super.finish();
        mACache.put("restCnt", restCnt);
    }

    private void restTimeCount()
    {
        if(mACache.getAsString("restCnt") == null)
        {
            restCnt = 0;
        }
        else
        {
            restCnt =  (int)mACache.getAsObject("restCnt");
        }

        mCounter = new Counter(restCnt*1000, 1000);
        mCounter.start();
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

    public void onSuccess(String content, Object object, int reqType)
    {
        super.onSuccess(content, object, reqType);

        if(reqType == HttpRequst.REQ_TYPE_SENDMSG)
        {
            mCounter = new Counter(60*1000, 1000);
            restCnt = 60;
            mCounter.start();


            try
            {
                JSONObject data = new JSONObject(content);
                int error = data.getInt("errcode");

                if(error == 0)
                {
                    Toast.makeText(this, "已向您的手机" + mPhoneNum + "发送验证码", Toast.LENGTH_LONG).show();
                }
                else if(error == 1)
                {
                    Toast.makeText(this, "未发送短信验证码", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "验证不通过", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, "发送验证码内部错误", Toast.LENGTH_LONG).show();
            }
        }

        if(reqType == HttpRequst.REQ_TYPE_CHECKCODE)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                int error = data.getInt("errcode");

                if(error == 0)
                {
                    final Intent intent = new Intent(this, RegisterPasswordActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("phoneNum",mPhoneNum);
                    intent.putExtra("code", mCheckSum);
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
        Toast.makeText(this, "网络错误", Toast.LENGTH_LONG).show();
    }


    public static boolean isMobileNO(String mobiles)
    {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
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
}
