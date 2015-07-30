package com.changtou.moneybox.module.page;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.usermodule.LoginNotifier;
import com.changtou.moneybox.module.usermodule.UserManager;
import com.changtou.moneybox.module.widget.ExEditView;
import com.changtou.R;
import com.changtou.moneybox.module.widget.ZProgressHUD;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends CTBaseActivity implements LoginNotifier{

    private ExEditView mUserNameView = null;
    private ExEditView mPassWordView = null;

    private UserManager mUserManager = null;

    private SweetAlertDialog mDialog = null;

    //记录是否登录
    private SharedPreferencesHelper sph = null;

    private Button mLoginBtn = null;

    private String[] mErrorContent = {"","用户名格式不正确", "不存在该用户名", "密码错误", "邮箱未激活", " 手机未激活", "服务器故障"};

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_login_layout);

        mUserNameView = (ExEditView)findViewById(R.id.login_username);
        mPassWordView = (ExEditView)findViewById(R.id.login_password);

        mUserManager = BaseApplication.getInstance().getUserModule();
        mUserManager.setLoginNotifier(this);

        mLoginBtn = (Button)findViewById(R.id.login_btn);

        TextView registBtn = (TextView)findViewById(R.id.loginpage_register_btn);
        final Intent intent2 = new Intent(this, RegisterActivity.class);
        registBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                startActivity(intent2);
            }
        });

        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("登陆");
        mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.ct_blue));
        mDialog.getProgressHelper().setRimColor(getResources().getColor(R.color.ct_blue_hint));
        sph = SharedPreferencesHelper.getInstance(getApplicationContext());

        mZProgressHUD.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            public void onDismiss(DialogInterface dialog)
            {
                mLoginBtn.setEnabled(true);
            }
        });
    }

    protected void initListener() {
        setOnClickListener(R.id.login_btn);
        setOnClickListener(R.id.forgot_password);
    }

    public void treatClickEvent(int id) {
        switch (id) {
            case R.id.login_btn: {
                String username = mUserNameView.getEditValue();
                String password = mPassWordView.getEditValue();

                if (username.equals("") || password.equals(""))
                {
                    popoErrorDialog();
                }
                else
                {
                    mZProgressHUD.show();
                    mUserManager.logIn(username, password);
                    mLoginBtn.setEnabled(false);
                }
                break;
            }

            case R.id.forgot_password:
            {
                final Intent intent = new Intent(this, PdFrogetActivity.class);
                intent.putExtra("pageType", 0);
                startActivity(intent);
                break;
            }
        }
    }

    protected void initData()
    {
        setPageTitle("登录");

    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    /**
     * 登陆成功回调函数
     */
    public void loginSucNotify()
    {
        getUserInfo();
    }

    public void loginIngNotify()
    {

    }

    public void loginErrNotify(int errcode)
    {
        mLoginBtn.setEnabled(true);
        mZProgressHUD.cancel();

        int code = (errcode < mErrorContent.length) ? errcode : (mErrorContent.length - 1);
        Toast toast = Toast.makeText(getApplicationContext(),
                mErrorContent[code], Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void logoutNotify()
    {

    }

    protected void onStop()
    {
        super.onStop();
    }

    /**
     * 禁用back按键
     */
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        LoginActivity.this.setResult(RESULT_CANCELED, intent);
        LoginActivity.this.finish();
    }

    /**
     * 弹出错误对话框
     */
    private void popoErrorDialog()
    {
        Toast toast = Toast.makeText(getApplicationContext(),
                "输入格式错误", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo()
    {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_USERINFO) +
                "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

        Log.e("CT_MONEY", ACache.get(BaseApplication.getInstance()).getAsString("token"));

        sendRequest(HttpRequst.REQ_TYPE_USERINFO, url, mParams,
                getAsyncClient(), false);
    }


    @Override
    public void onSuccess(String content, Object object, int reqType)
    {
        mLoginBtn.setEnabled(true);

        //初始化用户数据
        UserInfoEntity userInfo = UserInfoEntity.getInstance();
        ACache cache = ACache.get(BaseApplication.getInstance());
        cache.put("fullname", userInfo.getFullName());

        //缓存用户信息
        cache.put("userinfo", userInfo);

        //清空手势密码
        sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.LOGIN.toString());
        sph.putString(AppCfg.GSPD, "");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("login_state", 1);
        this.startActivity(intent);

        //初始化弹框
        cache.put("isPopu", true);
//        super.onSuccess(content, object, reqType);
    }

    @Override
    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }
}