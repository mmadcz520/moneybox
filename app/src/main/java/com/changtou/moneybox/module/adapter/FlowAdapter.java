package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.module.entity.FlowEntity;

/**
 * 现金流list 适配器
 *
 * Created by Administrator on 2015/6/2.
 */
public class FlowAdapter extends BaseAdapter{

    private LayoutInflater mInflater = null;
    private FlowEntity mEntity   = null;

    public FlowAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(FlowEntity entity)
    {
        this.mEntity = entity;
        notifyDataSetChanged();
    }

    public FlowEntity getData()
    {
        return mEntity;
    }

    /**
     *描述: 获取产品列表长度
     *
     * @return 产品列表长度
     */
    public int getCount()
    {
        if (mEntity != null && mEntity.mMonth != null && mEntity.mMonth.size() > 0)
        {
            return mEntity.mMonth.size();
        }
        return 0;
    }

    /**
     * 描述：获取内容
     *
     * @param position list列表位置
     * @return 产品实体对象
     * @see BaseAdapter#getItem(int)
     */
    public FlowEntity.MonthEntity getItem(int position)
    {
        if (mEntity.mMonth != null && mEntity.mMonth.size() > 0)
        {
            return mEntity.mMonth.get(position);
        }
        return null;
    }

    /**
     * @see BaseAdapter#getItemId(int)
     */
    public long getItemId(int position)
    {
        return 0;
    }

    /**
     * @see BaseAdapter#getView(int, View, ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        FlowEntity.MonthEntity entity = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.riches_flow_month_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mTimeView = (TextView) convertView.findViewById(R.id.flow_time);
            viewHolder.mDueInView = (TextView) convertView.findViewById(R.id.flow_dueIn);
            viewHolder.mReceivedView = (TextView) convertView.findViewById(R.id.flow_received);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTimeView.setText(entity.time);
        viewHolder.mDueInView.setText(entity.dueIn);
        viewHolder.mReceivedView.setText(entity.received);

        return convertView;
    }

    class ViewHolder
    {
        public TextView mTimeView = null;

        public TextView mDueInView = null;

        public TextView mReceivedView = null;
    }
}
