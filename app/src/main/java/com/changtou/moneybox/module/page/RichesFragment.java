package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/3/26.
 */
public class RichesFragment extends BaseFragment{

    GridView gv = null;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.riches_fragment, null);

        gv=(GridView) view.findViewById(R.id.brainheroall);
        ArrayList<HashMap<String,Object>> al=new ArrayList<HashMap<String,Object>>();

        for(int i=0;i<6;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("icon", R.mipmap.ic_launcher);//添加图像资源的ID
            map.put("name", "");//按序号做ItemText
            al.add(map);
        }

        SimpleAdapter sa=new SimpleAdapter(this.getActivity(),al,R.layout.riches_fragment,new String[]{"icon","name"},new int[]{R.id.ItemImage,R.id.ItemText});
        gv.setAdapter(sa);
        return view;
    }

    @Override
    protected void initLisener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onSuccess(String content, Object object, int reqType) {

    }

    @Override
    public void onFailure(Throwable error, String content, int reqType) {

    }
}
