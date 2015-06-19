package com.changtou.moneybox.module.page;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.changtou.R;
import com.changtou.moneybox.module.widget.EntryFragment;
import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SinglePage;

import java.util.ArrayList;
import java.util.List;

/**
 * App 引导页
 *
 */
public class BootActivity extends AbsGuideActivity
{
    public List<SinglePage> buildGuideContent()
    {
        // prepare the information for our guide
        List<SinglePage> guideContent = new ArrayList<SinglePage>();

        SinglePage page01 = new SinglePage();
        page01.mBackground = getResources().getDrawable(R.mipmap.boot_page1);
        guideContent.add(page01);

        SinglePage page02 = new SinglePage();
        page02.mBackground = getResources().getDrawable(R.mipmap.boot_page2);
        guideContent.add(page02);

        SinglePage page03 = new SinglePage();
        page03.mBackground = getResources().getDrawable(R.mipmap.boot_page3);
        guideContent.add(page03);

        SinglePage page04 = new SinglePage();
        page04.mCustomFragment = new EntryFragment();
        guideContent.add(page04);

        return guideContent;
    }

    @Override
    public Bitmap dotDefault()
    {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_dot_default);
    }

    @Override
    public Bitmap dotSelected()
    {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_dot_selected);
    }

    public int getPagerId()
    {
        return 1;
    }

    @Override
    public boolean drawDot()
    {
        return true;
    }

    public void entryApp()
    {
        // Time to entry your app! We just finish the activity, replace it with
        // your code.
        finish();
    }
}
