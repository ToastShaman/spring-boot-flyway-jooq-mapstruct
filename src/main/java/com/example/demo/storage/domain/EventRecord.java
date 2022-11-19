package com.example.demo.storage.domain;

import static java.time.ZoneOffset.UTC;

import com.example.demo.api.domain.EventId;
import com.example.demo.api.domain.EventStartDate;
import com.example.demo.api.domain.EventTitle;
import com.example.demo.events.domain.Event;
import java.time.LocalDateTime;

public record EventRecord(
    Long id,
    String title,
    LocalDateTime startDate
) {

  public Event<EventId> asEvent() {
    return new Event<>(
        new EventId(id),
        new EventTitle(title),
        new EventStartDate(startDate.toInstant(UTC))
    );
  }
}
