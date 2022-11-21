package com.example.demo.storage.sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.events.domain.EventBuilder;
import com.example.demo.scenarios.domain.EventTitles;
import com.example.demo.scenarios.domain.Events;
import com.example.demo.storage.DataConflictException;
import com.example.demo.storage.EventsRepository;
import java.time.Clock;
import java.util.Random;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
public interface EventsRepositoryContractTest {

  EventsRepository repository();

  Clock clock();

  Faker faker = new Faker(new Random(38474));

  @Test
  default void can_insert_event() {
    assertThat(repository().insert(Events.random())).isNotNull();
  }

  @Test
  default void throws_exception_on_version_mismatch() throws DataConflictException {
    var first = Events.random();
    var saved = repository().insert(first);
    var update = EventBuilder.builder(saved).title(EventTitles.random()).build();
    var updated = repository().update(update);
    var anotherEventWithUpdates = EventBuilder.builder(saved).title(EventTitles.random()).build();

    assertThatThrownBy(() -> repository().update(anotherEventWithUpdates))
        .isInstanceOf(DataConflictException.class);
  }
}
