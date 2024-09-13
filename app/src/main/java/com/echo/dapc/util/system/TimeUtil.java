package com.echo.dapc.util.system;


import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeUtil {
    /**
     * y 年份
     * Y 当周所在年份
     * M 月份
     * m 分钟
     * H 24小时制
     * h 12小时制
     */

    /**
     * MM月dd日
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat zh_mmDD= new SimpleDateFormat("MM月dd日");
    /**
     * yyyy年MM月dd日
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat zh_yyyyMMdd= new SimpleDateFormat("yyyy年MM月dd日");
    /**
     * yyyy-MM-dd HH:mm
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /**
     * yyyy年MM月dd日 HH时mm分
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat zh_yyyyMMddHHmm = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
    /**
     * dd:HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat ddHHmmSS = new SimpleDateFormat("dd:HH:mm:ss");
    /**
     * dd天HH时mm分ss秒
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat zh_ddHHmmSS = new SimpleDateFormat("dd天HH时mm分ss秒");
    /**
     * HH时mm分ss秒
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat zh_HHmmSS = new SimpleDateFormat("HH时mm分ss秒");
    /**
     * mm/dd
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat mmDD = new SimpleDateFormat("MM/dd");
    /**
     * EEE
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat eee = new SimpleDateFormat("EEE");
    /**
     * hh:MM
     */
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat hhMM = new SimpleDateFormat("HH:mm");

    private TimeUtil() {
        throw new UnsupportedOperationException("TimeUtil不能new");
    }

    /**
     * 获取当前毫秒数
     * @return 时间戳
     */
    public static long getCurrentTime(){
        return System.currentTimeMillis();
    }

    public static String format(final long time){
        if (countingDaysUntilNow(time)<=365){
            return zh_mmDD.format(new Date(time));
        }else {
            return zh_yyyyMMdd.format(new Date(time));
        }
    }

    public static String format(final long time, final DateFormat format){
        return format.format(new Date(time));
    }

    public static long countingDaysUntilNow(final long time) {
        return (getCurrentTime() - time) / (24 * 60 * 60 * 1000);
    }

    public static long countingHoursUntilNow(final Long time) {
        return (getCurrentTime() - time) / (60 * 60 * 1000);
    }

    public static long countingMinutesUntilNow(final Long time) {
        return (getCurrentTime() - time) / (60 * 1000);
    }
}
