package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.usermodule.LoginNotifier;
import com.changtou.moneybox.module.usermodule.UserManager;
import com.changtou.moneybox.module.widget.ExEditView;
import com.changtou.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends CTBaseActivity implements LoginNotifier{

    ImageView vc_image; //图标

    private Button mLoginButton = null;

    private ExEditView mUserNameView = null;
    private ExEditView mPassWordView = null;

    private UserManager mUserManager = null;

    private SweetAlertDialog mDialog = null;

    //忘记密码
    private TextView mForgetPd = null;

    //记录是否登录
    private SharedPreferencesHelper sph = null;

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_login_layout);

        mLoginButton = (Button)findViewById(R.id.login_btn);

        mUserNameView = (ExEditView)findViewById(R.id.login_username);
        mPassWordView = (ExEditView)findViewById(R.id.login_password);

        mForgetPd = (TextView)findViewById(R.id.forgot_password);

        mUserManager = BaseApplication.getInstance().getUserModule();
        mUserManager.setLoginNotifier(this);

//        TextView loginBtn = (TextView)findViewById(R.id.login_btn);
//        final Intent intent = new Intent(this, RichesPhoneBookActivity.class);
//        mForgetPd.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                UserManager um = BaseApplication.getInstance().getUserModule();
//                um.logIn("", "");
//
//                LoginActivity.this.setResult(RESULT_OK, intent);
//                LoginActivity.this.finish();
//            }
//        });
//
        TextView registBtn = (TextView)findViewById(R.id.loginpage_register_btn);
        final Intent intent2 = new Intent(this, RegisterActivity.class);
        registBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                startActivity(intent2);
            }
        });

        sph = SharedPreferencesHelper.getInstance(getApplicationContext());
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

                if (username.equals("") || password.equals("")) {

                    //注册成功后弹窗
//                    ColorStateList redColors = ColorStateList.valueOf(0xffff0000);
//                    SpannableStringBuilder spanBuilder = new SpannableStringBuilder("恭喜你注册成功！获得10元礼金\n 完善认证信息你将在获得20元礼金");
//                    //style 为0 即是正常的，还有Typeface.BOLD(粗体) Typeface.ITALIC(斜体)等
//                    //size  为0 即采用原始的正常的 size大小
//                    spanBuilder.setSpan(new TextAppearanceSpan(null, 0, 60, redColors, null), 10, 12, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//                    spanBuilder.setSpan(new TextAppearanceSpan(null, 0, 60, redColors, null), 28, 30, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//
//                    SpannableStringBuilder spanBuilder1 = new SpannableStringBuilder("继续认证得20元");
//                    spanBuilder1.setSpan(new TextAppearanceSpan(null, 0, 60, redColors, null), 5, 7, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//
//                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
//                            .setConfirmText(spanBuilder1) .setContentText(spanBuilder)
//                            .setCancelText("先逛逛")
//                            .show();

//                    mUserManager.logIn(username, password);
                } else {
                    mUserManager.logIn(username, password);
                    mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("登陆");
                    mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.ct_blue));
                    mDialog.getProgressHelper().setRimColor(getResources().getColor(R.color.ct_blue_hint));
                    mDialog.setCancelText("取消");
                    mDialog.setContentText("努力加载中...");
                    mDialog.show();
                    mDialog.setCancelable(true);

                    sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.LOGIN.toString());

                    //清空手势密码
                    sph.putString(AppCfg.GSPD, "");
                }
                break;
            }

            case R.id.forgot_password:
            {
                final Intent intent = new Intent(this, PdFrogetActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    protected void initData() {

    }

    /**
     * 登陆成功回调函数
     */
    public void loginSucNotify() {
        Intent intent = new Intent(this, MainActivity.class);
        LoginActivity.this.setResult(RESULT_OK, intent);
        LoginActivity.this.finish();
    }

    @Override
    public void loginIngNotify() {

    }

    @Override
    public void loginErrNotify() {
        mDialog.setContentText("账号密码错误");
        mDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
    }

    @Override
    public void logoutNotify() {

    }

    protected void onStop() {
        super.onStop();
    }

    /**
     * 禁用back按键
     */
    public void onBackPressed()
    {
//        super.onBackPressed();
    }

}