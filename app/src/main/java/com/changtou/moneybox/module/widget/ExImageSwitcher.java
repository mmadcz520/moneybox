package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher.ViewFactory;

import com.changtou.R;
import com.changtou.moneybox.common.utils.AppUtil;
import com.changtou.moneybox.common.utils.AsyncImageLoader;

import java.util.ArrayList;

/**
 * 自定义图片滑动组件
 *
 * @author Jone
 */
public class ExImageSwitcher extends FrameLayout implements OnTouchListener,AsyncImageLoader.ImageCallback,
		ViewFactory {
	/**
	 * ImagaSwitcher 的引用
	 */
	private ImageSwitcher mImageSwitcher;

	/**
	 * 当前选中的图片id序号
	 */
	private int currentPosition;
	/**
	 * 按下点的X坐标
	 */
	private float downX;
	/**
	 * 装载点点的容器
	 */
	private LinearLayout linearLayout;
	/**
	 * 点点数组
	 */
	private ImageView[] tips;

	private Context mContext = null;

	private int bannerCnt = 0;

	private int mSFlag = 1;
	private int mSCnt = 0;

	private ArrayList<Bitmap> mBannerImage = null;

	private ImageView mCurrentImageView = null;

	public ExImageSwitcher(Context context) {
		this(context, null);
		this.mContext = context;
	}

	public ExImageSwitcher(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		LayoutInflater.from(context).inflate(R.layout.widget_ex_imageswitcher,
				this, true);

		mImageSwitcher = (ImageSwitcher) this.findViewById(R.id.imageSwitcher1);
		mImageSwitcher.setOnTouchListener(this);
		mImageSwitcher.setFactory(this);

		mBannerImage = new ArrayList<>();

		SwitchTask st = new SwitchTask();
		st.execute();
	}

	/**
	 * 设置显示的图片
	 */
	public void setImage(int[] res) {
		linearLayout = (LinearLayout) findViewById(R.id.viewGroup);
		tips = new ImageView[bannerCnt];
		for (int i = 0; i < bannerCnt; i++) {
			ImageView mImageView = new ImageView(mContext);
			tips[i] = mImageView;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.rightMargin = AppUtil.dip2px(mContext, 4);
			layoutParams.leftMargin = AppUtil.dip2px(mContext, 4);
			layoutParams.width = AppUtil.dip2px(mContext, 7);
			layoutParams.height = AppUtil.dip2px(mContext, 7);

//			mImageView.setBackgroundResource(R.drawable.page_indicator_unfocused);
			linearLayout.addView(mImageView, layoutParams);
		}

		mImageSwitcher.setImageResource(res[currentPosition]);
		setImageBackground(currentPosition);
	}

	/**
	 * 设置显示的图片
	 */
	public void setImage(String[] url) {

		bannerCnt = url.length;
		mBannerImage.clear();
		currentPosition = 0;

		linearLayout = (LinearLayout) findViewById(R.id.viewGroup);
		tips = new ImageView[url.length];

		for (int i = 0; i < url.length; i++) {
			ImageView mImageView = new ImageView(mContext);
			tips[i] = mImageView;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.rightMargin = AppUtil.dip2px(mContext, 4);
			layoutParams.leftMargin = AppUtil.dip2px(mContext, 4);
			layoutParams.width = AppUtil.dip2px(mContext, 7);
			layoutParams.height = AppUtil.dip2px(mContext, 7);

//			mImageView.setBackgroundResource(R.drawable.page_indicator_unfocused);
			linearLayout.addView(mImageView, layoutParams);

			AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
			asyncImageLoader.loadImageAsyn(url[i], this);
		}

		setImageBackground(currentPosition);
	}

	/**
	 * 设置选中的tip的背景
	 *
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems)
	{
		for (int i = 0; i < tips.length; i++)
		{
			if (i == selectItems)
			{
//				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			}
			else
			{
//				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see android.widget.ViewSwitcher.ViewFactory#makeView()
	 */
	public View makeView()
	{
		final ImageView i = new ImageView(mContext);
		i.setBackgroundColor(0x5e5e5e);
		i.setScaleType(ImageView.ScaleType.CENTER_CROP);
		i.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}

	public boolean onTouch(View v, MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				// 手指按下的X坐标
				downX = event.getX();
				mSCnt = 0;
				break;
			}
			case MotionEvent.ACTION_UP:
			{
				float lastX = event.getX();
				if (lastX > downX)
				{
					mSFlag = 0;
					mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
							mContext, R.anim.switcher_left_in));
					mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
							mContext, R.anim.switcher_right_out));
					if (currentPosition > 0)
					{
						currentPosition--;
					}
					else
					{
						currentPosition = bannerCnt - 1;
					}
				}

				if (lastX <= downX)
				{
					mSFlag = 1;
					mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
							mContext, R.anim.switcher_right_in));
					mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
							mContext, R.anim.switcher_left_out));
					if (currentPosition < bannerCnt - 1)
					{
						currentPosition++;
					}
					else
					{
						currentPosition = 0;
					}
				}
				setImage();
			}
			break;
		}

		return true;
	}

	private void setImage()
	{
		if(mBannerImage.size() == 0 || currentPosition > mBannerImage.size()-1) return;
		mCurrentImageView = (ImageView)mImageSwitcher.getNextView();
		mCurrentImageView.setImageBitmap(mBannerImage.get(currentPosition));
		mCurrentImageView.setScaleType(ImageView.ScaleType.FIT_XY);
		mImageSwitcher.showNext();
	}

	/**
	 * 异步加载网络图片
	 *
	 * @param path
	 * @param bitmap
	 */
	public void loadImage(String path, Bitmap bitmap)
	{
		currentPosition = 0;
		mBannerImage.add(bitmap);
		mCurrentImageView = (ImageView)mImageSwitcher.getNextView();
		mCurrentImageView.setImageBitmap(mBannerImage.get(currentPosition));
		mCurrentImageView.setScaleType(ImageView.ScaleType.FIT_XY);
		mImageSwitcher.showNext();
	}

	private class SwitchTask extends AsyncTask<String, Integer, String> {

		/*
		 * (non-Javadoc)
		 *
		 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
		 */
		protected void onProgressUpdate(Integer... values) {

			if (mSFlag == 0) {
				mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
						mContext, R.anim.switcher_left_in));
				mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
						mContext, R.anim.switcher_right_out));
				if (currentPosition > 0) {
					currentPosition--;
				} else {
					currentPosition = bannerCnt - 1;
				}
			} else if (mSFlag == 1) {
				mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
						mContext, R.anim.switcher_right_in));
				mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
						mContext, R.anim.switcher_left_out));
				if (currentPosition < bannerCnt - 1) {
					currentPosition++;
				} else {
					currentPosition = 0;
				}
			}
			setImage();
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		protected String doInBackground(String... arg0) {

			new Thread(new Runnable() {
				public void run() {
					while (true) {
						try {
							Thread.sleep(1000);
							mSCnt++;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}).start();

			while (true) {
				try {
					Thread.sleep(5000);
					if (mSCnt > 10)
						publishProgress();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		         int w = bitmap.getWidth();
		         int h = bitmap.getHeight();
		         Matrix matrix = new Matrix();
		         float scaleWidth = ((float) width / w);
		         float scaleHeight = ((float) height / h);
		         matrix.postScale(scaleWidth, scaleHeight);
		       Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		         return newbmp;
		     }
}
