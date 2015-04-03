
package com.changtou.moneybox.module.widget;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseBootActivity;
import com.changtou.moneybox.module.page.MainActivity;

public class BootActivity extends BaseBootActivity {

    public View pager1, pager2, pager3, pager4;

    /*
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseFragmentActivity#onResume()
     */
    protected void onResume() {
        super.onResume();

    }

    /*
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseFragmentActivity#onPause()
     */
    protected void onPause() {
        super.onPause();
    }

    /*'
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseBootActivity#onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView start = (ImageView) pager3.findViewById(R.id.boot_title3_iv);
        start.setOnClickListener(new OnClickListener() {

        	/*
        	 * (non-Javadoc)
        	 * @see android.view.View.OnClickListener#onClick(android.view.View)
        	 */
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BootActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    /*
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseBootActivity#setPagesBackgruand()
     */
    public void setPagesBackgruand() {
    }

    /*
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseBootActivity#setPagesLayout()
     */
    public void setPagesLayout() {
        LayoutInflater inflater = this.getLayoutInflater();
        pager1 = inflater.inflate(R.layout.boot_view1, null);
        pager2 = inflater.inflate(R.layout.boot_view2, null);
        pager3 = inflater.inflate(R.layout.boot_view3, null);
        // pager4 = inflater.inflate(R.layout.boot_view4, null);

    }

    @Override
    public void addPagesToViewPager() {
        if (mListViews == null)
            mListViews = new ArrayList<View>();
        mListViews.clear();
        mListViews.add(pager1);
        mListViews.add(pager2);
        mListViews.add(pager3);
        // mListViews.add(pager4);
    }

    /*
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseBootActivity#getBootDotsSpace()
     */
    public int getBootDotsSpace() {
        return 10;
    }

    /*
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseBootActivity#getBootDotsMarginBottom()
     */
    public int getBootDotsMarginBottom() {
        return 400;
    }

    /*
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseBootActivity#setBootDotsResourceDrawable()
     */
    public int setBootDotsResourceDrawable() {
        return R.drawable.dot;
    }

    /*
     * (non-Javadoc)
     * @see com.razorfish.common.activity.BaseFragmentActivity#getPageName()
     */
    public String getPageName() {
        return "";
    }
}
