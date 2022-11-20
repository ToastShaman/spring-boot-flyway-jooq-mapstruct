package com.example.demo.scenarios.domain;

import com.example.demo.events.domain.EventTitle;
import java.util.Random;
import net.datafaker.Faker;

public final class EventTitles {

  private EventTitles() {
  }

  public static EventTitle random() {
    return random(new Faker(new Random(5838)));
  }

  public static EventTitle random(Faker faker) {
    var title = "%s %s (%s) vs %s %s (%s)".formatted(
        faker.name().firstName(),
        faker.name().lastName(),
        faker.country().countryCode3(),
        faker.name().firstName(),
        faker.name().lastName(),
        faker.country().countryCode3()
    );
    return new EventTitle(title);
  }
}
