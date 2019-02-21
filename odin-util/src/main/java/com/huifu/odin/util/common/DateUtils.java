package com.huifu.odin.util.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 时间工具类
 * </dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>none
 * </dd>
 * </dl>
 *
 * @author eric
 * @version 1.0, 2009-12-28
 * @since framework-1.4
 */
public class DateUtils {


    //============================0.获取当前时间====================================

    /**
     * 获取当前日期类型时间
     */
    public static Date getNow() {
        return new Date();
    }


    /**
     * 获取当前日期 yyyyMMdd
     */
    public static String getCurrentDate() {
        return toMailDateDtPartString(getNow());
    }


    /**
     * 将一个日期型转换为指定格式字串
     *
     * @param aDate
     * @param formatStr
     * @return
     */
    public static final String toFormatDateString(Date aDate, String formatStr) {
        if (aDate == null) {
            return StringUtils.EMPTY;
        }
        return new SimpleDateFormat(formatStr).format(aDate);
    }


    /**
     * 将一个日期型转换为'yyyyMMdd'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toMailDateDtPartString(Date aDate) {
        return toFormatDateString(aDate, MAIL_DATE_DT_PART_FORMAT);
    }


    /**
     * 日期合法check
     *
     * @param date 需要check的日期
     * @return 日期是否合法
     */
    public static boolean chkDateFormat(String date) {
        try {
            // 如果输入日期不是8位的,判定为false.
            if (null == date || "".equals(date) || !date.matches("[0-9]{8}")) {
                return false;
            }
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6)) - 1;
            int day = Integer.parseInt(date.substring(6));
            Calendar calendar = GregorianCalendar.getInstance();
            // 当 Calendar 处于 non-lenient 模式时，如果其日历字段中存在任何不一致性，它都会抛出一个异常。
            calendar.setLenient(false);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DATE, day);
            // 如果日期错误,执行该语句,必定抛出异常.
            calendar.get(Calendar.YEAR);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }


    public static final String MAIL_DATE_FORMAT = "yyyyMMddHHmmss";

    public static final String MAIL_DATE_DT_PART_FORMAT = "yyyyMMdd";

    public final static String SHORT_FORMAT = "yyyyMMdd";
    public final static String TIME_FORMAT = "HHmmss";


    private DateUtils() {
    }
}
