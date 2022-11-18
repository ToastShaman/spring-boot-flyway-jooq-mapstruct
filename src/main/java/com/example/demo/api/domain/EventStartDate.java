package com.example.demo.api.domain;

import com.example.demo.tinytypes.validation.Validator;
import com.example.demo.tinytypes.values.InstantValue;
import java.time.Clock;
import java.time.Instant;
import java.util.Objects;

public class EventStartDate extends InstantValue {

  public EventStartDate(String value) {
    super(Instant.parse(value), Validator.withClock(Clock.systemUTC()).Future(), Objects::toString);
  }

  public static EventStartDate of(Instant instant) {
    return new EventStartDate(instant.toString());
  }
}
