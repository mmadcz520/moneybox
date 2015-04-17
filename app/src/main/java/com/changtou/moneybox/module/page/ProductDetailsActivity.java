package com.changtou.moneybox.module.page;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.changtou.R;
import com.changtou.moneybox.module.widget.MyScrollView;
import com.changtou.moneybox.module.widget.VerticalPager;

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
        setContentView(R.layout.product_details);
        final VerticalPager vp = (VerticalPager)findViewById(R.id.vertyPager1);

        Button button = (Button)findViewById(R.id.confirm_button);

        final Intent intent = new Intent(this, ConfirmActivity.class);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent);
            }
        });

    }
}
