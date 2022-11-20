package com.example.demo.storage;

import com.example.demo.api.domain.EventId;
import com.example.demo.events.domain.Event;
import java.util.Optional;

public interface EventsRepository {

  Optional<Event<EventId>> findById(EventId id);

  Event<EventId> insert(Event<Void> event);

  Event<EventId> update(Event<EventId> event) throws DataConflictException;
}
