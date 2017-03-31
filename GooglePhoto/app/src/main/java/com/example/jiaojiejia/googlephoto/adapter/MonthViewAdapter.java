package com.example.jiaojiejia.googlephoto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.adapter.base.BaseViewAdapter;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.viewholder.item.MonthViewItemHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * 月视图Adapter
 * Created by jiaojie.jia on 2017/3/16.
 */

public class MonthViewAdapter extends BaseViewAdapter<MonthViewItemHolder> {

    @Override
    protected MonthViewItemHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.holder_month_item, parent, false);
        view.setOnLongClickListener(longClickListener);
        view.setOnClickListener(clickListener);
        return new MonthViewItemHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(MonthViewItemHolder holder, int section, int position) {
        PhotoItem photoItem = mSectionPhotos.get(section).get(position);
        holder.setData(photoItem);
    }

    @Override
    public void initOther() {
        int total = items.size() + mTitles.size();
        float cursor = 0;
        List<Float> percents = new ArrayList<>(mTitles.size());
        for(List<PhotoItem> photoSection: mSectionPhotos) {
            percents.add(cursor / total);
            cursor += photoSection.size() + 1;
        }
        mPresenter.setTimelineData(percents, mTitles);
    }

}
