package com.geostar.geoonline.entityserverhtml.servicepublish.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class NumberUtil {
  private static Logger logger = LoggerFactory.getLogger(NumberUtil.class);
  
  private static final NumberFormat theNumberFormatter = NumberFormat.getNumberInstance();
  
  public static boolean isLong(String str) {
    try {
      Long.parseLong(str);
      return true;
    } catch (Exception var2) {
      logger.error(var2.getMessage(), var2);
      return false;
    } 
  }
  
  public static String toString(double num, int fractionDigit) {
    NumberFormat nf = NumberFormat.getInstance();
    nf.setMinimumIntegerDigits(1);
    nf.setMinimumFractionDigits(fractionDigit);
    nf.setMaximumFractionDigits(fractionDigit);
    return nf.format(num);
  }
  
  public static String toString(double num) {
    NumberFormat f = NumberFormat.getInstance();
    if (f instanceof DecimalFormat)
      ((DecimalFormat)f).setDecimalSeparatorAlwaysShown(true); 
    f.setParseIntegerOnly(true);
    return f.format(num);
  }
  
  public static Integer toIntObject(String str) {
    return new Integer(str);
  }
  
  public static int toRawInt(String str) {
    return Integer.parseInt(str.trim());
  }
  
  public static int toFormattedInt(String str) {
    return toFormattedInt(str, 0);
  }
  
  public static int toFormattedInt(String str, int defaultValue) {
    if (StringUtil.empty(str))
      return defaultValue; 
    try {
      return toRawFormattedInt(str);
    } catch (Exception var3) {
      logger.error(var3.getMessage(), var3);
      return defaultValue;
    } 
  }
  
  public static int toRawFormattedInt(String str) throws NumberFormatException, ParseException {
    return Integer.parseInt(theNumberFormatter.parse(str.trim()).toString());
  }
  
  public static long toLong(String str) {
    return toLong(str, 0L);
  }
  
  public static Long toLongObject(String str) {
    return new Long(str.trim());
  }
  
  public static Long toLongObject(String str, long defaultValue) {
    return StringUtil.empty(str) ? Long.valueOf(defaultValue) : new Long(str.trim());
  }
  
  public static long toLong(String str, long defaultValue) {
    if (StringUtil.empty(str))
      return defaultValue; 
    try {
      return toRawLong(str);
    } catch (Exception var4) {
      logger.error(var4.getMessage(), var4);
      return defaultValue;
    } 
  }
  
  public static long toRawLong(String str) {
    return Long.parseLong(str.trim());
  }
  
  public static long toFormattedLong(String str) {
    return toFormattedLong(str, 0L);
  }
  
  public static long toFormattedLong(String str, long defaultValue) {
    if (StringUtil.empty(str))
      return defaultValue; 
    try {
      return toRawFormattedLong(str);
    } catch (Exception var4) {
      logger.error(var4.getMessage(), var4);
      return defaultValue;
    } 
  }
  
  public static long toRawFormattedLong(String str) throws NumberFormatException, ParseException {
    return Long.parseLong(theNumberFormatter.parse(str.trim()).toString());
  }
  
  public static double toDouble(String str) {
    return toDouble(str, 0.0D);
  }
  
  public static double toDouble(double num, int fractionDigit) {
    String d = toString(num, fractionDigit);
    return toDouble(d, 0.0D);
  }
  
  public static double toDouble(String str, double defaultValue) {
    if (StringUtil.empty(str))
      return defaultValue; 
    try {
      return toRawDouble(str);
    } catch (Exception var4) {
      logger.error(var4.getMessage(), var4);
      return defaultValue;
    } 
  }
  
  public static double toRawDouble(String str) {
    return Double.parseDouble(str.trim());
  }
  
  public static double toFormattedDouble(String str) {
    return toFormattedDouble(str, 0.0D);
  }
  
  public static double toFormattedDouble(String str, double defaultValue) {
    if (StringUtil.empty(str))
      return defaultValue; 
    try {
      return toRawFormattedDouble(str);
    } catch (Exception var4) {
      logger.error(var4.getMessage(), var4);
      return defaultValue;
    } 
  }
  
  public static double toRawFormattedDouble(String str) throws NumberFormatException, ParseException {
    return Double.parseDouble(theNumberFormatter.parse(str.trim()).toString());
  }
  
  public static Integer[] toIntegerObjectSet(int[] set) {
    if (set == null)
      return null; 
    Integer[] s = new Integer[set.length];
    for (int i = 0; i < set.length; i++)
      s[i] = Integer.valueOf(set[i]); 
    return s;
  }
  
  public static String join(int[] set, String delim) {
    if (null != set && set.length > 0) {
      StringBuilder sb = new StringBuilder();
      sb.append(set[0]);
      for (int i = 1; i < set.length; i++) {
        sb.append(delim);
        sb.append(set[i]);
      } 
      return sb.toString();
    } 
    return "";
  }
  
  public static final int adjustRange(int old, int min, int max) {
    int paramOld = old;
    if (old < min)
      paramOld = min; 
    if (paramOld > max)
      paramOld = max; 
    return paramOld;
  }
  
  public static final int adjustMinRange(int old, int min) {
    int paramOld = old;
    if (old < min)
      paramOld = min; 
    return paramOld;
  }
  
  public static final int adjustMaxRange(int old, int max) {
    int paramOld = old;
    if (old > max)
      paramOld = max; 
    return paramOld;
  }
  
  public static final String toString(long[] array) {
    if (null == array)
      return "count=0:[]"; 
    StringBuilder bld = new StringBuilder("count=" + array.length + ":[ ");
    for (int i = 0; i < array.length; i++) {
      if (i > 0)
        bld.append(", "); 
      bld.append(array[i]);
    } 
    bld.append(" ]");
    return bld.toString();
  }
  
  public static final String toString(int[] array) {
    if (null == array)
      return "count=0:[]"; 
    StringBuilder bld = new StringBuilder("count=" + array.length + ":[ ");
    for (int i = 0; i < array.length; i++) {
      if (i > 0)
        bld.append(", "); 
      bld.append(array[i]);
    } 
    bld.append(" ]");
    return bld.toString();
  }
  
  public static final String parseNumber(int num) {
    int paramNum = num;
    String[] digit = { "0", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    String[] unit = { "#", "十", "百", "千", "万", "十", "百", "千", "亿" };
    StringBuilder sb = new StringBuilder();
    for (int unitIndex = 0; paramNum > 0; paramNum /= 10) {
      int t = paramNum % 10;
      if (t > 0 || unitIndex % 4 == 0)
        sb.append(unit[unitIndex]); 
      if (unitIndex != 0 || t != 0)
        sb.append(digit[t]); 
      unitIndex++;
    } 
    return sb.reverse().toString().replaceAll("0{2,}", "0").replaceAll("0万", "万").replaceFirst("0#", "").replaceFirst("#", "");
  }
}
