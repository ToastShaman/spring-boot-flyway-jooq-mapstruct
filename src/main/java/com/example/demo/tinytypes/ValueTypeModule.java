package com.example.demo.tinytypes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ValueTypeModule extends SimpleModule {

  public ValueTypeModule() {
    addSerializer(AbstractValueType.class, new JsonSerializer<>() {
      @Override
      public void serialize(AbstractValueType value,
          JsonGenerator gen,
          SerializerProvider serializers) throws IOException {
        gen.getCodec().writeValue(gen, value.unwrap());
      }
    });
  }
}
