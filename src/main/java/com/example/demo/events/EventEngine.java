package com.example.demo.events;

import com.example.demo.events.domain.EventId;
import com.example.demo.events.domain.Event;
import com.example.demo.storage.DataConflictException;
import java.util.Optional;

public interface EventEngine {

  Optional<Event<EventId>> findById(EventId id);

  Event<EventId> create(Event<Void> event);

  Event<EventId> update(Event<EventId> event) throws DataConflictException;
}
