package com.example.demo.api.domain;

import com.example.demo.events.domain.Event;
import com.example.demo.events.domain.EventStartDate;
import com.example.demo.events.domain.EventTitle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public record CreateEventRequest(
    EventTitle title,
    FutureEventStartDate startDate
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

    default EventStartDate map(FutureEventStartDate date) {
      return new EventStartDate(date.unwrap());
    }
  }
}
