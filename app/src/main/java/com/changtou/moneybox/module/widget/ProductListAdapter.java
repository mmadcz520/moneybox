package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.BaseAdapter;

import com.changtou.R;
import com.changtou.moneybox.module.entity.ProductEntity;

/**
 * 描述: 产品列表适配器类
 *
 * @author zhoulongfei
 * @since 2015-04-10
 */
public class ProductListAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;
    private ProductEntity  mEntity   = null;

    public ProductListAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
    }

    public void setData(ProductEntity entity)
    {
        this.mEntity = entity;
        notifyDataSetChanged();
    }

    public ProductEntity getData()
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
    public ProductEntity.ProListEntity getItem(int position)
    {
        if (mEntity.mList != null && mEntity.mList.size() > 0)
        {
            return (ProductEntity.ProListEntity) mEntity.mList.get(position);
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
        ProductEntity.ProListEntity entity = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_product, null);
            viewHolder.txt_top = (TextView) convertView.findViewById(R.id.pro_list_titile);
//            viewHolder.txt_bottom = (TextView) convertView.findViewById(R.id.txt_bottom);
            convertView.setBackgroundColor(Color.GRAY);

//            RoundProgressBar mRoundProgressBar1 = (RoundProgressBar) convertView.findViewById(R.id.roundProgressBar);
//            mRoundProgressBar1.setProgress(15);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt_top.setText(entity.name);
        return convertView;
    }

    private class ViewHolder
    {
        public TextView txt_top;
//        public TextView txt_bottom;
    }
}
