package com.example.demo.api.domain;

import com.example.demo.events.domain.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public record UpdateEventRequest(
    EventTitle title,
    EventStartDate startDate
) {

  public Event<EventId> asEvent(EventId eventId) {
    return UpdateEventRequestMapper.INSTANCE.map(this).withId(eventId);
  }

  @Mapper
  interface UpdateEventRequestMapper {

    UpdateEventRequestMapper INSTANCE = Mappers.getMapper(UpdateEventRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    Event<Void> map(UpdateEventRequest request);
  }
}
