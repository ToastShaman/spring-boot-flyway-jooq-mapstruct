package com.example.demo.api.domain;

import static java.time.ZoneOffset.UTC;

import com.example.demo.tinytypes.validation.Validator;
import com.example.demo.tinytypes.values.InstantValue;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

public class EventStartDate extends InstantValue {

  public EventStartDate(String value) {
    this(Instant.parse(value));
  }

  public EventStartDate(Instant value) {
    super(value, Validator.withClock(Clock.systemUTC()).Future(), Objects::toString);
  }

  public LocalDateTime asLocalDateTime() {
    return LocalDateTime.ofInstant(value, UTC);
  }

  public static EventStartDate of(Instant instant) {
    return new EventStartDate(instant.toString());
  }
}
