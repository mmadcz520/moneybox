package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.module.entity.RechargeEntity;

/**
 * 充值记录适配
 *
 * Created by Administrator on 2015/6/2.
 */
public class RechargeAdapter extends BaseAdapter{

    private LayoutInflater mInflater = null;
    private RechargeEntity mEntity   = null;

    private Context mContext = null;

    public RechargeAdapter(Context context)
    {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(RechargeEntity entity)
    {
        this.mEntity = entity;
        notifyDataSetChanged();
    }

    public RechargeEntity getData()
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
    public RechargeEntity.ItemEntity getItem(int position)
    {
        if (mEntity.mList != null && mEntity.mList.size() > 0)
        {
            return (RechargeEntity.ItemEntity) mEntity.mList.get(position);
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
        RechargeEntity.ItemEntity entity = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.riches_recharge_list_item, null);
            viewHolder.tradeNumTextView = (TextView) convertView.findViewById(R.id.recharge_list_titile);
            viewHolder.payamountTextView = (TextView) convertView.findViewById(R.id.recharge_list_money);
            viewHolder.mMemoTextView = (TextView) convertView.findViewById(R.id.recharge_list_memo);
            viewHolder.mCreateTextView = (TextView) convertView.findViewById(R.id.recharge_list_time);
            viewHolder.statusTextView = (TextView) convertView.findViewById(R.id.recharge_list_state);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tradeNumTextView.setText("支付编号：" + entity.tradeNum);
        viewHolder.payamountTextView.setText(entity.payamount);
        viewHolder.mMemoTextView.setText(entity.memo);
        viewHolder.statusTextView.setText(entity.status);
        viewHolder.mCreateTextView.setText(entity.creatime);

        return convertView;
    }

    private class ViewHolder
    {
        public TextView tradeNumTextView;
        public TextView payamountTextView;
        public TextView mMemoTextView;
        public TextView statusTextView;
        public TextView mCreateTextView;
    }

}
