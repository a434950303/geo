package com.geostar.geoonline.entityserverhtml.servicepublish.basic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Value("${swagger.open}")
  private Boolean swaggerOpen;
  
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    if (this.swaggerOpen.booleanValue()) {
      registry.addResourceHandler(new String[] { "doc.html" }).addResourceLocations(new String[] { "classpath:/META-INF/resources/" });
      registry.addResourceHandler(new String[] { "/webjars/**" }).addResourceLocations(new String[] { "classpath:/META-INF/resources/webjars/" });
    } 
    registry.addResourceHandler(new String[] { "/**" }).addResourceLocations(new String[] { "classpath:/templates/" }).addResourceLocations(new String[] { "classpath:/static/" }).addResourceLocations(new String[] { "classpath:/static/cms/" });
  }
  
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:index.html");
    registry.setOrder(-2147483648);
  }
}
