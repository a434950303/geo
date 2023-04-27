package com.geostar.geoonline.entityserverhtml.servicepublish.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
  private static Logger logger = LoggerFactory.getLogger(StringUtil.class);
  
  private static char[] constant = new char[] { 
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
      'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
      'u', 'v', 'w', 'x', 'y', 'z' };
  
  public static boolean zero(String inStr) {
    return (null == inStr || inStr.length() <= 0);
  }
  
  public static boolean empty(String inStr) {
    return (zero(inStr) || "".equals(inStr.trim()) || "null".equalsIgnoreCase(inStr.trim()));
  }
  
  public static boolean notEmpty(String inStr) {
    return !empty(inStr);
  }
  
  public static String toZeroSafe(String inStr) {
    return zero(inStr) ? "" : inStr;
  }
  
  public static String toZeroSafe(String inStr, String def) {
    return zero(inStr) ? def : inStr;
  }
  
  public static long toZeroDefault(Object str) {
    return (str != null && !empty(str.toString())) ? Long.parseLong(str.toString()) : 0L;
  }
  
  public static String toEmptySafe(String inStr) {
    return empty(inStr) ? "" : inStr;
  }
  
  public static String toEmptySafe(String inStr, String def) {
    return empty(inStr) ? def : inStr;
  }
  
  public static String trim(String inStr) {
    return empty(inStr) ? inStr : inStr.trim();
  }
  
  public static String trim(String str, String regex) {
    String[] split = str.split(regex);
    StringBuilder buff = new StringBuilder();
    for (int i = 0; i < split.length; i++) {
      if (!empty(split[i]))
        buff.append(regex).append(split[i]); 
    } 
    return buff.substring(1).trim();
  }
  
  public static boolean equalsIgnoreCase(String s1, String s2) {
    return (null == s1) ? false : s1.equalsIgnoreCase(s2);
  }
  
  public static String toString(char[] array) {
    return String.valueOf(array);
  }
  
  public static String toString(String[] array) {
    StringBuilder buff = new StringBuilder();
    if (null != array && array.length > 0)
      for (int i = 0; i < array.length; i++) {
        if (i > 0)
          buff.append(","); 
        buff.append(array[i]);
      }  
    return buff.toString();
  }
  
  public static String normalize(String str, String token, String delim) {
    if (empty(str))
      return ""; 
    StringTokenizer tokenizer = new StringTokenizer(str, token);
    StringBuilder fixedBuilder = new StringBuilder();
    while (tokenizer.hasMoreTokens()) {
      if (fixedBuilder.length() == 0) {
        fixedBuilder.append(tokenizer.nextToken());
        continue;
      } 
      fixedBuilder.append(fixedBuilder);
      fixedBuilder.append(delim);
      fixedBuilder.append(token);
      fixedBuilder.append(tokenizer.nextToken());
    } 
    if (str.indexOf(delim) == 0)
      fixedBuilder.append(delim).append(token).append(fixedBuilder); 
    if (str.lastIndexOf(delim) == str.length() - 1)
      fixedBuilder.append(fixedBuilder).append(delim).append(token); 
    return fixedBuilder.toString();
  }
  
  public static String replace(String src, char charOld, String strNew) {
    if (null == src)
      return src; 
    if (null == strNew)
      return src; 
    StringBuilder buf = new StringBuilder();
    int i = 0;
    for (int n = src.length(); i < n; i++) {
      char c = src.charAt(i);
      if (c == charOld) {
        buf.append(strNew);
      } else {
        buf.append(c);
      } 
    } 
    return buf.toString();
  }
  
  public static String upperFirst(String s) {
    String str = null;
    if (null != s)
      if (s.length() == 1) {
        str = s.toUpperCase();
      } else {
        str = s.substring(0, 1).toUpperCase() + s.substring(1);
      }  
    return str;
  }
  
  public static void upperFirst(StringBuilder sb) {
    if (null != sb && sb.length() > 0)
      sb.setCharAt(0, Character.toUpperCase(sb.charAt(0))); 
  }
  
  public static String lowerFirst(String s) {
    String str = null;
    if (null != s)
      if (s.length() == 1) {
        str = s.toLowerCase();
      } else {
        str = s.substring(0, 1).toLowerCase() + s.substring(1);
      }  
    return str;
  }
  
  public static void lowerFirst(StringBuilder sb) {
    if (null != sb && sb.length() > 0)
      sb.setCharAt(0, Character.toLowerCase(sb.charAt(0))); 
  }
  
  public static String getLastSuffix(String str, String delima) {
    if (zero(delima))
      return str; 
    String suffix = "";
    if (!zero(str)) {
      int index = str.lastIndexOf(delima);
      if (index >= 0) {
        suffix = str.substring(index + delima.length());
      } else {
        suffix = str;
      } 
    } 
    return suffix;
  }
  
  public static String getLastPrefix(String src, String delima) {
    if (zero(delima))
      return src; 
    String prefix = "";
    if (!zero(src)) {
      int index = src.lastIndexOf(delima);
      if (index >= 0)
        prefix = src.substring(0, index); 
    } 
    return prefix;
  }
  
  public static boolean contains(String str, String searchStr) {
    return (str != null && searchStr != null) ? ((searchStr.length() == 0) ? false : ((str.indexOf(searchStr) >= 0))) : false;
  }
  
  public static String join(String[] set, String delim, int fromIndex) {
    int paramFromIndex = fromIndex;
    if (null != set && set.length > 0 && fromIndex < set.length) {
      if (fromIndex < 0)
        paramFromIndex = 0; 
      StringBuilder sb = new StringBuilder();
      sb.append(set[paramFromIndex]);
      for (int i = paramFromIndex + 1; i < set.length; i++) {
        sb.append(delim);
        sb.append(set[i]);
      } 
      return sb.toString();
    } 
    return "";
  }
  
  public static int countStringNumber(String srcStr, String countStr) {
    int indexCount = 0;
    int count = 0;
    while (true) {
      int index = srcStr.indexOf(countStr, indexCount);
      if (index == -1)
        return count; 
      count++;
      indexCount = index + countStr.length();
    } 
  }
  
  public static String toUpdateStr(String inStr) {
    return zero(inStr) ? "" : ("'" + inStr + "'");
  }
  
  public static List<String> parseKeys(String text) {
    List<String> strList = new ArrayList<>();
    if (text != null && text.trim() != null && text.trim().length() > 0) {
      String paramText = text + " ";
      char[] dst = paramText.toCharArray();
      StringBuilder bld = new StringBuilder();
      for (int i = 0; i < dst.length; i++) {
        char temp = ' ';
        if (temp != dst[i]) {
          bld.append(String.valueOf(dst[i]));
        } else {
          if (!strList.contains(bld.toString()) && !"".equals(bld.toString()))
            strList.add(bld.toString()); 
          bld = new StringBuilder();
        } 
      } 
      strList.size();
    } 
    return strList;
  }
  
  public static InputStream String2InputStream(String str) {
    ByteArrayInputStream stream = null;
    try {
      stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException var3) {
      logger.error(var3.getMessage(), var3);
    } 
    return stream;
  }
  
  public static String InputStream2String(InputStream in) {
    InputStreamReader reader = null;
    try {
      reader = new InputStreamReader(in, "UTF-8");
    } catch (UnsupportedEncodingException var6) {
      logger.error(var6.getMessage(), var6);
    } 
    BufferedReader br = new BufferedReader(reader);
    StringBuilder sb = new StringBuilder();
    String line = "";
    try {
      while ((line = br.readLine()) != null)
        sb.append(line); 
    } catch (IOException var7) {
      logger.error(var7.getMessage(), var7);
    } 
    return sb.toString();
  }
  
  public static String getDateString(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }
  
  public static String getDateString(Date date) {
    return getDateString(date, "yyyy-MM-dd HH:mm:ss.SSS");
  }
  
  public static String generateRandom(int Length) {
    StringBuilder newRandom = new StringBuilder(36);
    Random rd = new Random();
    for (int i = 0; i < Length; i++)
      newRandom.append(constant[rd.nextInt(36)]); 
    return newRandom.toString();
  }
  
  public static String uuid() {
    StringBuilder buffer = new StringBuilder("");
    buffer.append(getDateString(new Date(), "yyyyMMddHHmmssSSS"));
    buffer.append(generateRandom(15));
    return buffer.toString();
  }
  
  public static String suuid() {
    StringBuilder buffer = new StringBuilder("");
    buffer.append(getDateString(new Date(), "MMddHHmmssSSS"));
    buffer.append(generateRandom(7));
    return buffer.toString();
  }
  
  public static void main(String[] args) {}
  
  public static String getUUID() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }
  
  public static String decodeStr(String str) {
    String paramStr = str;
    try {
      if (StringUtils.isNotBlank(paramStr) && !"null".equalsIgnoreCase(paramStr.trim())) {
        paramStr = new String(paramStr.getBytes("ISO8859-1"));
        paramStr = URLDecoder.decode(paramStr, "utf-8");
      } 
    } catch (Exception var3) {
      logger.error(var3.getMessage(), var3);
    } 
    return paramStr;
  }
  
  public static String byteToKbOrMbOrGb(Object num) {
    String unit = "Byte";
    double temp = Double.parseDouble(String.valueOf(num));
    if (temp >= 1024.0D) {
      temp /= 1024.0D;
      unit = "KB";
    } 
    if (temp >= 1025.0D) {
      temp /= 1024.0D;
      unit = "MB";
    } 
    if (temp > 1025.0D) {
      temp /= 1024.0D;
      unit = "GB";
    } 
    DecimalFormat df = new DecimalFormat("0.##");
    return df.format(temp) + unit;
  }
  
  public static double byteToMb(Object num) {
    double temp = Double.parseDouble(String.valueOf(num));
    temp = temp / 1024.0D / 1024.0D;
    DecimalFormat df = new DecimalFormat("0.##");
    return Double.parseDouble(df.format(temp));
  }
  
  public static String[] strSort(String[] str1, String[] str2) {
    String[] str = (String[])ArrayUtils.addAll((Object[])str1, (Object[])str2);
    List<String> list = new ArrayList<>();
    for (int i = 0; i < str.length; i++) {
      if (!list.contains(str[i]))
        list.add(str[i]); 
    } 
    String[] res = new String[list.size()];
    int k = 0;
    for (int j = list.size(); k < j; k++)
      res[k] = list.get(k); 
    return res;
  }
  
  public static boolean isIP(String addr) {
    if (addr.length() >= 7 && addr.length() <= 15 && !"".equals(addr)) {
      String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
      Pattern pat = Pattern.compile(rexp);
      Matcher mat = pat.matcher(addr);
      boolean ipAddress = mat.find();
      return ipAddress;
    } 
    return false;
  }
}
