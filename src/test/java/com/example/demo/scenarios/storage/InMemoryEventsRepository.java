package com.example.demo.scenarios.storage;

import com.example.demo.api.domain.EventId;
import com.example.demo.storage.EventsRepository;
import com.example.demo.storage.domain.EventRecord;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

public class InMemoryEventsRepository implements EventsRepository {

  private final Map<Long, EventRecord<Long>> table = new TreeMap<>();

  @Override
  public Optional<EventRecord<Long>> findById(EventId id) {
    return Optional.ofNullable(table.get(id.unwrap()));
  }

  @Override
  public EventRecord<Long> insert(EventRecord<Void> event) {
    EventRecord<Long> record = EventRecordTestMapper.INSTANCE.map(event);
    table.put(record.id(), record);
    return record;
  }

  @Override
  public EventRecord<Long> update(EventRecord<Long> event) {
    return null;
  }

  @Mapper
  interface EventRecordTestMapper {
    AtomicLong index = new AtomicLong(0);

    EventRecordTestMapper INSTANCE = Mappers.getMapper(EventRecordTestMapper.class);

    EventRecord<Long> map(EventRecord<Void> event);

    default Long map(Void ignoredValue) {
      return index.incrementAndGet();
    }
  }
}
