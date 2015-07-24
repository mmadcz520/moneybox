package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
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


    /**
     * @see CTBaseActivity#initView(Bundle)
     */
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_safe_certification);

        mFullnameView = (EditText)findViewById(R.id.certify_fullname);

        mIdcardView = (EditText)findViewById(R.id.certify_idcard);

        UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
        mFullName = userInfoEntity.getFullName().trim();
        mFullnameView.setText(mFullName);
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
            Toast.makeText(this, "求输入完整信息", Toast.LENGTH_LONG).show();
        }
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        super.onSuccess(content, object, reqType);
        if(reqType == HttpRequst.REQ_TYPE_CERTIFY)
        {
            try
            {
                printLog(content);
                JSONObject json = new JSONObject(content);
                int errcode = json.getInt("errorcode");

                int code = (errcode < mError.length) ? errcode : (mError.length - 1);
                Toast toast = Toast.makeText(getApplicationContext(),
                        mError[code], Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
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

        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_CERTIFY) +
                "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

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
