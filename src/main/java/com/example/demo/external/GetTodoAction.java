package com.example.demo.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import okhttp3.Request;
import okhttp3.Response;

public class GetTodoAction implements JsonPlaceholderApiAction<Todo> {

  private final long id;

  public GetTodoAction(long id) {
    this.id = id;
  }

  @Override
  public Request toRequest() {
    return new Request.Builder()
        .url("https://example.com/todos/%d".formatted(id))
        .header("Accept", "application/json")
        .get()
        .build();
  }

  @Override
  @SuppressWarnings("ConstantConditions")
  public Either<Exception, Todo> fromResponse(Response response, ObjectMapper mapper) {
    try {
      if (response.isSuccessful()) {
        return Either.right(mapper.readValue(response.body().string(), Todo.class));
      }
      return Either.left(new RuntimeException("API returned: %d".formatted(response.code())));
    } catch (Exception e) {
      return Either.left(e);
    }
  }
}
