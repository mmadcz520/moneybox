package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changtou.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * 现金流item 适配器
 *
 * Created by Administrator on 2015/6/2.
 */
public class FlowItemAdapter extends BaseAdapter
{
    private ArrayList<Map<String, Object>> mData = null;
    private LayoutInflater mInflater = null;

    private Context mContext = null;

    public FlowItemAdapter(Context context)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if(mData != null)
        {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mData != null)
        {
            return mData.get(position);
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Map<String, Object> entity = mData.get(position);
        ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.riches_flow_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mTypeView = (ImageView) convertView.findViewById(R.id.flow_item_type);
            viewHolder.mTimeView = (TextView) convertView.findViewById(R.id.flow_item_time);
            viewHolder.mNameView = (TextView) convertView.findViewById(R.id.flow_item_name);
            viewHolder.mNumView = (TextView) convertView.findViewById(R.id.flow_item_num);
            viewHolder.mAffirmView = (ImageView) convertView.findViewById(R.id.flow_item_affirm);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int type = Integer.parseInt((String) entity.get("type"));
        if(type == 1)
        {
            viewHolder.mTypeView.setImageResource(R.mipmap.flow_interest);
        }
        else if(type == 0)
        {
            viewHolder.mTypeView.setImageResource(R.mipmap.flow_cash);
        }

        String time = (String)entity.get("time");
        String name = (String)entity.get("name");
        String num = (String)entity.get("num");
        viewHolder.mTimeView.setText(time);

        name = name.split("\\（")[0];

        viewHolder.mNameView.setText(name);
        viewHolder.mNumView.setText(num);

        boolean account = (boolean)entity.get("account");
        if(account)
        {
            viewHolder.mNumView.setTextColor(mContext.getResources().getColor(R.color.ct_blue));
            viewHolder.mAffirmView.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.mNumView.setTextColor(mContext.getResources().getColor(R.color.font_invalid));
            viewHolder.mAffirmView.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void setData( ArrayList<Map<String, Object>> data)
    {
        this.mData = data;
        notifyDataSetChanged();
    }

    class ViewHolder
    {
        public ImageView mTypeView = null;
        public TextView mTimeView = null;
        public TextView mNameView = null;
        public TextView mNumView = null;
        public ImageView mAffirmView = null;
    }
}
