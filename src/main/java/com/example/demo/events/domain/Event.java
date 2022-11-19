package com.example.demo.events.domain;

import com.example.demo.api.domain.EventId;
import com.example.demo.api.domain.EventStartDate;
import com.example.demo.api.domain.EventTitle;

public record Event<R>(
    R id,
    EventTitle title,
    EventStartDate startDate
) {

  public Event<EventId> withId(EventId id) {
    return new Event<>(id, title, startDate);
  }
}
