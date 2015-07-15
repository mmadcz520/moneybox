package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.module.entity.InvestListEntity;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/5/25.
 */
public class InvestListAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;
    private LinkedList mData   = null;

    public InvestListAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(LinkedList data)
    {
        this.mData = data;
        notifyDataSetChanged();
    }

    public LinkedList getData()
    {
        return mData;
    }

    /**
     *描述: 获取产品列表长度
     *
     * @return 产品列表长度
     */
    public int getCount()
    {
        if(mData != null)
        {
            return mData.size();
        }
        else
        {
            return 0;
        }
    }

    /**
     * 描述：获取内容
     *
     * @param position list列表位置
     * @return 产品实体对象
     * @see BaseAdapter#getItem(int)
     */
    public Object getItem(int position)
    {
        if (mData != null)
        {
            return mData.get(position);
        }
        return null;
    }

    /**
     * @see BaseAdapter#getItemId(int)
     */
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * @see BaseAdapter#getView(int, View, ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        InvestListEntity.ItemEntity entity = (InvestListEntity.ItemEntity)getItem(position);
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.riches_invest_list_item, parent, false);
            viewHolder.projectnameView = (TextView) convertView.findViewById(R.id.invest_item_titile);
            viewHolder.withdrawamountView = (TextView) convertView.findViewById(R.id.invest_item_withdrawamount);
            viewHolder.rateView = (TextView) convertView.findViewById(R.id.invest_item_rate);
            viewHolder.starttimeView = (TextView) convertView.findViewById(R.id.invest_item_starttime);
            viewHolder.endtimeView = (TextView) convertView.findViewById(R.id.invest_item_endtime);
            viewHolder.expectinView = (TextView) convertView.findViewById(R.id.invest_item_expectin);
            viewHolder.maturityView = (TextView) convertView.findViewById(R.id.invest_item_maturity);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.projectnameView.setText(entity.projectname);
        viewHolder.withdrawamountView.setText(entity.withdrawamount);
        viewHolder.rateView.setText(entity.rate);
        viewHolder.starttimeView.setText(entity.starttime);
        viewHolder.endtimeView.setText(entity.endtime);
        viewHolder.expectinView.setText(entity.expectin);
        viewHolder.maturityView.setText(entity.maturity);


        return convertView;
    }

    private class ViewHolder
    {
        public TextView projectnameView;
        public TextView withdrawamountView;
        public TextView rateView;
        public TextView starttimeView;
        public TextView endtimeView;
        public TextView expectinView;
        public TextView maturityView;
    }
}
