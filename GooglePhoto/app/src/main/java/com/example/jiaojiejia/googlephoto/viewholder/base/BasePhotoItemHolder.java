package com.example.jiaojiejia.googlephoto.viewholder.base;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.utils.ImageLoader;
import com.example.jiaojiejia.googlephoto.utils.UIUtils;
import com.nineoldandroids.view.ViewPropertyAnimator;


/**
 * 照片item基类
 * Created by jiaojie.jia on 2017/3/20.
 */

public abstract class BasePhotoItemHolder extends RecyclerView.ViewHolder{

    private final ImageView imgItem;        // 照片图片
    private final ImageView imgSelect;      // 右上角选中标识图片

    private ViewPropertyAnimator mAnimator;

    public BasePhotoItemHolder(View itemView) {
        super(itemView);
        imgItem = (ImageView) itemView.findViewById(R.id.iv_photo);
        imgSelect = (ImageView) itemView.findViewById(R.id.iv_select);
        int width = UIUtils.getScreenWidth() / getClumnCount();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, width);
        itemView.setLayoutParams(lp);
    }

    public void setData(PhotoItem photoItem) {
        imgSelect.setSelected(photoItem.isSelected());
        startAnim(photoItem);
        String path = TextUtils.isEmpty(photoItem.getThumbnail()) ? photoItem.getPath() : photoItem.getThumbnail();
        ImageLoader.loadGalleryImage(imgItem.getContext(), path, imgItem);
    }

    public abstract int getClumnCount();

    /**
     * 选中动画
     * @param photoItem
     */
    private void startAnim(PhotoItem photoItem) {
        if(mAnimator == null) {
            mAnimator = ViewPropertyAnimator.animate(imgItem);
        }
        if(photoItem.isSelected()) {
            mAnimator.scaleX(0.8f).scaleY(0.8f).setDuration(200);
        } else {
            mAnimator.scaleX(1.0f).scaleY(1.0f).setDuration(200);
        }
        mAnimator.start();
    }
}
