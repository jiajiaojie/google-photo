package com.example.jiaojiejia.googlephoto.adapter.base;

import android.view.View;

import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.contract.GooglePhotoContract;
import com.example.jiaojiejia.googlephoto.fastscroll.SectionTitleProvider;
import com.example.jiaojiejia.googlephoto.sectionedrecyclerviewadapter.SimpleSectionedAdapter;
import com.example.jiaojiejia.googlephoto.utils.Format;
import com.example.jiaojiejia.googlephoto.viewholder.base.BasePhotoItemHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 月/日 视图的基类Adapter
 * Created by jiaojie.jia on 2017/3/20.
 */

public abstract class BaseViewAdapter<T extends BasePhotoItemHolder> extends SimpleSectionedAdapter<T> implements SectionTitleProvider {

    private LinkedHashMap<String, List<PhotoItem>> mAllPhotos;       // key-日期（月或日), value-该日期下的所有照片

    protected List<String> mTitles;                             // 日期集合
    protected List<List<PhotoItem>> mSectionPhotos;      // 照片集合
    private List<PhotoItem> items;                       // 把上面照片集合转成一维集合，方便取值

    protected View.OnLongClickListener longClickListener;
    protected View.OnClickListener clickListener;

    protected GooglePhotoContract.Presenter mPresenter;

    public void setPresenter(GooglePhotoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public void setAllPhotos(LinkedHashMap<String, List<PhotoItem>> allPhotos) {
        mAllPhotos = allPhotos;
        mTitles = new ArrayList<>(mAllPhotos.size());
        mSectionPhotos = new ArrayList<>(mAllPhotos.size());
        items = new ArrayList<>();
        for(Map.Entry<String, List<PhotoItem>> entry: mAllPhotos.entrySet()) {
            mTitles.add(entry.getKey());
            mSectionPhotos.add(entry.getValue());
        }
        for(List<PhotoItem> photoSection: mSectionPhotos) {
            for(PhotoItem photoItem: photoSection) {
                items.add(photoItem);
            }
        }
        initOther();
        notifyDataSetChanged();
    }

    public void initOther(){}

    @Override
    protected String getSectionHeaderTitle(int section) {
        return mTitles.get(section);
    }

    @Override
    protected int getSectionCount() {
        return Format.isEmpty(mTitles) ? 0 : mTitles.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return Format.isEmpty(mSectionPhotos) ? 0 : mSectionPhotos.get(section).size();
    }

    public void setLongClickListener(View.OnLongClickListener clickListener) {
        this.longClickListener = clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /** 单选 */
    public void setSelected(int position) {
        PhotoItem photoItem = items.get(getDataPositionByView(position));
        boolean status = photoItem.isSelected();
        photoItem.setSelected(!status);
        mPresenter.selectPhoto(photoItem);
        notifyItemChanged(position);
    }

    /** 滑动选择 */
    public void selectRangeChange(int start, int end, boolean selected) {
        if (start < 0 || end >= items.size() + mSectionPhotos.size()) {
            return;
        }
        for (int i = start; i <= end; i++) {
            if(isSectionItem(i)) {
                return;
            }
            PhotoItem model = getItem(getDataPositionByView(i));
            model.setSelected(selected);
            mPresenter.selectPhoto(model);
            notifyItemChanged(i);
        }
    }

    /** 获取数据 */
    public PhotoItem getItem(int i) {
        return items.get(i);
    }

    /** 由列表位置得到数据位置 */
    private int getDataPositionByView(int position) {
        int dataPosition = position - getSection(position) - 1;
        return dataPosition < 0 ? 0 : dataPosition;
    }

    /** 根据列表物理位置返回此位置所属Section */
    private int getSection(int position) {
        int section = 0;
        int sum = 0;
        for(List<PhotoItem> photoSection: mSectionPhotos) {
            sum += photoSection.size() + 1;
            if(position < sum) {
                break;
            }
            section++;
        }
        return section;
    }

    /** 当前位置是否为header */
    private boolean isSectionItem(int position) {
        int sum = 0;
        for(List<PhotoItem> photoSection: mSectionPhotos) {
            sum += photoSection.size() + 1;
            if(position == sum) {
                return true;
            }
        }
        return false;
    }

    /** 获取指定分组header的位置 */
    public int getHeaderPosition(int section) {
        int sum = 0;
        for(int i = 0; i < section; i++) {
            List<PhotoItem> photoSection = mSectionPhotos.get(i);
            sum += photoSection.size() + 1;
        }
        return sum;
    }

    @Override
    public String getSectionTitle(int position) {
        return getSectionHeaderTitle(getSection(position));
    }

}
