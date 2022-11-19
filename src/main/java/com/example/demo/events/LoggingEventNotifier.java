package com.example.demo.events;

import com.example.demo.api.domain.EventId;
import com.example.demo.events.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingEventNotifier implements EventListener {

  private final Logger log = LoggerFactory.getLogger(LoggingEventNotifier.class);

  @Override
  public Event<EventId> onCreate(Event<EventId> event) {
    log.info("Event Created: {}", event);
    return event;
  }
}
