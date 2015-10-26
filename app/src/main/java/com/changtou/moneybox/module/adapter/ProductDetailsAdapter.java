package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.moneybox.R;

/**
 * Created by Administrator on 2015/5/3 0003.
 */
public class ProductDetailsAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;

    private String[] mKeys = {"项目名称", "还款方式", "还款时间"};
    private String[] mValues = {"", "", ""};

    public ProductDetailsAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return mKeys.length;
    }

    @Override
    public String getItem(int position) {
        return mValues[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.product_details_list_item, null);

            viewHolder.mKey = (TextView) convertView.findViewById(R.id.pro_key);
            viewHolder.mValue = (TextView) convertView.findViewById(R.id.pro_value);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mKey.setText(mKeys[position]);
        viewHolder.mValue.setText(mValues[position]);

        return convertView;
    }

    public void setData(String[] keys, String[] values)
    {
        mKeys = keys;
        mValues = values;

        notifyDataSetChanged();
    }


    private class ViewHolder
    {
        public TextView mKey;
        public TextView mValue;
    }
}
