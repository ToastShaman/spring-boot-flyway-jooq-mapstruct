package com.example.demo.api.domain;

import com.example.demo.events.domain.Event;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public record GetEventResponse(
    EventId id,
    EventTitle title,
    EventStartDate startDate
) {

  public static final GetEventResponseMapper MAPPER = GetEventResponseMapper.INSTANCE;

  @Mapper
  public interface GetEventResponseMapper {

    GetEventResponseMapper INSTANCE = Mappers.getMapper(GetEventResponseMapper.class);

    GetEventResponse map(Event<EventId> record);
  }
}

