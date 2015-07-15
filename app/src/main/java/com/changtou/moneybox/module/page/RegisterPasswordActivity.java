package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.usermodule.LoginNotifier;
import com.changtou.moneybox.module.usermodule.UserManager;
import com.changtou.moneybox.module.widget.ExEditView;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 确认密码页
 * Created by Administrator on 2015/5/18.
 */
public class RegisterPasswordActivity extends CTBaseActivity implements LoginNotifier
{
    private ExEditView mPasswordText = null;
    private ExEditView mAffirmText = null;

    private String mPhoneNum = "";
    private String mPassword = "";

    private UserManager mUserManager = null;

    private SharedPreferencesHelper sph = null;

    private int mPageType = 0;
    private String mCheckSum = "";

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_register_password_layout);

        Intent intent = this.getIntent();
        mPageType = intent.getIntExtra("type", 0);
        if(mPageType == 1)
        {
            mCheckSum = intent.getStringExtra("code");
        }

        mPhoneNum = intent.getStringExtra("phoneNum");

        mPasswordText = (ExEditView)findViewById(R.id.register_password);
        mAffirmText = (ExEditView)findViewById(R.id.register_affirm_password);

        mUserManager = BaseApplication.getInstance().getUserModule();
        mUserManager.setLoginNotifier(this);
        sph = SharedPreferencesHelper.getInstance(getApplicationContext());
    }

    protected void initListener()
    {
        setOnClickListener(R.id.riches_register_do);
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
            case R.id.riches_register_do:

                mPassword = mPasswordText.getEditValue();
                String affirm = mAffirmText.getEditValue();

                if(mPassword.length() < 6)
                {
                    Toast.makeText(this, "您设置的密码过短", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!mPassword.equals(affirm))
                {
                    Toast.makeText(this, "两次设置密码不一致, 重新输入", Toast.LENGTH_LONG).show();
                    return;
                }

                if(mPageType == 0)
                {
                    postregRequest(mPhoneNum, mPassword);
                }
                else
                {
                    postnewpwdRequest(mPhoneNum, mPassword);
                }

                break;
        }
    }

    public void onSuccess(String content, Object object, int reqType) {
        super.onSuccess(content, object, reqType);

        if(reqType == HttpRequst.REQ_TYPE_POSTREG)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                int error = data.getInt("errcode");

                if(error == 0)
                {
                    popoSuccDialog();
                }
                else if(error == 1)
                {
                    Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_LONG).show();
                }
                else if(error == 2)
                {
                    Toast.makeText(this, "该手机号码已被使用", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, "内部错误", Toast.LENGTH_LONG).show();
            }
        }

        if(reqType == HttpRequst.REQ_TYPE_NEWPWD)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                int error = data.getInt("errcode");
                if(error == 0)
                {
                    Toast.makeText(this, "修改密码成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
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
     * 注册请求
     */
    private void  postnewpwdRequest(String mobiles, String password)
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_NEWPWD) +
                    "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                    "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobiles);
            jsonObject.put("pwd", password);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_NEWPWD, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 注册请求
     */
    private void  postregRequest(String mobiles, String password)
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_POSTREG) +
                    "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                    "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mobile", mobiles);
            jsonObject.put("pwd", password);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_POSTREG, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 注册成功后弹框
     */
    private void popoSuccDialog()
    {
        //注册成功后弹窗
        ColorStateList redColors = ColorStateList.valueOf(0xffff0000);
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder("恭喜你注册成功！获得10元礼金\n 完善认证信息你将在获得20元礼金");
        //style 为0 即是正常的，还有Typeface.BOLD(粗体) Typeface.ITALIC(斜体)等
        //size  为0 即采用原始的正常的 size大小
        spanBuilder.setSpan(new TextAppearanceSpan(null, 0, 60, redColors, null), 10, 12, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spanBuilder.setSpan(new TextAppearanceSpan(null, 0, 60, redColors, null), 28, 30, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        SpannableStringBuilder spanBuilder1 = new SpannableStringBuilder("继续认证得20元");
        spanBuilder1.setSpan(new TextAppearanceSpan(null, 0, 60, redColors, null), 5, 7, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        final SweetAlertDialog sad = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        sad.setConfirmText(spanBuilder1) .setContentText(spanBuilder);
        sad.setCancelText("先逛逛");
        sad .show();

        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                sad.cancel();
                mUserManager.logIn(mPhoneNum, mPassword);
            }
        });

        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                sad.cancel();

                Intent intent = new Intent(RegisterPasswordActivity.this, RichesSafeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loginSucNotify()
    {
        sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.LOGIN.toString());
        sph.putString(AppCfg.GSPD, "");

        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finish();
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
}
