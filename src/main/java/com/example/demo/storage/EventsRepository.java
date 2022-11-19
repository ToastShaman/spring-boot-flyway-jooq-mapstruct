package com.example.demo.storage;

import com.example.demo.api.domain.EventId;
import com.example.demo.storage.domain.EventRecord;
import java.util.Optional;

public interface EventsRepository {

  Optional<EventRecord<Long>> findById(EventId id);

  EventRecord<Long> insert(EventRecord<Void> event);

  EventRecord<Long> update(EventRecord<Long> event);
}
