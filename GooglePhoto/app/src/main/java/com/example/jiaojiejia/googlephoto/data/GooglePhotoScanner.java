package com.example.jiaojiejia.googlephoto.data;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.example.jiaojiejia.googlephoto.activity.GooglePhotoActivity;
import com.example.jiaojiejia.googlephoto.bean.PhotoItem;
import com.example.jiaojiejia.googlephoto.bean.ImageFolder;
import com.example.jiaojiejia.googlephoto.utils.DateUtil;
import com.example.jiaojiejia.googlephoto.utils.UIUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Google相册扫描器
 * Created by jiaojie.jia on 2017/3/15.
 */

public class GooglePhotoScanner {

    private static final int MIN_SIZE = 1024 * 10;

    private static final long MIN_DATE = 1000000000;

    //扫描结果图片文件夹
    private static HashMap<String, ImageFolder> mGruopMap = new HashMap<>();

    private static LinkedHashMap<String, List<PhotoItem>> mSectionsOfMonth = new LinkedHashMap<>();
    private static LinkedHashMap<String, List<PhotoItem>> mSectionsOfDay = new LinkedHashMap<>();

    private static List<ImageFolder> imageFloders = new ArrayList<>();

    public static ImageFolder mDefaultFolder;                  // 默认图片文件夹

    private static final SimpleDateFormat mDataFormatOfMonth = new SimpleDateFormat("yyyy年MM月");
    private static final SimpleDateFormat mDataFormatOfDay = new SimpleDateFormat("yyyy年MM月dd日");


    public static void startScan(){
        readSystemGallery(MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        readSystemGallery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    }

    private static void readSystemGallery(Uri uri){
        //获取ContentResolver
        ContentResolver contentResolver = UIUtils.getContext().getContentResolver();
        //查询字段
        String[] projection = new String[]{MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.LONGITUDE,
                MediaStore.Images.Media.LATITUDE,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Media.DATE_TAKEN};
        // 条件
        String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or "
                + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?";
        // 条件值
        String[] selectionArgs = {"image/jpeg", "image/png", "image/gif", "image/webp"};
        // 排序
        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc";
        // 查询
        Cursor mCursor = MediaStore.Images.Media.query(contentResolver, uri, projection, selection, selectionArgs, sortOrder);

        while (mCursor != null && mCursor.moveToNext()) {
            //图片大小
            int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
            //过滤掉10k以下的图片
            if(size < MIN_SIZE)
                continue;
            //修改日期
            long modified = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
            if(modified < MIN_DATE)
                continue;
            //图片路径
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            if(TextUtils.isEmpty(path))
                continue;
            //图片Id
            int id = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media._ID));
            //图片宽度
            int width = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
            //图片高度
            int height = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
            //拍摄日期
            int takendate = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
            double longitude = mCursor.getDouble(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE));
            double latitude = mCursor.getDouble(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE));
            int orientation = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION));

            String parentName = new File(path).getParent();
            PhotoItem photoItem = new PhotoItem(id, path, width, height, size, latitude, longitude, 0, orientation, takendate, modified);

            // 查询缩略图非常消耗性能
//            photoItem.setThumbnail(getThumbnail(id));

            //根据父路径名将图片放入到mGruopMap中
            if (!mGruopMap.containsKey(parentName)) {
                ImageFolder floder = new ImageFolder();
                floder.setDir(parentName);
                floder.setFirstImagePath(path);
                floder.setCount(floder.getCount() + 1);
                List<PhotoItem> photoList = new ArrayList<>();
                photoList.add(photoItem);
                if(floder.isPhoto()) {
                    mDefaultFolder = floder;
                    sortPhotosByMonth(photoItem);
                    sortPhotosByDay(photoItem);
                }
                floder.setList(photoList);
                mGruopMap.put(parentName, floder);
                imageFloders.add(floder);
                if(mDefaultFolder == null || !mDefaultFolder.isPhoto()) {
                    mDefaultFolder = floder;
                }
            } else {
                ImageFolder floder = mGruopMap.get(parentName);
                floder.setCount(floder.getCount() + 1);
                floder.getList().add(photoItem);
                if(floder.isPhoto()) {
                    sortPhotosByMonth(photoItem);
                    sortPhotosByDay(photoItem);
                }
            }
        }
        if (mCursor != null) {
            mCursor.close();
        }
    }

    /** 获取照片缩略图 */
    private static String getThumbnail(int imageId) {
        String thumbnailPath = null;
        final String[] projection = {MediaStore.Images.Thumbnails.DATA, MediaStore.Images.Thumbnails.IMAGE_ID};
        Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(UIUtils.getContext().getContentResolver(),
                imageId, MediaStore.Images.Thumbnails.MICRO_KIND, projection);
        if(cursor != null && cursor.moveToFirst()) {
            thumbnailPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            cursor.close();
        }
        return thumbnailPath;
    }

    /** 根据当前视图，返回对应数据 */
    public static LinkedHashMap<String, List<PhotoItem>> getPhotoSections(GooglePhotoActivity.ViewType viewType) {
        switch (viewType) {
            case DAY:
                return mSectionsOfDay;
            case YEAR:
            case MONTH:
            default:
                return mSectionsOfMonth;
        }
    }

    public static List<ImageFolder> getImageFloders() {
        return imageFloders;
    }

    /** 把照片按月分类 */
    private static void sortPhotosByMonth(PhotoItem photo) {
        Date date = new Date(photo.getModified() * 1000);
        String millisecond = mDataFormatOfMonth.format(date);
        if(!mSectionsOfMonth.containsKey(millisecond)) {
            List<PhotoItem> section = new ArrayList<>();
            section.add(photo);
            mSectionsOfMonth.put(millisecond, section);
        } else {
            List<PhotoItem> section = mSectionsOfMonth.get(millisecond);
            section.add(photo);
        }
    }

    /** 把照片按日分类 */
    private static void sortPhotosByDay(PhotoItem photo) {
        Date date = new Date(photo.getModified() * 1000);
        String detail = mDataFormatOfDay.format(date);
        String week = DateUtil.getWeek(date);
        String dayKey = detail + week;
        if(!mSectionsOfDay.containsKey(dayKey)) {
            List<PhotoItem> section = new ArrayList<>();
            section.add(photo);
            mSectionsOfDay.put(dayKey, section);
        } else {
            List<PhotoItem> section = mSectionsOfDay.get(dayKey);
            section.add(photo);
        }
    }

    public static void clear() {
        if(mGruopMap != null)
            mGruopMap.clear();
        if(imageFloders != null)
            imageFloders.clear();
        if(mSectionsOfMonth != null)
            mSectionsOfMonth.clear();
        if(mSectionsOfDay != null)
            mSectionsOfDay.clear();
        mDefaultFolder = null;
    }
}
