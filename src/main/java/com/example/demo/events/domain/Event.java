package com.example.demo.events.domain;

import com.example.demo.api.domain.EventStartDate;
import com.example.demo.api.domain.EventTitle;
import com.example.demo.api.domain.EventVersion;

public record Event<R>(
    R id,
    EventVersion version,
    EventTitle title,
    EventStartDate startDate
) {

}
