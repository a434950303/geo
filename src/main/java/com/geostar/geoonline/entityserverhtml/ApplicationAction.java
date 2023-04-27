package com.geostar.geoonline.entityserverhtml;

import com.geostar.geoonline.entityserverhtml.framework.common.sys.Geolicense;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class})
public class ApplicationAction extends SpringBootServletInitializer {
  public static void main(String[] args) throws Exception {
    Geolicense geolicense = new Geolicense();
    SpringApplication.run(ApplicationAction.class, args);
  }
  
  protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
    return applicationBuilder.sources(new Class[] { ApplicationAction.class });
  }
}
