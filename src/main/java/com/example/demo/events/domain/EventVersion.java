package com.example.demo.events.domain;

import com.example.demo.tinytypes.validation.Validator;
import com.example.demo.tinytypes.values.LongValue;

public class EventVersion extends LongValue {

  public EventVersion(long value) {
    super(value, Validator.AlwaysValid());
  }

  public EventVersion inc() {
    return new EventVersion(value + 1);
  }
}
