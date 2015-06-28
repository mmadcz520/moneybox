package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.changtou.R;
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
    public String getItem(int position) {
        if (mImgList != null && mImgList.size() > 0) {
            return (String) mImgList.get(position);
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
        String imgSrc = getItem(position);

        Log.e("CT_MONEY", "imgSrc=" + imgSrc);

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.product_details_audit_item, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.audit_img);
            AsyncImageLoader mAsyncImageLoader = new AsyncImageLoader();
            mAsyncImageLoader.showImageAsyn(imageView, imgSrc, R.mipmap.ic_launcher);
        }
        else
        {

        }


        return convertView;
    }
}
