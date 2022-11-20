package com.example.demo.events.domain;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record Event<R>(
    R id,
    EventVersion version,
    EventTitle title,
    EventStartDate startDate
) {

}
