package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    private String[] mStateName = {"","还款中", "已结清", "已退出"};

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
        final InvestListEntity.ItemEntity entity = (InvestListEntity.ItemEntity)getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.riches_invest_list_item, parent, false);
            viewHolder.mItemLayout = convertView.findViewById(R.id.lld);
            viewHolder.projectnameView = (TextView) convertView.findViewById(R.id.invest_item_titile);
            viewHolder.withdrawamountView = (TextView) convertView.findViewById(R.id.invest_item_withdrawamount);
            viewHolder.rateView = (TextView) convertView.findViewById(R.id.invest_item_rate);
            viewHolder.starttimeView = (TextView) convertView.findViewById(R.id.invest_item_starttime);
            viewHolder.endtimeView = (TextView) convertView.findViewById(R.id.invest_item_endtime);
            viewHolder.expectinView = (TextView) convertView.findViewById(R.id.invest_item_expectin);
            viewHolder.maturityView = (TextView) convertView.findViewById(R.id.invest_item_maturity);
            viewHolder.fhView = (TextView) convertView.findViewById(R.id.fh);
            viewHolder.jxzqView = (TextView)convertView.findViewById(R.id.invest_item_jxzq);

            convertView.setTag(viewHolder);

            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    InvestListEntity.ItemEntity entity = (InvestListEntity.ItemEntity)getItem(viewHolder.postion);
                    if (entity.state == 1) {

                        InvestListEntity.ItemEntity item = (InvestListEntity.ItemEntity) mData.get(position);
                        String pid = item.id;
                        int type = item.type;

                        goToProducDtetails(pid, type, mStateName[entity.state]);
                    }
                    else
                    {
                        Toast.makeText(mContext,"产品已过期", Toast.LENGTH_SHORT).show();
                    }
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
        viewHolder.maturityView.setText(mStateName[entity.state]);
        viewHolder.postion = position;

        if(entity.state != 1)
        {
            viewHolder.mItemLayout.setBackgroundResource(R.color.ct_invalid);
            int color = mContext.getResources().getColor(R.color.font_invalid);
            viewHolder.projectnameView.setTextColor(color);
            viewHolder.withdrawamountView.setTextColor(color);
            viewHolder.rateView.setTextColor(color);
            viewHolder.starttimeView.setTextColor(color);
            viewHolder.endtimeView.setTextColor(color);
            viewHolder.expectinView.setTextColor(color);
            viewHolder.maturityView.setTextColor(color);
            viewHolder.jxzqView.setTextColor(color);

            viewHolder.fhView.setTextColor(color);

        }
        else
        {
            viewHolder.mItemLayout.setBackgroundResource(R.drawable.stroke_list_item);

            int black = mContext.getResources().getColor(R.color.font_black);
            int red = mContext.getResources().getColor(R.color.font_prompt);
            int importance = mContext.getResources().getColor(R.color.font_importance);
            viewHolder.projectnameView.setTextColor(black);
            viewHolder.withdrawamountView.setTextColor(red);
            viewHolder.rateView.setTextColor(red);
            viewHolder.starttimeView.setTextColor(importance);
            viewHolder.endtimeView.setTextColor(importance);
            viewHolder.expectinView.setTextColor(red);
            viewHolder.maturityView.setTextColor(importance);
            viewHolder.jxzqView.setTextColor(importance);

            viewHolder.fhView.setTextColor(importance);
        }

        return convertView;
    }

    private class ViewHolder
    {
        public View mItemLayout;
        public TextView projectnameView;
        public TextView withdrawamountView;
        public TextView rateView;
        public TextView starttimeView;
        public TextView endtimeView;
        public TextView expectinView;
        public TextView maturityView;
        public TextView fhView;
        public TextView jxzqView;

        private int postion = 0;
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
}
