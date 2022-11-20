package com.example.demo.scenarios.domain;

import com.example.demo.events.domain.EventStartDate;
import com.example.demo.events.domain.EventTitle;
import com.example.demo.events.domain.EventVersion;
import com.example.demo.events.domain.Event;
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
    var title = "%s %s (%s) vs %s %s (%s)".formatted(
        faker.name().firstName(),
        faker.name().lastName(),
        faker.country().countryCode3(),
        faker.name().firstName(),
        faker.name().lastName(),
        faker.country().countryCode3()
    );

    return new Event<>(
        null,
        new EventVersion(0),
        new EventTitle(title),
        EventStartDate.of(Instant.now(clock).plus(Duration.ofDays(1)))
    );
  }
}
