package com.example.demo.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.stereotype.Service;

@Service
public class HttpJsonPlaceholderApi implements JsonPlaceholderApi {

  private final OkHttpClient client;
  private final ObjectMapper mapper;

  public HttpJsonPlaceholderApi(
      OkHttpClient client,
      ObjectMapper mapper,
      JsonPlaceholderApiConfigurationProperties properties
  ) {
    this.mapper = mapper;
    this.client = client.newBuilder().addInterceptor(rewriteHostWith(properties)).build();
  }

  private Interceptor rewriteHostWith(JsonPlaceholderApiConfigurationProperties properties) {
    return chain -> {
      var request = chain.request();
      var newUrl = request.url().newBuilder().host(properties.getHost()).build();
      var modifiedRequest = request.newBuilder().url(newUrl).build();
      return chain.proceed(modifiedRequest);
    };
  }

  @Override
  public <R> Either<Exception, R> call(JsonPlaceholderApiAction<R> action) {
    try (Response response = client.newCall(action.toRequest()).execute()) {
      return action.fromResponse(response, mapper);
    } catch (Exception e) {
      return Either.left(e);
    }
  }
}
