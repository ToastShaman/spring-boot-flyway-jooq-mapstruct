package com.example.demo.events;

import com.example.demo.api.domain.EventId;
import com.example.demo.events.domain.Event;
import java.util.Optional;

public interface EventEngine {

  Optional<Event<EventId>> findById(EventId id);

  Event<EventId> create(Event<Void> event);

  Event<EventId> update(Event<EventId> event);
}
