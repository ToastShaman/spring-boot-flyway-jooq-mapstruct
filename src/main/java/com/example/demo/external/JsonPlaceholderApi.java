package com.example.demo.external;

import io.vavr.control.Either;

public interface JsonPlaceholderApi {

  <R> Either<Exception, R> call(JsonPlaceholderApiAction<R> action);

}
