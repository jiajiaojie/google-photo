package com.example.jiaojiejia.googlephoto.activity;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.adapter.PhotoFoldersAdapter;
import com.example.jiaojiejia.googlephoto.anim.SwitchViewAnimHelper;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.bean.ImageFolder;
import com.example.jiaojiejia.googlephoto.contract.GooglePhotoContract;
import com.example.jiaojiejia.googlephoto.listener.OnEditItemClickListener;
import com.example.jiaojiejia.googlephoto.listener.OnSwitchViewListener;
import com.example.jiaojiejia.googlephoto.presenter.GooglePhotoPresenter;
import com.example.jiaojiejia.googlephoto.viewholder.DayView;
import com.example.jiaojiejia.googlephoto.viewholder.MonthView;
import com.example.jiaojiejia.googlephoto.viewholder.OtherView;
import com.example.jiaojiejia.googlephoto.viewholder.YearView;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 主 Activity
 */
public class GooglePhotoActivity extends AppCompatActivity implements GooglePhotoContract.View {

    /**
     * 视图类型
     */
    public enum ViewType{
        YEAR, MONTH, DAY, OTHER
    }

    private ViewGroup mContainer;       // 视图容器

    private YearView mYearView;         // 年视图
    private MonthView mMonthView;       // 月视图
    private DayView mDayView;           // 日视图
    private OtherView mOtherView;  // 其他文件夹视图

    private MenuItem mDeleteMenuItem;

    private PhotoFoldersAdapter mFoldersAdapter;
    private BottomSheetBehavior mBottomSheetBehavior;

    private GooglePhotoContract.Presenter mPresenter;

    /** 缩放操作回调 */
    private OnSwitchViewListener mSwitchViewListener = new OnSwitchViewListener() {
        @Override
        public void onSwitchView(float scaleFactor) {
            switch (mPresenter.getViewType()) {
                case YEAR:
                    switchView(ViewType.MONTH, 0);
                    break;
                case MONTH:
                    if(scaleFactor > 1) {
                        switchView(ViewType.DAY);
                    } else if(scaleFactor < 1) {
                        switchView(ViewType.YEAR);
                    }
                    break;
            }
        }

        @Override
        public void onSwitchViewBySection(int section) {
            switchView(ViewType.MONTH, section);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_photo);

        mPresenter = new GooglePhotoPresenter(this);

        setupToolbar();

        initDateViews();

        initPhotoFolders();

        switchView(ViewType.MONTH);      // 默认显示月视图
    }

    /**
     * 初始化Toolbar
     */
    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * 初始化各个视图
     */
    private void initDateViews() {
        mContainer = (ViewGroup) findViewById(R.id.fl_container);
        mYearView = new YearView(this);
        mYearView.setSwitchViewListener(mSwitchViewListener);
        mMonthView = new MonthView(this);
        mMonthView.setSwitchViewListener(mSwitchViewListener);
        mMonthView.setPresenter(mPresenter);
        mDayView = new DayView(this);
        mDayView.setPresenter(mPresenter);
        mDayView.setSwitchViewListener(mSwitchViewListener);
        mOtherView = new OtherView(this);
        mOtherView.setPresenter(mPresenter);
    }

    /**
     * 初始化文件夹列表
     */
    private void initPhotoFolders() {
        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        RecyclerView rvPhotoFolders = (RecyclerView) bottomSheet.findViewById(R.id.rv_filedir);
        rvPhotoFolders.setLayoutManager(new LinearLayoutManager(this));
        mFoldersAdapter = new PhotoFoldersAdapter();
        rvPhotoFolders.setAdapter(mFoldersAdapter);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mFoldersAdapter.setOnItemClickListener(new OnEditItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                ImageFolder folder = mFoldersAdapter.getItem(position);
                mOtherView.setData(folder);
                if (folder.isPhoto()) {
                    switchView(ViewType.MONTH);
                } else {
                    mContainer.removeAllViews();
                    mPresenter.setViewType(ViewType.OTHER);
                    mContainer.addView(mOtherView.getRootView());
                }
            }
        });
    }

    @Override
    public void fullData(LinkedHashMap<String, List<PhotoItem>> sections) {
        switch (mPresenter.getViewType()) {
            case YEAR:
                mYearView.setData(sections);
                break;
            case MONTH:
                mMonthView.setData(sections);
                break;
            case DAY:
                mDayView.setData(sections);
                break;
        }
    }

    @Override
    public void fullFolders(List<ImageFolder> folders) {
        mFoldersAdapter.setData(folders);
    }

    @Override
    public void setDeleteButtonVisble(boolean visble) {
        mDeleteMenuItem.setVisible(visble);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clear();
    }

    @Override
    public void onBackPressed() {
        if(mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;
        }
        if(!mPresenter.isSelectedEmpty()) {
            mPresenter.cancleAllSelected();
            mMonthView.clearSelectedStatus();
            mDayView.clearSelectedStatus();
            mOtherView.clearSelectedStatus();
            return;
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_view_menu, menu);
        mDeleteMenuItem = menu.findItem(R.id.delete);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                mPresenter.deleteSelectedPhotos();
                break;
            case R.id.year_view:
                switchView(ViewType.YEAR);
                break;
            case R.id.month_view:
                switchView(ViewType.MONTH);
                break;
            case R.id.day_view:
                switchView(ViewType.DAY);
                break;
            case R.id.other_view:
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                break;
        }
        return true;
    }

    /**
     * 切换视图
     * @param viewType  视图类型
     */
    public void switchView(GooglePhotoActivity.ViewType viewType) {
        switchView(viewType, 0);
    }

    public void switchView(GooglePhotoActivity.ViewType viewType, int section) {
        if((mContainer.getChildCount() != 0 && mPresenter.getViewType() == viewType)
                || SwitchViewAnimHelper.getInstance().isAnimRunning()) {
            return;
        }
        boolean load = true;
        mContainer.removeAllViews();
        switch (viewType) {
            case YEAR:
                mContainer.addView(mYearView.getRootView());
                SwitchViewAnimHelper.getInstance().toSmallView(mYearView.getRootView());
                load = mYearView.isEmpty();
                break;
            case MONTH:
                mContainer.addView(mMonthView.getRootView());
                mMonthView.scrollToSection(section);
                if(mPresenter.getViewType() == ViewType.DAY) {
                    SwitchViewAnimHelper.getInstance().toSmallView(mMonthView.getRootView());
                } else if(mPresenter.getViewType() == ViewType.YEAR) {
                    SwitchViewAnimHelper.getInstance().toLargeView(mMonthView.getRootView());
                }
                load = mMonthView.isEmpty();
                break;
            case DAY:
                mContainer.addView(mDayView.getRootView());
                SwitchViewAnimHelper.getInstance().toLargeView(mDayView.getRootView());
                load = mDayView.isEmpty();
                break;
        }
        mPresenter.setViewType(viewType);
        if(load) {
            mPresenter.loadPhotos();
        }
    }
}
