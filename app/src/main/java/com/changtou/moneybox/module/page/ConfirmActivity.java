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

import com.changtou.R;
import com.changtou.moneybox.module.entity.UserEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;

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

    protected void initView(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();
        mContent = intent.getStringArrayExtra("details");

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
        EditText input = (EditText)findViewById(R.id.confirm_input_edit);
        input.addTextChangedListener(mTextWatcher);

        //显示余额
        UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
        String overage = userInfoEntity.getOverage();
        TextView textView = (TextView)findViewById(R.id.confirm_text_overage);
        textView.setText(overage);

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
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("登陆");
        mDialog.setConfirmText("确认");
        mDialog.setCancelText("取消");
        mDialog.setContentText("投资成功！！！！！！！！！！！！！！");

        mDialog.show();
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
            int num = 0;
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
}
