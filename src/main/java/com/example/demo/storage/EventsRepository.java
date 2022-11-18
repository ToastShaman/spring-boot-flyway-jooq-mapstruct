package com.example.demo.storage;

import com.example.demo.storage.domain.EventRecord;
import java.util.Optional;

public interface EventsRepository {

  Optional<EventRecord<Long>> findById(long id);

  EventRecord<Long> insert(EventRecord<Void> event);

}
