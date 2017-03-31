package com.example.jiaojiejia.googlephoto.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.example.jiaojiejia.googlephoto.bean.ImageFolder;
import com.example.jiaojiejia.googlephoto.listener.OnEditItemClickListener;
import com.example.jiaojiejia.googlephoto.utils.Format;
import com.example.jiaojiejia.googlephoto.viewholder.item.PhotoFolderItemHolder;

import java.util.List;

/**
 * 文件夹列表Adapter
 * Created by jiaojie.jia on 2017/3/23.
 */

public class PhotoFoldersAdapter extends RecyclerView.Adapter {

    private List<ImageFolder> mFolderList;                      // 文件夹集合

    private OnEditItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnEditItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<ImageFolder> folders) {
        mFolderList = folders;
        notifyDataSetChanged();
    }

    public ImageFolder getItem(int position) {
        if(!Format.isEmpty(mFolderList)) {
            return mFolderList.get(position);
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoFolderItemHolder(parent.getContext(), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoFolderItemHolder itemHolder = (PhotoFolderItemHolder) holder;
        itemHolder.setData(mFolderList.get(position));
    }

    @Override
    public int getItemCount() {
        return Format.isEmpty(mFolderList) ? 0 : mFolderList.size();
    }
}
