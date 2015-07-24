package com.changtou.moneybox.module.adapter;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class MyClickSpan extends ClickableSpan
{
    public ProductContractAdapter.OnTextviewClickListener mListener;

    public MyClickSpan(ProductContractAdapter.OnTextviewClickListener listener)
    {
        this.mListener = listener;
    }

    public void onClick(View widget)
    {
        mListener.clickTextView();
    }

    public void updateDrawState(TextPaint ds)
    {
        mListener.setStyle(ds);
    }
}