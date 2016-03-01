package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.widget.RoundProgressBar;

import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * 描述: 产品列表适配器类
 *
 * @author zhoulongfei
 * @since 2015-04-10
 */
public class ProductListAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;

    private LinkedList mData = null;

    public ProductListAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(LinkedList data)
    {
        this.mData = data;
        notifyDataSetChanged();
    }


    /**
     *描述: 获取产品列表长度
     *
     * @return 产品列表长度
     */
    public int getCount()
    {
        if (mData != null)
        {
            return mData.size();
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
        return 0;
    }

    /**
     * @see BaseAdapter#getView(int, View, ViewGroup)
     */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ProductEntity.ItemEntity item = (ProductEntity.ItemEntity)getItem(position);
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.product_list_item, null);

            viewHolder.mTitleTextView = (TextView) convertView.findViewById(R.id.pro_list_titile);
            viewHolder.interestTextView = (TextView) convertView.findViewById(R.id.pro_list_interest);
            viewHolder.maturityTextView = (TextView) convertView.findViewById(R.id.pro_list_maturity);
            viewHolder.minamountTextView = (TextView) convertView.findViewById(R.id.pro_list_minamount);
            viewHolder.amountTextView = (TextView) convertView.findViewById(R.id.pro_list_amount);

            viewHolder.scheduleView = (RoundProgressBar)convertView.findViewById(R.id.pro_list_schedule);
            viewHolder.describeTextView = (TextView)convertView.findViewById(R.id.pro_list_schedule_describe);

            viewHolder.lijinTextView = (TextView)convertView.findViewById(R.id.pro_list_lijininterest);

            viewHolder.lijinLayout = (FrameLayout)convertView.findViewById(R.id.pro_list_lijinlayout);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTitleTextView.setText(item.projectname);
        viewHolder.interestTextView.setText(item.interest);
        viewHolder.maturityTextView.setText(item.maturity);
        viewHolder.minamountTextView.setText(item.minamount);
        viewHolder.amountTextView.setText(item.syje);

        String jd = item.jd;
        double schedule = Double.parseDouble(jd) * 100;
        double d = Math.rint(schedule);
        int schedule_int = (int)(d);
        viewHolder.scheduleView.setProgress(schedule_int);
        viewHolder.describeTextView.setText(schedule_int + "%");

        viewHolder.lijinTextView.setText(item.lijininterest + "%"+ "礼金变现");

        if(item.lijininterest>0)
        {
            viewHolder.lijinLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            viewHolder.lijinLayout.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private class ViewHolder
    {
        public TextView mTitleTextView;
        public TextView interestTextView;
        public TextView maturityTextView;
        public TextView minamountTextView;
        public TextView amountTextView;

        public RoundProgressBar scheduleView;
        public TextView  describeTextView;

        public TextView lijinTextView;
        public FrameLayout lijinLayout;
    }
}
