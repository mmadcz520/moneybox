package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.usermodule.LoginNotifier;
import com.changtou.moneybox.module.usermodule.UserManager;
import com.changtou.moneybox.module.widget.ExEditView;
import com.changtou.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends CTBaseActivity implements LoginNotifier{

    private ExEditView mUserNameView = null;
    private ExEditView mPassWordView = null;

    private UserManager mUserManager = null;

    private SweetAlertDialog mDialog = null;

    //记录是否登录
    private SharedPreferencesHelper sph = null;

    private String[] mErrorContent = {"","用户名格式不正确", "不存在该用户名", "密码错误", "邮箱未激活", " 手机未激活", "服务器故障"};

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_login_layout);

        mUserNameView = (ExEditView)findViewById(R.id.login_username);
        mPassWordView = (ExEditView)findViewById(R.id.login_password);

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

        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("登陆");
        mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.ct_blue));
        mDialog.getProgressHelper().setRimColor(getResources().getColor(R.color.ct_blue_hint));
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


                if (username.equals("") || password.equals(""))
                {
                    popoErrorDialog();
                } else {
                    mUserManager.logIn(username, password);
//                    mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE).setTitleText("登陆");
//                    mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.ct_blue));
//                    mDialog.getProgressHelper().setRimColor(getResources().getColor(R.color.ct_blue_hint));
//                    mDialog.setCancelText("取消");
//                    mDialog.setContentText("努力加载中...");
//                    mDialog.show();
//                    mDialog.setCancelable(true);
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
        //清空手势密码
        sph.putString(AppCfg.CFG_LOGIN, AppCfg.LOGIN_STATE.LOGIN.toString());
        sph.putString(AppCfg.GSPD, "");

        Intent intent = new Intent(this, MainActivity.class);
        LoginActivity.this.setResult(RESULT_OK, intent);
        LoginActivity.this.finish();
    }

    public void loginIngNotify()
    {

    }

    public void loginErrNotify(int errcode)
    {
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
     * 注册成功后弹框
     */
    private void popoSuccDialog()
    {
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
    }

    /**
     * 弹出错误对话框
     */
    private void popoErrorDialog()
    {
//        mDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("错误");
//        mDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.ct_blue));
//        mDialog.getProgressHelper().setRimColor(getResources().getColor(R.color.ct_blue_hint));
//        mDialog.setCancelText("取 消");
//        mDialog.setContentText("用户密码错误");
//        mDialog.show();
//        mDialog.setCancelable(true);


        Toast toast = Toast.makeText(getApplicationContext(),
                "输入格式错误", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}