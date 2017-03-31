package com.example.jiaojiejia.googlephoto.viewholder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.jiaojiejia.googlephoto.adapter.DayViewAdapter;
import com.example.jiaojiejia.googlephoto.adapter.base.BaseViewAdapter;
import com.example.jiaojiejia.googlephoto.viewholder.base.BasePhotoView;

/**
 * Google相册日视图
 * Created by jiaojie.jia on 2017/3/20.
 */

public class DayView extends BasePhotoView {

    public static final int CLUMN_COUNT = 2;

    public DayView(Context context) {
        super(context);
    }

    @Override
    protected ScaleGestureDetector getScaleDetector() {
        return new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener(){
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                if(detector.getScaleFactor() < 1) {
                    View child = mRecyclerView.getChildAt(0);
                    int position = mRecyclerView.getLayoutManager().getPosition(child);
                    int section = ((DayViewAdapter) mAdapter).getSectionInMonthView(position);
                    mSwitchViewListener.onSwitchViewBySection(section);
                }
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
        return new DayViewAdapter();
    }
}
