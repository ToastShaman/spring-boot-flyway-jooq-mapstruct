package com.example.demo.events;

import com.example.demo.api.domain.EventId;
import com.example.demo.events.domain.Event;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EventNotifier implements EventListener {

  private final List<EventListener> listeners;

  public EventNotifier(List<EventListener> listeners) {
    this.listeners = listeners;
  }

  @Override
  public Event<EventId> onCreate(Event<EventId> event) {
    listeners.stream().parallel().forEach(it -> it.onCreate(event));
    return event;
  }
}
