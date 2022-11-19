package com.example.demo.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import okhttp3.Request;
import okhttp3.Response;

public interface JsonPlaceholderApiAction<R> {

  Request toRequest();

  Either<Exception, R> fromResponse(Response response, ObjectMapper mapper);
}
