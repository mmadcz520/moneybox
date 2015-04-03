
package com.changtou.moneybox.module.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.changtou.R;

public class ExProgressBar extends ImageView {

    public ExProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getContext(),
                    R.anim.custom_progressbar);
            // 使用ImageView显示动画
            startAnimation(hyperspaceJumpAnimation);
        } else {
            clearAnimation();
        }
    }
}
