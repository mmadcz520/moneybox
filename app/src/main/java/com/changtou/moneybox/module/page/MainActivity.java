package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.widget.ExFPAdapter;
import com.changtou.moneybox.module.widget.ExViewPager;
import com.changtou.moneybox.module.widget.SignInHUD;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateConfig;

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

    private ExViewPager mViewpager = null;

    //页面底部导航控件
    private LinearLayout[] mBars = new LinearLayout[4];

//    private SignInHUD mSignInHUD = null;

    /**
     * @see com.changtou.moneybox.common.activity.BaseActivity#initView(Bundle)
     * @param bundle 保存页面参数
     */
    protected void initView(Bundle bundle)
    {
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
        viewList.add(new MoreFragment());

        ExFPAdapter fPAdapter = new ExFPAdapter(getSupportFragmentManager(), viewList);
        mViewpager.setAdapter(fPAdapter);
        mViewpager.setScanScroll(false);
        mViewpager.setCurrentItem(0, false);
        mViewpager.setOffscreenPageLimit(viewList.size());

//        mSignInHUD = (SignInHUD)this.findViewById(R.id.signin_fragment);
        UpdateConfig.setDebug(true);
        UmengUpdateAgent.update(this);
    }

    /**
     * @see com.changtou.moneybox.common.activity.BaseActivity#initListener()
     */
    protected void initListener()
    {
        setOnClickListener(R.id.navbar_home);
        setOnClickListener(R.id.navbar_product);
        setOnClickListener(R.id.navbar_user);
        setOnClickListener(R.id.navbar_more);
    }

    @Override
    protected int setPageType() {
        return 0;
    }

    /***
     * http 请求 成功
     *
     * @param content   返回值
     * @param object    返回的转化对象
     * @param reqType   请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType)
    {
//        if(reqType == HttpRequst.REQ_TYPE_USERINFO)
//        {
//            UserInfoEntity userInfo = UserInfoEntity.getInstance();
//            Log.e("CT_MONEY", "userInfo" + userInfo.getEmail());
//            Log.e("CT_MONEY", "userInfo" + userInfo.getFullName());
//            Log.e("CT_MONEY", "userInfo" + userInfo.getIdCard());
//            Log.e("CT_MONEY", "userInfo" + userInfo.getCreatetime());
//            Log.e("CT_MONEY", "userInfo" + userInfo.getMobile());
//        }
    }

    /**
     *
     * http 请求 失败
     *
     * @param error
     * @param content
     * @param reqType
     */
    public void onFailure(Throwable error, String content, int reqType)
    {
        super.onFailure(error, content, reqType);
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

                //已经登录
                if(!BaseApplication.getInstance().isUserLogin())
                {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                }

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
            mBars[i].setSelected(i==pageId);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            //设置手势密码
            BaseApplication.getInstance().onBackground();
        }
        else
        {
            mViewpager.setCurrentItem(0);
            switchNavBar(0);
        }
    }

    public void onBackPressed()
    {
//        if(mSignInHUD.getVisibility() == View.VISIBLE)
//        {
//            mSignInHUD.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
            super.onBackPressed();
//        }
    }
}
