package com.example.demo.api.domain;

import com.example.demo.storage.domain.EventRecord;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public record GetEventResponse(
    long title,
    LocalDateTime startDate
) {

  public static GetEventResponseMapper MAPPER = GetEventResponseMapper.INSTANCE;

  @Mapper
  public interface GetEventResponseMapper {

    GetEventResponseMapper INSTANCE = Mappers.getMapper(GetEventResponseMapper.class);

    GetEventResponse map(EventRecord<Long> record);
  }
}

