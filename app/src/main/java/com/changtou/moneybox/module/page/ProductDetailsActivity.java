package com.changtou.moneybox.module.page;


import android.os.Bundle;
import android.widget.RelativeLayout;

import com.changtou.R;
import com.changtou.moneybox.module.widget.BomPanel;

/**
 * 描述： 产品详情页
 *
 * @author zhoulongfei
 * @since 2015-4-13
 */
public class ProductDetailsActivity extends CTBaseActivity
{
    RelativeLayout.LayoutParams parentParams;
    RelativeLayout.LayoutParams paneBomParams;

    /**
     * @see CTBaseActivity#initView(Bundle)
     */
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.pager_vertical_test);
    }
}
