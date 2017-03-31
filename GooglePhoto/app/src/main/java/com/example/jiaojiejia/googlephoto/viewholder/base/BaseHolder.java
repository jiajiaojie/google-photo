package com.example.jiaojiejia.googlephoto.viewholder.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * 模块基类
 */
public abstract class BaseHolder<T> {
    protected final Context context;                    // 上下文
    protected View view;                                // 视图
    protected T data;                                   // 数据


    public BaseHolder(Context context) {
        this.context = context;
        init();
        view = initView();
    }

    /**
     * 初始化视图之前的准备
     */
    public void init() {
    }

    /**
     * 初始化视图
     */
    public abstract View initView();


    /**
     * 设置数据
     */
    public void setData(T data) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) return;
        this.data = data;
        refreshView();
    }

    /**
     * 刷新视图
     */
    public abstract void refreshView();

    /**
     * 获得所属的持有者View
     */
    public View getRootView() {
        return view;
    }
}
