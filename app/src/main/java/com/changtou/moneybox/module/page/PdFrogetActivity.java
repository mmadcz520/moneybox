package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.changtou.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 忘记密码
 *
 * Created by Administrator on 2015/6/1.
 */
public class PdFrogetActivity extends CTBaseActivity
{
    //发送验证码
    private Button mBtnSendCS = null;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private int count = 0;

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    mBtnSendCS.setBackgroundResource(R.drawable.btn_selector);
                    mBtnSendCS.setText("发送验证码");
                    mBtnSendCS.setEnabled(true);
                    break;
                case 1:
                    mBtnSendCS.setText(""+count+" 秒内验证");
                    break;
            }
        }
    };

    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.password_forget_activity);
        mBtnSendCS = (Button)findViewById(R.id.btn_send_checksum);
    }

    protected void initListener() {
        setOnClickListener(R.id.btn_send_checksum);
        setOnClickListener(R.id.password_forget_submit);
    }

    @Override
    protected void initData()
    {
        setPageTitle("忘记密码");
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    public void treatClickEvent(int id)
    {
        switch (id)
        {
            case R.id.btn_send_checksum:
            {
                mBtnSendCS.setBackgroundResource(R.color.ct_invalid);
                mBtnSendCS.setEnabled(false);

                count = 0;
                mTimer = new Timer();
                mTimerTask = new TimerTask() {
                    public void run() {
                        count++;
                        myHandler.sendEmptyMessage(1);
                        if (count == 60) {
                            mTimer.cancel();
                            myHandler.sendEmptyMessage(0);
                        }
                    }
                };
                mTimer.schedule(mTimerTask, 0, 1000);
                break;
            }

            case R.id.password_forget_submit:
            {
                final Intent intent = new Intent(this, RegisterPasswordActivity.class);
                startActivity(intent);
                break;
            }

        }
    }
}
