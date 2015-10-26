package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.service.ReadSMsTool;
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

    //提交验证码
    private Button mBtnSubmitCS = null;

    private Counter mCounter = null;

    private int restCnt = 0;

    private ACache mACache = null;

    private ExEditView mAccountEditView = null;
    private ExEditView mCodeText = null;

    private String mPhoneNum = null;
    private String mCheckSum = null;

    private int mPageType = 0;

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.password_forget_activity);
        mBtnSendCS = (Button)findViewById(R.id.btn_send_checksum);

        mBtnSubmitCS = (Button)findViewById(R.id.password_forget_submit);

        mAccountEditView = (ExEditView)findViewById(R.id.forget_pwd_account);
        mCodeText = (ExEditView)findViewById(R.id.forget_pwd_code);

        mACache = ACache.get(this);
        restTimeCount();

        ReadSMsTool readSMsTool = BaseApplication.getInstance().getReadSMsTool();
        readSMsTool.setSMSReadListener(new ReadSMsTool.ReadSMSListener()
        {
            public void readCallback(String code)
            {
                mCodeText.setEditValue(code);
            }
        });
    }

    protected void initListener()
    {
        setOnClickListener(R.id.btn_send_checksum);
        setOnClickListener(R.id.password_forget_submit);
    }

    @Override
    protected void initData()
    {
        Intent intent = this.getIntent();
        mPageType = intent.getIntExtra("pageType",0);
        if(mPageType == 0)
        {
            setPageTitle("忘记密码");
        }
        else
        {
            setPageTitle("手机认证");
            if(intent.getBooleanExtra("isPhoneauth", false))
            {
                mBtnSendCS.setEnabled(false);
                mBtnSubmitCS.setEnabled(false);
                String phone = UserInfoEntity.getInstance().getMobile();
                mAccountEditView.setEditValue(phone);
            }
        }

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
//                    sendMsgRequest(mPhoneNum);
                    isRegister(mPhoneNum);
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
                mPhoneNum = mAccountEditView.getEditValue();
                if(!mCheckSum.equals("") && isMobileNO(mPhoneNum))
                {
                    mCheckSum = mCodeText.getEditValue();
                    checkCodeRequest(mPhoneNum, mCheckSum);
                }
                else
                {
                    Toast.makeText(this, "输入有误, 请重新输入", Toast.LENGTH_LONG).show();
                }


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
//        if(mACache.getAsString("restCnt") == null)
//        {
            restCnt = 0;
//        }
//        else
//        {
//            restCnt =  (int)mACache.getAsObject("restCnt");
//        }

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
                    if(mPageType == 0)
                    {
                        final Intent intent = new Intent(this, RegisterPasswordActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("phoneNum",mPhoneNum);
                        intent.putExtra("code", mCheckSum);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(this,"验证手机成功", Toast.LENGTH_LONG).show();
                    }

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

        if(reqType == HttpRequst.REQ_TYPE_ISREG)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                boolean error = data.getBoolean("isreg");

                if(!error)
                {
                    Toast.makeText(this, "该手机未注册，请先注册", Toast.LENGTH_LONG).show();
                }
                else
                {
                    sendMsgRequest(mPhoneNum);
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

    /**
     * 是否是注册过的
     */
    private void isRegister(String mobiles)
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_ISREG);

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobiles);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_ISREG, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
