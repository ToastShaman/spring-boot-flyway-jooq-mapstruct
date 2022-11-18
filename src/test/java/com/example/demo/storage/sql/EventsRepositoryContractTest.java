package com.example.demo.storage.sql;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.storage.EventsRepository;
import com.example.demo.storage.domain.EventRecord;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public interface EventsRepositoryContractTest {

  EventsRepository repository();

  @Test
  default void can_insert_event() {
    var record = new EventRecord<Void>(null, "A vs B", LocalDateTime.now());
    var inserted = repository().insert(record);
    assertThat(inserted).isNotNull();
  }
}
