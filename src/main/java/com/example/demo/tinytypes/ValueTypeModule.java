package com.example.demo.tinytypes;

import com.example.demo.tinytypes.values.InstantValue;
import com.example.demo.tinytypes.values.LongValue;
import com.example.demo.tinytypes.values.StringValue;
import com.example.demo.tinytypes.values.UUIDValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Function;

public class ValueTypeModule extends SimpleModule {

  public ValueTypeModule() {
    addSerializer(
        AbstractValueType.class,
        new JsonSerializer<>() {
          @Override
          public void serialize(
              AbstractValueType value, JsonGenerator gen, SerializerProvider serializers)
              throws IOException {
            gen.getCodec().writeValue(gen, value.unwrap());
          }
        });
  }

  public <T extends UUIDValue> ValueTypeModule uuid(Class<T> clazz, Function<UUID, T> fn) {
    addDeserializer(
        clazz,
        new JsonDeserializer<T>() {
          @Override
          public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return fn.apply(UUID.fromString(p.getText()));
          }
        });
    return this;
  }

  public <T extends StringValue> ValueTypeModule text(Class<T> clazz, Function<String, T> fn) {
    addDeserializer(
        clazz,
        new JsonDeserializer<>() {
          @Override
          public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return fn.apply(p.getText());
          }
        });
    return this;
  }

  public <T extends InstantValue> ValueTypeModule instant(Class<T> clazz, Function<Instant, T> fn) {
    addDeserializer(
        clazz,
        new JsonDeserializer<>() {
          @Override
          public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return fn.apply(Instant.parse(p.getText()));
          }
        });
    return this;
  }

  public <T extends LongValue> ValueTypeModule number(Class<T> clazz, Function<Long, T> fn) {
    addDeserializer(
        clazz,
        new JsonDeserializer<>() {
          @Override
          public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return fn.apply(new BigDecimal(p.getText()).longValueExact());
          }
        });
    return this;
  }
}
