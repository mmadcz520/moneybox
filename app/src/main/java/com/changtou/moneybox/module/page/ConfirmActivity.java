package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.changtou.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private String[] mContent = {"[长投宝]上手易256期", "投资期限:3个月", "年化利率:11%", "剩余可投金额:10000.00"};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

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
    }

    @Override
    protected int setPageType() {
        return 0;
    }
}
