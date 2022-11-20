package com.example.demo.api.domain;

import com.example.demo.events.domain.Event;

public record CreateEventResponse(EventId id) {

  public static CreateEventResponse from(Event<EventId> event) {
    return new CreateEventResponse(event.id());
  }
}

