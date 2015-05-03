//package com.changtou.moneybox.module.widget;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.changtou.R;
//import com.changtou.moneybox.module.entity.Actor;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2015/4/23.
// */
//public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
//{
//    private List<Actor> actors;
//
//    private Context mContext;
//
//    public ProductAdapter( Context context , List<Actor> actors)
//    {
//        this.mContext = context;
//        this.actors = actors;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
//    {
//        // 给ViewHolder设置布局文件
//        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_product, viewGroup, false);
//        return new ViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder( ViewHolder viewHolder, int i )
//    {
//        // 给ViewHolder设置元素
//        Actor p = actors.get(i);
//        viewHolder.mTextView.setText(p.name);
////        viewHolder.mImageView.setImageDrawable(mContext.getDrawable(p.getImageResourceId(mContext)));
//    }
//
//    @Override
//    public int getItemCount()
//    {
//        // 返回数据总数
//        return actors == null ? 0 : actors.size();
//    }
//
//    // 重写的自定义ViewHolder
//    public static class ViewHolder
//            extends RecyclerView.ViewHolder
//    {
//        public TextView mTextView;
//
//        public ImageView mImageView;
//
//        public ViewHolder( View v )
//        {
//            super(v);
//            mTextView = (TextView) v.findViewById(R.id.name);
//            mImageView = (ImageView) v.findViewById(R.id.pic);
//        }
//    }
//}
