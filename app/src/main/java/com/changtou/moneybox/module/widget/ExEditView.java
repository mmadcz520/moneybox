package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.changtou.R;
import com.changtou.moneybox.common.utils.AppUtil;

/**
 *
 * app 内自定义文本编辑框
 *
 * 图标 文字
 *
 * Created by Administrator on 2015/5/18.
 */
public class ExEditView extends LinearLayout
{
    private Context mContext = null;
    private String mDefaultHit = "请输入用户名";
    private int mInputType = -1;

    private int mMessageColor = 0;
    private int mImgSrc = -1;
    private int mIcon = -1;
    private String mTitle;
    private String mMessage;

    private LinearLayout mEditView = null;

    private Location mLocation;
    public enum Location{
        TOP(0), MIDDLE(1), BOTTOM(2);
        int mV;
        Location(int v){
            mV = v;
        }
    }

    public ViewType mViewType;
    public enum ViewType{
        EDIT(0), TEXT(1), OTHER(2);
        int mV;
        ViewType(int v){
            mV = v;
        }
    }

    public HideIndicator mHideIndicator;
    public enum HideIndicator{
        SHOW(0), HIDE(1),;
        int mV;
        HideIndicator(int v){
            mV = v;
        }
    }

    public ExEditView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ExEditView);
        mDefaultHit = mTypedArray.getString(R.styleable.ExEditView_hintText);
        mImgSrc = mTypedArray.getResourceId(R.styleable.ExEditView_imgSrc, -1);
        mInputType = mTypedArray.getInt(R.styleable.ExEditView_editType, InputType.TYPE_CLASS_TEXT);
        mLocation = Location.values()[mTypedArray.getInt(R.styleable.ExEditView_position, 0)];
        mViewType = ViewType.values()[mTypedArray.getInt(R.styleable.ExEditView_viewType, 0)];
        mHideIndicator = HideIndicator.values()[mTypedArray.getInt(R.styleable.ExEditView_ex_hide_indicator, 0)];

        mIcon = mTypedArray.getResourceId(R.styleable.ExEditView_ex_icon, -1);
        mTitle =  mTypedArray.getString(R.styleable.ExEditView_ex_title);
        mMessage =  mTypedArray.getString(R.styleable.ExEditView_ex_message);
        mMessageColor = mTypedArray.getColor(R.styleable.ExEditView_ex_message_color, getResources().getColor(R.color.ct_red));

        mTypedArray.recycle();

        if(mViewType == ViewType.EDIT)
        {
            initEditView();
        }
        else
        {
            initTextView();
        }
    }

    private void initImgView()
    {
        ImageView iv = new ImageView(mContext);
        iv.setImageResource(mImgSrc);
        float paddingleft = getResources().getDimension(R.dimen.edit_img_paddingLeft);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.height = AppUtil.dip2px(mContext, 20);
        layoutParams.width = AppUtil.dip2px(mContext,20);
        layoutParams.leftMargin = (int)paddingleft;
        layoutParams.gravity =  Gravity.CENTER_VERTICAL;
        iv.setLayoutParams(layoutParams);

        this.addView(iv);
    }

    private void initTextView()
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        RelativeLayout view = (RelativeLayout)inflater.inflate(R.layout.ex_editview_text, this, false);

        ImageView imageView = (ImageView)view.findViewById(R.id.ex_img);
        TextView title = (TextView)view.findViewById(R.id.ex_text);
        TextView message = (TextView)view.findViewById(R.id.ex_msg);
        ImageView imageView1 = (ImageView)view.findViewById(R.id.ex_indicator);

        if(mLocation == Location.BOTTOM)
        {
            view.setBackgroundResource(R.drawable.stroke_edit_fillpraent_bottom);
        }
        else
        {
            view.setBackgroundResource(R.drawable.stroke_edit_fillpraent);
        }

        if(mHideIndicator == HideIndicator.HIDE)
        {
            imageView1.setVisibility(INVISIBLE);
        }

        imageView.setImageResource(mIcon);
        title.setText(mTitle);
        message.setText(mMessage);
        message.setTextColor(mMessageColor);

        this.addView(view);
    }

    private void initEditView()
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mEditView = (LinearLayout)inflater.inflate(R.layout.ex_editview, this, false);

        EditText editText = (EditText)mEditView.findViewById(R.id.edit_text);
        editText.setHint(mDefaultHit);

        ImageView iv = (ImageView)mEditView.findViewById(R.id.edit_img);
        iv.setImageResource(mImgSrc);

        if(mLocation == Location.BOTTOM)
        {
            mEditView.setBackgroundResource(R.drawable.stroke_edit_fillpraent_bottom);
        }
        else
        {
            mEditView.setBackgroundResource(R.drawable.stroke_edit_fillpraent);
        }

        switch (mInputType)
        {
            case 0:
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 1:
                editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case 2:
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
        }

        this.addView(mEditView);
    }

    public String getEditValue()
    {
//        return  mEditText.getText().toString().trim();
        return "sss";
    }
}
