package com.example.demo.api.domain;

import com.example.demo.storage.domain.EventRecord;

public record UpdateEventRequest(
    EventTitle title,
    EventStartDate startDate
) {

  public EventRecord<Long> asRecord(EventId eventId) {
    return new EventRecord<>(eventId.unwrap(), title.unwrap(), startDate.toLocalDateTime());
  }
}
