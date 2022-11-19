package com.example.demo.external;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.junit.jupiter.api.Test;

class HttpJsonPlaceholderApiTest {

  @Test
  void calls_api() {
    var interceptor = new HttpLoggingInterceptor(System.out::println)
        .setLevel(Level.BODY);

    var client = new OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .build();

    var properties = new JsonPlaceholderApiConfigurationProperties()
        .setHost("jsonplaceholder.typicode.com");

    var api = new HttpJsonPlaceholderApi(client, new ObjectMapper(), properties);
    var action = new GetTodoAction(1);
    var result = api.call(action);

    assertThat(result.isRight()).isTrue();
  }
}