package com.geostar.geoonline.entityserverhtml.servicepublish.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SecretConfigUtil {
  private static final String resourcePath = "secret-key.properties";
  
  private String privateKey;
  
  private String publicKey;
  
  public SecretConfigUtil() {
    loadProperties();
  }
  
  public void loadProperties() {
    Properties properties = new Properties();
    InputStream ris = Thread.currentThread().getContextClassLoader().getResourceAsStream("secret-key.properties");
    try {
      properties.load(ris);
      this.privateKey = properties.getProperty("privateKey");
      this.publicKey = properties.getProperty("publicKey");
      ris.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public String getPrivateKey() {
    return this.privateKey;
  }
  
  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }
  
  public String getPublicKey() {
    return this.publicKey;
  }
  
  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }
}
