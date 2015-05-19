package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.usermodule.UserManager;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.ExViewPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述 : App主界面
 * 1. 手势密码功能
 * 2. 四个子页面的切换
 *
 * @author zhoulongfei
 *
 */
public class MainActivity extends CTBaseActivity {

    //手势密码控件
//    private LocusPassWordView mPwdView = null;

    private ExViewPager mViewpager = null;

    //页面底部导航控件
    private LinearLayout[] mBars = new LinearLayout[4];

//    /**
//     * @param savedInstanceState
//     */
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.draw_pwd);
//        mPwdView = (LocusPassWordView) this.findViewById(R.id.mPassWordView);
//        mPwdView.setOnCompleteListener(new LocusPassWordView.OnCompleteListener() {
//
//            public void onComplete(String mPassword) {
//                //TODO 校验密码好进行跳转
//                //finish();
//            }
//        });
//    }

    /**
     * @see com.changtou.moneybox.common.activity.BaseActivity#initView(Bundle)
     * @param bundle 保存页面参数
     */
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_main);
        LinearLayout navbar_home = (LinearLayout) this.findViewById(R.id.navbar_home);
        LinearLayout navbar_product = (LinearLayout) this.findViewById(R.id.navbar_product);
        LinearLayout navbar_user = (LinearLayout) this.findViewById(R.id.navbar_user);
        LinearLayout navbar_more = (LinearLayout) this.findViewById(R.id.navbar_more);
        mViewpager = (ExViewPager) findViewById(R.id.viewpager);
        mViewpager.setScanScroll(false);

        mBars[0] = navbar_home;
        mBars[1] = navbar_product;
        mBars[2] = navbar_user;
        mBars[3] = navbar_more;

        switchNavBar(0);

        List<BaseFragment> viewList = new ArrayList<>();
        viewList.add(new HomeFragment());
        viewList.add(new ProductFragment());
        viewList.add(new RichesFragment());
        viewList.add(new SettingFragment());

        ExFPAdapter fPAdapter = new ExFPAdapter(getSupportFragmentManager(), viewList);
        mViewpager.setAdapter(fPAdapter);
        mViewpager.setScanScroll(false);
        mViewpager.setCurrentItem(0, false);
        mViewpager.setOffscreenPageLimit(viewList.size());
    }

    /**
     * @see com.changtou.moneybox.common.activity.BaseActivity#initLisener()
     */
    protected void initLisener() {
        setOnClickListener(R.id.navbar_home);
        setOnClickListener(R.id.navbar_product);
        setOnClickListener(R.id.navbar_user);
        setOnClickListener(R.id.navbar_more);
    }

    /**
     * @see com.changtou.moneybox.common.activity.BaseActivity#initData()
     */
    protected void initData() {

    }

    /**
     * 相应控件点击事件
     * @param id 控件id
     */
    public void treatClickEvent(int id) {
        switch (id)
        {
            case R.id.navbar_home:
                mViewpager.setCurrentItem(0);
                switchNavBar(0);
                break;
            case R.id.navbar_product:
                mViewpager.setCurrentItem(1);
                switchNavBar(1);
                break;
            case R.id.navbar_user:

//                ACache cache = ACache.get(this);
//                String token = cache.getAsString("token");

                mViewpager.setCurrentItem(2);
                switchNavBar(2);

                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.navbar_more:
                mViewpager.setCurrentItem(3);
                switchNavBar(3);
                break;
        }
    }

    /**
     *切换导航条
     *
     * @param pageId 页面
     */
    private void switchNavBar(int pageId)
    {
        for(int i = 0; i < 4; i++)
        {
            boolean a = (i==pageId);
            mBars[i].setSelected(i==pageId);
        }
    }

    /**
     * 保存app 状态
     *
     * @param outState
     * @param outPersistentState
     */
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//                mViewpager.setCurrentItem(2);
//                switchNavBar(2);
        Log.e("CT_MONEY", "onActivityResultonActivityResultonActivityResult");
    }
}
