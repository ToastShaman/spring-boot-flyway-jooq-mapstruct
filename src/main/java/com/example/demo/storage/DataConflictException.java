package com.example.demo.storage;

import java.io.IOException;

public class DataConflictException extends IOException {

  public DataConflictException(Throwable cause) {
    super(cause);
  }
}
