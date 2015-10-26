package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.module.entity.InvestListEntity;
import com.changtou.moneybox.module.page.ProductDetailsActivity;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/5/25.
 */
public class InvestListAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;
    private LinkedList mData   = null;

    private Context mContext = null;

    private static String[] mTypeName = {"还款中", "已结清", "已退出"};
    private int mType = 0;

    public InvestListAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
        mContext = context;
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
    public View getView(final int position, View convertView, ViewGroup parent)
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

            convertView.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    InvestListEntity.ItemEntity item = ( InvestListEntity.ItemEntity)mData.get(position);
                    String pid = item.id;
                    int type = item.type;

                    goToProducDtetails(pid, type, mTypeName[mType]);
                }
            });
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

    /**
     * 跳转到详情页
     */
    private void goToProducDtetails(String id, int type, String state)
    {
        Intent intent = new Intent(mContext, ProductDetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("state", state);
        mContext.startActivity(intent);
    }

    public void setProdType(int type)
    {
        this.mType = type;
    }
}
