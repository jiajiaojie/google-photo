package com.example.jiaojiejia.googlephoto.contract;


import com.example.jiaojiejia.googlephoto.activity.GooglePhotoActivity;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.bean.ImageFolder;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by jiaojie.jia on 2017/3/15.
 */

public interface GooglePhotoContract {

    interface View {

        /**
         * 填充相册数据
         */
        void fullData(LinkedHashMap<String, List<PhotoItem>> sections);

        /**
         * 填充文件夹数据
         *
         * @param folders
         */
        void fullFolders(List<ImageFolder> folders);

    }

    interface Presenter {

        /**
         * 设置视图类型
         */
        void setViewType(GooglePhotoActivity.ViewType viewType);

        /**
         * 获取视图类型
         */
        GooglePhotoActivity.ViewType getViewType();

        /**
         * 开始加载数据
         */
        void loadPhotos();

        /**
         * 选中照片
         *
         * @param photoItem
         */
        void selectPhoto(PhotoItem photoItem);

        /**
         * 是否选择了照片
         * @return
         */
        boolean isSelectedEmpty();

        /**
         * 清楚所有选中的照片
         */
        void cancleAllSelected();

        /**
         * 清楚数据
         */
        void clear();
    }
}
