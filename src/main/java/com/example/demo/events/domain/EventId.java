package com.example.demo.events.domain;

import com.example.demo.tinytypes.validation.Validator;
import com.example.demo.tinytypes.values.LongValue;

public class EventId extends LongValue {

  public EventId(long value) {
    super(value, Validator.Min(1L));
  }
}
