package com.changtou.moneybox.module.page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.changtou.moneybox.R;
import com.changtou.moneybox.common.utils.ACache;
import com.changtou.moneybox.module.entity.UserInfoEntity;
import com.changtou.moneybox.module.phonebook.RichesPhoneBookActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

/**
 * 推荐好友页面
 *
 * Created by Administrator on 2015/6/10.
 */
public class RichesPromotionActivity extends CTBaseActivity
{

    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

    private UserInfoEntity mUserInfoEntity = null;

    @Override
    protected void initView(Bundle bundle)
    {
        setContentView(R.layout.riches_promotion_layout);

        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
    // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this,appID,appSecret);
        wxHandler.addToSocialSDK();
    //支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this,appID,appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        mUserInfoEntity = (UserInfoEntity)ACache.get(this).getAsObject("userinfo");

        ImageView tuijianrenIV = (ImageView)findViewById(R.id.riches_promo_tuijianren);
        tuijianrenIV.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(RichesPromotionActivity.this, RegisterPromotionActivity.class);
                startActivity(intent);
            }
        });

        ImageView tuijianguizeIV = (ImageView)findViewById(R.id.riches_promo_tuijianguize);
        tuijianguizeIV.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(RichesPromotionActivity.this, WebActivity.class);
                intent.putExtra("url", "https://m.changtounet.com/APP_web/Recommend.htm");
                startActivity(intent);
            }
        });

        final ScrollView sroll = (ScrollView)findViewById(R.id.scrollView_showMessages);
        final int offset = sroll.getMeasuredHeight() - sroll.getHeight();

        Button wytjBtn = (Button)findViewById(R.id.riches_promo_wytj);
        wytjBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                sroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    @Override
    protected void initListener()
    {
        setOnClickListener(R.id.riches_pro_phonenum);
        setOnClickListener(R.id.riches_rewards);
    }

    @Override
    protected int setPageType() {
        return PAGE_TYPE_SUB;
    }

    public void treatClickEvent(int id)
    {
        switch (id) {
            case R.id.riches_pro_phonenum:

                if (mUserInfoEntity.getHasinvestrecords())
                {
                    final Intent intent1 = new Intent(this, RichesPhoneBookActivity.class);
                    startActivity(intent1);
                }
                else
                {
                    Toast.makeText(this, "请先进行第一笔投资", Toast.LENGTH_LONG).show();
                }


//                postWexin();

//                postSinaWeibo();

                break;

            case R.id.riches_rewards:
                final Intent intent2 = new Intent(this, RichesRewardsActivity.class);
                startActivity(intent2);
                break;
        }
    }

    private void postSinaWeibo()
    {
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
// 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(this,
                "http://www.baidu.com/img/bdlogo.png"));


        mController.postShare(this, SHARE_MEDIA.SINA,
                new SocializeListeners.SnsPostListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(RichesPromotionActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onComplete(SHARE_MEDIA platform, int eCode,SocializeEntity entity) {
                        if (eCode == 200) {
                            Toast.makeText(RichesPromotionActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                        } else {
                            String eMsg = "";
                            if (eCode == -101){
                                eMsg = "没有授权";
                            }
                            Toast.makeText(RichesPromotionActivity.this, "分享失败[" + eCode + "] " +
                                    eMsg,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void postWexin()
    {
        //设置微信好友分享内容
        WeiXinShareContent weixinContent = new WeiXinShareContent();
        //设置分享文字
        weixinContent.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能，微信");
        //设置title
        weixinContent.setTitle("友盟社会化分享组件-微信");
        //设置分享内容跳转URL
        weixinContent.setTargetUrl("你的URL链接");
        //设置分享图片
//        weixinContent.setShareImage(localImage);
        mController.setShareMedia(weixinContent);

//        mController.openShare(this, false);

        mController.postShare(this, SHARE_MEDIA.WEIXIN,
                new SocializeListeners.SnsPostListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(RichesPromotionActivity.this, "开始分享.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
                        if (eCode == 200) {
                            Toast.makeText(RichesPromotionActivity.this, "分享成功.", Toast.LENGTH_SHORT).show();
                        } else {
                            String eMsg = "";
                            if (eCode == -101) {
                                eMsg = "没有授权";
                            }
                            Toast.makeText(RichesPromotionActivity.this, "分享失败[" + eCode + "] " +
                                    eMsg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
