package com.changtou.moneybox.module.page;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.entity.ProductEntity;
import com.changtou.moneybox.module.http.HttpRequst;
import com.changtou.moneybox.module.widget.CountView;
import com.changtou.moneybox.module.widget.ExImageSwitcher;
import com.changtou.moneybox.module.widget.ExLoadingDialog;
import com.changtou.moneybox.module.widget.LineView;
import com.changtou.moneybox.module.widget.ProductListAdapter;
import com.changtou.moneybox.module.widget.RoundProgressBar;

/**
 * 描述： 主页面
 *
 * @author zhoulongfei
 * @since 2015-3-20
 */
public class HomeFragment extends BaseFragment
{

    private Context mContext = null;
    private ProductListAdapter mAdapter = null;

//    private ExLoadingDialog mExLoading = null;

    private static final int MSG_DATA_CHANGE = 0x11;
    private LineView mLineView;
    private Handler mHandler;
    private int mX = 0;

    private RoundProgressBar mRPB = null;

    int  i = 0;

    /**
     * 初始化信息列表
     */
    public void onInitList()
    {
        initParam();
//        mParams.put("action", "app_article_list");
//        mParams.put("forum_id", "57,287,59,61");// 1表示资讯 2表示攻略
//        mParams.put("page", 1 + "");
//        mParams.put("per_page", 10 + "");// 1表示资讯 2表示攻略
        sendRequest(HttpRequst.REQ_TYPE_PRODUCT_HOME,
                HttpRequst.getInstance().getUrl(HttpRequst.REQ_TYPE_PRODUCT_HOME),
                mParams,
                mAct.getAsyncClient(), false);
    }

    /**
     * @param inflater           1
     * @param container          2
     * @param savedInstanceState 3
     * @return view
     */
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mView = inflater.inflate(R.layout.fragment_home, null);
        ExImageSwitcher exImage = (ExImageSwitcher) mView.findViewById(R.id.hp_ad);
        exImage.setImage(new int[]{R.mipmap.home_default_banner});

        //loading
//        mExLoading = new ExLoadingDialog(getActivity(), R.style.dialog_loading);
//        mExLoading.show();
        CountView cv1 = (CountView)mView.findViewById(R.id.homepage_countview1);
        cv1.showNumberWithAnimation(1789110);

        CountView cv2 = (CountView)mView.findViewById(R.id.homepage_countview2);
        cv2.showNumberWithAnimation(21811210);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
//                switch (msg.what) {
//                    case MSG_DATA_CHANGE:
//                        mLineView.setLinePoint(msg.arg1, msg.arg2);
//                        playHeartbeatAnimation();
                        mRPB.setProgress(90);
                        i++;
//                        break;
//
//                    default:
//                        break;
//                }
                super.handleMessage(msg);
            }
        };

        new Thread(){
            public void run() {
                for (int index=0; index<100; index++)
                {
                    Message message = new Message();
                    message.what = MSG_DATA_CHANGE;
                    message.arg1 = mX;
                    message.arg2 = (int)(Math.random()*200);;

                    try {
                        sleep(5000);
//                        mHandler.sendMessage(message);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    mX += 30;
                }
            };
        }.start();

        mRPB = (RoundProgressBar)mView.findViewById(R.id.roundProgressBar2);
        mRPB.setWillNotDraw(false);
        mRPB.setProgress(80);

        return mView;
    }

    /**
     * 初始化控件监听
     */
    protected void initLisener()
    {

    }

    /**
     * @param savedInstanceState 状态信息
     */
    protected void initData(Bundle savedInstanceState)
    {
        mContext = this.getActivity();
        mAdapter = new ProductListAdapter(mContext);
        onInitList();
    }

    /**
     * @param content 返回值
     * @param object  返回的转化对象
     * @param reqType 请求的唯一识别
     */
    public void onSuccess(String content, Object object, int reqType)
    {
//        mExLoading.cancel();
//        if (reqType == HttpRequst.REQ_TYPE_PRODUCT_HOME)
//        {
//            ProductEntity entity = (ProductEntity) object;
//            mAdapter.setData(entity);
//        }
    }

    /**
     * @param error   错误
     * @param content 返回值
     * @param reqType 请求的唯一识别
     */
    public void onFailure(Throwable error, String content, int reqType)
    {

    }

    private void playHeartbeatAnimation() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f));
        animationSet.addAnimation(new AlphaAnimation(1.0f, 0.4f));

        animationSet.setDuration(200);
        animationSet.setInterpolator(new AccelerateInterpolator());
        animationSet.setFillAfter(true);

        animationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(new ScaleAnimation(1.8f, 1.0f, 1.8f,
                        1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f));
                animationSet.addAnimation(new AlphaAnimation(0.4f, 1.0f));

                animationSet.setDuration(600);
                animationSet.setInterpolator(new DecelerateInterpolator());
                animationSet.setFillAfter(false);

                // 实现心跳的View
                mRPB.startAnimation(animationSet);
            }
        });

        // 实现心跳的View
        mRPB.startAnimation(animationSet);
    }
}
