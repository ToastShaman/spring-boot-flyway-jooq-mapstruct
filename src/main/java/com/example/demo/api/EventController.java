package com.example.demo.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.example.demo.api.domain.CreateEventRequest;
import com.example.demo.api.domain.CreateEventResponse;
import com.example.demo.api.domain.GetEventResponse;
import com.example.demo.storage.EventsRepository;
import com.example.demo.storage.domain.EventRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        .findById(id)
        .map(GetEventResponse.MAPPER::map)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/events")
  public ResponseEntity<CreateEventResponse> create(@RequestBody CreateEventRequest request) {
    EventRecord<Long> inserted = repository.insert(request.asRecord());
    CreateEventResponse response = new CreateEventResponse(inserted.id());
    return ResponseEntity.status(CREATED).body(response);
  }
}

