package com.example.jiaojiejia.googlephoto.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片文件夹数据类
 * <p/>
 * Created by windsander on 2016/9/1.
 */
public class ImageFolder implements Serializable {


    public static final String CAMERA_PATH_ROOT = "/DCIM/Camera";
    public static final String CAMERA_PATH_ROOT_NAME = "相机照片";
    public static final String WEIXIN_PATH_ROOT = "/WeiXin";
    public static final String WEIXIN_PATH_ROOT_NAME = "微信";
    public static final String SCREENSHOTS_PATH_ROOT = "/Screenshots";
    public static final String SCREENSHOTS_PATH_ROOT_NAME = "截图";


    private String dir;                         // 文件夹位置路径
    private String firstImagePath;              // 第一张图片路径
    private String name;                        // 文件夹的名称
    private int count;                          // 文件夹图片数量
    private List<PhotoItem> list;               // 文件夹图片集合
    private boolean isPhoto;                    // 是否是相册标识

    public String getDir() {
        return dir;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public int getCount() {
        return list.size();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PhotoItem> getList() {
        return list;
    }

    public void setList(List<PhotoItem> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }


    public ImageFolder() {
        list = new ArrayList<>();
    }

    /** 设定文件夹地址，同时截取文件夹名称 */
    public void setDir(String dir) {
        this.dir = dir;
        this.name = initName();
    }

    /** 截取文件夹姓名 */
    private String initName() {
        if (dir != null && !dir.isEmpty()) {
            if (dir.contains(CAMERA_PATH_ROOT)) {
                return CAMERA_PATH_ROOT_NAME;
            } else if (dir.contains(WEIXIN_PATH_ROOT)) {
                return WEIXIN_PATH_ROOT_NAME;
            } else if (dir.contains(SCREENSHOTS_PATH_ROOT)) {
                return SCREENSHOTS_PATH_ROOT_NAME;
            }
        }
        int lastIndexOf = this.dir.lastIndexOf("/");
        return this.dir.substring(lastIndexOf + 1);
    }

    /** 获取当前相册标识 */
    public boolean isPhoto() {
        return name != null && CAMERA_PATH_ROOT_NAME.equals(name);
    }

}
