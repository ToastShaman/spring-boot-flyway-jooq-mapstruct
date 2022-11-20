package com.example.demo.events.domain;

import com.example.demo.tinytypes.validation.Validator;
import com.example.demo.tinytypes.values.NonBlankStringValue;

public class EventTitle extends NonBlankStringValue {

  public EventTitle(String value) {
    super(value, Validator.MaxLength(255));
  }
}
