package com.example.jiaojiejia.googlephoto.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by jiaojie.jia on 16/6/8.
 */
public class ImageLoader {

    public static final String FILE_PROTOCAL = "file://";


    public static void loadGalleryImage(Context context, String url, final ImageView imageView) {
        if (isActivityDestory(context)) return;
        Uri uri = Uri.parse(FILE_PROTOCAL + url);
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }

    /**
     * 清除当前页面内存缓存(需要在主线程中执行)
     */
    public static void clearMemory() {
        Glide.get(UIUtils.getContext()).clearMemory();
    }

    /**
     * 清除硬盘缓存(需要在子线程中执行)
     */
    public static void clearDiskCache() {
        Glide.get(UIUtils.getContext()).clearDiskCache();
    }

    /**
     * 当前 Activity 是否可用
     * @param context
     * @return
     */
    private static boolean isActivityDestory(Context context) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing() ||
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ((Activity) context).isDestroyed())) {
                return true;
            }
        }
        return false;
    }

}
