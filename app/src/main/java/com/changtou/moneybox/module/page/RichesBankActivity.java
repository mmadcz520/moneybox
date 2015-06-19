package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.common.utils.AppUtil;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenu;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenuCreator;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenuItem;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenuListView;
import com.changtou.moneybox.module.CTMoneyApplication;
import com.changtou.moneybox.module.adapter.BankCardAdapter;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.service.BankParserHandler;
import com.changtou.moneybox.module.widget.MultiStateView;
import com.changtou.moneybox.module.widget.ZProgressHUD;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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

    /**
     * 银行列表
     */
    protected String[] mBankDatas;

    /**
     * 银行基本信列表
     */
    protected Map<String, String> mBankInfoList = null;

    protected void initView(Bundle bundle) {

        setContentView(R.layout.riches_safe_bank);

        initBankDatas();
        mBankListView = (SwipeMenuListView)findViewById(R.id.riches_safe_bank_list);

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
                        changeDefaultRequest(position);
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


        final Intent intent3 = new Intent(this, RichesBankAddActivity.class);
        View view = findViewById(R.id.riches_bank_add);
        view.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent3);
            }
        });
    }

    protected void initData()
    {
        mAdapter = new BankCardAdapter(this, mBankInfoList);
        mBankListView.setAdapter(mAdapter);

        //初始化银行卡列表
        UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
        mEntity = userInfoEntity.getBankCardEntity();
        mAdapter.setData(mEntity);

        initBankListRequest();
    }

    @Override
    protected int setPageType() {
        return 0;
    }

    /**
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType) {

        super.onSuccess(content, object, reqType);

        if (reqType == HttpRequst.REQ_TYPE_BANKCARD)
        {
            mEntity = (BankCardEntity) object;
            mAdapter.setData(mEntity);

            UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
            userInfoEntity.setBankCardEntity(mEntity);
        }

        if(reqType == HttpRequst.REQ_TYPE_DELBANK)
        {
            initBankListRequest();
        }

        if(reqType == HttpRequst.REQ_TYPE_CHANGEBANK)
        {
            initBankListRequest();
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
        Log.e("CT_MONEY", "onFailure" + content);
        super.onFailure(error,content,  reqType);
    }

    /**
     * 解析银行卡信息
     */
    protected void initBankDatas()
    {
        List<String> bankList;

        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("bank_data.xml");

            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            BankParserHandler handler = new BankParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            bankList = handler.getDataList();
            mBankInfoList = handler.getBankInfoList();
            mBankDatas = new String[bankList.size()];

            for(int i = 0; i < bankList.size(); i++)
            {
                mBankDatas[i] = bankList.get(i);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 删除银行卡
     */
    private void deleteBankRequest(int position)
    {
        try
        {
            String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_DELBANK) +
                    "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                    "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

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
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_BANKCARD) +
                "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

        sendRequest(HttpRequst.REQ_TYPE_BANKCARD,
                url,
                mParams,
                getAsyncClient(), false);

        printLog(url);
    }

    private void changeDefaultRequest(int position)
    {
        try {
            String url = HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_CHANGEBANK) +
                    "userid=" + ACache.get(BaseApplication.getInstance()).getAsString("userid") +
                    "&token=" + ACache.get(BaseApplication.getInstance()).getAsString("token");

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
}
