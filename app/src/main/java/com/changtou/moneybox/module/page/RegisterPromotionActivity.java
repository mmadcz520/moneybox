package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.usermodule.LoginNotifier;
import com.changtou.moneybox.module.usermodule.UserManager;

import org.json.JSONObject;

/**
 * 注册好友推荐页面
 * Created by Administrator on 2015/5/18.
 */
public class RegisterPromotionActivity extends CTBaseActivity implements LoginNotifier
{
    private Button mSumbitBtn = null;
    private Button mCancalBtn = null;

    private TextView mPhoneView = null;

    private int mURLType = 0;

    private String mPhoneNum = null;

    private String mPassword = null;

    private UserManager mUserManager = null;

    private SharedPreferencesHelper sph = null;

    private boolean isTuijian = false;

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_register_promotion_layout);
        mPhoneView = (TextView)findViewById(R.id.riches_promotion_phone);

        Intent intent = getIntent();
        mURLType = intent.getIntExtra("urltype", 0);
        mCancalBtn = (Button)findViewById(R.id.riches_pro_cancalbtn);
        mSumbitBtn = (Button)findViewById(R.id.riches_pro_okbtn);

        if(mURLType == 1)
        {
            mCancalBtn.setText("跳 过");
            mPhoneNum = intent.getStringExtra("phonenum");
            mPassword = intent.getStringExtra("password");
        }
        else
        {
            mCancalBtn.setText("取 消");
        }

        mUserManager = BaseApplication.getInstance().getUserModule();
        mUserManager.setLoginNotifier(this);
        sph = SharedPreferencesHelper.getInstance(getApplicationContext());

        TextView textView = (TextView)findViewById(R.id.riches_promo_jiangliguize);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterPromotionActivity.this, WebActivity.class);
                intent.putExtra("url", "https://m.changtounet.com/APP_web/Recommend.htm");
                startActivity(intent);
            }
        });

        getPromoRequest();
    }

    protected void initListener()
    {
        setOnClickListener(R.id.riches_pro_okbtn);
        setOnClickListener(R.id.riches_pro_cancalbtn);
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    public void treatClickEvent(int id)
    {
        switch (id) {
            case R.id.riches_pro_okbtn:
                if(isTuijian)
                {
                    Toast.makeText(this,"您已绑定过推荐人", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String num = mPhoneView.getText().toString();
                    postPromoRequest(num);
                }

                break;

            case R.id.riches_pro_cancalbtn:

                if(mURLType == 1)
                {
                    sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.LOGIN.toString());
                    sph.putString(AppCfg.GSPD, "");

                    BaseApplication.getInstance().onBackground();

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("login_state", 1);
                    this.startActivity(intent);
                }
                else
                {
                    finish();
                }

                break;
        }
    }

    public void onSuccess(String content, Object object, int reqType) {
        super.onSuccess(content, object, reqType);
        if(reqType == HttpRequst.REQ_TYPE_PROMO)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                int errcode = data.getInt("errcode");
                if(errcode == 0 && mURLType == 1)
                {
                    sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.LOGIN.toString());
                    sph.putString(AppCfg.GSPD, "");

                    BaseApplication.getInstance().onBackground();

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("login_state", 1);
                    this.startActivity(intent);
                }

                Toast.makeText(this, "" +data.getString("msg"), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(reqType == HttpRequst.REQ_TYPE_TUIJIANREN)
        {
            try {

                JSONObject data = new JSONObject(content);
                int errcode = data.getInt("errcode");
                String phone = data.getString("mobile");
                if(errcode == 0)
                {
                    mSumbitBtn.setEnabled(false);
                    isTuijian = true;
                    mPhoneView.setText(phone);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        mSumbitBtn.setEnabled(true);
        super.onFailure(error, content, reqType);
    }


    /**
     * 获取推荐人信息
     */
    private void  getPromoRequest()
    {
        sendRequest(HttpRequst.REQ_TYPE_TUIJIANREN,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_TUIJIANREN),
                mParams,
                getAsyncClient(), false);
    }

    /**
     * 绑定推荐人请求
     */
    private void  postPromoRequest(String mobiles)
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PROMO);

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobiles);
            if(mURLType == 1)
            {
                jsonObject.put("type", 0);
            }
            else
            {
                jsonObject.put("type", 1);
            }

            params.put("data", jsonObject.toString());
            sendRequest(HttpRequst.REQ_TYPE_PROMO, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void loginSucNotify() {

        sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.LOGIN.toString());
        sph.putString(AppCfg.GSPD, "");

        BaseApplication.getInstance().onBackground();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("login_state", 1);
        this.startActivity(intent);

    }

    @Override
    public void loginIngNotify()
    {

    }

    @Override
    public void loginErrNotify(int errcode)
    {
    }

    @Override
    public void logoutNotify() {

    }

    @Override
    public void loginUserInfo(Object oject) {

    }
}
