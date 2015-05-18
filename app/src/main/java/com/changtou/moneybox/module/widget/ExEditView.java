package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.changtou.R;

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

    private int mImgSrc = -1;

    public ExEditView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mContext = context;

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ExEditView);
        mDefaultHit = mTypedArray.getString(R.styleable.ExEditView_hintText);
        mImgSrc = mTypedArray.getResourceId(R.styleable.ExEditView_imgSrc, -1);
        mInputType = mTypedArray.getInt(R.styleable.ExEditView_editType, InputType.TYPE_CLASS_TEXT);
        mTypedArray.recycle();

        this.setBackgroundResource(R.drawable.stroke_edit_fillpraent);
        this.setOrientation(LinearLayout.HORIZONTAL);

        initImgView();
        initEditView();
    }

    private void initImgView()
    {
        ImageView iv = new ImageView(mContext);
        iv.setImageResource(mImgSrc);
        float paddingleft = getResources().getDimension(R.dimen.edit_img_paddingLeft);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.height = 60;
        layoutParams.width = 60;
        layoutParams.leftMargin = (int)paddingleft;
        layoutParams.gravity =  Gravity.CENTER_VERTICAL;
        iv.setLayoutParams(layoutParams);

        this.addView(iv);
    }

    private void initEditView()
    {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        EditText view = (EditText)inflater.inflate(R.layout.ex_editview, this, false);
        float paddingleft = getResources().getDimension(R.dimen.edit_edit_paddingLeft);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity =  Gravity.CENTER_VERTICAL;
        layoutParams.leftMargin = (int)paddingleft;
        view.setLayoutParams(layoutParams);
        view.setHint(mDefaultHit);

        switch (mInputType)
        {
            case 0:
                view.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 1:
                view.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }

        this.addView(view);
    }
}
