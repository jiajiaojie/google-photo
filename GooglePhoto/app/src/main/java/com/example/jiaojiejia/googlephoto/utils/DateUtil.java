package com.example.jiaojiejia.googlephoto.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static String[] WEEKS = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};


    /** 获取指定日期为星期几 */
    public static String getWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return WEEKS[week_index];
    }
}
