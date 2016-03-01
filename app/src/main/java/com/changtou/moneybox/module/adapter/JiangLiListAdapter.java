package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.module.entity.JiangLiInfoEntity;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/5/25.
 */
public class JiangLiListAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;
    private LinkedList mData   = null;

    private Context mContext = null;

    public JiangLiListAdapter(Context context)
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
        final JiangLiInfoEntity.ItemEntity entity = (JiangLiInfoEntity.ItemEntity)getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.riches_jiangli_list_item, parent, false);
            viewHolder.interestText = (TextView)convertView.findViewById(R.id.riches_jiangli_percent);
            viewHolder.moneyText = (TextView) convertView.findViewById(R.id.riches_jiangli_money);
            viewHolder.memoText = (TextView) convertView.findViewById(R.id.riches_jiangli_memo);
            viewHolder.repaytimeText = (TextView) convertView.findViewById(R.id.riches_jiangli_time);
            viewHolder.statusText = (TextView)convertView.findViewById(R.id.riches_jiangli_state);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String[] interst = entity.interest.split("\\%");
        viewHolder.interestText.setText(interst[0]);
        viewHolder.moneyText.setText(entity.money);
        viewHolder.memoText.setText(entity.memo);
        viewHolder.repaytimeText.setText("支付时间:" + entity.repaytime);
        viewHolder.statusText.setText("(" + entity.status + ")");

        return convertView;
    }

    private class ViewHolder
    {
        public TextView interestText;
        public TextView moneyText;
        public TextView statusText;
        public TextView memoText;
        public TextView repaytimeText;
    }
}
