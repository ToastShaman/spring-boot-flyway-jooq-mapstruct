package com.example.demo.tinytypes.values;

import static com.example.demo.tinytypes.validation.Validator.AlwaysValid;

import com.example.demo.tinytypes.AbstractValueType;
import java.util.UUID;

public class UUIDValue extends AbstractValueType<UUID> {

  public UUIDValue(UUID value) {
    super(value, AlwaysValid(), Object::toString);
  }
}
