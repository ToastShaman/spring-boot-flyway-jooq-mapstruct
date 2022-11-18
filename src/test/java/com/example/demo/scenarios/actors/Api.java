package com.example.demo.scenarios.actors;

import com.example.demo.api.EventController;
import com.example.demo.api.domain.CreateEventRequest;
import com.example.demo.api.domain.CreateEventResponse;
import com.example.demo.storage.EventsRepository;

public class Api {

  private final EventsRepository repository;

  public Api(EventsRepository repository) {
    this.repository = repository;
  }

  public CreateEventResponse createEvent(CreateEventRequest request) {
    return new EventController(repository).create(request).getBody();
  }
}
