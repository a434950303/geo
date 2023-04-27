package com.geostar.geoonline.entityserverhtml.servicepublish.util;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class ElasticsearchClient {
  private String ipPort;
  
  private String usr;
  
  private String pwd;
  
  private static String environment = "dev";
  
  public String getIpPort() {
    return this.ipPort;
  }
  
  public String getEnvironment() {
    return environment;
  }
  
  public RestHighLevelClient getClient(String ipPort, String usr, String pwd) {
    this.ipPort = ipPort;
    this.usr = usr;
    this.pwd = pwd;
    return getClient();
  }
  
  private RestHighLevelClient getClient() {
    BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
    basicCredentialsProvider.setCredentials(AuthScope.ANY, (Credentials)new UsernamePasswordCredentials(this.usr, this.pwd));
    String[] address = this.ipPort.split(":");
    String ip = address[0];
    int port = Integer.parseInt(address[1]);
    RestClientBuilder clientBuilder = RestClient.builder(new HttpHost[] { new HttpHost(ip, port, "http") });
    if (environment.equals("dev"))
      clientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.disableAuthCaching();
            return httpClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider);
          }); 
    return new RestHighLevelClient(clientBuilder);
  }
  
  public void destroy(RestHighLevelClient client) throws IOException {
    client.close();
  }
}
