package com.example.demo.api.domain;

import com.example.demo.events.domain.Event;
import com.example.demo.events.domain.EventId;

public record CreateEventResponse(EventId id) {

  public static CreateEventResponse from(Event<EventId> event) {
    return new CreateEventResponse(event.id());
  }
}

