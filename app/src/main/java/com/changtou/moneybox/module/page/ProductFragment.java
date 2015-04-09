package com.changtou.moneybox.module.page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.widget.DemoAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 描述:产品页
 * Created by Administrator on 2015/3/26.
 *
 * @author zhoulongfei
 * @since 2015-3-26
 */
public class ProductFragment extends BaseFragment{

    ///111111111111111111111111111111111
    //12121212212

    private Context mContext = null;
    private ListView xList = null;
    private DemoAdapter adapter = null;

    GridView gv=null;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_product, null);

        mContext=this.getActivity();

        gv=(GridView) mView.findViewById(R.id.pro_list);
        ArrayList<HashMap<String,Object>> al=new ArrayList<HashMap<String,Object>>();

        for(int i = 0;i < 3;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put("icon", R.drawable.icon_more);//添加图像资源的ID
            map.put("name","长投宝");//按序号做ItemText
            al.add(map);
        }

        SimpleAdapter sa=new SimpleAdapter(mContext,al,R.layout.gridview_product,new String[]{"icon","name"},new int[]{R.id.ItemImage,R.id.ItemText});
        gv.setAdapter(sa);

        return mView;
    }

    @Override
    protected void initLisener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //adapter=new DemoAdapter(mContext);
        //xList.setAdapter(adapter);
        //onInitList();
    }

    @Override
    public void onSuccess(String content, Object object, int reqType) {

    }

    @Override
    public void onFailure(Throwable error, String content, int reqType) {

    }
}
