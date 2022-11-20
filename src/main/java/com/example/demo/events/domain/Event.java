package com.example.demo.events.domain;

public record Event<R>(
    R id,
    EventVersion version,
    EventTitle title,
    EventStartDate startDate
) {

}
