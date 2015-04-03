package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.BaseAdapter;

import com.changtou.R;
import com.changtou.moneybox.module.entity.ProductEntity;

/**
 * Created by Administrator on 2015/3/30.
 */
public class ProductListAdapter extends BaseAdapter{

    private Context mContext = null;
    private LayoutInflater mInflater;
    private ProductEntity mEntity = null;

    public ProductListAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setData(ProductEntity entity) {
        this.mEntity = entity;
        notifyDataSetChanged();
    }

    public ProductEntity getData() {
        return mEntity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (mEntity != null && mEntity.mList != null && mEntity.mList.size() > 0) {
            return mEntity.mList.size();
        }
        return 0;
    }

    @Override
    public ProductEntity.ProListEntity getItem(int position) {
        if (mEntity.mList != null && mEntity.mList.size() > 0) {
            return (ProductEntity.ProListEntity)mEntity.mList.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductEntity.ProListEntity entity = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_product, null);
//            viewHolder.txt_top = (TextView) convertView
//                    .findViewById(R.id.txt_top);
//            viewHolder.txt_bottom = (TextView) convertView
//                    .findViewById(R.id.txt_bottom);
            //convertView.setBackgroundColor(Color.GRAY);

            RoundProgressBar mRoundProgressBar1 = (RoundProgressBar) convertView.findViewById(R.id.roundProgressBar);
            mRoundProgressBar1.setProgress(15);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.txt_top.setText(entity.name);
        return convertView;
    }

    private class ViewHolder {
        public TextView txt_top;
        public TextView txt_bottom;
    }
}
