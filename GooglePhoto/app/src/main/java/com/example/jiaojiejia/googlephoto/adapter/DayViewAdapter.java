package com.example.jiaojiejia.googlephoto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.adapter.base.BaseViewAdapter;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.viewholder.item.DayViewItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 日视图Adapter
 * Created by jiaojie.jia on 2017/3/20.
 */

public class DayViewAdapter extends BaseViewAdapter<DayViewItemHolder> {

    private List<String> mMonths;       // 月集合

    @Override
    protected DayViewItemHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.holder_month_item, parent, false);
        view.setOnLongClickListener(longClickListener);
        view.setOnClickListener(clickListener);
        return new DayViewItemHolder(view);
    }

    @Override
    protected void onBindItemViewHolder(DayViewItemHolder holder, int section, int position) {
        PhotoItem photoItem = mSectionPhotos.get(section).get(position);
        holder.setData(photoItem);
    }

    @Override
    public void initOther() {
        mMonths = new ArrayList<>();
        for(String title: mTitles) {
            String month = title.substring(0, 8);
            if(!mMonths.contains(month)) {
                mMonths.add(month);
            }
        }
    }

    /**
     * 由日视图中的 position 得到在月视图中所属 Section
     * @param position
     * @return
     */
    public int getSectionInMonthView(int position) {
        String title = getSectionTitle(position);
        int section = 0;
        for(String month: mMonths) {
            if(title.startsWith(month)) {
                break;
            }
            section++;
        }
        return section;
    }
}
