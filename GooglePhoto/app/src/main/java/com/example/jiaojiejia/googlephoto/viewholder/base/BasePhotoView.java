package com.example.jiaojiejia.googlephoto.viewholder.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.adapter.base.BaseViewAdapter;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.contract.GooglePhotoContract;
import com.example.jiaojiejia.googlephoto.fastscroll.FastScroller;
import com.example.jiaojiejia.googlephoto.listener.DragSelectTouchListener;
import com.example.jiaojiejia.googlephoto.listener.OnSwitchViewListener;
import com.example.jiaojiejia.googlephoto.sectionedrecyclerviewadapter.SectionedSpanSizeLookup;
import com.example.jiaojiejia.googlephoto.utils.Format;
import com.example.jiaojiejia.googlephoto.utils.UIUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 相册视图基类
 * Created by jiaojie.jia on 2017/3/20.
 */

public abstract class BasePhotoView extends BaseHolder<LinkedHashMap<String, List<PhotoItem>>> {

    protected RecyclerView mRecyclerView;           // 列表
    protected BaseViewAdapter mAdapter;
    private FastScroller fastScroller;              // 右侧快速导航

    private DragSelectTouchListener touchListener;              // 滑动选择 Listener
    protected OnSwitchViewListener mSwitchViewListener;         // 缩放手势 Listener

    private GooglePhotoContract.Presenter mPresenter;

    public BasePhotoView(Context context) {
        super(context);
    }

    public void setSwitchViewListener(OnSwitchViewListener switchViewListener) {
        mSwitchViewListener = switchViewListener;
    }

    public void setPresenter(GooglePhotoContract.Presenter presenter) {
        mPresenter = presenter;
        mAdapter.setPresenter(presenter);
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.holder_month_view, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_month);
        fastScroller = (FastScroller) view.findViewById(R.id.fastscroll);
        mAdapter = getAdapter();
        mRecyclerView.setAdapter(mAdapter);
        fastScroller.setRecyclerView(mRecyclerView);
        GridLayoutManager layoutManager = getLayoutManager();
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(mAdapter, layoutManager);
        layoutManager.setSpanSizeLookup(lookup);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter.setLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = mRecyclerView.getChildAdapterPosition(v);
                mAdapter.setSelected(position);
                touchListener.setStartSelectPosition(position);
                return false;
            }
        });

        mAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mRecyclerView.getChildAdapterPosition(v);
                mAdapter.setSelected(position);
            }
        });

        // 添加滑动监听
        touchListener = new DragSelectTouchListener();
        mRecyclerView.addOnItemTouchListener(touchListener);

        // 取消默认选中动画（闪烁）
        RecyclerView.ItemAnimator animator = mRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        touchListener.setSelectListener(new DragSelectTouchListener.onSelectListener() {
            @Override
            public void onSelectChange(int start, int end, boolean isSelected) {
                mAdapter.selectRangeChange(start, end, isSelected);
            }
        });

        touchListener.setScaleGestureDetector(getScaleDetector());

        return view;
    }

    protected abstract ScaleGestureDetector getScaleDetector();

    protected abstract GridLayoutManager getLayoutManager();

    protected abstract BaseViewAdapter getAdapter();

    /**
     * 滚动到指定位置
     */
    public void scrollToPosition(int position) {
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(position);
        }
    }

    /**
     * 滚动到指定分组
     */
    public void scrollToSection(int section) {
        int position = mAdapter.getHeaderPosition(section);
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(position);
            int scrollY = 0;
            RecyclerView.ViewHolder viewHolder = mRecyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder != null) {        // 显示在屏幕中
                View view = viewHolder.itemView;
                scrollY = view.getTop();
            } else {                        // 没有显示
                View view = mRecyclerView.getChildAt(0);
                if (view != null) {
                    int firstVisable = mRecyclerView.getLayoutManager().getPosition(view);
                    if (firstVisable < position) {       // 目标位置在下方，需要滚动
                        scrollY = UIUtils.getScreenHeight() - UIUtils.dip2px(98) - UIUtils.getStatusBarHeight();
                    }
                }
            }
            final int finalScrollY = scrollY;
            if (finalScrollY != 0) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.scrollBy(0, finalScrollY);
                    }
                }, 100);
            }
        }
    }

    /**
     * 判断当前视图是否填充了数据
     */
    public boolean isEmpty() {
        return !(mAdapter != null && mAdapter.getItemCount() > 0);
    }

    @Override
    public void refreshView() {
        if (Format.isEmpty(data)) return;
        mAdapter.setAllPhotos(data);
    }

    /**
     * 清除数据选中状态
     */
    public void clearSelectedStatus() {
        mAdapter.notifyDataSetChanged();
    }

}
