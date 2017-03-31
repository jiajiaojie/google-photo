package com.example.jiaojiejia.googlephoto.viewholder.item;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.utils.ImageLoader;
import com.example.jiaojiejia.googlephoto.utils.UIUtils;
import com.example.jiaojiejia.googlephoto.viewholder.base.BaseHolder;

/**
 * 年视图Item的Item
 * Created by jiaojie.jia on 2017/3/21.
 */

public class YearViewItemItemHolder extends BaseHolder<PhotoItem> {

    private ImageView mIvImage;

    public YearViewItemItemHolder(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.imageview, null);
        mIvImage = (ImageView) view.findViewById(R.id.iv_image);
        int width = (UIUtils.getScreenWidth() - UIUtils.dip2px(100)) / 7;
        mIvImage.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        return view;
    }

    @Override
    public void refreshView() {
        if(data == null) return;
        String path = TextUtils.isEmpty(data.getThumbnail()) ? data.getPath() : data.getThumbnail();
        ImageLoader.loadGalleryImage(context, path, mIvImage);
    }
}
