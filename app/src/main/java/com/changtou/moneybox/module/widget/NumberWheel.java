package com.changtou.moneybox.module.widget;

import java.text.NumberFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import com.changtou.moneybox.R;

/**
 * 1280*720 密度2；暂不支持适配
 * 运行SDK android4.4.2
 * @author Administrator
 *
 */
public class NumberWheel extends SurfaceView implements Callback,Runnable {
	private int oldms=500;//不更新UI时线程休眠时间
	private int ms= 9;//更新UI时线程休眠时间(帧)
	private int mvingDistance= 50;//更新UI时数字每次滚动距离
	private long millisecond=500;//刚创建时延迟运行毫秒数

	private boolean isRunnable=false;
	private Drawable down=null;
	private Drawable middle=null;
	private Drawable up=null;
	private Drawable background=null;
	private SurfaceHolder holder = null;

	private int width=0;//控件宽
	private int height=0;//控件高

	//回调函数
	private CallBackListener cbl;

	private boolean isSurfaveCreated = false;

	private NumberBean nBean=null;

	static NumberWheel instance;

	public static NumberWheel getInstance(Context context) {
		if (instance == null) {
			instance = new NumberWheel(context);
		}
		return instance;
	}

	private NumberWheel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		nBean=new NumberWheel.NumberBean();
		nBean.setStartShowNumString("0000");
		RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(338,130);
		setLayoutParams(rl);
		init();
	}
	//控件初始化
	public void init(){
		background=getResources().getDrawable(nBean.getBackgroundImage());
		ViewGroup.LayoutParams lp=getLayoutParams();
		if(null!=lp){
			width=lp.width>background.getMinimumWidth()?background.getMinimumWidth():lp.width;
			height=lp.height>background.getMinimumHeight()?background.getMinimumHeight():lp.height;
		}else{
			width=background.getMinimumWidth();
			height=background.getMinimumHeight();
		}
		nBean.doLocations(getContext(), width, height);
		millisecond+=System.currentTimeMillis();

		holder = getHolder();
		holder.setFormat(PixelFormat.TRANSPARENT);
		holder.addCallback(this); //设置Surface生命周期回调
	}



	public CallBackListener getCallBackListener() {
		return cbl;
	}
	public void setCallBackListener(CallBackListener cbl) {
		this.cbl = cbl;
	}
	public void restart(float endShowNum){
		ms=17;
		down=null;
		up=null;
		middle=null;
		nBean.setEndShowNum(endShowNum);
		nBean.reset(getContext(), width, height);
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		drawStatic(nBean.getStartShowNumString());
		isRunnable=true;
		if(null!=cbl){
			cbl.start();
		}

		isSurfaveCreated = true;

		new Thread(this).start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isRunnable=false;
	}

	//线程运行
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(isRunnable){
			try {
				if(millisecond<System.currentTimeMillis()&&ms!=oldms){
					if(nBean.endByOrder(nBean.getRunningNum().length)){//是否结束本轮更新
						ms=oldms;
						nBean.setStartShowNum(nBean.getEndShowNum());
						nBean.setStartShowNumString(nBean.getEndShowNumString());
						if(null!=cbl)
							cbl.end();
					}else
						doDraw();
				}
				Thread.sleep(ms);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//
	private void doDraw(){
		Canvas canvas = holder.lockCanvas(null);
		canvas.drawColor(Color.GRAY);
		drawbackground(canvas);
		for(int i=0,j=0;i<nBean.getNotWheelImages().length;i++){
			int temp=nBean.getNotWheelImages()[i];
			if(temp!=0){
				middle=getContext().getResources().getDrawable(temp);
				middle.setBounds(nBean.getLocations()[i][0],nBean.getLocations()[i][1],nBean.getLocations()[i][0]+middle.getMinimumWidth(),nBean.getLocations()[i][1]+middle.getMinimumHeight());
				middle.draw(canvas);
			}else{
				int [] temp2=nBean.getRunningNum()[j];
				Drawable d=getResources().getDrawable(nBean.getNumberImages()[temp2[0]]);
				if(temp2[3]==0){
					j++;
					d.setBounds(nBean.getLocations()[i][0],nBean.getLocations()[i][1],nBean.getLocations()[i][0]+d.getMinimumWidth(),nBean.getLocations()[i][1]+d.getMinimumHeight());
					d.draw(canvas);
					continue;
				}
				if(temp2[2]>height){//坐标重置到顶端
					temp2[2]=temp2[2]-d.getMinimumHeight()*3;
					temp2[0]=temp2[0]+3>9?temp2[0]+3-10:temp2[0]+3;
					d=getResources().getDrawable(nBean.getNumberImages()[temp2[0]]);
				}
				int location=0;//所在位置0上1中2下
				int y=(height-d.getMinimumHeight())/2;
				if(temp2[2]>=(y+d.getMinimumHeight())){
					location=2;
				}else if(temp2[2]>=y){
					location=1;
				}else {
					location=0;
				}
				int x=nBean.getEndShowNumString().charAt(i)-48;
				boolean isContinue=false;
				int z=0;
				switch (location){
					case 0:
						z=temp2[0]-1<0?temp2[0]-1+10:temp2[0]-1;
						if(x==z&&temp2[2]+d.getMinimumHeight()>=y&&nBean.endByOrder(j)){
							isContinue=true;
							d=getResources().getDrawable(nBean.getNumberImages()[z]);
						}
						break;
					case 1:
						z=temp2[0];
						if(x==z&&temp2[2]>=y&&nBean.endByOrder(j))
							isContinue=true;
						break;
					case 2:
						z=temp2[0]+1>9?temp2[0]+1-10:temp2[0]+1;
						if(x==z&&temp2[2]-d.getMinimumHeight()>=y&&nBean.endByOrder(j)){
							isContinue=true;
							d=getResources().getDrawable(nBean.getNumberImages()[z]);
						}
						break;
				}
				if(isContinue){
					j++;
					temp2[0]=z;
					temp2[3]=0;
					d.setBounds(nBean.getLocations()[i][0],nBean.getLocations()[i][1],nBean.getLocations()[i][0]+d.getMinimumWidth(),nBean.getLocations()[i][1]+d.getMinimumHeight());
					d.draw(canvas);
					continue;
				}
				Rect r=new Rect(temp2[1], temp2[2], d.getMinimumWidth()+temp2[1], d.getMinimumHeight()+temp2[2]);
				switch (location){
					case 0:
						up=d;
						up.setBounds(r);
						up.draw(canvas);

						r.top+=d.getMinimumHeight();
						r.bottom+=d.getMinimumHeight();
						middle=getResources().getDrawable(nBean.getNumberImages()[temp2[0]-1<0?temp2[0]-1+10:temp2[0]-1]);
						middle.setBounds(r);
						middle.draw(canvas);

						if((r.top+d.getMinimumHeight())>=height){
							r.top-=d.getMinimumHeight()*2;
							r.bottom-=d.getMinimumHeight()*2;
							down=getResources().getDrawable(nBean.getNumberImages()[temp2[0]+1>9?temp2[0]+1-10:temp2[0]+1]);
						}else{
							r.top+=d.getMinimumHeight();
							r.bottom+=d.getMinimumHeight();
							down=getResources().getDrawable(nBean.getNumberImages()[temp2[0]-2<0?temp2[0]-2+10:temp2[0]-2]);
						}
						down.setBounds(r);
						down.draw(canvas);
						break;
					case 1:
						middle=d;
						middle.setBounds(r);
						middle.draw(canvas);

						r.top-=d.getMinimumHeight();
						r.bottom-=d.getMinimumHeight();
						up=getResources().getDrawable(nBean.getNumberImages()[temp2[0]+1>9?temp2[0]+1-10:temp2[0]+1]);
						up.setBounds(r);
						up.draw(canvas);

						if((r.top+d.getMinimumHeight()*2)>=height){
							r.bottom=r.top;
							r.top-=d.getMinimumHeight();
							down=getResources().getDrawable(nBean.getNumberImages()[temp2[0]+2>9?temp2[0]+2-10:temp2[0]+2]);
						}else{
							r.top+=d.getMinimumHeight()*2;
							r.bottom+=d.getMinimumHeight()*2;
							down=getResources().getDrawable(nBean.getNumberImages()[temp2[0]-1<0?temp2[0]-1+10:temp2[0]-1]);
						}
						down.setBounds(r);
						down.draw(canvas);
						break;
					case 2:
						down=d;
						down.setBounds(r);
						down.draw(canvas);

						r.top-=d.getMinimumHeight();
						r.bottom-=d.getMinimumHeight();
						middle=getResources().getDrawable(nBean.getNumberImages()[temp2[0]+1>9?temp2[0]+1-10:temp2[0]+1]);
						middle.setBounds(r);
						middle.draw(canvas);

						r.top-=d.getMinimumHeight();
						r.bottom-=d.getMinimumHeight();
						up=getResources().getDrawable(nBean.getNumberImages()[temp2[0]+2>9?temp2[0]+2-10:temp2[0]+2]);
						up.setBounds(r);
						up.draw(canvas);
				}
				temp2[2]+=mvingDistance;
				j++;
			}
		}
		holder.unlockCanvasAndPost(canvas);
	}
	//画初使数值前赋值高度
	private void drawStatic(String num){
		Canvas canvas=holder.lockCanvas(null);
		canvas.drawColor(Color.GRAY);
		drawbackground(canvas);
		for(int i=0;i<nBean.getNotWheelImages().length;i++){
			int temp=nBean.getNotWheelImages()[i];
			if(temp!=0){
				middle=getContext().getResources().getDrawable(temp);
			}else{
				temp=nBean.getNumberImages()[Integer.parseInt(num.substring(i, i+1))];
				middle=getContext().getResources().getDrawable(temp);
			}
			middle.setBounds(nBean.getLocations()[i][0],nBean.getLocations()[i][1],nBean.getLocations()[i][0]+middle.getMinimumWidth(),nBean.getLocations()[i][1]+middle.getMinimumHeight());
			middle.draw(canvas);
		}
		holder.unlockCanvasAndPost(canvas);
	}
	//画背景
	private void drawbackground(Canvas canvas){
		background.setBounds(0, 0, background.getMinimumWidth(), background.getMinimumHeight());
		background.draw(canvas);
	}
	//回调
	public interface CallBackListener{
		public void start();
		public void end();
	}

	public static class NumberBean{
		private int [] numberImages=null;//数字图片资源,顺序从0-9
		private int [] notWheelImages=null;//不滚动资源，要给滚动资源留出位置并且值必须为0
		private int [][] locations=null;//每个位置的x,y坐标
		private float startShowNum=0f;//开始时显示数值
		private float endShowNum=0f;//最终显示数值
		private String startShowNumString=null;//初始数字字符串
		private String endShowNumString=null;//结束数字字符串
		private int [][] runningNum;//正在运行的坐标记录
		private int backgroundImage=0;//背景资源

		private int integerDigits=4;//整数位个数
		private int fractionDigits=0;//小数位个数

		public NumberBean(int [] numberImages,int [] notWheelImages,float startShowNum,float endShowNum,int backgroundImage){
			this.numberImages=numberImages;
			this.notWheelImages=notWheelImages;
			this.startShowNum=startShowNum;
			this.endShowNum=endShowNum;
			this.backgroundImage=backgroundImage;

			init();
		}

		public NumberBean(int [] numberImages,int [] notWheelImages,float startShowNum,float endShowNum,int backgroundImage,int integerDigits,int fractionDigits){
			this.numberImages=numberImages;
			this.notWheelImages=notWheelImages;
			this.startShowNum=startShowNum;
			this.endShowNum=endShowNum;
			this.backgroundImage=backgroundImage;
			this.integerDigits=integerDigits;
			this.fractionDigits=fractionDigits;
			init();
		}
		public NumberBean(){
			numberImages=new int[10];
			numberImages[0]=R.drawable.num_0;
			numberImages[1]=R.drawable.num_1;
			numberImages[2]=R.drawable.num_2;
			numberImages[3]=R.drawable.num_3;
			numberImages[4]=R.drawable.num_4;
			numberImages[5]=R.drawable.num_5;
			numberImages[6]=R.drawable.num_6;
			numberImages[7]=R.drawable.num_7;
			numberImages[8]=R.drawable.num_8;
			numberImages[9]= R.drawable.num_9;

			notWheelImages=new int[4];
			notWheelImages[0]=0;
			notWheelImages[1]=0;
			notWheelImages[2]=0;
			notWheelImages[3]=0;

			startShowNum=0f;
			endShowNum=151.51f;
			backgroundImage=R.drawable.background_num_wheel;

			init();
		}
		//初使化基本信息
		public void init(){
			NumberFormat nf=NumberFormat.getNumberInstance();
			nf.setMaximumIntegerDigits(integerDigits);
			nf.setMaximumFractionDigits(fractionDigits);
			nf.setMinimumIntegerDigits(integerDigits);
			nf.setMinimumFractionDigits(fractionDigits);
			startShowNumString="0000";
			endShowNumString="1988";

			runningNum=new int[integerDigits+fractionDigits][];
			char [] ssns=startShowNumString.toCharArray();
			char [] esns=endShowNumString.toCharArray();
			for (int i = 0,j=0; i < ssns.length; i++) {
				char c=ssns[i];
				if(c>47&&c<58){
					runningNum[j]=new int[4];
					runningNum[j][0]=c-48;
					int ec=esns[i]-48;
					if(runningNum[j][0]==ec&&endByOrder(j))
						runningNum[j][3]=0;
					else
						runningNum[j][3]=1;
					j++;
				}
			}
		}
		public void reset(Context context,int width,int height){
			init();
			doLocations(context, width, height);
		}
		public int[] getNumberImages() {
			return numberImages;
		}
		public void setNumberImages(int[] numberImages) {
			this.numberImages = numberImages;
		}
		public int[] getNotWheelImages() {
			return notWheelImages;
		}
		public void setNotWheelImages(int[] notWheelImages) {
			this.notWheelImages = notWheelImages;
		}
		public int[][] getLocations() {
			return locations;
		}
		public void setLocations(int[][] locations) {
			this.locations = locations;
		}
		public int getBackgroundImage() {
			return backgroundImage;
		}
		public void setBackgroundImage(int backgroundImage) {
			this.backgroundImage = backgroundImage;
		}

		public float getStartShowNum() {
			return startShowNum;
		}

		public void setStartShowNum(float startShowNum) {
			this.startShowNum = startShowNum;
		}

		public float getEndShowNum() {
			return endShowNum;
		}

		public void setEndShowNum(float endShowNum) {
			this.endShowNum = endShowNum;
		}

		public String getStartShowNumString() {
			return startShowNumString;
		}
		public void setStartShowNumString(String startShowNumString) {
			this.startShowNumString = startShowNumString;
		}
		public String getEndShowNumString() {
			return endShowNumString;
		}
		public void setEndShowNumString(String endShowNumString) {
			this.endShowNumString = endShowNumString;
		}

		public int[][] getRunningNum() {
			return runningNum;
		}

		public void setRunningNum(int[][] runningNum) {
			this.runningNum = runningNum;
		}
		//按顺序结束滚动
		private boolean endByOrder(int j){
			for (int i = 0; i < j; i++) {
				int [] temp=getRunningNum()[i];
				if(temp[3]!=0)
					return false;
			}
			return true;
		}
		//赋值位置坐标
		public void doLocations(Context context,int width,int height){
			locations=new int[notWheelImages.length][];
			for(int i=0,j=0;i<notWheelImages.length;i++){
				locations[i]=new int[2];
				locations[i][0]=width/notWheelImages.length*i+2;
				Drawable d=null;
				if(notWheelImages[i]==0){
					d=context.getResources().getDrawable(getNumberImages()[notWheelImages[i]]);
					runningNum[j][1]=locations[i][0];
					runningNum[j][2]=locations[i][1];
					j++;
				}else{
					d=context.getResources().getDrawable(notWheelImages[i]);
				}
				locations[i][1]=(height-d.getMinimumHeight())/2;
			}
		}
	}
}
