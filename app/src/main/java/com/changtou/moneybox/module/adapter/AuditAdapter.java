package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.utils.AsyncImageLoader;

import java.util.LinkedList;

/**
 * Created by Administrator on 2015/5/29.
 * <p/>
 * 合同图片适配器
 */
public class AuditAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private LinkedList mImgList = null;

    private AsyncImageLoader mAsyncImageLoader = null;

    public AuditAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mAsyncImageLoader = new AsyncImageLoader();
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setData(LinkedList list) {
        this.mImgList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mImgList != null && mImgList.size() > 0) {
            return mImgList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mImgList != null) {
            return mImgList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String imgSrc = (String)getItem(position);
        ViewHolder viewHolder;

        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.product_details_audit_item, null);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.audit_img);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AsyncImageLoader mAsyncImageLoader = new AsyncImageLoader();
        mAsyncImageLoader.showImageAsyn(viewHolder.mImageView, imgSrc, R.mipmap.ic_launcher);

        return convertView;
    }


    private class ViewHolder
    {
        public ImageView mImageView;
    }
}
