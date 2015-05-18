package com.changtou.moneybox.module.page;


import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.changtou.moneybox.module.widget.ValidateImageView;

import com.changtou.R;


public class LoginActivity extends CTBaseActivity {

    ImageView vc_image; //图标

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_login_layout);

//        vc_image=(ImageView)findViewById(R.id.vc_image);
//        vc_image.setImageBitmap(ValidateImageView.getInstance().getBitmap());
//
//        vc_image.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                vc_image.setImageBitmap(ValidateImageView.getInstance().getBitmap());
//            }
//        });

        TextView loginBtn = (TextView)findViewById(R.id.login_btn);
        final Intent intent = new Intent(this, MainActivity.class);
        loginBtn.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                LoginActivity.this.setResult(RESULT_OK, intent);
                LoginActivity.this.finish();
            }
        });

        TextView registBtn = (TextView)findViewById(R.id.loginpage_register_btn);
        final Intent intent2 = new Intent(this, RegisterActivity.class);
        registBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                startActivity(intent2);
            }
        });
    }

    @Override
    protected void initLisener() {

    }

    @Override
    protected void initData() {

    }

}

