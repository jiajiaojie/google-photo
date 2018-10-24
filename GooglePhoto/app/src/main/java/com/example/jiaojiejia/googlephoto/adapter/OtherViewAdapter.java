package com.example.jiaojiejia.googlephoto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.contract.GooglePhotoContract;
import com.example.jiaojiejia.googlephoto.fastscroll.SectionTitleProvider;
import com.example.jiaojiejia.googlephoto.utils.Format;
import com.example.jiaojiejia.googlephoto.viewholder.item.OtherViewItemHolder;

import java.util.List;

/**
 * 除相册文件夹的其他Adapter
 * Created by jiaojie.jia on 2017/3/23.
 */

public class OtherViewAdapter extends RecyclerView.Adapter implements SectionTitleProvider {

    private List<PhotoItem> mPhotoItems;             // 照片集合（除相册文件夹，其他不分组）

    private View.OnLongClickListener longClickListener;
    private View.OnClickListener clickListener;

    private GooglePhotoContract.Presenter mPresenter;

    public void setPresenter(GooglePhotoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setData(List<PhotoItem> items) {
        mPhotoItems = items;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.holder_month_item, parent, false);
        view.setOnLongClickListener(longClickListener);
        view.setOnClickListener(clickListener);
        return new OtherViewItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OtherViewItemHolder itemHolder = (OtherViewItemHolder) holder;
        itemHolder.setData(mPhotoItems.get(position));
    }

    @Override
    public int getItemCount() {
        return Format.isEmpty(mPhotoItems) ? 0 : mPhotoItems.size();
    }

    public void setLongClickListener(View.OnLongClickListener clickListener) {
        this.longClickListener = clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /** 单选 */
    public void setSelected(int position) {
        PhotoItem photoItem = mPhotoItems.get(position);
        boolean status = photoItem.isSelected();
        photoItem.setSelected(!status);
        mPresenter.selectPhoto(photoItem);
        notifyItemChanged(position);
    }

    /** 滑动选择 */
    public void selectRangeChange(int start, int end, boolean selected) {
        if (start < 0 || end >= mPhotoItems.size()) {
            return;
        }
        for (int i = start; i <= end; i++) {
            PhotoItem model = getItem(i);
            model.setSelected(selected);
            mPresenter.selectPhoto(model);
            notifyItemChanged(i);
        }
    }

    /** 获取数据 */
    public PhotoItem getItem(int i) {
        return mPhotoItems.get(i);
    }

    /**
     * 获取照片位置
     */
    public int getPhotoPosition(PhotoItem photoItem) {
        return mPhotoItems.isEmpty() ? -1 : mPhotoItems.indexOf(photoItem);
    }

    /**
     * 删除照片
     */
    public void removePhoto(PhotoItem photoItem) {
        mPhotoItems.remove(photoItem);
    }

    @Override
    public String getSectionTitle(int position) {
        return null;
    }
}
