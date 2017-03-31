package com.example.jiaojiejia.googlephoto.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.ScaleGestureDetector;

import com.example.jiaojiejia.googlephoto.adapter.MonthViewAdapter;
import com.example.jiaojiejia.googlephoto.adapter.base.BaseViewAdapter;
import com.example.jiaojiejia.googlephoto.viewholder.base.BasePhotoView;

/**
 * Google相册月视图
 * Created by jiaojie.jia on 2017/3/15.
 */

public class MonthView extends BasePhotoView {

    public static final int CLUMN_COUNT = 4;

    public MonthView(Context context) {
        super(context);
    }

    @Override
    protected ScaleGestureDetector getScaleDetector() {
        return new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener(){
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mSwitchViewListener.onSwitchView(detector.getScaleFactor());
                return true;
            }
        });
    }

    @Override
    protected GridLayoutManager getLayoutManager() {
        return new GridLayoutManager(context, CLUMN_COUNT);
    }

    @Override
    protected BaseViewAdapter getAdapter() {
        return new MonthViewAdapter();
    }
}
