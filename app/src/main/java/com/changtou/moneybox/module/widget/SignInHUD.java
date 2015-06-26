package com.changtou.moneybox.module.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.changtou.R;


public class SignInHUD extends FrameLayout {

	private static final int UNKNOWN_VIEW = -1;

	private static final int CONTENT_VIEW = 0;

	private static final int ERROR_VIEW = 1;

	private static final int EMPTY_VIEW = 2;

	private static final int LOADING_VIEW = 3;

	public enum ViewState {
		CONTENT,
		LOADING,
		EMPTY,
		ERROR
	}

	private LayoutInflater mInflater;

	private View mContentView;

	private View mLoadingView;

	private View mErrorView;

	private View mEmptyView;

	private ViewState mViewState = ViewState.CONTENT;

	NumberWheel.NumberBean nBean;

	NumberWheel nw;

	public SignInHUD(Context context) {
		this(context, null);
	}

	public SignInHUD(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public SignInHUD(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		mInflater = LayoutInflater.from(getContext());
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateView);

		mContentView = mInflater.inflate(R.layout.riches_sign_dialog, this, false);

		nBean=new NumberWheel.NumberBean();
		nBean.setStartShowNumString("0000");
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(338,130);
		nw = NumberWheel.getInstance(getContext());
		RelativeLayout r = (RelativeLayout)mContentView.findViewById(R.id.number_count);
		r.removeView(nw);
		r.addView(nw);

		addView(mContentView, mContentView.getLayoutParams());

		a.recycle();
	}

	public void changeNum()
	{
		nw.restart(0);
		nBean.setEndShowNumString("2009");
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mContentView == null) throw new IllegalArgumentException("Content view is not defined");
		setView();
	}

	/* All of the addView methods have been overridden so that it can obtain the content view via XML
     It is NOT recommended to add views into MultiStateView via the addView methods, but rather use
     any of the setViewForState methods to set views for their given ViewState accordingly */
	@Override
	public void addView(View child) {
		if (isValidContentView(child)) mContentView = child;
		super.addView(child);
	}

	@Override
	public void addView(View child, int index) {
		if (isValidContentView(child)) mContentView = child;
		super.addView(child, index);
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		if (isValidContentView(child)) mContentView = child;
		super.addView(child, index, params);
	}

	@Override
	public void addView(View child, ViewGroup.LayoutParams params) {
		if (isValidContentView(child)) mContentView = child;
		super.addView(child, params);
	}

	@Override
	public void addView(View child, int width, int height) {
		if (isValidContentView(child)) mContentView = child;
		super.addView(child, width, height);
	}

	@Override
	protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
		if (isValidContentView(child)) mContentView = child;
		return super.addViewInLayout(child, index, params);
	}

	@Override
	protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
		if (isValidContentView(child)) mContentView = child;
		return super.addViewInLayout(child, index, params, preventRequestLayout);
	}

	public View getView(ViewState state) {
		switch (state) {
			case LOADING:
				return mLoadingView;

			case CONTENT:
				return mContentView;

			case EMPTY:
				return mEmptyView;

			case ERROR:
				return mErrorView;

			default:
				return null;
		}
	}


	public ViewState getViewState() {
		return mViewState;
	}


	public void setViewState(ViewState state) {
		if (state != mViewState) {
			mViewState = state;
			setView();
		}
	}

	private void setView() {
		switch (mViewState) {
			case LOADING:
				if (mLoadingView == null) {
					throw new NullPointerException("Loading View");
				}

				mLoadingView.setVisibility(View.VISIBLE);
				if (mContentView != null) mContentView.setVisibility(View.GONE);
				if (mErrorView != null) mErrorView.setVisibility(View.GONE);
				if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
				break;

			case EMPTY:
				if (mEmptyView == null) {
					throw new NullPointerException("Empty View");
				}

				mEmptyView.setVisibility(View.VISIBLE);
				if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
				if (mErrorView != null) mErrorView.setVisibility(View.GONE);
				if (mContentView != null) mContentView.setVisibility(View.GONE);
				break;

			case ERROR:
				if (mErrorView == null) {
					throw new NullPointerException("Error View");
				}

				mErrorView.setVisibility(View.VISIBLE);
				if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
				if (mContentView != null) mContentView.setVisibility(View.GONE);
				if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
				break;

			case CONTENT:
			default:
				if (mContentView == null) {
					// Should never happen, the view should throw an exception if no content view is present upon creation
					throw new NullPointerException("Content View");
				}

				mContentView.setVisibility(View.VISIBLE);
				if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
				if (mErrorView != null) mErrorView.setVisibility(View.GONE);
				if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
				break;
		}
	}

	/**
	 * Checks if the given {@link View} is valid for the Content View
	 *
	 * @param view The {@link View} to check
	 * @return
	 */
	private boolean isValidContentView(View view) {
		if (mContentView != null && mContentView != view) {
			return false;
		}

		return view != mLoadingView && view != mErrorView && view != mEmptyView;
	}
}
