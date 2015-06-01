package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.changtou.R;
import com.changtou.moneybox.module.widget.ExEditView;

/**
 * Created by Administrator on 2015/5/22.
 *
 * 用户中心  安全管理
 */
public class RichesSafeActivity extends CTBaseActivity
{
    private ExEditView mExEditView = null;

    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.riches_safe_layout);

        mExEditView = (ExEditView)findViewById(R.id.btn_pd_manager);
        final Intent intent0 = new Intent(this, PdManagerActivity.class);
        mExEditView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent0);
            }
        });
    }


}
