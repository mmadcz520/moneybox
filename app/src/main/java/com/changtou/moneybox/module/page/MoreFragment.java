package com.changtou.moneybox.module.page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/3/26.
 */
public class MoreFragment extends BaseFragment{

    private View mView = null;

    private Context mContext = null;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.setting_fragment, null);
        mContext=this.getActivity();

//        ListView pageList1 = (ListView) mView.findViewById(R.id.lv_setting1);
//        ListView pageList2 = (ListView) mView.findViewById(R.id.lv_setting2);
//        initSettingList(pageList1, R.array.item_setting1);
//        initSettingList(pageList2, R.array.item_setting2);
        return mView;
    }


    protected void initListener() {

    }

    protected void initData(Bundle savedInstanceState) {

    }

    public void onSuccess(String content, Object object, int reqType) {

    }

    public void onFailure(Throwable error, String content, int reqType) {

    }

    /*
    * 初始化设置列表
    */
    private void initSettingList(ListView listView, int resId) {
        List<Map<String, Object>> list = new ArrayList();
        String[] titleList = mContext.getResources().getStringArray(resId);
        for (int i = 0; i < titleList.length; i++) {
            Map<String, Object> map = new HashMap();
            map.put("text", titleList[i]);
            map.put("img", R.drawable.common_icon_arrow);
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(mContext, list, R.layout.setting_item,
                new String[] {"text", "img"}, new int[] {
                R.id.homepage_lv_text, R.id.homepage_lv_img
        });
        ViewStub viewStub= new ViewStub(mContext);
        listView.addHeaderView(viewStub);
        listView.setAdapter(adapter);
    }
}
