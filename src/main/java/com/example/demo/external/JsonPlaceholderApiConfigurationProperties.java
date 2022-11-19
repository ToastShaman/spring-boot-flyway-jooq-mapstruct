package com.example.demo.external;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
public class JsonPlaceholderApiConfigurationProperties {

  private String host = "jsonplaceholder.typicode.com";

  public String getHost() {
    return host;
  }

  public JsonPlaceholderApiConfigurationProperties setHost(String host) {
    this.host = host;
    return this;
  }
}
