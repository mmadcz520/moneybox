package com.changtou.moneybox.module.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.changtou.moneybox.R;
import com.changtou.moneybox.module.entity.UserInfoEntity;


public class VerifyDialog extends Dialog implements View.OnClickListener
{
	private static VerifyDialog instance;
	private View view;
	private Context context;

	private Button mOKButton = null;
	private Button mCancalButton = null;

	private VerifyListener mVerifyListener = null;

	private TextView mUserNameView = null;
	private ExEditView mPassWordView = null;

	public interface VerifyListener
	{
		void onConfirm(String username, String password);

		void onCancal();
	}

	public VerifyDialog(Context context)
	{
		super(context, R.style.dialog_loading);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		this.setCanceledOnTouchOutside(false);
		this.context = context;
		view = getLayoutInflater().inflate(R.layout.verify_dialog_layout, null);

		mOKButton = (Button)view.findViewById(R.id.pd_confirm_button);
		mCancalButton = (Button)view.findViewById(R.id.pd_cancel_button);

		mUserNameView = (TextView)view.findViewById(R.id.verify_usernane);
		mPassWordView = (ExEditView)view.findViewById(R.id.verify_login_password);

		UserInfoEntity userInfoEntity = UserInfoEntity.getInstance();
		String username = userInfoEntity.getMobile();
		mUserNameView.setText(username);

		mOKButton.setOnClickListener(this);
		mCancalButton.setOnClickListener(this);

		this.setContentView(view);
	}


	public void setVerifyListener(VerifyListener verifyListener)
	{
		this.mVerifyListener = verifyListener;
	}

	@Override
	public void show()
	{
		if (!((Activity) context).isFinishing())
		{
			super.show();
		}
		else
		{
			instance = null;
		}
	}

	public void onClick(View v)
	{
		int id = v.getId();

		if(id == R.id.pd_confirm_button)
		{

			String username = mUserNameView.getText().toString().trim();
			String password = mPassWordView.getEditValue();

			if(mVerifyListener != null)
			{
				mVerifyListener.onConfirm(username, password);
			}
		}
		else if(id == R.id.pd_cancel_button)
		{
			if(mVerifyListener != null)
			{
				mVerifyListener.onCancal();
			}
		}
	}
}
