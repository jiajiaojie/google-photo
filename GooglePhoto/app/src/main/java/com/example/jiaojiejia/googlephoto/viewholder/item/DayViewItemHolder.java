package com.example.jiaojiejia.googlephoto.viewholder.item;

import android.view.View;

import com.example.jiaojiejia.googlephoto.viewholder.DayView;
import com.example.jiaojiejia.googlephoto.viewholder.base.BasePhotoItemHolder;

/**
 * 日视图照片Item
 * Created by jiaojie.jia on 2017/3/20.
 */

public class DayViewItemHolder extends BasePhotoItemHolder {


    public DayViewItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getClumnCount() {
        return DayView.CLUMN_COUNT;
    }
}
