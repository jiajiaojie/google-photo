package com.example.jiaojiejia.googlephoto.application;

import android.app.Application;
import android.content.Context;

/**
 * Application
 * Created by jiaojie.jia on 2017/3/24.
 */

public class GooglePhotoApplication extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
