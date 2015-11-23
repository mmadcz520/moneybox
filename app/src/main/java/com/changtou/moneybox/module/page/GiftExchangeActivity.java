package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 投园兑换礼金页面
 * Created by Jone on 2015/10/20.
 */
public class GiftExchangeActivity extends CTBaseActivity {
    private TextView mTouyuanNum = null;
    private Button mMaxButton = null;
    private EditText mTouyuanInput = null;
    private TextView mExchangeLijinView = null;

    private int mTouyuan = 0;
    private View mPopExchange = null;
    private Button mExChangeBtn = null;

    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exchange_gift);

        mTouyuanNum = (TextView) findViewById(R.id.exchange_touyuan_num);
        UserInfoEntity userInfoEntity = (UserInfoEntity) ACache.get(this).getAsObject("userinfo");
        mTouyuan = userInfoEntity.getTouYuan();
        mTouyuanNum.setText(String.valueOf(mTouyuan));

        mTouyuanInput = (EditText) findViewById(R.id.exchange_touyuan_input);
        mTouyuanInput.addTextChangedListener(mTextWatcher);

        mExchangeLijinView = (TextView) findViewById(R.id.exchange_lijin);

        mPopExchange = findViewById(R.id.exchange_touyuan_pop);

        mMaxButton = (Button) findViewById(R.id.exchange_touyuan_max);

        mExChangeBtn = (Button) findViewById(R.id.exchange_btn);

        mMaxButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mTouyuanInput.setText(String.valueOf(mTouyuan));
            }
        });

        mExChangeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //兑换请求
                String touyuanStr = mTouyuanInput.getEditableText().toString();
                if (touyuanStr != null && !touyuanStr.equals("")) {
                    try {
                        int touyuan = Integer.parseInt(touyuanStr);
                        if (touyuan > 0) {
                            exchangeGiftRequest(touyuan);
                        } else {
                            Toast.makeText(GiftExchangeActivity.this, "请输入正确投圆数", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(GiftExchangeActivity.this, "请输入正确投圆数", Toast.LENGTH_SHORT).show();
                    }

                } else
                {
                    Toast.makeText(GiftExchangeActivity.this, "请输入正确投圆数", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mPopExchange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popuDailog();
            }
        });

//        userInfoEntity.setTouyuan(500);
//        ACache.get(this).put("userinfo", userInfoEntity);
    }

    protected void initData() {
        setPageTitle("投圆兑换");
    }

    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    /**
     * 输入投资金额监听函数
     */
    TextWatcher mTextWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if(str == null || str.equals("")) return;
            int num;
            try {
                num = Integer.parseInt(str);
                int lijin = (num / 100) * 5;
                DecimalFormat df =new DecimalFormat("#");
                df.setRoundingMode(RoundingMode.DOWN);
                mExchangeLijinView.setText(df.format(lijin));
            } catch (Exception e) {
                mTouyuanInput.setText("");
                Toast.makeText(GiftExchangeActivity.this, "请输入正确投圆数量", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onSuccess(String content, Object object, int reqType) {
        super.onSuccess(content, object, reqType);
        try {
            JSONObject jsObj = new JSONObject(content);
            int errcode = jsObj.getInt("errcode");
            if(errcode == 0)
            {
                String touyuan = jsObj.getString("lefttouyuan");
                String lijin = jsObj.getString("lijin");
                popuSuccDailog(touyuan, lijin);
            }
            else if(errcode == 1)
            {
                Toast.makeText(this, "兑换投圆失败", Toast.LENGTH_SHORT).show();
            }
            else  if(errcode == 2)
            {
                Toast.makeText(this, "兑换投圆数须大于100", Toast.LENGTH_SHORT).show();
            }
            else  if(errcode == 3)
            {
                Toast.makeText(this, "投圆数不足", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable error, String content, int reqType) {
        super.onFailure(error, content, reqType);
    }

    /**
     * 弹出抵扣礼金提示框
     */
    private void popuDailog() {
        final SweetAlertDialog sad = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        sad.setTitleText("100投圆可兑换5元礼金\n");
        sad.setConfirmText("我知道啦");
        sad.showCancelButton(false);
        sad.show();

        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.cancel();
            }
        });

        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.cancel();
            }
        });
    }

    /**
     * 弹出抵扣礼金提示框
     */
    private void popuSuccDailog(String touyuan, String lijin) {
        final SweetAlertDialog sad = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        String content = "您成功兑换" + lijin + "礼金！ 剩余" + touyuan + "投圆。";
        sad.setTitleText(content);
        sad.setConfirmText("我知道啦");
        sad.showCancelButton(false);
        sad.show();

        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.cancel();
            }
        });

        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.cancel();
            }
        });
    }


    /**
     * 兑换礼金接口
     */
    private void exchangeGiftRequest(int touyuan) {
        try {
            String url = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_GIFTEXCHANGE);

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("touyuan", touyuan);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_GIFTEXCHANGE, url, params, getAsyncClient(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
