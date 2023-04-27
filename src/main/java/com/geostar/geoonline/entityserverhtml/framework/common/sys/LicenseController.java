package com.geostar.geoonline.entityserverhtml.framework.common.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/license"})
public class LicenseController {
  private static final Logger log = LoggerFactory.getLogger(LicenseController.class);
  
  @Autowired
  private Geolicense geolicense;
  
  @Value("false")
  private String licenseDisabled;
  
  @ResponseBody
  @GetMapping({"/checkLicense"})
  public JsonResult checkLicense() {
    JsonResult result = new JsonResult();
    try {
      if (!Boolean.parseBoolean(this.licenseDisabled)) {
        this.geolicense.licensecheck();
        result.setStatus(true);
        result.setCode("200");
        result.setMessage("已授权");
      } else {
        result.setStatus(false);
        result.setCode("200");
        result.setMessage("授权未启用");
      } 
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      result.setStatus(false);
      result.setCode("403");
      result.setMessage(e.getMessage());
    } 
    return result;
  }
}
