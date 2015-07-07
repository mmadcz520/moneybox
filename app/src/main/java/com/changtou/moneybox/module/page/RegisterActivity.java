package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
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
 * 注册页面
 * Created by Administrator on 2015/5/18.
 */
public class RegisterActivity extends CTBaseActivity
{
    private ExEditView mPhoneNum = null;

    private String phoneNum = null;

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_register_layout);

        mPhoneNum = (ExEditView)findViewById(R.id.register_phone_no);
    }

    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    @Override
    protected void initListener()
    {
        setOnClickListener(R.id.register_btn);
    }

    public void treatClickEvent(int id)
    {
        phoneNum = mPhoneNum.getEditValue();

        if(isMobileNO(phoneNum))
        {
            sendMsgRequest(phoneNum);
        }
        else
        {
            Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isMobileNO(String mobiles)
    {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    @Override
    public void onSuccess(String content, Object object, int reqType)
    {
        super.onSuccess(content, object, reqType);
        if(reqType == HttpRequst.REQ_TYPE_SENDMSG)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                int error = data.getInt("errcode");

                if(error == 0)
                {
                    final Intent intent = new Intent(this, RegisterNextActivity.class);
                    intent.putExtra("phoneNum",phoneNum);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "发送短信失败", Toast.LENGTH_LONG).show();
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
