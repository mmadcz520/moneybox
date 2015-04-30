package com.changtou.moneybox.module.page;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.changtou.R;
import com.changtou.moneybox.module.widget.MyScrollView;
import com.changtou.moneybox.module.widget.RoundProgressBar;
import com.changtou.moneybox.module.widget.VerticalPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Button button = (Button)findViewById(R.id.confirm_button);

        final Intent intent = new Intent(this, ConfirmActivity.class);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(intent);
            }
        });

        ListView prodList = (ListView)findViewById(R.id.lv_details);
        initSettingList(prodList, R.array.item_setting1);

        RoundProgressBar rpb = (RoundProgressBar)findViewById(R.id.details_progressbar);
        rpb.setProgress(60);
    }

    /*
* 初始化设置列表
*/
    private void initSettingList(ListView listView, int resId) {
        List<Map<String, Object>> list = new ArrayList();
        String[] titleList = this.getResources().getStringArray(resId);
        for (int i = 0; i < titleList.length; i++) {
            Map<String, Object> map = new HashMap();
            map.put("text", titleList[i]);
            map.put("img", R.drawable.common_icon_arrow);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.item_setting,
                new String[] {"text", "img"}, new int[] {
                R.id.homepage_lv_text, R.id.homepage_lv_img
        });
        ViewStub viewStub= new ViewStub(this);
        listView.addHeaderView(viewStub);
        listView.setAdapter(adapter);
    }
}
