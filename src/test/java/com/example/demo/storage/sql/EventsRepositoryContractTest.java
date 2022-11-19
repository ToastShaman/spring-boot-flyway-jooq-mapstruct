package com.example.demo.storage.sql;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.scenarios.domain.Events;
import com.example.demo.storage.EventsRepository;
import org.junit.jupiter.api.Test;

public interface EventsRepositoryContractTest {

  EventsRepository repository();

  @Test
  default void can_insert_event() {
    var inserted = repository().insert(Events.random());
    assertThat(inserted).isNotNull();
  }
}
