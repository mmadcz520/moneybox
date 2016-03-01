package com.changtou.moneybox.module.page;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.widget.ExEditView;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2015/3/26.
 */
public class MoreFragment extends BaseFragment implements View.OnTouchListener{

    private View mView = null;

    private Context mContext = null;

    private ExEditView mVersionView = null;

    private ToggleButton tglSound = null;

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.setting_fragment, null);
        mContext = this.getActivity();

        mVersionView = (ExEditView) mView.findViewById(R.id.more_check_update);

        String versionName = BaseApplication.getInstance().getVersionName() + "("+BaseApplication.getInstance().getChannelName()+")";
        int color = getResources().getColor(R.color.font_black);
        mVersionView.setMessage(versionName, color);

//        ListView pageList1 = (ListView) mView.findViewById(R.id.lv_setting1);
//        ListView pageList2 = (ListView) mView.findViewById(R.id.lv_setting2);
//        initSettingList(pageList1, R.array.item_setting1);
//        initSettingList(pageList2, R.array.item_setting2);

        ExEditView view1 = (ExEditView)mView.findViewById(R.id.more_about_me);
        ExEditView view2 = (ExEditView)mView.findViewById(R.id.more_tell_service);
        ExEditView view3 = (ExEditView)mView.findViewById(R.id.more_push_msg);

        tglSound = (ToggleButton)mView.findViewById(R.id.tglSound);

        view1.setOnTouchListener(this);
        view2.setOnTouchListener(this);
        view3.setOnTouchListener(this);
        mVersionView.setOnTouchListener(this);


        final ACache cache = ACache.get(BaseApplication.getInstance());
        boolean ispush = true;
        final PushAgent mPushAgent = PushAgent.getInstance(this.getActivity());
        if(cache.getAsObject("ispush") == null)
        {
            ispush = true;
        }
        else
        {
            ispush = (boolean)cache.getAsObject("ispush");
        }
        tglSound.setChecked(ispush);

        if(ispush) {
            mPushAgent.enable();
            PushAgent.getInstance(this.getActivity()).onAppStart();
        }
        else
        {
            mPushAgent.disable();
        }

        tglSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Toast.makeText(MoreFragment.this.getActivity(), "推送消息启用", Toast.LENGTH_LONG).show();
                    mPushAgent.enable();

                    cache.put("ispush", true);
                }
                else
                {
                    Toast.makeText(MoreFragment.this.getActivity(), "推送消息关闭", Toast.LENGTH_LONG).show();
                    mPushAgent.disable();

                    cache.put("ispush", false);
                }
            }
        });

        return mView;
    }


    protected void initListener() {
//        setOnClickListener(R.id.more_about_me);
//        setOnClickListener(R.id.more_tell_service);
//        setOnClickListener(R.id.more_check_update);
//        setOnClickListener(R.id.more_push_msg);
    }

    public void treatClickEvent(int id) {
        switch (id) {
            case R.id.more_about_me: {
                Intent intent = new Intent(this.getActivity(), WebActivity.class);
                intent.putExtra("url", "http://m.changtounet.com");
                startActivity(intent);

                break;
            }

            case R.id.more_tell_service: {
                popoTeleDialog();
                break;
            }

            case R.id.more_check_update: {
                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.Timeout: // time out
                                Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });

                UmengUpdateAgent.update(mContext);

                break;
            }
        }
    }

    protected void initData(Bundle savedInstanceState) {

    }

    public void onSuccess(String content, Object object, int reqType) {

    }

    public void onFailure(Throwable error, String content, int reqType) {

    }

    /**
     * 注册成功后弹框
     */
    private void popoTeleDialog() {

        final SweetAlertDialog sad = new SweetAlertDialog(this.getActivity(), SweetAlertDialog.NORMAL_TYPE);
        sad.setContentText("400-800-6270");
        sad.setConfirmText("呼叫");
        sad.setCancelText("取消");
        sad.setTitleText("客服电话");
        sad.show();

        sad.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4008006270"));
                startActivity(intent);

                sad.cancel();
            }
        });
    }


    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int id = v.getId();

        switch (id) {
            case R.id.more_about_me: {
                Intent intent = new Intent(this.getActivity(), WebActivity.class);
                intent.putExtra("url", "https://m.changtounet.com/about.htm");
                intent.putExtra("title", "关于我们");
                startActivity(intent);

                break;
            }

            case R.id.more_tell_service: {
                popoTeleDialog();
                break;
            }

            case R.id.more_check_update: {
                UmengUpdateAgent.setUpdateAutoPopup(false);
                UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        switch (updateStatus) {
                            case UpdateStatus.Yes: // has update
                                UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
                                break;
                            case UpdateStatus.No: // has no update
                                Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.NoneWifi: // none wifi
                                Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                                break;
                            case UpdateStatus.Timeout: // time out
                                Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });

                UmengUpdateAgent.update(mContext);

                break;
            }
        }

        return false;
    }
}
