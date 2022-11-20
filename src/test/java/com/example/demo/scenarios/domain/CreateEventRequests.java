package com.example.demo.scenarios.domain;

import com.example.demo.api.domain.CreateEventRequest;
import com.example.demo.api.domain.FutureEventStartDate;
import com.example.demo.events.domain.EventTitle;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import net.datafaker.Faker;

public final class CreateEventRequests {

  private CreateEventRequests() {
  }

  public static CreateEventRequest random() {
    return random(new Faker(new Random(12323)), Clock.systemUTC());
  }

  public static CreateEventRequest random(Faker faker, Clock clock) {
    var title = "%s %s (%s) vs %s %s (%s)".formatted(
        faker.name().firstName(),
        faker.name().lastName(),
        faker.country().countryCode3(),
        faker.name().firstName(),
        faker.name().lastName(),
        faker.country().countryCode3()
    );

    return new CreateEventRequest(
        new EventTitle(title),
        FutureEventStartDate.of(Instant.now(clock).plus(Duration.ofDays(1)))
    );
  }
}
