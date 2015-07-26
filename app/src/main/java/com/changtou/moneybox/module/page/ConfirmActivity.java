package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.UserEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private String[] mError = {"投资成功", "参数为空", "不存在该产品",  " 产品类型参数错误",
                "用户ID参数错误", "产品ID参数错误", "投资金额参数错误", "不存在该用户",
                "找不到原始转让产品", "投资必须是投资金额的整数倍", "已经不是新手不能投资新手标",
                "此项目为专属项目", "投资额不能小于起投金额", "该项目融资已满或者尚未发布",
                "余额不足", "输入金额大于项目剩余金额", " 系统错误", "参数名错误", " 产品不在可投资时间内"};

    protected void initView(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();
        mContent = intent.getStringArrayExtra("details");
        mProductType = intent.getIntExtra("type",0);
        mProductId = intent.getStringExtra("id");

        if(mContent == null) return;

        mConfirmDetails = (ListView)findViewById(R.id.confirm_product_details);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.confirm_listview_item,
                new String[] {"text"}, new int[] {R.id.confirm_lv_text});

        for (int i = 0; i < 4; i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", mContent[i]);
            list.add(map);
        }

        ViewStub viewStub= new ViewStub(this);
        mConfirmDetails.addHeaderView(viewStub);
        mConfirmDetails.setAdapter(adapter);

        mConfirmBtn = (Button)findViewById(R.id.confirm_button_do);
        mNumInput = (EditText)findViewById(R.id.confirm_input_edit);
        mNumInput.addTextChangedListener(mTextWatcher);

        //显示余额
        UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
        String overage = userInfoEntity.getOverage();
        TextView textView = (TextView)findViewById(R.id.confirm_text_overage);
        textView.setText(overage);

        mDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        mDialog.setConfirmText("确认");
        mDialog.setContentText("恭喜你投资成功!\n 获得2个投元");
        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
        {
            public void onClick(SweetAlertDialog sweetAlertDialog)
            {
                Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
                intent.putExtra("login_state", 1);
                ConfirmActivity.this.startActivity(intent);
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
        String mum = mNumInput.getEditableText().toString();
        postInvestRequest(mProductType, mProductId, mum);
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        if(reqType == HttpRequst.REQ_TYPE_INVEST)
        {
            try
            {
                JSONObject data = new JSONObject(content);
                int result = data.getInt("result");

                if(result == 0)
                {
                    mDialog.show();
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
        }
    };

    /**
     * 投资请求
     */
    private void postInvestRequest(int type, String projid, String money)
    {
        try
        {
            String url = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_INVEST) +
                    "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                    "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

            RequestParams params = new RequestParams();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("projid", projid);
            jsonObject.put("investmoney", money);
            jsonObject.put("ly", 0);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_INVEST, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
