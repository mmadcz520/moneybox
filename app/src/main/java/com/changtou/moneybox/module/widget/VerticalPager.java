package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class VerticalPager extends ViewGroup{

    private Scroller mScroller;
    private Context mContext;

    private int startY = 0;
    private int endY = 0;

    private boolean mIsIntercept = false;

    public VerticalPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        mScroller=new Scroller(context);
//      mScroller=new Scroller(mContext, new Interpolator() {  
//            
//          public float getInterpolation(float input) {
//              return 300;  
//          }  
//      });  

    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int totalHeight=0;
        int count=getChildCount();

        for(int i=0;i<count;i++){
            View childView=getChildAt(i);

            //int measureHeight=childView.getMeasuredHeight();
            //int measureWidth=childView.getMeasuredWidth();

            childView.layout(l, totalHeight, r, totalHeight+b);
            totalHeight+=b;
        }
    }

    private VelocityTracker mVelocityTracker;

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);

        int count=getChildCount();
        for(int i=0;i<count;i++){
            getChildAt(i).measure(width, height);
        }
        setMeasuredDimension(width, height);
    }

    private int mLastMotionY;

    public boolean onTouchEvent(MotionEvent event) {

        if(mVelocityTracker==null){
            mVelocityTracker=VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        int action=event.getAction();

        float y=event.getY();

        switch(action){
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastMotionY=(int) y;
                startY = (int)y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY=(int) (mLastMotionY-y);

                scrollBy(0, deltaY);
                //mScroller.startScroll(0, getScrollY(), 0, deltaY);
//                invalidate();

                mLastMotionY=(int) y;
                break;
            case MotionEvent.ACTION_UP:

                endY = (int)y;

                if(mVelocityTracker!=null){
                    mVelocityTracker.recycle();
                    mVelocityTracker=null;
                }

                if(getScrollY()<0)
                {
                    Log.e("eeeee", "666666666666666666666666666");
                    mScroller.startScroll(0, 0, 0, 0);
                }
                else if(getScrollY()>(getHeight()*(getChildCount()-1))){

                    Log.e("eeeee", "555555555555555555555555555");

                    View lastView=getChildAt(getChildCount()-1);
                    mScroller.startScroll(0,lastView.getTop()+0, 0, -0);
                    mIsIntercept = false;
                }
                else
                {
                    int position=getScrollY()/getHeight();
                    int mod=Math.abs(endY-startY);

                    if((endY-startY)<0)
                    {
                        if(mod>getHeight()/6){

                            Log.e("eeeee", "4444444444444444444444444444444444");

                            View positionView=getChildAt(position+1);
                            mScroller.startScroll(0, positionView.getTop()-mod, 0, mod);
                        }else{

                            Log.e("eeeee", "333333333333333333333333333333");

                            mIsIntercept = false;
                            View positionView=getChildAt(position);
                            mScroller.startScroll(0, positionView.getTop()+50, 0, -50);
                        }
                    }
                    else if((endY-startY)>0)
                    {
                        if(mod>getHeight()/6){

                            Log.e("eeeee", "111111111111111111111111111111");

                            mIsIntercept = false;
                            View positionView=getChildAt(position);
                            mScroller.startScroll(0, positionView.getTop()+mod, 0, -mod);
                        }else{

                            Log.e("eeeee", "2222222222222222222222222222222222");

                            View positionView=getChildAt(position+1);
                            mScroller.startScroll(0, positionView.getTop()-mod, 0, mod);
                        }
                    }
                    else
                    {
                        Log.e("eeeee", "77777777777777777777777777777");
                    }
                }
                invalidate();
                break;
//      case MotionEvent.ACTION_MASK:  
//          if(getScrollY()<0){  
//              mScroller.startScroll(0, 0, 0, 0);  
//          }else if(getScrollY()>(getHeight()*(getChildCount()-1)){  
//          }  
//          invalidate();  
//          break;  
        }

        return true;
    }

    public void computeScroll() {
        super.computeScroll();

        if(mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
        }else{

        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        super.onInterceptTouchEvent(ev);
        return mIsIntercept;
    }

    public void interceptEnable(boolean isIntercept)
    {
        this.mIsIntercept = isIntercept;
    }
}