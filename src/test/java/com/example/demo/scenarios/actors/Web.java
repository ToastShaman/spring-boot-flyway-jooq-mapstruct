package com.example.demo.scenarios.actors;

import com.example.demo.api.domain.CreateEventRequest;
import com.example.demo.api.domain.CreateEventResponse;

public class Web {

  private final Api api;

  public Web(Api api) {
    this.api = api;
  }

  public CreateEventResponse createEvent(CreateEventRequest request) {
    return api.createEvent(request);
  }
}
