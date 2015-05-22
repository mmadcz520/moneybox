package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changtou.R;

/**
 * Created by Administrator on 2015/5/12.
 */
public class ExGridAdapter extends BaseAdapter {

    private ImageView mImageBtn = null;
    private TextView mTitleView = null;

    private LayoutInflater mInflater = null;

    private int[] mResIds = null;
    private String[] mTitels = null;

    /**
     *
     * @param resIds
     */
    public ExGridAdapter(Context context, int[] resIds, String[] titels)
    {
        this.mResIds = resIds;
        this.mTitels = titels;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mResIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.riches_module_item, null);
            mImageBtn = (ImageView)convertView.findViewById(R.id.riches_grid_image);
            mImageBtn.setBackgroundResource(mResIds[position]);
            mTitleView = (TextView)convertView.findViewById(R.id.riches_grid_name);
            mTitleView.setText(mTitels[position]);
        }
        else
        {

        }

        return convertView;
    }
}
