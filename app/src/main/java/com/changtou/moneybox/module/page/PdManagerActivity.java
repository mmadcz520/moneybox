package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.usermodule.LoginNotifier;
import com.changtou.moneybox.module.usermodule.UserManager;
import com.changtou.moneybox.module.widget.ExEditView;
import com.changtou.moneybox.module.widget.VerifyDialog;

/**
 * 1. 密码管理页面
 *
 * Created by Administrator on 2015/6/1.
 */
public class PdManagerActivity extends CTBaseActivity implements VerifyDialog.VerifyListener, LoginNotifier, View.OnTouchListener
{
    private SharedPreferencesHelper sph = null;

    private VerifyDialog mVerifyDialog = null;

    private UserManager mUserManager = null;

    private ExEditView loginBtn = null;
    private ExEditView gestureBtn = null;

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.password_mananger_activity);
        sph = SharedPreferencesHelper.getInstance(getApplicationContext());


        mUserManager = BaseApplication.getInstance().getUserModule();
        mUserManager.setLoginNotifier(this);

        loginBtn = (ExEditView)findViewById(R.id.btn_pd_login);
        gestureBtn = (ExEditView)findViewById(R.id.btn_pd_gesture);

        loginBtn.setOnTouchListener(this);
        gestureBtn.setOnTouchListener(this);
    }

    protected void initListener()
    {
//        setOnClickListener(R.id.btn_pd_login);
//        setOnClickListener(R.id.btn_pd_gesture);
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.btn_pd_login:
            {
                Intent intent = new Intent(this, PdFrogetActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.btn_pd_gesture:
            {
                mVerifyDialog = new VerifyDialog(this);
                mVerifyDialog.show();
                mVerifyDialog.setVerifyListener(this);

                break;
            }
        }
    }

    public void onConfirm(String username, String password)
    {
        if (username.equals("") || password.equals(""))
        {
            Toast.makeText(this, "请输入有效密码", Toast.LENGTH_LONG).show();
        }
        else
        {
            mUserManager.logIn(username, password);
        }
    }

    public void onCancal()
    {
        mVerifyDialog.cancel();
    }

    @Override
    public void loginSucNotify()
    {
        Toast.makeText(this, "验证密码成功", Toast.LENGTH_LONG).show();
        mVerifyDialog.cancel();

        sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.LOGIN.toString());
        sph.putString(AppCfg.GSPD, "");

        BaseApplication.getInstance().onBackground();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("login_state", 1);
        this.startActivity(intent);
    }

    @Override
    public void loginIngNotify() {

    }

    @Override
    public void loginErrNotify(int errcode)
    {
        Toast.makeText(this, "验证密码失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void logoutNotify() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int id = v.getId();


        switch (id)
        {
            case R.id.btn_pd_login:
            {
                Intent intent = new Intent(this, PdFrogetActivity.class);
                startActivity(intent);

                break;
            }

            case R.id.btn_pd_gesture:
            {
                mVerifyDialog = new VerifyDialog(this);
                mVerifyDialog.show();
                mVerifyDialog.setVerifyListener(this);

                break;
            }
        }

        return false;
    }
}
