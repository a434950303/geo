package com.geostar.geoonline.entityserverhtml.framework.common.sys;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.*;

@Component
public final class Geolicense {
  private static final Logger log = LoggerFactory.getLogger(Geolicense.class);
  
  private static final int MIN_KEY_LENGTH = 4;
  
  @Value("${GEOLICENSE.CHECK.PATH}")
  private String path;
  
  @Value("${GEOLICENSE.CHECK.SOFTNAME}")
  private String softname;
  
  @Value("${GEOLICENSE.CHECK.SOFTVER}")
  private String softver;
  
  private static String encryptN(String source, String key) {
    return encrypt(source, "n+" + key);
  }
  
  private static int[] encrypt(int[] v, int[] k) {
    int n = v.length;
    if (n - 1 < 1)
      return v; 
    if (k.length < 4) {
      int[] key = new int[4];
      key = Arrays.copyOf(k, 4);
      k = key;
    } 
    int rounds = 6 + 52 / n;
    int sum = 0;
    int z = v[n - 1];
    int delta = -1640531527;
    while (true) {
      sum += delta;
      int e = sum >>> 2 & 0x3;
      int p;
      for (p = 0; p < n - 1; p++) {
        int i = v[p + 1];
        z = v[p] = v[p] + ((z >>> 5 ^ i << 2) + (i >>> 3 ^ z << 4) ^ (sum ^ i) + (k[p & 0x3 ^ e] ^ z));
      } 
      int y = v[0];
      z = v[n - 1] = v[n - 1] + ((z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 0x3 ^ e] ^ z));
      if (--rounds <= 0)
        return v; 
    } 
  }
  
  private static String encrypt(String source, String key) {
    if (source == null)
      return null; 
    if ("".equals(source))
      return ""; 
    String cipherText = null;
    try {
      byte[] byteArraySource = source.getBytes("utf-8");
      cipherText = Base64.getEncoder().encodeToString(byteArraySource);
      int[] intArraySource = toIntArray(cipherText.getBytes("utf-8"), true);
      int[] intArrayKey = toIntArray(key.getBytes("utf-8"), false);
      int[] result = encrypt(intArraySource, intArrayKey);
      cipherText = Base64.getEncoder().encodeToString(toByteArray(result, false));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    return cipherText;
  }
  
  private static String decryptN(String source, String key) {
    return decrypt(source, "n+" + key);
  }
  
  private static String decrypt(String source, String key) {
    if (source == null)
      return null; 
    if ("".equals(source))
      return ""; 
    String plainText = null;
    try {
      byte[] byteArraySource = Base64.getDecoder().decode(source);
      int[] result = decrypt(toIntArray(byteArraySource, false), toIntArray(key.getBytes("utf-8"), false));
      plainText = new String(toByteArray(result, true), "utf-8");
      plainText = new String(Base64.getDecoder().decode(plainText), "utf-8");
    } catch (Exception e) {
      return "";
    } 
    return plainText;
  }
  
  public void licensecheck() throws Exception {
    int checkStruts = checkLicense(this.path, this.softname, this.softver);
    if (checkStruts != 0)
      throw new Exception("Permission Exception：The product is not authorized to start"); 
  }
  
  private static int[] decrypt(int[] v, int[] k) {
    int n = v.length;
    if (n - 1 < 1)
      return v; 
    if (k.length < 4) {
      int[] key = new int[4];
      key = Arrays.copyOf(k, 4);
      k = key;
    } 
    int z = v[n - 1], y = v[0], delta = -1640531527;
    int rounds = 6 + 52 / n;
    int sum = rounds * delta;
    y = v[0];
    while (true) {
      int e = sum >>> 2 & 0x3;
      int p;
      for (p = n - 1; p > 0; p--) {
        z = v[p - 1];
        y = v[p] = v[p] - ((z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 0x3 ^ e] ^ z));
      } 
      z = v[n - 1];
      y = v[0] = v[0] - ((z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 0x3 ^ e] ^ z));
      if ((sum -= delta) == 0)
        return v; 
    } 
  }
  
  private static byte[] toByteArray(int[] data, boolean includeLength) {
    int n = data.length << 2;
    if (includeLength) {
      int m = data[data.length - 1];
      if (m > n)
        return null; 
      n = m;
    } 
    byte[] result = new byte[n];
    for (int i = 0; i < n; i++)
      result[i] = (byte)(data[i >>> 2] >>> (i & 0x3) << 3 & 0xFF); 
    return result;
  }
  
  private static int[] toIntArray(byte[] data, boolean includeLength) {
    int result[], n = ((data.length & 0x3) == 0) ? (data.length >>> 2) : ((data.length >>> 2) + 1);
    if (includeLength) {
      result = new int[n + 1];
      result[n] = data.length;
    } else {
      result = new int[n];
    } 
    n = data.length;
    for (int i = 0; i < n; i++)
      result[i >>> 2] = result[i >>> 2] | (0xFF & data[i]) << (i & 0x3) << 3; 
    return result;
  }
  
  private static String str2HexStr(String str) {
    char[] chars = "0123456789ABCDEF".toCharArray();
    StringBuilder sb = new StringBuilder("");
    byte[] bs = str.getBytes();
    for (int i = 0; i < bs.length; i++) {
      int bit = (bs[i] & 0xF0) >> 4;
      sb.append(chars[bit]);
      bit = bs[i] & 0xF;
      sb.append(chars[bit]);
    } 
    return sb.toString().trim();
  }
  
  private static String sendPost(String url, HashMap<String, String> params) throws Exception {
    DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
    HttpPost post = new HttpPost(url);
    post.setHeader("User-Agent", "User-Agent");
    List<NameValuePair> urlParameters = new ArrayList<>();
    for (String key : params.keySet())
      urlParameters.add(new BasicNameValuePair(key, params.get(key))); 
    post.setEntity((HttpEntity)new UrlEncodedFormEntity(urlParameters));
    HttpResponse response = defaultHttpClient.execute((HttpUriRequest)post);
    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    StringBuffer result = new StringBuffer();
    String line;
    while ((line = rd.readLine()) != null)
      result.append(line); 
    return result.toString();
  }
  
  private static int checkLicense(String path, String softName, String softVer) throws Exception {
    int res = 0;
    HashMap<String, String> map = new HashMap<>();
    String url = path + "/ios/license/getTicket";
    String url_close = path + "/ios/license/closeConnect";
    String ip = InetAddress.getLocalHost().getHostAddress();
    String key = "GeoLIC_" + System.currentTimeMillis();
    String encrypt_data = URLEncoder.encode(encrypt(ip, key), "UTF-8");
    map.put("userIp", encrypt_data);
    map.put("timestamp", System.currentTimeMillis() + "");
    String result = sendPost(url, map);
    if (((Integer)JSONObject.fromObject(result).get("code")).intValue() == 0) {
      String url_check = path + "/ios/license/check";
      String ticket = (String)JSONObject.fromObject(JSONObject.fromObject(result).get("resData")).get("ticket");
      HashMap<String, String> m = new HashMap<>();
      m.put("softName", URLEncoder.encode(encrypt(softName, key), "UTF-8"));
      m.put("softVer", URLEncoder.encode(encrypt(softVer, key), "UTF-8"));
      m.put("userIp", URLEncoder.encode(encrypt(ip, key), "UTF-8"));
      m.put("timestamp", System.currentTimeMillis() + "");
      m.put("ticket", ticket);
      JSONObject result_check = JSONObject.fromObject(sendPost(url_check, m));
      if (((Integer)result_check.get("code")).intValue() != 0) {
        res = -2;
        log.error(result_check.get("msg").toString());
      } 
    } else {
      res = -1;
      log.error(JSONObject.fromObject(result).get("msg").toString());
    } 
    HashMap<String, String> m_close = new HashMap<>();
    m_close.put("softName", URLEncoder.encode(encrypt(softName, key), "UTF-8"));
    m_close.put("softVer", URLEncoder.encode(encrypt(softVer, key), "UTF-8"));
    m_close.put("userIp", URLEncoder.encode(encrypt(ip, key), "UTF-8"));
    m_close.put("timestamp", System.currentTimeMillis() + "");
    JSONObject result_close = JSONObject.fromObject(sendPost(url_close, m_close));
    if (((Integer)result_close.get("code")).intValue() != 0) {
      res = -2;
      log.error(result_close.get("msg").toString());
    } 
    return res;
  }
  
  public String getLicenseRead(String path, String softName, String softVer) throws Exception {
    HashMap<String, String> map = new HashMap<>();
    String url = path + "/ios/license/getTicket";
    String url_close = path + "/ios/license/closeConnect";
    String ip = InetAddress.getLocalHost().getHostAddress();
    String key = "GeoLIC_" + System.currentTimeMillis();
    String encrypt_data = URLEncoder.encode(encrypt(ip, key), "UTF-8");
    map.put("userIp", encrypt_data);
    map.put("timestamp", System.currentTimeMillis() + "");
    String result = sendPost(url, map);
    String result_read = "";
    if (((Integer)JSONObject.fromObject(result).get("code")).intValue() == 0) {
      String url_read = path + "/ios/license/read";
      String paser = (String)JSONObject.fromObject(JSONObject.fromObject(result).get("resData")).get("ticket");
      HashMap<String, String> m = new HashMap<>();
      m.put("softName", URLEncoder.encode(encrypt(softName, key), "UTF-8"));
      m.put("softVer", URLEncoder.encode(encrypt(softVer, key), "UTF-8"));
      m.put("userIp", URLEncoder.encode(encrypt(ip, key), "UTF-8"));
      m.put("timestamp", System.currentTimeMillis() + "");
      m.put("ticket", paser);
      result_read = sendPost(url_read, m);
      System.out.println("获取到的Read" + result_read);
      return result_read;
    } 
    return null;
  }
}
