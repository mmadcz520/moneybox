package com.changtou.moneybox.module.page;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.safe.LocusPassWordView;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.ExViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CTBaseActivity {

    //手势密码控件
    private LocusPassWordView mPwdView = null;

    private ExViewPager mViewpager = null;
    private ExFPAdapter mFPAdapter = null;
    private List<BaseFragment> viewList = null;
    private ImageButton btn_main,btn_product, btn_riches,btn_setting;

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
     * @param bundle
     */
    protected void initView(Bundle bundle) {
        setContentView(R.layout.activity_main);
        btn_main = (ImageButton) this.findViewById(R.id.btn_main);
        btn_product = (ImageButton) this.findViewById(R.id.btn_product);
        btn_riches = (ImageButton) this.findViewById(R.id.btn_riches);
        btn_setting = (ImageButton) this.findViewById(R.id.btn_setting);
        mViewpager = (ExViewPager) findViewById(R.id.viewpager);
    }

    /**
     * @see com.changtou.moneybox.common.activity.BaseActivity#initLisener()
     */
    protected void initLisener() {
        setOnClickListener(R.id.btn_main);
        setOnClickListener(R.id.btn_product);
        setOnClickListener(R.id.btn_riches);
        setOnClickListener(R.id.btn_setting);
    }

    /**
     * @see com.changtou.moneybox.common.activity.BaseActivity#initData(Bundle)
     * @param bundle
     */
    protected void initData(Bundle bundle) {
        viewList = new ArrayList();
        viewList.add(new HomeFragment());
        viewList.add(new ProductFragment());
        viewList.add(new RichesFragment());
        viewList.add(new SettingFragment());

        mFPAdapter = new ExFPAdapter(getSupportFragmentManager(), viewList);
        mViewpager.setAdapter(mFPAdapter);
        mViewpager.setScanScroll(false);
        mViewpager.setCurrentItem(0,false);
        mViewpager.setOffscreenPageLimit(viewList.size());
    }

    /**
     *
     * @param id
     */
    public void treatClickEvent(int id) {
        switch (id)
        {
            case R.id.btn_main:
                mViewpager.setCurrentItem(0);
                break;
            case R.id.btn_product:
                mViewpager.setCurrentItem(1);
                break;
            case R.id.btn_riches:
                mViewpager.setCurrentItem(2);
                break;
            case R.id.btn_setting:
                mViewpager.setCurrentItem(3);
                break;
        }
    }
}
