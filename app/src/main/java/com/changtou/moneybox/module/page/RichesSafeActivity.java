package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.SharedPreferencesHelper;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.widget.ExEditView;

/**
 * Created by Administrator on 2015/5/22.
 *
 * 用户中心  安全管理
 */
public class RichesSafeActivity extends CTBaseActivity implements View.OnTouchListener
{
    private ExEditView phoneauthBtn = null;
    private ExEditView certyBtn = null;
    private Button quitBtn = null;
    private ExEditView bankBtn = null;
    private ExEditView pdBtn = null;

    private TextView mTitleView = null;

    private SharedPreferencesHelper sph = null;

    private UserInfoEntity userInfoEntity = null;

    protected void initView(Bundle bundle) {
        sph = SharedPreferencesHelper.getInstance(getApplicationContext());
        setContentView(R.layout.riches_safe_layout);

        mTitleView = (TextView)findViewById(R.id.riches_safe_title);

        phoneauthBtn = (ExEditView)findViewById(R.id.btn_phoneauth);
        certyBtn = (ExEditView)findViewById(R.id.btn_certification_manager);
        quitBtn = (Button)findViewById(R.id.safe_page_quit);
        bankBtn = (ExEditView)findViewById(R.id.btn_bank_manager);
        pdBtn = (ExEditView)findViewById(R.id.btn_pd_manager);

        phoneauthBtn.setOnTouchListener(this);
        certyBtn.setOnTouchListener(this);
        quitBtn.setOnTouchListener(this);
        bankBtn.setOnTouchListener(this);
        pdBtn.setOnTouchListener(this);

        int bcolor = getResources().getColor(R.color.font_black);
        int rcolor = getResources().getColor(R.color.ct_red);
        userInfoEntity = (UserInfoEntity)ACache.get(this).getAsObject("userinfo");

        if(userInfoEntity == null) return;
        if(userInfoEntity.getMobilecheck())
        {
            phoneauthBtn.setMessage("已认证", bcolor);
        }
        else
        {
            phoneauthBtn.setMessage("认证得10元礼金", rcolor);
        }

        if(userInfoEntity.getIdentycheck())
        {
            certyBtn.setMessage("已认证", bcolor);
        }
        else
        {
            certyBtn.setMessage("认证得10元礼金", rcolor);
        }
    }

    protected void initListener()
    {
//        setOnClickListener(R.id.btn_phoneauth);
//        setOnClickListener(R.id.btn_certification_manager);
//        setOnClickListener(R.id.safe_page_quit);
//        setOnClickListener(R.id.btn_bank_manager);
//        setOnClickListener(R.id.btn_pd_manager);
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    @Override
    protected void initData()
    {
        setPageTitle("安全设置");

        String fullname = userInfoEntity.getFullName();
        mTitleView.setText(fullname);
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.btn_phoneauth:
            {
                final Intent intent = new Intent(this, PdFrogetActivity.class);
                intent.putExtra("pageType", 1);
                intent.putExtra("isPhoneauth", userInfoEntity.getMobilecheck());
                startActivity(intent);
                break;
            }

            case R.id.btn_certification_manager:
            {
                final Intent intent = new Intent(this, RichesCertificationActivity.class);
                intent.putExtra("isCerfy", userInfoEntity.getIdentycheck());
                startActivity(intent);
                break;
            }

            case R.id.btn_bank_manager:
            {
                if(userInfoEntity.getIdentycheck())
                {
                    final Intent intent = new Intent(this, RichesBankActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(RichesSafeActivity.this, "请先进行实名认证！", Toast.LENGTH_LONG).show();
                }


                break;
            }

            case R.id.btn_pd_manager:
            {
                final Intent intent0 = new Intent(this, PdManagerActivity.class);
                startActivity(intent0);
                break;
            }

            // 退出app
            case R.id.safe_page_quit:
            {
                BaseApplication.getInstance().backToLoginPage();

                break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int id = v.getId();

        switch (id)
        {
            case R.id.btn_phoneauth:
            {
                final Intent intent = new Intent(this, PdFrogetActivity.class);
                intent.putExtra("pageType", 1);
                intent.putExtra("isPhoneauth", userInfoEntity.getMobilecheck());
                startActivity(intent);
                break;
            }

            case R.id.btn_certification_manager:
            {
                final Intent intent = new Intent(this, RichesCertificationActivity.class);
                intent.putExtra("isCerfy", userInfoEntity.getIdentycheck());
                startActivity(intent);
                break;
            }

            case R.id.btn_bank_manager:
            {
                final Intent intent = new Intent(this, RichesBankActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.btn_pd_manager:
            {
                final Intent intent0 = new Intent(this, PdManagerActivity.class);
                startActivity(intent0);
                break;
            }

            // 退出app
            case R.id.safe_page_quit:
            {
                BaseApplication.getInstance().backToLoginPage();

                break;
            }
        }

        return false;
    }
}
