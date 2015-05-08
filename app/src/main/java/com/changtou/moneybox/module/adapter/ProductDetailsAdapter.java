package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.module.entity.ProductDetailsEntity;

/**
 * Created by Administrator on 2015/5/3 0003.
 */
public class ProductDetailsAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;
    private ProductDetailsEntity mEntity   = null;

    private String[] keys = {"项目名称", "还款方式", "还款时间"};
    private String[] values = {"[长投宝]上手易第256期", "按月付息,到期还本", "2015-07-17"};

    public ProductDetailsAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
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
    public ProductDetailsEntity.ProListEntity getItem(int position) {
        if (mEntity.mList != null && mEntity.mList.size() > 0)
        {
            return (ProductDetailsEntity.ProListEntity) mEntity.mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.product_details_list_item, null);

            viewHolder.mKey = (TextView) convertView.findViewById(R.id.pro_key);
            viewHolder.mValue = (TextView) convertView.findViewById(R.id.pro_value);


            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mKey.setText(keys[position]);
        viewHolder.mValue.setText(values[position]);

        return convertView;
    }

    public void setData(ProductDetailsEntity entity)
    {
        this.mEntity = entity;
        notifyDataSetChanged();
    }


    private class ViewHolder
    {
        public TextView mKey;
        public TextView mValue;
    }
}
