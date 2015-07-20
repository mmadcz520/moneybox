package com.changtou.moneybox.module.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.appcfg.AppCfg;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.safe.LocusPassWordView;
import com.changtou.moneybox.module.safe.Md5Utils;
import com.umeng.analytics.MobclickAgent;

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

    private long exitTime = 0;

    private TextView forgetPdView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getInstance().addActivity(this);

        setContentView(R.layout.draw_pwd);
        mPwdView = (LocusPassWordView) this.findViewById(R.id.mPassWordView);
        mGesturePrompt = (TextView)this.findViewById(R.id.pd_gesture_prompt);

        forgetPdView = (TextView)this.findViewById(R.id.pd_gesture_forget);
        forgetPdView.setOnClickListener(this);

        //保存密码
        sph = SharedPreferencesHelper.getInstance(getApplicationContext());
        md5 = new Md5Utils();
        mGSWD = sph.getString(AppCfg.GSPD, "");

        if(!mGSWD.equals(""))
        {
            mGesturePrompt.setText("请输入手势密码");
        }

        TextView text = (TextView)this.findViewById(R.id.pd_gesture_fullname);
        ACache cache = ACache.get(BaseApplication.getInstance());
        String fullname = cache.getAsString("fullname") + "," + "您好！";
        text.setText(fullname);

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
//        BaseApplication.getInstance().AppExit();
    }

    /**
     * 忘记密码
     * @param v
     */
    public void onClick(View v)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if((System.currentTimeMillis()-exitTime) > 2000)
            {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                BaseApplication.getInstance().AppExit();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
