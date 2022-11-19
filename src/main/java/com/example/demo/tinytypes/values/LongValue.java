package com.example.demo.tinytypes.values;

import com.example.demo.tinytypes.AbstractValueType;
import com.example.demo.tinytypes.validation.Validator;

public class LongValue extends AbstractValueType<Long> {

  public LongValue(Long value, Validator<Long> validator) {
    super(value, validator, Object::toString);
  }
}
