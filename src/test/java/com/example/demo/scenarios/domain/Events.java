package com.example.demo.scenarios.domain;

import com.example.demo.events.domain.Event;
import com.example.demo.events.domain.EventBuilder;
import com.example.demo.events.domain.EventStartDate;
import com.example.demo.events.domain.EventVersion;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import net.datafaker.Faker;

public final class Events {

  private Events() {
  }

  public static Event<Void> random() {
    return random(new Faker(new Random(383743)), Clock.systemUTC());
  }

  public static Event<Void> random(Faker faker, Clock clock) {
    Instant startDate = Instant.now(clock).plus(Duration.ofDays(1));

    return EventBuilder.<Void>builder()
        .id(null)
        .version(new EventVersion(0))
        .title(EventTitles.random(faker))
        .startDate(new EventStartDate(startDate))
        .build();
  }
}
