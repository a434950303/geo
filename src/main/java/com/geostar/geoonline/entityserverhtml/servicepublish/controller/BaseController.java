package com.geostar.geoonline.entityserverhtml.servicepublish.controller;

import com.alibaba.fastjson.JSONObject;
import com.geostar.geoonline.entityserverhtml.framework.common.page.Page;
import com.geostar.geoonline.entityserverhtml.servicepublish.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditor;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {
  private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
  
  protected Gson exposeAnnotationGson;
  
  protected BaseController() {
    Type type1 = (new TypeToken<Date>() {
      
      }).getType();
    Type type2 = (new TypeToken<Timestamp>() {
      
      }).getType();
    this
      
      .exposeAnnotationGson = (new GsonBuilder()).excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(type1, new TypeAdapter<Date>() {
          public void write(JsonWriter out, Date value) throws IOException {
            out.value(TimeUtil.toStringY(value));
          }
          
          public Date read(JsonReader reader) throws IOException {
            reader.beginObject();
            if (reader.hasNext()) {
              String dataStr = reader.nextString();
              try {
                TimeUtil.toRawDate(dataStr, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
              } catch (Exception var4) {
                BaseController.logger.error(var4.getMessage(), var4);
              } 
            } 
            return null;
          }
        }).registerTypeAdapter(type2, new TypeAdapter<Timestamp>() {
          public void write(JsonWriter out, Timestamp value) throws IOException {
            out.value(TimeUtil.toString(value));
          }
          
          public Timestamp read(JsonReader reader) throws IOException {
            reader.beginObject();
            if (reader.hasNext()) {
              String dataStr = reader.nextString();
              try {
                TimeUtil.toRawDate(dataStr, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
              } catch (Exception var4) {
                BaseController.logger.error(var4.getMessage(), var4);
              } 
            } 
            return null;
          }
        }).create();
  }
  
  protected HttpServletRequest getRequest() {
    return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
  }
  
  protected HttpServletResponse getResponse() {
    HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html");
    return response;
  }
  
  protected HttpSession getSession() {
    return getRequest().getSession();
  }
  
  protected String getUrlHeader() {
    return getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + "/";
  }
  
  public String printJSONError() {
    Map<String, Object> map = new HashMap<>();
    map.put("result", Boolean.valueOf(false));
    map.put("message", "程序异常，请联系管理员");
    if (this.exposeAnnotationGson != null) {
      writePrint(this.exposeAnnotationGson.toJson(map));
      return this.exposeAnnotationGson.toJson(map);
    } 
    return null;
  }
  
  protected String printJSONResult(boolean result) {
    Map<String, Object> map = new HashMap<>();
    map.put("result", Boolean.valueOf(result));
    if (this.exposeAnnotationGson != null) {
      writePrint(this.exposeAnnotationGson.toJson(map));
      return this.exposeAnnotationGson.toJson(map);
    } 
    return null;
  }
  
  protected String printJSONByPage(boolean result, Page<?> page) {
    if (page == null)
      return null; 
    Map<String, Object> map = new HashMap<>();
    page.resetPageNo();
    map.put("result", Boolean.valueOf(result));
    map.put("total", Integer.valueOf(page.getTotalCount()));
    map.put("pageIndex", Integer.valueOf(page.getPageIndex()));
    map.put("pageSize", Integer.valueOf(page.getPageSize()));
    map.put("pageNo", Integer.valueOf(page.getPageNo()));
    map.put("pageCount", Integer.valueOf(page.getPageCount()));
    map.put("data", page.getData());
    if (this.exposeAnnotationGson != null) {
      writePrint(this.exposeAnnotationGson.toJson(map));
      return this.exposeAnnotationGson.toJson(map);
    } 
    return null;
  }
  
  protected String printJSON(boolean result, List<?> list) {
    if (list == null)
      return null; 
    Map<String, Object> map = new HashMap<>();
    map.put("result", Boolean.valueOf(result));
    map.put("data", list);
    map.put("total", Integer.valueOf(list.size()));
    if (this.exposeAnnotationGson != null) {
      writePrint(this.exposeAnnotationGson.toJson(map));
      return this.exposeAnnotationGson.toJson(map);
    } 
    return null;
  }
  
  protected String printJsonArray(boolean result, JsonArray jsonArray) {
    if (jsonArray == null)
      return null; 
    Map<String, Object> map = new HashMap<>();
    map.put("result", Boolean.valueOf(result));
    map.put("data", jsonArray);
    map.put("total", Integer.valueOf(jsonArray.size()));
    if (this.exposeAnnotationGson != null) {
      writePrint(this.exposeAnnotationGson.toJson(map));
      return this.exposeAnnotationGson.toJson(map);
    } 
    return null;
  }
  
  protected String printJSON(boolean result, Map<?, ?> map) {
    if (map == null)
      return null; 
    Map<String, Object> map1 = new HashMap<>();
    map1.put("result", Boolean.valueOf(result));
    map1.put("data", map);
    map1.put("total", Integer.valueOf(map.size()));
    if (this.exposeAnnotationGson != null) {
      writePrint(this.exposeAnnotationGson.toJson(map1));
      return this.exposeAnnotationGson.toJson(map1);
    } 
    return null;
  }
  
  protected String printJSON(boolean result, Object entity) {
    Map<String, Object> map = new HashMap<>();
    map.put("result", Boolean.valueOf(result));
    map.put("data", entity);
    if (this.exposeAnnotationGson != null) {
      writePrint(this.exposeAnnotationGson.toJson(map));
      return this.exposeAnnotationGson.toJson(map);
    } 
    return null;
  }
  
  protected String printJSON(boolean result, JSONObject entity) {
    Map<String, Object> map = new HashMap<>();
    map.put("result", Boolean.valueOf(result));
    map.put("data", entity);
    if (this.exposeAnnotationGson != null) {
      writePrint(this.exposeAnnotationGson.toJson(map));
      return this.exposeAnnotationGson.toJson(map);
    } 
    return null;
  }
  
  protected void writePrint(String text) {
    PrintWriter write = null;
    try {
      HttpServletResponse response = getResponse();
      response.setHeader("Content-type", "text/html;charset=utf-8");
      response.setCharacterEncoding("UTF-8");
      response.setContentType("text/html");
      response.setHeader("Access-Control-Allow-Origin", "*");
      write = response.getWriter();
      write.print(text);
    } catch (IOException var12) {
      write = null;
      logger.info(var12.getMessage(), var12);
    } finally {
      if (null != write)
        try {
          write.close();
        } catch (Exception var11) {
          logger.error(var11.getMessage(), var11);
        }  
    } 
  }
  
  public ResponseType checkResponseType(String responseType) {
    ResponseType resp = null;
    if (responseType != null && !"".equals(responseType))
      resp = ResponseType.valueOf(responseType.toUpperCase()); 
    return resp;
  }
  
  @InitBinder
  protected void initBinder(WebDataBinder binder) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    binder.registerCustomEditor(Timestamp.class, (PropertyEditor)new CustomDateEditor(dateFormat, true));
  }
  
  public enum ResponseType {
    DEFAULT, JSON, VIEW, XML;
  }
}
