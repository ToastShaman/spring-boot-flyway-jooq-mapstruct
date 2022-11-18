package com.example.demo.scenarios.storage;

import com.example.demo.scenarios.storage.InMemoryEventsRepository;
import com.example.demo.storage.EventsRepository;
import com.example.demo.storage.sql.EventsRepositoryContractTest;

class InMemoryEventsRepositoryTest implements EventsRepositoryContractTest {

  @Override
  public EventsRepository repository() {
    return new InMemoryEventsRepository();
  }
}