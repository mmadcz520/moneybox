package com.changtou.moneybox.module.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.changtou.R;
import com.changtou.moneybox.common.activity.BaseFragment;
import com.changtou.moneybox.module.adapter.ExGridAdapter;

/**
 * Created by Administrator on 2015/3/26.
 */
public class RichesFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int[] mImgRes = {R.drawable.riches_btn_adf_selector, R.mipmap.name02, R.mipmap.name03,
                R.mipmap.name04, R.mipmap.name05, R.mipmap.name06};
        String[] titleList = this.getActivity().getResources().getStringArray(R.array.riches_modules);

        View view = inflater.inflate(R.layout.riches_fragment, null);
        GridView gv = null;
        gv = (GridView) view.findViewById(R.id.brainheroall);
        ExGridAdapter sa = new ExGridAdapter(this.getActivity(), mImgRes, titleList);
        gv.setAdapter(sa);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                printLog("selected sub page position=" + position);
            }
        });

        ImageView imageView = (ImageView)view.findViewById(R.id.riches_signbar);
        Animation translateAnimation = AnimationUtils.loadAnimation(this.getActivity(), R.anim.tip);
        translateAnimation.setDuration(200);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        imageView.setAnimation(translateAnimation); //这里iv就是我们要执行动画的item，例如一个imageView
        translateAnimation.start();

//        Animation operatingAnim = AnimationUtils.loadAnimation(this.getActivity(), R.anim.tip);
//        LinearInterpolator lin = new LinearInterpolator();
//        operatingAnim.setInterpolator(lin);
//        if (operatingAnim != null) {
//            imageView.startAnimation(operatingAnim);
//        }

        return view;
    }

    @Override
    protected void initLisener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onSuccess(String content, Object object, int reqType) {

    }

    @Override
    public void onFailure(Throwable error, String content, int reqType) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
