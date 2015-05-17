package com.changtou.moneybox.module.page;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.changtou.moneybox.common.activity.BaseActivity;
import com.changtou.moneybox.module.widget.ValidateImageView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;
import java.util.List;

import com.changtou.R;


public class LoginActivity extends CTBaseActivity {

    ImageView vc_image; //Í¼±ê

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_login_layout);

        vc_image=(ImageView)findViewById(R.id.vc_image);
        vc_image.setImageBitmap(ValidateImageView.getInstance().getBitmap());

        vc_image.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                vc_image.setImageBitmap(ValidateImageView.getInstance().getBitmap());
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

