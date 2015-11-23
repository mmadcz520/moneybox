package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;


import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 描述: 投资确认页
 *
 * @author zhoulongfei
 * @since 2015-04-17
 */
public class ConfirmActivity extends CTBaseActivity
{
    //投资详情确认
    private ListView mConfirmDetails = null;

    private String[] mContent = {"", "", "", ""};

    private Button mConfirmBtn = null;

    private SweetAlertDialog mDialog = null;

    private int mProductType = 0;

    private String mProductId = "";

    private EditText mNumInput = null;

    private int touyuan = 10;

    private int mSyLijin = 0;

    private TextView mRechargeBtn = null;

    private String[] mError = {"投资成功", "参数为空", "不存在该产品",  " 产品类型参数错误",
                "用户ID参数错误", "产品ID参数错误", "投资金额参数错误", "不存在该用户",
                "找不到原始转让产品", "投资必须是投资金额的整数倍", "已经不是新手不能投资新手标",
                "此项目为专属项目", "投资额不能小于起投金额", "该项目融资已满或者尚未发布",
                "余额不足", "输入金额大于项目剩余金额", " 系统错误", "参数名错误", " 产品不在可投资时间内"};

    private TextView mLinjinView = null;

    protected void initView(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();
        mContent = intent.getStringArrayExtra("details");
        mProductType = intent.getIntExtra("type",0);
        mProductId = intent.getStringExtra("id");

        if(mContent == null) return;

        TextView tx1 = (TextView)findViewById(R.id.confirm_product_name);
        TextView tx2 = (TextView)findViewById(R.id.confirm_tzxq);
        TextView tx3 = (TextView)findViewById(R.id.confirm_nhln);
        TextView tx4 = (TextView)findViewById(R.id.confirm_ktje);

        tx1.setText(mContent[0]);
        tx2.setText(mContent[1]);
        tx3.setText(mContent[2]);
        tx4.setText(mContent[3]);

        mConfirmBtn = (Button)findViewById(R.id.confirm_button_do);
        mNumInput = (EditText)findViewById(R.id.confirm_input_edit);
        mNumInput.addTextChangedListener(mTextWatcher);

        mLinjinView = (TextView)findViewById(R.id.confirm_lijin_num);

        //显示余额
        UserInfoEntity userInfoEntity = (UserInfoEntity) ACache.get(this).getAsObject("userinfo");
        String overage = userInfoEntity.getOverage();
        String gift = String.valueOf(userInfoEntity.getGifts());
         if(gift != null && !gift.equals(""))
        {
            mSyLijin = (int)Double.parseDouble(String.valueOf(userInfoEntity.getGifts()));
        }
        TextView textView = (TextView)findViewById(R.id.confirm_text_overage);
        textView.setText(overage);

        mDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        mDialog.setConfirmText("确认");
        mDialog.setCancelText("继续投资");
//        mDialog.setContentText("恭喜你您资成功!\n 获得" + touyuan + "个投圆");
                mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
                        intent.putExtra("login_state", 1);
                        ConfirmActivity.this.startActivity(intent);
                    }
                });

        mDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                mDialog.dismiss();
            }
        });

        View dklj = findViewById(R.id.confirm_button_dklj);
        dklj.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                popuDailog();
            }
        });

        mRechargeBtn = (TextView)findViewById(R.id.recharge_btn);
        mRechargeBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(ConfirmActivity.this, RichesRechargePage.class);
                intent.putExtra("urlType", 1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    @Override
    protected void initData()
    {
        setPageTitle("确认投资");
    }

    @Override
    protected void initListener()
    {
        setOnClickListener(R.id.confirm_button_do);
    }

    /**
     * 提示用户投资成功或失败
     *
     * @param id
     */
    public void treatClickEvent(int id)
    {
        mConfirmBtn.setEnabled(false);
        String mum = mNumInput.getEditableText().toString();
        postInvestRequest(mProductType, mProductId, mum);
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        if(reqType == HttpRequst.REQ_TYPE_INVEST)
        {
            try
            {
                mConfirmBtn.setEnabled(true);

                JSONObject data = new JSONObject(content);

                int result = data.getInt("result");
                touyuan = data.getInt("touyuan");

                if(result == 0)
                {
//                    mDialog.show();
                    Intent intent = new Intent(this, InvestmentSuccPage.class);
                    intent.putExtra("touyuan", String.valueOf(touyuan));
                    startActivity(intent);
                }
                else
                {
                    int code = (result < mError.length) ? result : (mError.length - 1);
                    Toast.makeText(this, mError[code], Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        super.onSuccess(content, object, reqType);
    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        Toast.makeText(this, "网络异常", Toast.LENGTH_LONG).show();
        super.onFailure(error, content, reqType);
    }

    /**
     * 弹出抵扣礼金提示框
     */
    private void popuDailog()
    {
        final SweetAlertDialog sad = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        sad.setTitleText("每投资1000元,5元礼金变为5元现金\n");
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
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                sad.cancel();
            }
        });
    }

    /**
     * 输入投资金额监听函数
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

            if(num >= 100)
            {
                mConfirmBtn.setEnabled(true);
            }
            else
            {
                mConfirmBtn.setEnabled(false);
            }

            int sylijin = mSyLijin;
            int lijin = (num/1000*5)> sylijin ? sylijin:((num/1000) * 5);
            mLinjinView.setText(String.valueOf(lijin));
        }
    };

    /**
     * 投资请求
     */
    private void postInvestRequest(int type, String projid, String money)
    {
        try
        {
            String url = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_INVEST);

            RequestParams params = new RequestParams();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("projid", projid);
            jsonObject.put("investmoney", money);
            jsonObject.put("ly", "android");
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_INVEST, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
