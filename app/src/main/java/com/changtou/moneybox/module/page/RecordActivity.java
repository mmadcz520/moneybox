package com.changtou.moneybox.module.page;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.graphics.drawable.PaintDrawable;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.http.async.RequestParams;
import com.changtou.moneybox.module.adapter.InvestListAdapter;
import com.changtou.moneybox.module.adapter.TradeAdapter;
import com.changtou.moneybox.module.entity.InvestListEntity;
import com.changtou.moneybox.module.entity.TradeEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.MyAdapter;
import com.changtou.moneybox.module.widget.MyToAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

public class RecordActivity extends CTBaseActivity implements OnTouchListener,
        OnDismissListener {
    private LinearLayout ll_Investment, ll_Trading;
    private ListView lv1;
    private ImageView icon1, icon2;
    private int screenWidth;
    private int screenHeight;
    private MyAdapter adapter;
    private MyToAdapter mToadapter;
    private int idx;

    private BaseAdapter mAdapter = null;
    private PullToRefreshListView mPullRefreshListView;
    private ListView actualListView;

    private LinkedList mAllList = null;

    private LinkedList list1 = null;
    private LinkedList list2 = null;
    private LinkedList list3 = null;

    private String mShouzhi = "";
    @Override
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_jilu);
        initScreenWidth();
        findViews();
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    protected void initData()
    {
        initInvestListRequest();
    }

    private void initInvestListRequest() {
        String url =  HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_INVEST_LIST);

        sendRequest(HttpRequst.REQ_TYPE_INVEST_LIST,
                url,
                mParams,
                getAsyncClient(), false);
    }

    private void findViews() {
        ll_Investment = (LinearLayout) findViewById(R.id.ll_all);
        ll_Trading = (LinearLayout) findViewById(R.id.ll_location);
        icon1 = (ImageView) findViewById(R.id.icon1);
        icon2 = (ImageView) findViewById(R.id.icon2);

        ll_Investment.setOnTouchListener(this);
        ll_Trading.setOnTouchListener(this);

        mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.product_list);
        actualListView = mPullRefreshListView.getRefreshableView();
        actualListView.setEnabled(true);

        mAdapter = new InvestListAdapter(this);
        actualListView.setAdapter(mAdapter);


    }

    public void showPopupWindow(View anchor, int flag) {
        final PopupWindow popupWindow = new PopupWindow(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(RecordActivity.this).inflate(
                R.layout.windows_popupwindow, null);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        lv1 = (ListView) contentView.findViewById(R.id.lv1);
        switch (flag) {
            case 1:
                adapter = new MyAdapter(RecordActivity.this, initLv1Data());
                break;
            case 2:
                adapter = new MyAdapter(RecordActivity.this, initLv2Data());
                break;
        }
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String name = String.valueOf(lv1.getAdapter().getItem(position));
                switchContent(name);
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(this);
        popupWindow.setWidth(screenWidth);
//        popupWindow.setHeight(screenHeight);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.showAsDropDown(anchor);

    }

    private void initScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
    }

    private List<String> initLv1Data() {
        List<String> l = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    l.add("全部");
                    break;
                case 1:
                    l.add("还款中");
                    break;
                case 2:
                    l.add("已结清");
                    break;
                case 3:
                    l.add("已退出");
                    break;
            }
        }
        return l;
    }

    private List<String> initLv2Data() {
        List<String> l = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    l.add("全部");
                    break;
                case 1:
                    l.add("收入");
                    break;
                case 2:
                    l.add("支出");
                    break;
            }
        }
        return l;
    }

    @Override
    public void onDismiss() {
        icon1.setImageResource(R.drawable.icon_435);
        icon2.setImageResource(R.drawable.icon_435);
    }

    public boolean onTouch(View arg0, MotionEvent arg1) {

        final int action = MotionEventCompat.getActionMasked(arg1);

//	    switch (action) {
//	        case MotionEvent.ACTION_UP: {

        switch (arg0.getId()) {
            case R.id.ll_all:
                idx = 1;
                icon1.setImageResource(R.drawable.icon_43343434);
                showPopupWindow(findViewById(R.id.ll_layout), 1);
                break;
            case R.id.ll_location:
                idx = 2;
                icon2.setImageResource(R.drawable.icon_43343434);
                showPopupWindow(findViewById(R.id.ll_layout), 2);
                break;
        }

        return false;
    }

    private void switchContent(String keyStr) {
        if (idx == 1) {

            actualListView.setAdapter(mAdapter);
            if (keyStr.equals("还款中")) {
//            ((InvestListAdapter)mAdapter).setProdType(0);
                ((InvestListAdapter) mAdapter).setData(list1);
            } else if (keyStr.equals("已结清")) {
//            ((InvestListAdapter)mAdapter).setProdType(1);
                ((InvestListAdapter) mAdapter).setData(list2);
            } else if (keyStr.equals("已退出")) {
//            ((InvestListAdapter)mAdapter).setProdType(2);
                ((InvestListAdapter) mAdapter).setData(list3);
            }
            else if(keyStr.equals("全部"))
            {
                ((InvestListAdapter) mAdapter).setData(mAllList);
            }
        }

        if(idx == 2) {
            if (keyStr.equals("全部")) {
                mShouzhi = "";
                initDealRequest();
            } else if (keyStr.equals("收入")) {
                mShouzhi = "0";
                initDealRequest();
            } else if (keyStr.equals("支出")) {
                mShouzhi = "1";
                initDealRequest();
            }
        }

    }


    public void onSuccess(String content, Object object, int reqType)
    {
        super.onSuccess(content, object, reqType);

        try {

            if(reqType == HttpRequst.REQ_TYPE_INVEST_LIST)
            {
                InvestListEntity entity = (InvestListEntity) object;
                Map<String, LinkedList> investMap = entity.getInvestMap();

                list1 = investMap.get("1");
                list2 = investMap.get("2");
                list3 = investMap.get("3");

                mAllList = entity.getAllInvestList();

                ((InvestListAdapter) mAdapter).setData(mAllList);
//                adapter.notifyDataSetChanged();

//                mSubPage1.initInvestList(list1);
//                mSubPage2.initInvestList(list2);
//                mSubPage3.initInvestList(list3);
            }
            if(reqType == HttpRequst.REQ_TYPE_DEAL)
            {
                TradeAdapter adapter = new TradeAdapter(this);
                actualListView.setAdapter(adapter);

                TradeEntity entity = (TradeEntity) object;
                adapter.setData(entity);
                adapter.notifyDataSetChanged();
            }
        }
        catch (Exception e)
        {
            BaseApplication.getInstance().backToLoginPage();
            Toast.makeText(BaseApplication.getInstance(), "账号在其他设备登陆,请重新登录", Toast.LENGTH_LONG).show();
        }
    }

    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
    }


    private void initDealRequest() {
        try {
            RequestParams params = new RequestParams();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shouzhi", mShouzhi);
            params.put("data", jsonObject.toString());

            sendRequest(HttpRequst.REQ_TYPE_DEAL,
                    HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_DEAL),
                    params,
                    getAsyncClient(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
