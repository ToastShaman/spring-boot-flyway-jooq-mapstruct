package com.example.demo.api.domain;

import com.example.demo.events.domain.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public record UpdateEventRequest(
    EventTitle title,
    EventStartDate startDate,
    EventVersion version
) {

  public Event<EventId> asEvent(EventId eventId) {
    return UpdateEventRequestMapper.INSTANCE.map(this, eventId);
  }

  @Mapper
  interface UpdateEventRequestMapper {

    UpdateEventRequestMapper INSTANCE = Mappers.getMapper(UpdateEventRequestMapper.class);

    @Mapping(source = "eventId", target = "id")
    Event<EventId> map(UpdateEventRequest request, EventId eventId);
  }
}
