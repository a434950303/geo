package com.geostar.geoonline.entityserverhtml.servicepublish.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
  private static ApplicationContext context = null;
  
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }
  
  public static <T> T getBean(String beanName) {
    return (T)context.getBean(beanName);
  }
  
  public static <T> T getBean(Class<? extends T> clazz) {
    return (T)context.getBean(clazz);
  }
}
