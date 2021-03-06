package com.coates.tools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * <p> Title: DateUtils.java </p>
 * <p> Package com.shenpinkj.utils </p>
 * <p> Description: TODO(获取时间所有操作) </p>
 * <p> Company: www.shenpinkj.cn </p>
 *
 * @author 牟超
 * @version 1.0
 * @date 2017年10月24日下午2:14:18
 */
public class DateUtils {
    private static volatile DateUtils dateUtils;
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String CHINESE_DATE_FORMAT_LINE = "yyyy-MM-dd";
    public static final String DATETIME_NOT_SEPARATOR = "yyyyMMddHHmmssSSS";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    private DateUtils() {
    }

    public static DateUtils getDateUtils() {
        if (dateUtils == null) {
            synchronized (DateUtils.class) {
                if (dateUtils == null) {
                    dateUtils = new DateUtils();
                }
            }
        }
        return dateUtils;
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：获取当前时间
     *
     * @return yyyy-MM-dd
     */
    public String getTodayDate() {
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：获取指定时间格式的当前系统时间
     *
     * @param format
     * @return
     */
    public String getTodayDate(String format) {
        Calendar c = Calendar.getInstance();
        sdf.applyPattern(format);
        return sdf.format(c.getTime());
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年11月2日
     * 方法描述：获取指定时间格式的当前系统时间  yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public String getTodayDate1() {
        Date d = new Date();
        return sdf.format(d);
    }


    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：时间毫秒转时间格式
     *
     * @param time
     * @param format
     * @return
     */
    public String longToString(long time, String format) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        sdf.applyPattern(format);
        return sdf.format(c.getTime());
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：获取指定分钟数以前的时间
     *
     * @param minute
     * @param format
     * @return
     */
    public String getTimeBeformMinute(int minute, String format) {
        Calendar c = Calendar.getInstance();
        sdf.applyPattern(format);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - minute);
        return sdf.format(c.getTime());
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：获取指定分钟数以后的时间 默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @param minute
     * @param format
     * @return yyyy-MM-dd HH:mm:ss
     */
    public String getTimeAfterMinute(int minute, String format) {
        Calendar c = Calendar.getInstance();
        sdf.applyPattern(format);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minute);
        return sdf.format(c.getTime());
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：获取指定分钟数以后的时间
     *
     * @param time
     * @param minute
     * @param format
     * @return yyyy-MM-dd HH:mm:ss
     */
    public String getTimeAfterMinute(String time, int minute, String format) {
        Calendar c = Calendar.getInstance();
        sdf.applyPattern(format);
        try {
            c.setTime(sdf.parse(time));
            c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(c.getTime());
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：获取今天以前的指定天数日期
     *
     * @param day
     * @param format
     * @return 默认格式yyyy-MM-dd
     */
    public String getBeforeDay(int day, String format) {
        Calendar c = Calendar.getInstance();
        sdf.applyPattern(format);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - day);
        return sdf.format(c.getTime());
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：计算出两个时间得分钟数差值
     *
     * @param c1 大
     * @param c2 小
     * @return
     */
    public int getMinutePoor(String c1, String c2) {
        try {
            Calendar ca1 = Calendar.getInstance();
            ca1.setTime(sdf.parse(c1));
            Calendar ca2 = Calendar.getInstance();
            ca2.setTime(sdf.parse(c2));
            long sub = ca1.getTimeInMillis() - ca2.getTimeInMillis();
            long m = sub / 1000 / 60;
            return Math.abs(Integer.parseInt(m + ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：计算出两个时间得秒数差值
     *
     * @param c1 大
     * @param c2 小
     * @return
     */
    public int getSecondsPoor(String c1, String c2) {
        try {
            Calendar ca1 = Calendar.getInstance();
            ca1.setTime(sdf.parse(c1));
            Calendar ca2 = Calendar.getInstance();
            ca2.setTime(sdf.parse(c2));
            long sub = ca1.getTimeInMillis() - ca2.getTimeInMillis();
            long m = sub / 1000;
            return Math.abs(Integer.parseInt(m + ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：获取当前时间毫秒数
     *
     * @return
     */
    public long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：获取两个日期间隔天数
     *
     * @return
     */
    public int daysBetween(String bdate, String edate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(bdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(edate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：获取指定天数以前的时间
     *
     * @param day 天数
     * @return
     */
    public String getBeforeDate(int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - day);
        sdf.applyPattern(CHINESE_DATE_FORMAT_LINE);
        return sdf.format(c.getTime());
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述： 获取多少分钟数以前时间
     *
     * @param minute
     * @return
     */
    public String getBeforeTime(int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - minute);
        return sdf.format(c.getTime());
    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：格式化指定日期
     *
     * @param date 日期
     * @return
     */
    public static String formatDate(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        synchronized (sdf) {
            return sdf.format(date);
        }

    }

    /**
     * 创 建 人：牟 超
     * 创建时间：2017年10月24日
     * 方法描述：格式化指定日期
     *
     * @param strDate 日期
     * @return
     */
    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        synchronized (strDate) {
            return sdf.parse(strDate);
        }
    }

    private static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    private static String YYYYMMDD = "yyyy-MM-dd";
    private static String YYYYMM = "yyyy-MM";


    /**
     * 获取到当前日期
     *
     * @return YYYY-MM-DD
     */
    public static String getCurrentDay() {
        LocalDate todayDate = LocalDate.now();
        System.out.println("今天的日期：" + todayDate);
        return todayDate.toString();
    }


    /**
     * 获取到当前的
     * @param type 是否显示毫秒 true 显示 fasle 不显示
     * @return HH:mm:ss
     */
    public static String getCurrentTime(boolean type) {
        LocalTime nowTime = type == true ? LocalTime.now() : LocalTime.now().withNano(0);
        System.out.println("今天的日期：" + nowTime);
        return nowTime.toString();
    }

    /**
     * 获取当前或者提前n小时
     * @param type 是否显示毫秒 true 显示 fasle 不显示
     * @param number 当前时间增加N小时
     * @return HH:mm:ss
     */

    public static String getCurrentTime(boolean type,int number) {
        LocalTime nowTime = type == true ? LocalTime.now().plusHours(number) : LocalTime.now().plusHours(number).withNano(0);
        System.out.println("今天的日期：" + nowTime);
        return nowTime.toString();
    }


    /**
     * 获取本月第一天
     *
     * @return YYYY-MM-DD
     */
    public static String getFirstDay() {
        LocalDate todayDate = LocalDate.now();
        //取2016年10月的第1天
        LocalDate firstDay = todayDate.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDay);
        return firstDay.toString();
    }

    /**
     * 获取本月第一天
     *
     * @return YYYY-MM-DD
     */
    public static String getLastDay() {
        LocalDate todayDate = LocalDate.now();
        //取2016年10月的第1天
        LocalDate firstDay = todayDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(firstDay);
        return firstDay.toString();
    }

    /**
     * 获取某月最后一天,可查去年(-1去年,0今年,1明年,以此类推)
     *
     * @return
     */
    public static final int getLastDayOfMonth(int month, int year) {
        LocalDate localDatew = LocalDate.now().plusYears(year).withMonth(month);
        //某月的最后一天
        LocalDate lastDay = localDatew.with(TemporalAdjusters.lastDayOfMonth());
        return lastDay.getDayOfMonth();
    }

    /**
     * 获取某年的开始日期
     *
     * @param offset 0今年，1明年，-1去年，依次类推
     * @return
     */
    public static LocalDate getYearStart(int offset) {
        return LocalDate.now().plusYears(offset).with(TemporalAdjusters.firstDayOfYear());
    }

    public static String getplusDays(int day) {
        LocalDate todayDate = LocalDate.now();
        //当前日期＋1天
        LocalDate tomorrow = todayDate.plusDays(day);
        System.out.println(tomorrow);
        return tomorrow.toString();
    }

    /**
     * 生日检查或者账单日检查
     *
     * @param pastYear  过去年
     * @param pastMonth 过去月
     * @param pastDay   过去日
     * @param year      当前年
     * @param month     当前月
     * @param day       当前日
     * @return true/false
     */
    public static Boolean get(int pastYear, int pastMonth, int pastDay, int year, int month, int day) {
        LocalDate birthday = LocalDate.of(pastYear, pastMonth, pastDay);
        MonthDay birthdayMd = MonthDay.of(birthday.getMonth(), birthday.getDayOfMonth());
        MonthDay today = MonthDay.from(LocalDate.of(year, month, day));
        System.out.println(today.equals(birthdayMd));
        return today.equals(birthdayMd);
    }

    public static void main(String[] args) {
        LocalDate todayDate = LocalDate.now();
        //取2016年10月的第1天
        LocalDate firstDay = todayDate.with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDay);
        //当前日期＋1天
        LocalDate tomorrow = todayDate.plusDays(1);
        System.out.println(tomorrow);
    }

}
