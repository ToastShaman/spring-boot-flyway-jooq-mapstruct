package com.example.demo.scenarios.actors;

import com.example.demo.api.EventController;
import com.example.demo.api.domain.CreateEventRequest;
import com.example.demo.api.domain.CreateEventResponse;
import com.example.demo.events.EventEngine;

public class Api {

  private final EventEngine eventEngine;

  public Api(EventEngine eventEngine) {
    this.eventEngine = eventEngine;
  }

  public CreateEventResponse createEvent(CreateEventRequest request) {
    return new EventController(eventEngine).create(request).getBody();
  }
}
