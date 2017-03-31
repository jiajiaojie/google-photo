package com.example.jiaojiejia.googlephoto.viewholder.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.bean.ImageFolder;
import com.example.jiaojiejia.googlephoto.listener.OnEditItemClickListener;
import com.example.jiaojiejia.googlephoto.utils.ImageLoader;

/**
 * 文件夹Item
 * Created by jiaojie.jia on 2017/3/23.
 */

public class PhotoFolderItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Context mContext;
    private ImageView mIvThumbnails;        // 文件夹缩略图
    private TextView mTvFolderName;         // 文件夹名称
    private TextView mTvPictureNum;         // 文件图片数

    private OnEditItemClickListener mOnEditItemClickListener;

    public PhotoFolderItemHolder(Context context, OnEditItemClickListener itemClickListener) {
        super(View.inflate(context, R.layout.holder_gallery_folder_list_item, null));
        mContext = context;
        mOnEditItemClickListener = itemClickListener;
        mIvThumbnails = (ImageView) itemView.findViewById(R.id.iv_folder_thumbnail);
        mTvFolderName = (TextView) itemView.findViewById(R.id.tv_folder_name);
        mTvPictureNum = (TextView) itemView.findViewById(R.id.tv_picture_num);
        itemView.setOnClickListener(this);
    }

    public void setData(ImageFolder folder) {
        if(folder != null) {
            ImageLoader.loadGalleryImage(mContext, folder.getFirstImagePath(), mIvThumbnails);
            mTvFolderName.setText(folder.getName());
            mTvPictureNum.setText(String.valueOf(folder.getCount()));
        }
    }

    @Override
    public void onClick(View v) {
        if(mOnEditItemClickListener != null) {
            mOnEditItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
