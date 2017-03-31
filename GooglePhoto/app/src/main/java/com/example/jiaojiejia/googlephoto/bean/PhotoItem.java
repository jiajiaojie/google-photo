package com.example.jiaojiejia.googlephoto.bean;

import java.io.Serializable;

public class PhotoItem implements Serializable {


    private String path;                    // 路径
    private String thumbnail;               // 缩略图
    private int imageId;                    // 图片ID
    private int imageWidth;                 // 图片宽度
    private int imageHeight;                // 图片高度
    private int size;                       // 图片大小
    private int tokenDate;                  // 拍摄时间
    private long modified;                  // 图片修改时间
    private int orientation;                // 照片方向
    private double latitude;                // 纬度
    private double longitude;               // 经度
    private double altitude;                // 海拔

    private boolean isSelected;

    public PhotoItem(int imageId, String path, int imageWidth, int imageHeight, int size,
                     double latitude, double longitude, double altitude, int orientation,
                     int tokenDate, long modified) {
        this.imageHeight = imageHeight;
        this.imageId = imageId;
        this.imageWidth = imageWidth;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.modified = modified;
        this.orientation = orientation;
        this.path = path;
        this.size = size;
        this.tokenDate = tokenDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }

    public int getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(int tokenDate) {
        this.tokenDate = tokenDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        longitude = longitude;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
