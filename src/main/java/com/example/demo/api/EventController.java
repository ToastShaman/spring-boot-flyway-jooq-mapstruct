package com.example.demo.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import com.example.demo.api.domain.CreateEventRequest;
import com.example.demo.api.domain.CreateEventResponse;
import com.example.demo.api.domain.EventId;
import com.example.demo.api.domain.GetEventResponse;
import com.example.demo.api.domain.UpdateEventRequest;
import com.example.demo.storage.EventsRepository;
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

  private final EventsRepository repository;

  public EventController(EventsRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/events/{id}")
  public ResponseEntity<?> get(@PathVariable long id) {
    return repository
        .findById(EventId.of(id))
        .map(GetEventResponse.MAPPER::map)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/events")
  public ResponseEntity<CreateEventResponse> create(@RequestBody CreateEventRequest request) {
    var inserted = repository.insert(request.asRecord());
    var response = new CreateEventResponse(inserted.id());
    return ResponseEntity.status(CREATED).body(response);
  }

  @PutMapping("/events/{id}")
  public ResponseEntity<?> update(@PathVariable long id, @RequestBody UpdateEventRequest request) {
    var eventId = EventId.of(id);

    boolean present = repository.findById(eventId).isPresent();
    if (!present) {
      return ResponseEntity.notFound().build();
    }

    repository.update(request.asRecord(eventId));

    return ResponseEntity.status(NO_CONTENT).build();
  }
}

