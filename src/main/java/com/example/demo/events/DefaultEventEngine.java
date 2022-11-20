package com.example.demo.events;

import com.example.demo.events.domain.EventId;
import com.example.demo.events.domain.Event;
import com.example.demo.storage.DataConflictException;
import com.example.demo.storage.EventsRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DefaultEventEngine implements EventEngine {

  private final EventsRepository repository;
  private final EventNotifier notifier;

  public DefaultEventEngine(
      EventsRepository repository,
      EventNotifier notifier
  ) {
    this.repository = repository;
    this.notifier = notifier;
  }

  @Override
  public Optional<Event<EventId>> findById(EventId id) {
    return repository.findById(id);
  }

  @Override
  public Event<EventId> create(Event<Void> event) {
    return notifier.onCreate(repository.insert(event));
  }

  @Override
  public Event<EventId> update(Event<EventId> event) throws DataConflictException {
    return repository.update(event);
  }
}
