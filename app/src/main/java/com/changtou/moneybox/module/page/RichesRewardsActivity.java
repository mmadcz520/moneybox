package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.view.ViewStub;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.module.http.HttpRequst;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐奖励页面
 *
 * Created by Administrator on 2015/6/11.
 */
public class RichesRewardsActivity extends CTBaseActivity
{
    private ListView mRewarListView = null;

    private TextView mZhuceTextView = null;
    private TextView mTouziTextView = null;
    private TextView mLijinTextView = null;
    private TextView mXianjinTextView = null;

    /**
     * @see CTBaseActivity#initView(Bundle)
     */
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_rewards_layout);
        mRewarListView = (ListView)findViewById(R.id.riches_rewards_list);

        mZhuceTextView = (TextView)findViewById(R.id.zhuce_renshu);
        mTouziTextView = (TextView)findViewById(R.id.touzi_bishu);
        mLijinTextView = (TextView)findViewById(R.id.jiangli_lijin);
        mXianjinTextView = (TextView)findViewById(R.id.jiangli_xianjin);

    }

    @Override
    protected void initData()
    {
        setPageTitle("查看奖励");
//        initSettingList(mRewarListView);
        initRewardsListRequest();
    }

    @Override
    protected int setPageType()
    {
        return PAGE_TYPE_SUB;
    }

//    /*
//    * 初始化设置列表
//    */
//    private void initSettingList(ListView listView, String[] data, int len) {
//        List<Map<String, Object>> list = new ArrayList();
//        for (int i = 0; i < len; i++) {
//            Map<String, Object> map = new HashMap();
//            map.put("zhanghao", data[0]);
//            map.put("zhuce", data[1]);
//            map.put("touzi", data[2]);
//            list.add(map);
//        }
//
//        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.setting_item,
//                new String[]{"zhanghao", "zhuce", "touzi"}, new int[]{
//                R.id.zhanghao, R.id.zhuce, R.id.touzi
//        });
//        ViewStub viewStub = new ViewStub(this);
//        listView.addHeaderView(viewStub);
//        listView.setAdapter(adapter);
//    }

    private void initRewardsListRequest() {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_RECOMMENDLIST);

        sendRequest(HttpRequst.REQ_TYPE_RECOMMENDLIST,
                url,
                mParams,
                getAsyncClient(), false);
    }

    public void onSuccess(String content, Object object, int reqType)
    {
        try
        {
            JSONArray array = new JSONArray(content);

            JSONObject listObj = array.getJSONObject(0);
            JSONArray listData = listObj.getJSONArray("_list");

            int size = listData.length();

            List<Map<String, Object>> list = new ArrayList();
            for(int i = 0; i< size; i++)
            {
                JSONObject data = listData.getJSONObject(i);
                Map<String, Object> map = new HashMap();
                map.put("zhanghao", data.get("name"));
                map.put("zhuce", data.get("zhuce"));
                map.put("touzi", data.get("touzi"));
                list.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.setting_item,
                    new String[]{"zhanghao", "zhuce", "touzi"}, new int[]{
                    R.id.zhanghao, R.id.zhuce, R.id.touzi
            });
            ViewStub viewStub = new ViewStub(this);
            mRewarListView.addHeaderView(viewStub);
            mRewarListView.setAdapter(adapter);

            JSONObject listObj1 = array.getJSONObject(1);
            JSONObject listObj2 = array.getJSONObject(2);
            JSONObject listObj3 = array.getJSONObject(3);
            JSONObject listObj4 = array.getJSONObject(4);

            mZhuceTextView.setText(listObj1.getString("zhuceNum"));
            mTouziTextView.setText(listObj2.getString("touziNmu"));
            mLijinTextView.setText(listObj4.getString("lijin"));
            mXianjinTextView.setText(listObj3.getString("xianjin"));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        super.onSuccess(content, object, reqType);
    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }
}
