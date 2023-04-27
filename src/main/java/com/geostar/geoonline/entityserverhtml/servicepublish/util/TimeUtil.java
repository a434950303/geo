package com.geostar.geoonline.entityserverhtml.servicepublish.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeUtil {
  private static Logger logger = LoggerFactory.getLogger(TimeUtil.class);
  
  public static final long SECOND = 1000L;
  
  public static final long MINUTE = 60000L;
  
  public static final long HOUR = 3600000L;
  
  public static final long DAY = 86400000L;
  
  public static final long WEEK = 604800000L;
  
  public static final String theTimeFormat = "yyyy-MM-dd HH:mm:ss";
  
  public static final String theTimeFormatY = "yyyy-MM-dd";
  
  public static final String theTimeFormatH = "yyyy-MM-dd HH:mm";
  
  public static final String otherTimeFormat = "yyyyMMddHHmmss";
  
  public static final String yearMonthTimeFormat = "yyyyMM";
  
  public static final String yearMonthDayTimeFormat = "yyyyMMdd";
  
  public static final boolean useFastDateFormatter = true;
  
  private static Map<String, Class<?>> theFastTimeFormatterMap = new HashMap<>();
  
  public static final DateFormat getTimeFormatter(String format) {
    Class<?> sdf = theFastTimeFormatterMap.get(format);
    if (sdf != null)
      try {
        return (DateFormat)sdf.newInstance();
      } catch (InstantiationException var3) {
        logger.error(var3.getMessage(), var3);
      } catch (IllegalAccessException var4) {
        logger.error(var4.getMessage(), var4);
      }  
    return new SimpleDateFormat(format);
  }
  
  public static SimpleDateFormat newTimeFormatter(String format) {
    return new SimpleDateFormat(format);
  }
  
  public static Calendar clone(Calendar cal) {
    Calendar c = Calendar.getInstance();
    c.set(cal.get(1), cal.get(2), cal.get(5), cal.get(11), cal.get(12), cal.get(13));
    c.set(14, cal.get(14));
    return c;
  }
  
  public static String toString(Calendar cal, DateFormat formator) {
    return (null == cal) ? "" : formator.format(cal.getTime());
  }
  
  public static String toString(Calendar cal, String format) {
    return (null == cal) ? "" : toString(cal, getTimeFormatter(format));
  }
  
  public static String toString(Date date, DateFormat formator) {
    return (null == date) ? "" : formator.format(date);
  }
  
  public static String toString(Date date, String format) {
    String paramFormat = format;
    if (null == date)
      return ""; 
    if (StringUtil.empty(format))
      paramFormat = "yyyy-MM-dd"; 
    return toString(date, getTimeFormatter(paramFormat));
  }
  
  public static String toString(String format) {
    return toString(Calendar.getInstance(), format);
  }
  
  public static String toString(String time, String fromFormat, String toFormat) {
    try {
      return toString(toCalendar(time, fromFormat), toFormat);
    } catch (Exception var4) {
      logger.error(var4.getMessage(), var4);
      return time;
    } 
  }
  
  public static String toString(Calendar cal) {
    return toString(cal, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
  }
  
  public static String toString(Date date) {
    return toString(date, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
  }
  
  public static String toStringh(Date date) {
    return toString(date, new SimpleDateFormat("yyyy-MM-dd HH:mm"));
  }
  
  public static String toStringY(Date date) {
    return toString(date, new SimpleDateFormat("yyyy-MM-dd"));
  }
  
  public static String toString(long millSeconds) {
    Date date = new Date(millSeconds);
    return toString(date);
  }
  
  public static String toString(long millSeconds, String format) {
    Date date = new Date(millSeconds);
    return toString(date, format);
  }
  
  public static Date stringToDate(String str, String format) {
    if (null != str && !"".equals(str.trim()))
      try {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
      } catch (ParseException var3) {
        logger.error(var3.getMessage(), var3);
        return null;
      }  
    return null;
  }
  
  public static String formatSpecialDate(String date, String patten) {
    if (date != null && date.trim().length() != 0) {
      SimpleDateFormat df = new SimpleDateFormat(patten);
      try {
        Date d = df.parse(date);
        return df.format(d);
      } catch (ParseException var5) {
        return "";
      } 
    } 
    return "";
  }
  
  public static long toNumber(Calendar cal, String format) {
    String time = toString(cal, format);
    return Long.parseLong(time);
  }
  
  public static long toNumber(String strTime, String timeFormat, String numberFormat) throws ParseException {
    return toNumber(toCalendar(strTime, timeFormat), numberFormat);
  }
  
  public static Long toLong(Calendar time) {
    try {
      return Long.valueOf(time.getTime().getTime());
    } catch (Exception var2) {
      logger.error(var2.getMessage(), var2);
      return null;
    } 
  }
  
  public static Long toLong(Date time) {
    try {
      return Long.valueOf(time.getTime());
    } catch (Exception var2) {
      logger.error(var2.getMessage(), var2);
      return null;
    } 
  }
  
  public static long toLong(Date time, String format) {
    String str = toString(time, format);
    return NumberUtil.toLong(str);
  }
  
  public static long toNumberOfyyyyMMdd(Calendar cal) {
    return (cal.get(1) * 100 + cal.get(2) + 1) * 100L + cal.get(5);
  }
  
  public static Calendar toRawCalendar(String time, DateFormat formator) throws ParseException {
    Calendar cal = Calendar.getInstance();
    Date d = formator.parse(time);
    cal.setTime(d);
    cal.set(14, 0);
    return cal;
  }
  
  public static Date toRawDate(String time, DateFormat formator) throws ParseException {
    Date d = formator.parse(time);
    return d;
  }
  
  public static Calendar toCalendar(String time, DateFormat formator) {
    try {
      return toRawCalendar(time, formator);
    } catch (Exception var3) {
      logger.error(var3.getMessage(), var3);
      return Calendar.getInstance();
    } 
  }
  
  public static Calendar toRawCalendar(String time, String format) throws ParseException {
    return toRawCalendar(time, getTimeFormatter(format));
  }
  
  public static Calendar toCalendar(String time, String format) throws ParseException {
    return toRawCalendar(time, getTimeFormatter(format));
  }
  
  public static Calendar toRawCalendar(long time, String format) throws ParseException {
    return toRawCalendar(String.valueOf(time), getTimeFormatter(format));
  }
  
  public static Calendar toCalendar(long time, String format) {
    return toCalendar(String.valueOf(time), getTimeFormatter(format));
  }
  
  public static Calendar toRawCalendar(String time) throws ParseException {
    return toRawCalendar(time, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
  }
  
  public static Calendar toCalendar(String time) {
    return toCalendar(time, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
  }
  
  public static Calendar toBoundaryCalendar(int year, int month, int day, int hour, int minute, int second) {
    Calendar cal = Calendar.getInstance();
    cal.set(1, NumberUtil.adjustRange(year, 2000, 2010));
    cal.set(2, NumberUtil.adjustRange(month, 1, 12) - 1);
    cal.set(5, NumberUtil.adjustRange(day, 1, cal.getActualMaximum(5)));
    cal.set(11, NumberUtil.adjustRange(hour, 0, 23));
    cal.set(12, NumberUtil.adjustRange(minute, 0, 59));
    cal.set(13, NumberUtil.adjustRange(second, 0, 59));
    cal.set(14, 0);
    return cal;
  }
  
  public static Calendar minTime(Calendar time1, Calendar time2) {
    return (null != time1 && !time1.after(time2)) ? time1 : time2;
  }
  
  public static Calendar maxTime(Calendar time1, Calendar time2) {
    return (null != time1 && !time1.before(time2)) ? time1 : time2;
  }
  
  public static String getTwoTimeMinute(String stratTime, String endTime) {
    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
    long minute = 0L;
    long second = 0L;
    try {
      Date date = myFormatter.parse(stratTime);
      Date mydate = myFormatter.parse(endTime);
      minute = (date.getTime() - mydate.getTime()) / 60000L;
      second = (date.getTime() - mydate.getTime()) / 1000L - minute * 60L;
    } catch (Exception var9) {
      logger.error(var9.getMessage(), var9);
      return "";
    } 
    return minute + "分钟" + second + "秒";
  }
  
  public static String getTwoTimeSecond(String stratTime, String endTime, String timeFormat) {
    SimpleDateFormat myFormatter = new SimpleDateFormat(timeFormat);
    long second = 0L;
    try {
      Date date = myFormatter.parse(stratTime);
      Date mydate = myFormatter.parse(endTime);
      second = (mydate.getTime() - date.getTime()) / 1000L;
    } catch (Exception var8) {
      logger.error(var8.getMessage(), var8);
      return "0";
    } 
    return (second == 0L) ? "0" : String.valueOf(second);
  }
  
  public static String[] getCurrenyAndBeforeTimeYYYYMM() {
    Calendar calendar = Calendar.getInstance();
    Calendar beforeCalendar = Calendar.getInstance();
    beforeCalendar.add(2, -1);
    String currenyYYYYMM = toString(calendar, "yyyy-MM");
    String beforeYYYYMM = toString(beforeCalendar, "yyyy-MM");
    return new String[] { currenyYYYYMM, beforeYYYYMM };
  }
  
  public static String[] getCurrenyAndBeforeTimeYYYYMMDD() {
    Calendar calendar = Calendar.getInstance();
    Calendar beforeCalendar = Calendar.getInstance();
    beforeCalendar.add(2, -1);
    String currenyYYYYMMdd = toString(calendar, "yyyy-MM-dd");
    String beforeYYYYMMdd = toString(beforeCalendar, "yyyy-MM") + "-01";
    return new String[] { currenyYYYYMMdd, beforeYYYYMMdd };
  }
  
  public static String[] getCurrenyAndBeforeTimeYYYYMMDDHHmmSS() {
    Calendar calendar = Calendar.getInstance();
    Calendar beforeCalendar = Calendar.getInstance();
    beforeCalendar.add(5, -1);
    String currenyYYYYMMdd = toString(calendar, "yyyy-MM-dd HH:mm:ss");
    String beforeYYYYMMdd = toString(beforeCalendar, "yyyy-MM-dd ") + "00:00:00";
    return new String[] { currenyYYYYMMdd, beforeYYYYMMdd };
  }
}
