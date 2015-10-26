package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.module.entity.TradeEntity;

/**
 * Created by Administrator on 2015/6/2.
 */
public class TradeAdapter extends BaseAdapter{

    private LayoutInflater mInflater = null;
    private TradeEntity mEntity   = null;

    private String[] mType = {"现金", "礼金"};

    private Context mContext = null;

    public TradeAdapter(Context context)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(TradeEntity entity)
    {
        this.mEntity = entity;
        notifyDataSetChanged();
    }

    public TradeEntity getData()
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
        if (mEntity != null && mEntity.mList != null && mEntity.mList.size() > 0)
        {
            return mEntity.mList.size();
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
    public TradeEntity.ItemEntity getItem(int position)
    {
        if (mEntity.mList != null && mEntity.mList.size() > 0)
        {
            return (TradeEntity.ItemEntity) mEntity.mList.get(position);
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

    public View getView(int position, View convertView, ViewGroup parent)
    {
        TradeEntity.ItemEntity entity = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.riches_trade_list_item, null);
            viewHolder.mShouzhiIcon = (ImageView) convertView.findViewById(R.id.riches_trade_icon);
            viewHolder.typeTextView = (TextView) convertView.findViewById(R.id.riches_trade_type);
            viewHolder.mMemoTextView = (TextView) convertView.findViewById(R.id.riches_trade_memo);
            viewHolder.mCreateTextView = (TextView) convertView.findViewById(R.id.riches_trade_createtime);
            viewHolder.mPayTextView = (TextView) convertView.findViewById(R.id.riches_trade_payamount);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //现金
        if(entity.type == 0)
        {
            //收
            if(entity.shouzhi == 0)
            {
                int color =  mContext.getResources().getColor(R.color.ct_red);
                viewHolder.mPayTextView.setTextColor(color);
                viewHolder.mShouzhiIcon.setImageResource(R.mipmap.trade_cash_income);
            }
            //支
            else if(entity.shouzhi == 1)
            {
                int color =  mContext.getResources().getColor(R.color.ct_blue);
                viewHolder.mPayTextView.setTextColor(color);
                viewHolder.mShouzhiIcon.setImageResource(R.mipmap.trade_cash_expend);
            }
        }
        //礼金
        else if(entity.type == 1)
        {
            //收
            if(entity.shouzhi == 0)
            {
                int color =  mContext.getResources().getColor(R.color.ct_red);
                viewHolder.mPayTextView.setTextColor(color);
                viewHolder.mShouzhiIcon.setImageResource(R.mipmap.trade_gift_income);
            }
            //支
            else if(entity.shouzhi == 1)
            {
                int color =  mContext.getResources().getColor(R.color.ct_blue);
                viewHolder.mPayTextView.setTextColor(color);
                viewHolder.mShouzhiIcon.setImageResource(R.mipmap.trade_gift_expend);
            }
        }

        viewHolder.typeTextView.setText(mType[entity.type]);
        viewHolder.mMemoTextView.setText(entity.memo);
        viewHolder.mPayTextView.setText(entity.payamount);
        viewHolder.mCreateTextView.setText(entity.createtime);

        return convertView;
    }

    private class ViewHolder
    {
        public ImageView mShouzhiIcon;
        public TextView typeTextView;
        public TextView mMemoTextView;
        public TextView mPayTextView;
        public TextView mCreateTextView;
    }

}
