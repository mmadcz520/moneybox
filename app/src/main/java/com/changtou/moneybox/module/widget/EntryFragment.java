package com.changtou.moneybox.module.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.changtou.moneybox.R;
import com.changtou.moneybox.module.page.BootActivity;

public class EntryFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_boot_entry, null);
        v.findViewById(R.id.btn_entry).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                BootActivity activity = (BootActivity) getActivity();
                activity.entryApp();
            }
        });
        return v;
    }
}
