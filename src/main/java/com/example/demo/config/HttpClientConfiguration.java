package com.example.demo.config;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

  @Bean
  public OkHttpClient httpClient(HttpClientConfigurationProperties properties) {
    return new OkHttpClient.Builder()
        .addInterceptor(new HttpLoggingInterceptor(System.out::println).setLevel(Level.BODY))
        .connectTimeout(properties.getTimeout())
        .callTimeout(properties.getTimeout())
        .readTimeout(properties.getTimeout())
        .connectionPool(new ConnectionPool(properties.maxIdleConnections, 5, TimeUnit.MINUTES))
        .build();
  }

  @Configuration
  @ConfigurationProperties(prefix = "http")
  public static class HttpClientConfigurationProperties {
    private Duration timeout = Duration.ofMinutes(5);
    private int maxIdleConnections = 10;

    public Duration getTimeout() {
      return timeout;
    }

    public void setTimeout(Duration timeout) {
      this.timeout = timeout;
    }

    public int getMaxIdleConnections() {
      return maxIdleConnections;
    }

    public void setMaxIdleConnections(int maxIdleConnections) {
      this.maxIdleConnections = maxIdleConnections;
    }
  }
}
