
package com.changtou.moneybox.common.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.changtou.R;
import com.changtou.moneybox.module.widget.BootViewPagerAdapter;

public abstract class BaseBootActivity extends BaseFragmentActivity {

    Context context;

    public ViewPager bootViewPager;

    public ArrayList<View> mListViews = new ArrayList<View>();

    // 底部小点图片
    public ImageView[] dots;

    // 记录当前选中位置
    public int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boot_page);
        context = this;
        bootViewPager = (ViewPager) findViewById(R.id.boot_viewpager);

        setPagesLayout();

        setPagesBackgruand();

        addPagesToViewPager();

        BootViewPagerAdapter bootViewPagerAdapter = new BootViewPagerAdapter(mListViews);
        bootViewPager.setAdapter(bootViewPagerAdapter);
        bootViewPager.setOffscreenPageLimit(1);

        initDots();

        bootViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                setCurrentDot(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                // setCurrentDot(arg0);
            }
        });
    }

    /*
 * 
 */
    public abstract void addPagesToViewPager();

    public abstract void setPagesLayout();

    public abstract void setPagesBackgruand();

    // public abstract void setBootDotsSpace(float Space);

    public abstract int getBootDotsSpace();

    // public abstract void setBootDotsMarginBottom(float Margin);

    public abstract int getBootDotsMarginBottom();

    public abstract int setBootDotsResourceDrawable();

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[mListViews.size()];

        for (int i = 0; i < mListViews.size(); i++) {
            ImageView dot = new ImageView(this);

            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            lp1.leftMargin = getBootDotsSpace();
            lp1.bottomMargin = getBootDotsMarginBottom();

            dot.setLayoutParams(lp1);
            dot.setImageResource(setBootDotsResourceDrawable());

            dot.setSelected(false);

            ll.addView(dot);
        }

        // 循环取得小点图片
        for (int i = 0; i < mListViews.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);

            dots[i].setTag(i);

            dots[i].setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    int pagerindex = (Integer) v.getTag();
                    Toast.makeText(context, "选中" + pagerindex + "页", Toast.LENGTH_SHORT).show();

                    bootViewPager.setCurrentItem(pagerindex);
                }
            });
            dots[i].setSelected(false);// 都设为灰�?
        }

        currentIndex = 0;
        dots[currentIndex].setSelected(true);// 设置为白色，即�?中状�?
    }

    private void setCurrentDot(int position) {
        if (position < 0 || position > mListViews.size() - 1 || currentIndex == position) {
            return;
        }

        dots[position].setSelected(true);
        dots[currentIndex].setSelected(false);

        currentIndex = position;
    }

}
