package com.example.demo.events.domain;

import static java.time.ZoneOffset.UTC;

import com.example.demo.tinytypes.validation.Validator;
import com.example.demo.tinytypes.values.InstantValue;
import java.time.Instant;
import java.time.LocalDateTime;

public class EventStartDate extends InstantValue {

  public EventStartDate(String value) {
    this(Instant.parse(value));
  }

  public EventStartDate(Instant value) {
    super(value, Validator.AlwaysValid(), Object::toString);
  }

  public LocalDateTime asLocalDateTime() {
    return LocalDateTime.ofInstant(value, UTC);
  }

}
