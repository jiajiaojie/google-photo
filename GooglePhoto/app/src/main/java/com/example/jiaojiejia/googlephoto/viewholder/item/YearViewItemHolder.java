package com.example.jiaojiejia.googlephoto.viewholder.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jiaojiejia.googlephoto.R;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.listener.OnGridViewClickListener;
import com.example.jiaojiejia.googlephoto.utils.Format;

import java.util.List;

/**
 * 年视图Item（包含一个文字和一个GridView）
 * Created by jiaojie.jia on 2017/3/21.
 */

public class YearViewItemHolder extends RecyclerView.ViewHolder {

    private TextView mTextView;
    private GridView mGridView;
    private MyAdapter mAdapter;

    private OnGridViewClickListener mClickListener;

    private View.OnClickListener mChildClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mClickListener.onGridViewClick(getAdapterPosition());
        }
    };

    public void setClickListener(OnGridViewClickListener clickListener) {
        mClickListener = clickListener;
    }

    public YearViewItemHolder(final View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.tv_month);
        mGridView = (GridView) itemView.findViewById(R.id.gv_photos);
        mAdapter = new MyAdapter();
        mGridView.setAdapter(mAdapter);
    }

    public void removeView() {
        if(itemView != null) {
            ((ViewGroup) itemView).removeView(mGridView);
        }
    }

    public void setData(String month, List<PhotoItem> photos) {
        mTextView.setText(month.substring(5));
        if(((ViewGroup) itemView).getChildCount() == 1) {
            ((ViewGroup) itemView).addView(mGridView);
        }
        mAdapter.setPhotos(photos);
    }

    class MyAdapter extends BaseAdapter {

        List<PhotoItem> photos;

        public void setPhotos(List<PhotoItem> photos) {
            this.photos = photos;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return Format.isEmpty(photos) ? 0 : photos.size();
        }

        @Override
        public Object getItem(int position) {
            return photos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            YearViewItemItemHolder itemHolder;
            if(convertView == null) {
                itemHolder = new YearViewItemItemHolder(parent.getContext());
                convertView = itemHolder.getRootView();
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (YearViewItemItemHolder) convertView.getTag();
            }
            itemHolder.setData(photos.get(position));
            itemHolder.getRootView().setOnClickListener(mChildClickListener);
            return itemHolder.getRootView();
        }
    }

}
