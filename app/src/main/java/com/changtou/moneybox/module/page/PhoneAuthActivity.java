package com.changtou.moneybox.module.page;

import android.os.Bundle;

import com.changtou.R;
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

    protected void initView(Bundle bundle)
    {
        super.initView(bundle);
        setContentView(R.layout.riches_safe_phoneauth);
    }

    @Override
    protected void initListener()
    {
        super.initListener();
    }

    @Override
    protected void initData() {
        super.initData();
        setPageTitle("手机认证");
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
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

    public static boolean isMobileNO(String mobiles)
    {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }
}
