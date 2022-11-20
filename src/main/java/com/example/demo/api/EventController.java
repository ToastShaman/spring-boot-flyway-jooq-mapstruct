package com.example.demo.api;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.example.demo.api.domain.CreateEventRequest;
import com.example.demo.api.domain.CreateEventResponse;
import com.example.demo.events.domain.EventId;
import com.example.demo.api.domain.GetEventResponse;
import com.example.demo.api.domain.UpdateEventRequest;
import com.example.demo.events.EventEngine;
import com.example.demo.storage.DataConflictException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EventController {

  private final EventEngine eventEngine;

  public EventController(EventEngine eventEngine) {
    this.eventEngine = eventEngine;
  }

  @GetMapping("/events/{id}")
  public ResponseEntity<?> get(@PathVariable long id) {
    return eventEngine
        .findById(new EventId(id))
        .map(GetEventResponse::from)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/events")
  public ResponseEntity<CreateEventResponse> create(@RequestBody CreateEventRequest request) {
    var event = request.asEvent();
    var created = eventEngine.create(event);
    var response = CreateEventResponse.from(created);
    return ResponseEntity.status(CREATED).body(response);
  }

  @PutMapping("/events/{id}")
  public ResponseEntity<?> update(
      @PathVariable long id,
      @RequestBody UpdateEventRequest request
  ) {
    try {
      var eventId = new EventId(id);
      var present = eventEngine.findById(eventId).isPresent();
      if (!present) {
        return ResponseEntity.notFound().build();
      }
      var event = request.asEvent(eventId);
      eventEngine.update(event);
      return ResponseEntity.status(NO_CONTENT).build();
    } catch (DataConflictException e) {
      return ResponseEntity.status(CONFLICT).build();
    }
  }
}

