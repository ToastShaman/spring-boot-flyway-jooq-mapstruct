package com.example.demo.api.domain;

import com.example.demo.events.domain.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public record CreateEventRequest(
    EventTitle title,
    EventStartDate startDate
) {

  public Event<Void> asEvent() {
    return CreateEventRequestMapper.INSTANCE.map(this);
  }

  @Mapper
  public interface CreateEventRequestMapper {

    CreateEventRequestMapper INSTANCE = Mappers.getMapper(CreateEventRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Event<Void> map(CreateEventRequest request);
  }
}
