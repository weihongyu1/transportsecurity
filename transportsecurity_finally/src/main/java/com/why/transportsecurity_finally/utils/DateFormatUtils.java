package com.why.transportsecurity_finally.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description TODO 时间格式化处理
 * @Author why
 * @Date 2021/6/4 13:32
 * Version 1.0
 **/
public class DateFormatUtils {

    /**
     * 返回当前时间的long值
     * @return
     */
    public static long dateLong(){
        return new Date().getTime();
    }

    /**
     * 时间戳long值转换为yyyy-MM-dd HH:mm:ss时间
     * @param time
     * @return
     */
    public static String myFormatTime(long time){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(time);
    }

    public static java.sql.Date stringToDate(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date date1 = new java.sql.Date(date.getTime());
        return date1;
    }
}