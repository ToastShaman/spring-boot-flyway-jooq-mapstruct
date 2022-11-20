package com.example.demo.api.domain;

import com.example.demo.tinytypes.validation.Validator;
import com.example.demo.tinytypes.values.InstantValue;
import java.time.Clock;
import java.time.Instant;
import java.util.Objects;

public class FutureEventStartDate extends InstantValue {

  public FutureEventStartDate(String value) {
    this(Instant.parse(value));
  }

  public FutureEventStartDate(Instant value) {
    super(value, Validator.withClock(Clock.systemUTC()).Future(), Objects::toString);
  }

  public static FutureEventStartDate of(Instant instant) {
    return new FutureEventStartDate(instant.toString());
  }
}
