package com.changtou.moneybox.module.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.activity.BaseApplication;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.page.WebActivity;

import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 长投宝 产品合同详情页适配器
 *
 * Created by Administrator on 2015/5/3 0003.
 */
public class ProductContractAdapter extends BaseAdapter
{
    private LayoutInflater mInflater = null;

    private Context mContext = null;

    private String  mProID = null;

    private String mAmount = null;

    private static final Pattern topicPattern = Pattern.compile("《\\w+》");

    private String[] mKeys = {"产品名称：", "投标范围：", "产品期限：", "付息方式：", "预期年化收益：",
    "加入条件：", "加入上限：", "锁定截止日期：", "到期退出方式：", "提前退出方式：", "费用：", "金额服务费：",
    "提前退出费用：", "保障方式："};
    private String[] mValues = {
            "[长投宝]",
            "赎楼宝、PI贷、普通标、回购标",
            "6个月",
            "按月付息，到期还本",
            "14%",
            "1000元起投，以100元的整数倍递增",
            "1,000,000.00元",
            "2015-06-11",
            "通过长投在线债权转让平台转让退出",
            "通过长投在线债权转让平台转让退出",
            "管理费参加《长投宝服务协议》",
            "推广期全免",
            "加入金额 * 7.5‰",
            "本息保障"
    };

    public ProductContractAdapter(Context context)
    {
        mInflater = LayoutInflater.from(context);
//        mValues = new String[mKeys.length];
        this.mContext = context;
    }

    @Override
    public int getCount()
    {
        return mKeys.length;
    }

    @Override
    public String getItem(int position) {
        return mValues[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.product_contract_list_item, null);

            viewHolder.mKey = (TextView) convertView.findViewById(R.id.pro_key);
            viewHolder.mValue = (TextView) convertView.findViewById(R.id.pro_value);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mKey.setText(mKeys[position]);

        SpannableString ss = new SpannableString(mValues[position]);

        viewHolder.mValue.setText(mValues[position]);
        setKeyworkClickable(viewHolder.mValue, ss, topicPattern, new MyClickSpan(new OnTextviewClickListener() {
            @Override
            public void clickTextView()
            {
                try
                {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    String url = "http://www.changtounet.com/contract/app_coCtbService.aspx?";
                    String userId =  BaseApplication.getInstance().getUserId();

                    String uid = URLEncoder.encode(userId, "UTF-8");
                    String amount = URLEncoder.encode(mAmount, "UTF-8");
                    String prd = URLEncoder.encode(mProID, "UTF-8");
                    url = url + "userid=" + userId + "&amount=" +  amount + "&ctbid=" + prd;

                    intent.putExtra("url", url);
                    intent.putExtra("title", "长投宝服务协议");
                    mContext.startActivity(intent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void setStyle(TextPaint ds) {
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(true);
            }
        }));

        return convertView;
    }

    public void setData(String[] data, String amount, String proID)
    {
        if(data.length == mValues.length)
        {
            this.mValues = data;
            this.mAmount = amount;
            this.mProID = proID;
        }

        notifyDataSetChanged();
    }


    private class ViewHolder
    {
        public TextView mKey;
        public TextView mValue;
    }

    /**
     * 设置具体某个关键字可点
     *
     * @param textView
     * @param ss
     * @param pattern
     * @param cs
     */
    private void  setKeyworkClickable(TextView textView, SpannableString ss, Pattern pattern, ClickableSpan cs)
    {
        Matcher matcher = pattern.matcher(ss.toString());
        while(matcher.find())
        {
            String key = matcher.group();
            if(!"".equals(key))
            {
                int start = ss.toString().indexOf(key);
                int end = start + key.length();
                setClickTextView(textView, ss, start, end, cs);
            }
        }
    }

    private void setClickTextView(TextView textView, SpannableString ss, int start, int end, ClickableSpan cs)
    {
        ss.setSpan(cs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }



    public interface OnTextviewClickListener
    {
         void clickTextView();

         void setStyle(TextPaint ds);
    }
}
