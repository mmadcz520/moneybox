package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.changtou.R;
import com.changtou.moneybox.common.utils.AppUtil;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenu;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenuCreator;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenuItem;
import com.changtou.moneybox.common.widget.swipeMenuListView.SwipeMenuListView;
import com.changtou.moneybox.module.adapter.BankCardAdapter;
import com.changtou.moneybox.module.entity.BankCardEntity;
import com.changtou.moneybox.module.http.HttpRequst;

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

    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_safe_bank);

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
                    case 0:
                        // open
                        mAdapter.changeDefaultCard(2);
                        break;
                    case 1:
                        // delete
//					delete(item);
                        mAdapter.notifyDataSetChanged();
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
        mAdapter = new BankCardAdapter(this);
        mBankListView.setAdapter(mAdapter);

        sendRequest(HttpRequst.REQ_TYPE_BANKCARD,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_BANKCARD),
                mParams,
                getAsyncClient(), false);
    }

    /**
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType) {

        if (reqType == HttpRequst.REQ_TYPE_BANKCARD)
        {
            BankCardEntity entity = (BankCardEntity) object;
            mAdapter.setData(entity);
        }
    }

    /**
     *
     * @param error
     * @param content
     * @param reqType
     */
    public void onFailure(Throwable error, String content, int reqType) {

    }
}
