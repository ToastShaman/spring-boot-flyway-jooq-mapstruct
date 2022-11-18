package com.example.demo.api.domain;

import com.example.demo.storage.domain.EventRecord;

public record CreateEventRequest(
    EventTitle title,
    EventStartDate startDate
) {

  public EventRecord<Void> asRecord() {
    return new EventRecord<>(null, title.unwrap(), startDate.toLocalDateTime());
  }
}
