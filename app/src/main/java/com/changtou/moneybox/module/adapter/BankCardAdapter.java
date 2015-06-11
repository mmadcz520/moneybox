package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.module.entity.BankCardEntity;

/**
 * Created by Administrator on 2015/5/21.
 */
public class BankCardAdapter extends BaseAdapter{

    private LayoutInflater mInflater = null;
    private BankCardEntity mEntity   = null;

    private int mDefaultCard = 0;

    public BankCardAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 设置数据
     * @param entity
     */
    public void setData(BankCardEntity entity)
    {
        this.mEntity = entity;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mEntity != null && mEntity.mList != null && mEntity.mList.size() > 0)
        {
            return mEntity.mList.size();
        }
        return 0;
    }

    @Override
    public BankCardEntity.BankListEntity getItem(int position) {
        if (mEntity.mList != null && mEntity.mList.size() > 0)
        {
            return (BankCardEntity.BankListEntity) mEntity.mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BankCardEntity.BankListEntity entity = getItem(position);
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.riches_safe_bank_item, null);
//            LinearLayout ll = (LinearLayout)convertView.findViewById(R.id.roundProgressBar);
//            ll.setWillNotDraw(false);
            viewHolder.txt_top = (TextView) convertView.findViewById(R.id.riches_bank_item_no);
//            viewHolder.txt_bottom = (TextView) convertView.findViewById(R.id.txt_bottom);
//            RoundProgressBar mRoundProgressBar1 = (RoundProgressBar) convertView.findViewById(R.id.roundProgressBar);
//            mRoundProgressBar1.setProgress(15);

            convertView.setTag(viewHolder);
//            viewHolder.txt_top.setText(entity.name);
        }
        else
        {
//            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(entity.deFlag == mDefaultCard)
        {
            convertView.setBackgroundResource(R.drawable.stroke_bankcard_default);
        }

        return convertView;
    }

    private class ViewHolder
    {
        public TextView txt_top;
//        public TextView txt_bottom;
    }

    /**
     * 更改默认提现银行卡
     */
    public void changeDefaultCard(int cardId)
    {
        this.mDefaultCard = cardId;
        notifyDataSetChanged();
    }
}
