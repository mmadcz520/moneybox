package com.changtou.moneybox.module.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.changtou.R;

/**
 * 自定义loading对话框
 * 
 * @author Jone
 */
public class ExLoadingDialog extends Dialog {

	// private Context mContext = null;
	private ExProgressBar mCpb = null;

	public ExLoadingDialog(Context context) {
		super(context);
		// this.mContext = context;
	}

	public ExLoadingDialog(Context context, int theme) {

		super(context, theme);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Dialog#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.common_dialog_loading);

		mCpb = (ExProgressBar) findViewById(R.id.loading_img);

		this.setCancelable(false);
	}

	/**
	 * 
	 */
	public void show() {
		super.show();
		mCpb.setVisibility(View.VISIBLE);
	}
}
