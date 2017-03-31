package com.example.jiaojiejia.googlephoto.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 列表中嵌套的GridView
 * Created by jiaojie.jia on 2017/3/24.
 */

public class ScrollViewGridView extends GridView {

    public ScrollViewGridView(Context context) {
        super(context);
    }
    public ScrollViewGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    public ScrollViewGridView(Context context, AttributeSet attrs,
                              int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
