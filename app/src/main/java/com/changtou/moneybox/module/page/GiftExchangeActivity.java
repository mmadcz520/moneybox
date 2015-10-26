package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.UserInfoEntity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 投园兑换礼金页面
 * Created by Jone on 2015/10/20.
 */
public class GiftExchangeActivity extends CTBaseActivity
{
    private TextView mTouyuanNum = null;
    private Button mMaxButton = null;
    private EditText mTouyuanInput = null;
    private TextView mExchangeLijinView = null;

    private int mTouyuan = 0;
    private View mPopExchange = null;
    private Button mExChangeBtn = null;

    protected void initView(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_exchange_gift);

        mTouyuanNum = (TextView)findViewById(R.id.exchange_touyuan_num);
        UserInfoEntity userInfoEntity = (UserInfoEntity) ACache.get(this).getAsObject("userinfo");
        mTouyuan = userInfoEntity.getTouYuan();
        mTouyuanNum.setText(String.valueOf(mTouyuan));

        mTouyuanInput = (EditText)findViewById(R.id.exchange_touyuan_input);
        mTouyuanInput.addTextChangedListener(mTextWatcher);

        mExchangeLijinView = (TextView)findViewById(R.id.exchange_lijin);

        mPopExchange = findViewById(R.id.exchange_touyuan_pop);

        mMaxButton = (Button)findViewById(R.id.exchange_touyuan_max);

        mExChangeBtn = (Button)findViewById(R.id.exchange_btn);

        mMaxButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                mTouyuanInput.setText(String.valueOf(mTouyuan));
            }
        });

        mExChangeBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

            }
        });

        mPopExchange.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                popuDailog();
            }
        });

        userInfoEntity.setTouyuan(500);
        ACache.get(this).put("userinfo", userInfoEntity);
    }

    protected void initData()
    {
        setPageTitle("投园兑换");
    }

    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    /**
     * 输入投资金额监听函数
     */
    TextWatcher mTextWatcher = new TextWatcher()
    {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void afterTextChanged(Editable s)
        {
            String str = s.toString();
            int num;
            try
            {
                num = Integer.parseInt(str);
            }
            catch (Exception e)
            {
                num = 0;
            }

            mExchangeLijinView.setText(String.valueOf(num/100));
        }
    };

    /**
     * 弹出抵扣礼金提示框
     */
    private void popuDailog()
    {
        final SweetAlertDialog sad = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        sad.setTitleText("100投园可兑换5元礼金\n");
        sad.setConfirmText("我知道啦");
        sad.showCancelButton(false);
        sad.show();

        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                sad.cancel();
            }
        });

        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                sad.cancel();
            }
        });
    }
}
