package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2015/7/29.
 *
 * 邀请好友注册按钮
 *
 */
public class PromotionBtn extends Button implements View.OnClickListener
{
    private Context mContext = null;

    private PromotionListener mPromotionListener = null;

    private Counter mCounter = null;

    public interface PromotionListener
    {
        void promot();
    }


    public PromotionBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);
        this.mContext = context;

//        mCounter = new Counter(60*1000, 1000);
    }

    @Override
    public void onClick(View v)
    {
//        this.setEnabled(false);
//        this.setText("已邀请");
//        mCounter.start();

        if(mPromotionListener != null)
        {
            mPromotionListener.promot();
        }
    }

    public void setPromotionListener(PromotionListener promotionListener)
    {
        this.mPromotionListener = promotionListener;
    }


    public class Counter extends CountDownTimer {

        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated method stub
        }

        @Override
        public void onFinish()
        {
            PromotionBtn.this.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
        }
    }
}
