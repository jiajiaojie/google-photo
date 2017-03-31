package com.example.jiaojiejia.googlephoto.viewholder.item;

import android.view.View;

import com.example.jiaojiejia.googlephoto.viewholder.MonthView;
import com.example.jiaojiejia.googlephoto.viewholder.base.BasePhotoItemHolder;

/**
 * 月视图照片Item
 * Created by jiaojie.jia on 2017/3/16.
 */

public class MonthViewItemHolder extends BasePhotoItemHolder {

    public MonthViewItemHolder(View itemView) {
        super(itemView);
    }

    @Override
    public int getClumnCount() {
        return MonthView.CLUMN_COUNT;
    }
}
