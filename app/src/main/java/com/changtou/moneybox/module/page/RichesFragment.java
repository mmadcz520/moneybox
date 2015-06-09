package com.changtou.moneybox.module.page;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.adapter.ExGridAdapter;

import java.util.HashMap;
import java.util.Map;

public class RichesFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int[] mImgRes = {R.drawable.riches_btn_adf_selector,
                R.drawable.riches_btn_invest_selector,
                R.drawable.riches_btn_flow_selector,
                R.drawable.riches_btn_safe_selector,
                R.drawable.riches_btn_wd_selector,
                R.drawable.riches_btn_safe_selector};

        String[] titleList = this.getActivity().getResources().getStringArray(R.array.riches_modules);

        View view = inflater.inflate(R.layout.riches_fragment, container, false);
        GridView gv = (GridView) view.findViewById(R.id.brainheroall);
        ExGridAdapter sa = new ExGridAdapter(this.getActivity(), mImgRes, titleList);
        gv.setAdapter(sa);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position)
                {
                    case 0:
//                        final Intent intent0 = new Intent(RichesFragment.this.getActivity(), RichesFlowActivity.class);
//                        startActivity(intent0);
                        break;
                    case 1:
                        final Intent intent1 = new Intent(RichesFragment.this.getActivity(), RichesInvestListActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        final Intent intent2= new Intent(RichesFragment.this.getActivity(), RichesTradeActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        final Intent intent3 = new Intent(RichesFragment.this.getActivity(), RichesFlowActivity.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        final Intent intent4 = new Intent(RichesFragment.this.getActivity(), RichesWithdrawActivity.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        final Intent intent5 = new Intent(RichesFragment.this.getActivity(), RichesSafeActivity.class);
                        startActivity(intent5);
                        break;
                }
            }
        });

        ImageView imageView = (ImageView)view.findViewById(R.id.riches_signbar);
        Animation translateAnimation = AnimationUtils.loadAnimation(this.getActivity(), R.anim.tip);
        translateAnimation.setDuration(200);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        imageView.setAnimation(translateAnimation); //这里iv就是我们要执行动画的item，例如一个imageView
        translateAnimation.start();


        getAllCallRecords(this.getActivity());

        return view;
    }

    public static Map<String, String> getAllCallRecords(Context context) {

        Map<String, String> temp = new HashMap<>();
        Cursor c = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        if (c.moveToFirst()) {
            do {
                // 获得联系人的ID号
                String contactId = c.getString(c
                        .getColumnIndex(ContactsContract.Contacts._ID));
                // 获得联系人姓名
                String name = c
                        .getString(c
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                // 查看该联系人有多少个电话号码。如果没有这返回值为0
                int phoneCount = c
                        .getInt(c
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String number = null;
                if (phoneCount > 0) {
                    // 获得联系人的电话号码
                    Cursor phones = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + contactId, null, null);
                    if (phones.moveToFirst()) {
                        number = phones
                                .getString(phones
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    phones.close();
                }
                temp.put(name, number);
            } while (c.moveToNext());
        }
        c.close();
        return temp;
    }



    @Override
    protected void initListener() {

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
