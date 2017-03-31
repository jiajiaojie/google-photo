package com.example.jiaojiejia.googlephoto.utils;

import android.util.SparseArray;

import java.util.List;
import java.util.Map;


public class Format {

    /**
     * 判断是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        if (list == null) {
            return true;
        }
        return list.isEmpty();
    }

    /**
     * 判断是否为空
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        if (map == null) {
            return true;
        }
        return map.isEmpty();
    }

    /**
     * 判断是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(SparseArray array) {
        if (array == null) {
            return true;
        }
        return array.size() <= 0;
    }

    /**
     * 判断是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        return str.isEmpty();

    }

    /**
     * 判断是否为空
     *
     * @param objs
     * @return
     */
    public static boolean isEmpty(Object[] objs) {
        if (objs == null) {
            return true;
        }
        return objs.length <= 0;
    }

    /**
     * 判断是否为空
     *
     * @param bytes
     * @return
     */
    public static boolean isEmpty(byte[] bytes) {
        if (bytes == null) {
            return true;
        }
        return bytes.length <= 0;
    }

}
