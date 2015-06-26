package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.module.entity.InvestorEntity;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;

import java.util.LinkedList;

/**
 * 产品详情 ->  投资人列表
 *
 *
 */
public class ProductInvestorAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;
    private LinkedList mData = new LinkedList();

    public ProductInvestorAdapter(Context context)
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
        return mData.size();
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
        ProductDetailsEntity.TzListEntity entity = (ProductDetailsEntity.TzListEntity)getItem(position);
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.product_investor_item, parent, false);
            viewHolder.investor_name = (TextView) convertView.findViewById(R.id.investor_user);
            viewHolder.investor_sum = (TextView) convertView.findViewById(R.id.investor_sum);
            viewHolder.investor_time = (TextView) convertView.findViewById(R.id.investor_time);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.investor_name.setText(entity.username);
        viewHolder.investor_sum.setText(entity.withdrawAmount);
        viewHolder.investor_time.setText(entity.createTime);
        return convertView;
    }

    private class ViewHolder
    {
        public TextView investor_name;
        public TextView investor_sum;
        public TextView investor_time;
    }
}
