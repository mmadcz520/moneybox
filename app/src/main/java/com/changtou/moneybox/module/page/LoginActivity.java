package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.module.usermodule.LoginNotifier;
import com.changtou.moneybox.module.usermodule.UserManager;
import com.changtou.moneybox.module.widget.ExEditView;
import com.changtou.R;

public class LoginActivity extends CTBaseActivity implements LoginNotifier{

    ImageView vc_image; //图标

    private Button mLoginButton = null;

    private ExEditView mUserNameView = null;
    private ExEditView mPassWordView = null;

    private UserManager mUserManager = null;

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_login_layout);

        mLoginButton = (Button)findViewById(R.id.login_btn);

        mUserNameView = (ExEditView)findViewById(R.id.login_username);
        mPassWordView = (ExEditView)findViewById(R.id.login_password);

        mUserManager = BaseApplication.getInstance().getUserModule();
        mUserManager.setLoginNotifier(this);

//        TextView loginBtn = (TextView)findViewById(R.id.login_btn);
//        final Intent intent = new Intent(this, MainActivity.class);
//        loginBtn.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                UserManager um = BaseApplication.getInstance().getUserModule();
//                um.logIn("", "");
//
//                LoginActivity.this.setResult(RESULT_OK, intent);
//                LoginActivity.this.finish();
//            }
//        });
//
//        TextView registBtn = (TextView)findViewById(R.id.loginpage_register_btn);
//        final Intent intent2 = new Intent(this, RegisterActivity.class);
//        registBtn.setOnClickListener(new OnClickListener() {
//            public void onClick(View v)
//            {
//                startActivity(intent2);
//            }
//        });
    }

    protected void initLisener() {
        setOnClickListener(R.id.login_btn);
    }

    public void treatClickEvent(int id) {
        switch (id) {
            case R.id.login_btn:
                String username = mUserNameView.getEditValue();
                String password = mPassWordView.getEditValue();
                mUserManager.logIn(username, password);

                mLoginButton.setEnabled(false);
                mUserNameView.setEnabled(false);
                mPassWordView.setEnabled(false);

                mLoginButton.setText("登录中...");

                break;
        }
    }

    protected void initData() {

    }

    /**
     * 登陆成功回调函数
     */
    public void loginSucNotify() {
        mLoginButton.setEnabled(true);
        mLoginButton.setText("登录");

        Intent intent = new Intent(this, MainActivity.class);
        LoginActivity.this.setResult(RESULT_OK, intent);
        LoginActivity.this.finish();
    }

    @Override
    public void loginIngNotify() {

    }

    @Override
    public void loginErrNotify() {

    }

    @Override
    public void logoutNotify() {

    }
}

