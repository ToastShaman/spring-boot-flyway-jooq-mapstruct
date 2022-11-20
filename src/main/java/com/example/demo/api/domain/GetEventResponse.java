package com.example.demo.api.domain;

import com.example.demo.events.domain.Event;
import com.example.demo.events.domain.EventId;
import com.example.demo.events.domain.EventStartDate;
import com.example.demo.events.domain.EventTitle;
import com.example.demo.events.domain.EventVersion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public record GetEventResponse(
    EventId id,
    EventVersion version,
    EventTitle title,
    EventStartDate startDate
) {

  public static GetEventResponse from(Event<EventId> record) {
    return GetEventResponseMapper.INSTANCE.map(record);
  }

  @Mapper
  public interface GetEventResponseMapper {

    GetEventResponseMapper INSTANCE = Mappers.getMapper(GetEventResponseMapper.class);

    GetEventResponse map(Event<EventId> record);
  }
}

