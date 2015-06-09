package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.safe.LocusPassWordView;
import com.changtou.moneybox.module.safe.Md5Utils;

/**
 * 手势密码页
 *
 * Created by Administrator on 2015/6/5.
 */
public class GesturePWActivity extends Activity implements LocusPassWordView.OnCompleteListener, View.OnClickListener
{
    private LocusPassWordView mPwdView = null;
    private TextView mGesturePrompt = null;

    private String mLastPassword = "";

    //密码
    private String mGSWD = "";

    private SharedPreferencesHelper sph = null;
    private Md5Utils md5 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.draw_pwd);
        mPwdView = (LocusPassWordView) this.findViewById(R.id.mPassWordView);
        mGesturePrompt = (TextView)this.findViewById(R.id.pd_gesture_prompt);

        //保存密码
        sph = SharedPreferencesHelper.getInstance(getApplicationContext());
        md5 = new Md5Utils();
        mGSWD = sph.getString(AppCfg.GSPD, "");

        mPwdView.setOnCompleteListener(this);
    }

    public void onComplete(String password)
    {
        mPwdView.clearPassword();
        mGesturePrompt.setTextColor(getResources().getColor(R.color.ct_white));

        if(mGSWD.equals(""))
        {
            if(mLastPassword.equals(""))
            {
                mLastPassword = password;
                mGesturePrompt.setText(getResources().getText(R.string.pd_gesture_set_again));
            }
            else
            {
                if(password.equals(mLastPassword))
                {
                    mGesturePrompt.setText(getResources().getText(R.string.pd_gesture_set_succeed));
                    // 存入密码
                    sph.putString(AppCfg.GSPD, md5.toMd5(password, ""));
                    finish();
                }
                else
                {
                    mLastPassword = "";
                    mGesturePrompt.setText(getResources().getText(R.string.pd_gesture_set_diff));
                    mGesturePrompt.setTextColor(getResources().getColor(R.color.ct_red));
                    Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
                    mGesturePrompt.startAnimation(shake);
                }
            }
        }
        else if(mGSWD.equals(md5.toMd5(password, "")))
        {
            finish();
        }
        else
        {
            mGesturePrompt.setText("密码输入错误");
            mGesturePrompt.setTextColor(getResources().getColor(R.color.ct_red));
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mGesturePrompt.startAnimation(shake);
        }

    }

    @Override
    public void onTooShort() {
        mGesturePrompt.setText(getResources().getText(R.string.pd_gesture_prompt_error));
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        mGesturePrompt.startAnimation(shake);
    }

    /**
     * 禁用back按键
     */
    public void onBackPressed()
    {
//        super.onBackPressed();
    }

    /**
     * 忘记密码
     * @param v
     */
    public void onClick(View v)
    {

    }
}
