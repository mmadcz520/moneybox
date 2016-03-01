package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONObject;

/**
 * 实名认证页面
 *
 * Created by Administrator on 2015/6/11.
 */
public class RichesCertificationActivity extends CTBaseActivity
{
    private String mFullName = "";
    private EditText mFullnameView = null;

    private String mIdcard = "";
    private EditText mIdcardView = null;

    private String[] mError = {"认证成功", "该身份证号已认证", "实名认证错误", "身份证号与姓名不匹配"};

    //是否实名认证
    private boolean isCertifty= false;

    private UserInfoEntity mUserInfoEntity = null;

    private int mURLType = 0;


    /**
     * @see CTBaseActivity#initView(Bundle)
     */
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_safe_certification);

        mFullnameView = (EditText)findViewById(R.id.certify_fullname);

        mIdcardView = (EditText)findViewById(R.id.certify_idcard);

        Button button = (Button)findViewById(R.id.certify_btn);

        mUserInfoEntity = (UserInfoEntity)ACache.get(this).getAsObject("userinfo");

        Intent intent = getIntent();
        isCertifty = intent.getBooleanExtra("isCerfy", false);
        if(isCertifty)
        {
            mFullName = mUserInfoEntity.getFullName().trim();
            mFullnameView.setText(mFullName);
            mFullnameView.setEnabled(false);

            String idcard = mUserInfoEntity.getIdCard();
            int len = idcard.length();
            idcard = idcard.substring(0,3);
            for(int i = 0; i < len-7;i++)
            {
                idcard = idcard + " *";
            }

            idcard = idcard + mUserInfoEntity.getIdCard().substring(len-4, len);
            mIdcardView.setText(idcard);
            mIdcardView.setEnabled(false);

            button.setEnabled(false);
        }

        mURLType = intent.getIntExtra("URLType", 0);
    }

    protected void initListener()
    {
        setOnClickListener(R.id.certify_btn);
    }

    public void treatClickEvent(int id)
    {
        mIdcard = mIdcardView.getText().toString().trim();
        mFullName = mFullnameView.getText().toString().trim();
        if(!mIdcard.equals("") && !mFullName.equals(""))
        {
            requestCertify();
        }
        else
        {
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_LONG).show();
        }
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        super.onSuccess(content, object, reqType);
        if(reqType == HttpRequst.REQ_TYPE_CERTIFY)
        {
            try
            {
                JSONObject json = new JSONObject(content);
                int errcode = json.getInt("errorcode");

                int code = (errcode < mError.length) ? errcode : (mError.length - 1);
                Toast toast = Toast.makeText(getApplicationContext(),
                        mError[code], Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                //添加银行卡时，先实名认证后跳转
                if(errcode == 0 && !isCertifty)
                {
                    mUserInfoEntity.setIdentycheck(true);
                    mUserInfoEntity.setFullName(mFullName);
                    mUserInfoEntity.setIdCard(mIdcard);
                    ACache.get(this).put("userinfo", mUserInfoEntity);

                    this.finish();
                    final Intent intent = new Intent(this, RichesBankAddActivity.class);
                    intent.putExtra("URLType", mURLType);
                    startActivity(intent);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }

    protected void initData()
    {
        setPageTitle("实名认证");
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }


    /**
     * 请求实名认证接口
     */
    private void requestCertify()
    {
        mIdcard = mIdcardView.getText().toString();

        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_CERTIFY);

        try
        {
            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fullname", mFullName);
            jsonObject.put("idcard", mIdcard);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_CERTIFY,
                    url,
                    params,
                    getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
