package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.module.entity.BankCardEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/21.
 */
public class BankCardAdapter extends BaseAdapter{

    private LayoutInflater mInflater = null;
    private BankCardEntity mEntity   = null;

    private String[] list_image = null;

    private Context mContext = null;

    private Map<String, String> mBankInfoList = null;

    public BankCardAdapter(Context context, Map<String, String> bank)
    {
        try
        {
            mInflater = LayoutInflater.from(context);
            list_image =  context.getAssets().list("bank_icon");
            this.mContext = context;

            this.mBankInfoList = bank;
        }
        catch (Exception e)
        {

        }
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
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.riches_safe_bank_item, null);
            viewHolder.mBankNum = (TextView) convertView.findViewById(R.id.riches_bank_item_no);
            viewHolder.mBankIcon = (ImageView) convertView.findViewById(R.id.riches_bank_item_logo);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String account = entity.account;
        if(account.length()>10)
        {
            account = account.substring(0,4) + " **** " + account.substring(account.length()-4,account.length());
        }

        viewHolder.mBankNum.setText(account);

        if(entity.isdefault.equals("是"))
        {
            convertView.setBackgroundResource(R.drawable.stroke_bankcard_default);
        }
        else
        {
            convertView.setBackgroundResource(R.color.ct_white);
        }
        setBankIcon(viewHolder.mBankIcon, entity.bank);

        return convertView;
    }

    private class ViewHolder
    {
        public ImageView mBankIcon = null;
        public TextView mBankNum = null;
    }

    /**
     * 更改默认提现银行卡
     */
    public void changeDefaultCard(int cardId)
    {
        notifyDataSetChanged();
    }

    private void setBankIcon(ImageView imageView, String bankName) {
        InputStream open = null;
        try {
            if (mBankInfoList == null) return;
            String temp = mBankInfoList.get(bankName);
            if (temp == null || !temp.startsWith("bank_icon")) return;

            open = mContext.getAssets().open(temp);
            Bitmap bitmap = BitmapFactory.decodeStream(open);

            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
