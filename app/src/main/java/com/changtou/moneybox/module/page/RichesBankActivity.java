package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.AppUtil;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenu;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenuCreator;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenuItem;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenuListView;
import com.changtou.moneybox.module.adapter.BankCardAdapter;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONObject;


import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Administrator on 2015/5/21.
 *
 * 1. 绑定银行卡页面
 *
 */
public class RichesBankActivity extends CTBaseActivity
{
    private SwipeMenuListView mBankListView;
    private BankCardAdapter mAdapter = null;

    private BankCardEntity mEntity = null;

    private boolean isCertify = false;

    private boolean isRecharge = false;

    private UserInfoEntity mUserInfoEntity = null;

    private int mURLType = 0;

    /**
     * 银行基本信列表
     */
    protected Map<String, String> mBankInfoList = null;

    protected void initView(Bundle bundle) {

        setContentView(R.layout.riches_safe_bank);

        mBankListView = (SwipeMenuListView)findViewById(R.id.riches_safe_bank_list);

        mUserInfoEntity = (UserInfoEntity)ACache.get(this).getAsObject("userinfo");
        isCertify = mUserInfoEntity.getIdentycheck();
        isRecharge = mUserInfoEntity.getIsRecharged();

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(AppUtil.dip2px(RichesBankActivity.this,90));
                // set item title
                openItem.setTitle("设为默认");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(R.color.ct_red);
                // set item width
                deleteItem.setWidth(AppUtil.dip2px(RichesBankActivity.this, 90));
                // set a icon
                deleteItem.setIcon(R.mipmap.riches_bank_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        mBankListView.setMenuCreator(creator);

        mBankListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 1:
                        // 删除银行卡
                        BankCardEntity.BankListEntity bank = (BankCardEntity.BankListEntity)mEntity.mList.get(position);
                        if(bank.isdefault.equals("是"))
                        {
                            Toast.makeText(RichesBankActivity.this, "不能删除默认提现银行卡", Toast.LENGTH_LONG).show();
                            break;
                        }
                        deleteBankRequest(position);
                        break;
                    case 0:
                        //跟换默认银行卡
                        if(isRecharge)
                        {
                            popuDailog();
                        }
                        else
                        {
                            changeDefaultRequest(position);
                        }
                        break;
                }
                return false;
            }
        });

        // set SwipeListener
        mBankListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });


        mBankListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                mBankListView.smoothOpenMenu(position);
            }
        });

        final Intent intent3 = new Intent(this, RichesBankAddActivity.class);
        View view = findViewById(R.id.riches_bank_add);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isCertify) {
                    if(!isRecharge)
                    {
                        intent3.putExtra("URLType",mURLType);
                        startActivity(intent3);
                    }
                    else
                    {
                        popuDailog();
                    }
                } else {
                    Toast.makeText(RichesBankActivity.this, "请先进行实名认证！", Toast.LENGTH_LONG).show();
                }
            }
        });

        Intent intent = getIntent();
        mURLType = intent.getIntExtra("URLType", 0);

        mBankInfoList = BaseApplication.getInstance().getBankInfoList();
    }

    protected void initData()
    {
        setPageTitle("银行卡管理");

        mAdapter = new BankCardAdapter(this, mBankInfoList);
        mBankListView.setAdapter(mAdapter);

        //初始化银行卡列表
        mEntity = mUserInfoEntity.getBankCardEntity();
        mAdapter.setData(mEntity);

        initBankListRequest();
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

    /**
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType) {

        super.onSuccess(content, object, reqType);

        try {
            if (reqType == HttpRequst.REQ_TYPE_BANKCARD) {
                mEntity = (BankCardEntity) object;
                mAdapter.setData(mEntity);

                mUserInfoEntity.setBankCardEntity(mEntity);
                ACache.get(this).put("userinfo", mUserInfoEntity);
            }

            if (reqType == HttpRequst.REQ_TYPE_DELBANK) {
                initBankListRequest();
            }

            if (reqType == HttpRequst.REQ_TYPE_CHANGEBANK) {
                initBankListRequest();
            }
        }
        catch (Exception e)
        {
            BaseApplication.getInstance().backToLoginPage();
            Toast.makeText(BaseApplication.getInstance(), "账号在其他设备登陆,请重新登录", Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     * @param error
     * @param content
     * @param reqType
     */
    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }

    /**
     * 删除银行卡
     */
    private void deleteBankRequest(int position)
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_DELBANK);

            BankCardEntity.BankListEntity bank = (BankCardEntity.BankListEntity)mEntity.mList.get(position);

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", bank.account);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_DELBANK, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void initBankListRequest()
    {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_BANKCARD);
        sendRequest(HttpRequst.REQ_TYPE_BANKCARD,
                url,
                mParams,
                getAsyncClient(), false);
    }

    private void changeDefaultRequest(int position)
    {
        try {
            String url = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_CHANGEBANK);

            BankCardEntity.BankListEntity bank = (BankCardEntity.BankListEntity) mEntity.mList.get(position);

            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account", bank.account);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_CHANGEBANK, url, params, getAsyncClient(), false);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void popuDailog() {
        final SweetAlertDialog sad = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        String str =
                "为保障用户资金安全，首次充值成功后，该卡即为用户默认充值及提现银行卡，不可更改、删除。\n\n详情请致电客服：400-880-6270\n";

        sad.setTitleText(str);
        sad.setConfirmText("我知道啦");
        sad.show();

        sad.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.cancel();
            }
        });

        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sad.cancel();
            }
        });
    }

//    protected void onBackIcon()
//    {
//        super.onBackIcon();
//        if(mURLType == 0)
//        {
//            BaseApplication.getInstance().cleanAllActivity();
//            final Intent intent = new Intent(this, RichesSafeActivity.class);
//            startActivity(intent);
//        }
//
//    }
//
//    public void onBackPressed() {
//        super.onBackPressed();
//        if(mURLType == 0) {
//            BaseApplication.getInstance().cleanAllActivity();
//            final Intent intent = new Intent(this, RichesSafeActivity.class);
//            startActivity(intent);
//        }
//    }
}
