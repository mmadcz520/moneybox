package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/21.
 * 提现页面
 */
public class RichesWithdrawActivity extends CTBaseActivity
{
    private TextView mBankNoView = null;
    private ImageView mBankIconView = null;

    private Map<String, String> mBankInfoList = null;

    private EditText mWithdrawNum = null;
    private TextView mWithdrawMoney = null;
    private TextView mWithdrawTotle = null;
    private TextView mWithdrawHandling = null;

    private String mTotalAssets = null;
    private String mHandling = "5";

    @Override
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_withdraw_layout);

        final Intent intent3 = new Intent(this, RichesBankActivity.class);

        RelativeLayout ll = (RelativeLayout)findViewById(R.id.riches_bank_item);
        ll.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent3);
            }
        });

        mWithdrawNum = (EditText)findViewById(R.id.riches_withdraw_number);
        mWithdrawNum.addTextChangedListener(mTextWatcher);

        mWithdrawMoney = (TextView)findViewById(R.id.riches_withdraw_money);
        mWithdrawTotle = (TextView)findViewById(R.id.riches_withdraw_total);
        mWithdrawHandling = (TextView)findViewById(R.id.riches_withdraw_handling);

        mBankNoView = (TextView)findViewById(R.id.riches_bank_item_no);
        mBankIconView = (ImageView)findViewById(R.id.riches_bank_item_logo);

        mBankInfoList = BaseApplication.getInstance().getBankInfoList();
    }

    @Override
    protected void initData()
    {
        initBankInfo();

        UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
        mTotalAssets = userInfoEntity.getTotalAssets();
        mWithdrawTotle.setText(customFormat(mTotalAssets));

        mWithdrawHandling.setText(customFormat("5"));

        super.initData();
    }

    private void initBankInfo()
    {
        UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
        BankCardEntity bank = userInfoEntity.getBankCardEntity();

        int len = bank.mList.size();
        BankCardEntity.BankListEntity bList ;

        for(int i = 0; i < len; i++)
        {
            bList = (BankCardEntity.BankListEntity)bank.mList.get(i);
            String account = bList.account;
            if(account.length()>10)
            {
                account = account.substring(0,4) + " **** " + account.substring(account.length()-4,account.length());
            }
            if(bList.isdefault.equals("是"))
            {
                mBankNoView.setText(account);
                setBankIcon(mBankIconView, bList.bank);
            }
        }
    }

    protected int setPageType() {
        return 0;
    }

    private void setBankIcon(ImageView imageView, String bankName) {
        InputStream open = null;
        try {
            if (mBankInfoList == null) return;
            String temp = mBankInfoList.get(bankName);
            if (temp == null || !temp.startsWith("bank_icon")) return;

            open = this.getAssets().open(temp);
            Bitmap bitmap = BitmapFactory.decodeStream(open);

            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 输入取现金额时监听函数
     */
    TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            try
            {
                mHandling = mHandling.replace(",","");
                Float money_f = Float.parseFloat(mHandling);

                String input = s.toString();
                input = input.replace(",","");
                Float input_f = Float.parseFloat(input);

                Float m = input_f - money_f;
                if(m > 0)
                {
                    mWithdrawMoney.setText(customFormat(m.toString()));
                }
                else
                {
                    mWithdrawMoney.setText("");
                }
            }
            catch (Exception e)
            {
                return;
            }


        }
    };

    public String customFormat(String value) {
        DecimalFormat myFormatter = new DecimalFormat("###,###.00");
        try
        {
            value = value.replace(",","");
            Float f = Float.parseFloat(value);
            String output = myFormatter.format(f);
            return output;
        }
        catch (Exception e)
        {
            Toast.makeText(RichesWithdrawActivity.this,"请输入正确格式", Toast.LENGTH_LONG).show();
            return "";
        }
    }
}
