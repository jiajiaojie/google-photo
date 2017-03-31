package com.example.jiaojiejia.googlephoto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.listener.OnGridViewClickListener;
import com.example.jiaojiejia.googlephoto.sectionedrecyclerviewadapter.SimpleSectionedAdapter;
import com.example.jiaojiejia.googlephoto.utils.Format;
import com.example.jiaojiejia.googlephoto.viewholder.item.YearViewItemHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 年视图Adapter
 * Created by jiaojie.jia on 2017/3/21.
 */

public class YearViewAdapter extends SimpleSectionedAdapter<YearViewItemHolder> {

    private List<String> mYears;
    private LinkedHashMap<String, List<String>> mMonthsOfYear;
    private LinkedHashMap<String, LinkedHashMap<String, List<PhotoItem>>> mYearPhotos;

    protected OnGridViewClickListener clickListener;

    public void setClickListener(OnGridViewClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setAllPhotos(LinkedHashMap<String, List<PhotoItem>> allPhotos) {
        mYears = new ArrayList<>();
        mMonthsOfYear = new LinkedHashMap<>();
        mYearPhotos = new LinkedHashMap<>();
        for(Map.Entry<String, List<PhotoItem>> entry: allPhotos.entrySet()) {
            String key = entry.getKey();
            List<PhotoItem> value = entry.getValue();
            String year = key.substring(0, 5);
            if(!mYearPhotos.containsKey(year)) {
                List<String> months = new ArrayList<>();
                months.add(key);
                mMonthsOfYear.put(year, months);
                LinkedHashMap<String, List<PhotoItem>> oneYear = new LinkedHashMap<>();
                oneYear.put(key, value);
                mYears.add(year);
                mYearPhotos.put(year, oneYear);
            } else {
                List<String> months = mMonthsOfYear.get(year);
                months.add(key);
                LinkedHashMap<String, List<PhotoItem>> oneYear = mYearPhotos.get(year);
                oneYear.put(key, value);
            }
        }
        notifyDataSetChanged();
    }

    public int getMonthPosition(int position) {
        int section = 0;
        int sum = 0;
        for(Map.Entry<String, List<String>> photoSection: mMonthsOfYear.entrySet()) {
            sum += photoSection.getValue().size() + 1;
            if(position < sum) {
                break;
            }
            section++;
        }
        int monthPosition = position - section - 1;
        monthPosition = monthPosition < 0 ? 0 : monthPosition;
        return monthPosition;
    }

    @Override
    protected String getSectionHeaderTitle(int section) {
        return mYears.get(section);
    }

    @Override
    protected int getSectionCount() {
        return Format.isEmpty(mYears) ? 0 : mYears.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        String key = mYears.get(section);
        return Format.isEmpty(mYearPhotos) ? 0 : mYearPhotos.get(key).size();
    }

    @Override
    protected YearViewItemHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.holder_year_item, parent, false);
        YearViewItemHolder holder = new YearViewItemHolder(view);
        holder.setClickListener(clickListener);
        return holder;
    }

    @Override
    protected void onBindItemViewHolder(YearViewItemHolder holder, int section, int position) {
        String yearKey = mYears.get(section);
        String monthKey = mMonthsOfYear.get(yearKey).get(position);
        List<PhotoItem> photoItem = mYearPhotos.get(yearKey).get(monthKey);
        holder.setData(monthKey, photoItem);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder instanceof YearViewItemHolder) {
            YearViewItemHolder yearViewItemHolder = (YearViewItemHolder) holder;
            yearViewItemHolder.removeView();
        }
    }
}
