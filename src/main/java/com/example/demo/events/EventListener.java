package com.example.demo.events;

import com.example.demo.events.domain.EventId;
import com.example.demo.events.domain.Event;

public interface EventListener {

  Event<EventId> onCreate(Event<EventId> event);

}
