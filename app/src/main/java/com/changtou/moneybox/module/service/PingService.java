package com.changtou.moneybox.module.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.http.base.BaseHttpClient;
import com.changtou.moneybox.common.http.base.HttpCallback;
import com.changtou.moneybox.common.logger.Logger;
import com.changtou.moneybox.module.http.HttpRequst;

import java.util.Timer;
import java.util.TimerTask;

public class PingService extends Service implements HttpCallback {
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;

    private RequestParams mParams = new RequestParams();

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        stopTimer();
        startTimer();

        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        Logger.e("onDestroy");

        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    public void sendRequest(int reqType, String url, RequestParams params,
                            BaseHttpClient baseHttpClient, boolean showDialog) {
        if (baseHttpClient != null) {
            if (reqType > 1000) {
                baseHttpClient.post(reqType, this, url, params, this);
            } else {
                baseHttpClient.get(reqType, url, params, this);
            }
        }
    }

    @Override
    public void onSuccess(String content, Object object, int reqType) {
        Logger.d("onSuccess");
    }

    @Override
    public void onFailure(Throwable error, String content, int reqType)
    {
        Logger.d("onFailure" + content);
    }

    private void stopTimer() {

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {

                    do {
                        try {
                            sendRequest(HttpRequst.REQ_TYPE_PING,
                                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PING),
                                    mParams,
                                    BaseApplication.getInstance().getAsyncClient(), false);

                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                        }
                    } while (true);

                }
            };
        }

        if (mTimer != null && mTimerTask != null)
            mTimer.schedule(mTimerTask, 1000, 1000);

    }
}