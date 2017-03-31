package com.example.jiaojiejia.googlephoto.presenter;

import android.os.AsyncTask;

import com.example.jiaojiejia.googlephoto.activity.GooglePhotoActivity;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.contract.GooglePhotoContract;
import com.example.jiaojiejia.googlephoto.data.GooglePhotoScanner;
import com.example.jiaojiejia.googlephoto.utils.Format;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by jiaojie.jia on 2017/3/15.
 */

public class GooglePhotoPresenter implements GooglePhotoContract.Presenter {

    private GooglePhotoActivity.ViewType mViewType;         // 当前视图类型

    private GooglePhotoContract.View mView;                 // view

    private List<PhotoItem> mSelectedPhotos;                // 选中的照片集合

    private List<Float> mPercents;
    private List<String> mTimelineTags;

    public GooglePhotoPresenter(GooglePhotoContract.View view) {
        mView = view;
        mSelectedPhotos = new ArrayList<>();
    }

    @Override
    public void setViewType(GooglePhotoActivity.ViewType viewType) {
        mViewType = viewType;
    }

    @Override
    public GooglePhotoActivity.ViewType getViewType() {
        return mViewType;
    }

    @Override
    public void loadPhotos() {
        if(!Format.isEmpty(getPhotoData()) && !Format.isEmpty(GooglePhotoScanner.getImageFloders())) {
            mView.fullData(getPhotoData());
            mView.fullFolders(GooglePhotoScanner.getImageFloders());
        } else {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    GooglePhotoScanner.startScan();
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    mView.fullData(getPhotoData());
                    mView.fullFolders(GooglePhotoScanner.getImageFloders());
                }
            }.execute();
        }
    }

    @Override
    public void selectPhoto(PhotoItem photoItem) {
        if(photoItem.isSelected() && !mSelectedPhotos.contains(photoItem)) {
            mSelectedPhotos.add(photoItem);
        } else if(!photoItem.isSelected() && mSelectedPhotos.contains(photoItem)){
            mSelectedPhotos.remove(photoItem);
        }
    }

    @Override
    public boolean isSelectedEmpty() {
        return Format.isEmpty(mSelectedPhotos);
    }

    @Override
    public void cancleAllSelected() {
        if(!Format.isEmpty(mSelectedPhotos)) {
            for(PhotoItem photoItem: mSelectedPhotos) {
                photoItem.setSelected(false);
            }
            mSelectedPhotos.clear();
        }
    }

    @Override
    public void setTimelineData(List<Float> percents, List<String> timelineTags) {
        this.mPercents = percents;
        this.mTimelineTags = timelineTags;
    }

    @Override
    public List<Float> getPercents() {
        return mPercents;
    }

    @Override
    public List<String> getTimelineTags() {
        return mTimelineTags;
    }

    @Override
    public void clear() {
        mSelectedPhotos.clear();
        GooglePhotoScanner.clear();
    }

    /** 获取与视图对应的数据 */
    private LinkedHashMap<String, List<PhotoItem>> getPhotoData() {
        return GooglePhotoScanner.getPhotoSections(mViewType);
    }

}
