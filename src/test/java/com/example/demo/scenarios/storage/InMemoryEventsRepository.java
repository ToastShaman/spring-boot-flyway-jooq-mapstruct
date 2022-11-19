package com.example.demo.scenarios.storage;

import com.example.demo.api.domain.EventId;
import com.example.demo.events.domain.Event;
import com.example.demo.storage.EventsRepository;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryEventsRepository implements EventsRepository {

  private final AtomicLong nextId = new AtomicLong(0);

  private final Map<EventId, Event<EventId>> table = new TreeMap<>();

  @Override
  public Optional<Event<EventId>> findById(EventId id) {
    return Optional.ofNullable(table.get(id));
  }

  @Override
  public Event<EventId> insert(Event<Void> event) {
    var eventId = EventId.of(nextId.incrementAndGet());
    var newEvent = event.withId(eventId);
    table.put(eventId, newEvent);
    return newEvent;
  }

  @Override
  public Event<EventId> update(Event<EventId> event) {
    table.replace(event.id(), event);
    return event;
  }
}
