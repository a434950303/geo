package com.geostar.geoonline.entityserverhtml.framework.common.sys;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextRegister implements ApplicationContextAware {
  private static ApplicationContext APPLICATION_CONTEXT;
  
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    APPLICATION_CONTEXT = applicationContext;
  }
  
  public static ApplicationContext getApplicationContext() {
    return APPLICATION_CONTEXT;
  }
}
