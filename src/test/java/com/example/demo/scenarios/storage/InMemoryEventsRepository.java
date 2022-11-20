package com.example.demo.scenarios.storage;

import com.example.demo.events.domain.EventId;
import com.example.demo.events.domain.Event;
import com.example.demo.storage.EventsRepository;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public class InMemoryEventsRepository implements EventsRepository {

  private final AtomicLong nextId = new AtomicLong(0);

  private final Map<EventId, Event<EventId>> table = new TreeMap<>();

  @Override
  public Optional<Event<EventId>> findById(EventId id) {
    return Optional.ofNullable(table.get(id));
  }

  @Override
  public Event<EventId> insert(Event<Void> event) {
    var eventId = new EventId(nextId.incrementAndGet());
    var newEvent = TestEventMapper.INSTANCE.map(event, eventId);
    table.put(eventId, newEvent);
    return newEvent;
  }

  @Override
  public Event<EventId> update(Event<EventId> event) {
    table.replace(event.id(), event);
    return event;
  }

  @Mapper
  interface TestEventMapper {

    TestEventMapper INSTANCE = Mappers.getMapper(TestEventMapper.class);

    @Mapping(source = "eventId", target = "id")
    Event<EventId> map(Event<Void> event, EventId eventId);
  }
}
