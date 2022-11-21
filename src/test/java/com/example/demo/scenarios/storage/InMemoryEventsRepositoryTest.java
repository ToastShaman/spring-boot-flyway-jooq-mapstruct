package com.example.demo.scenarios.storage;

import com.example.demo.storage.EventsRepository;
import com.example.demo.storage.sql.EventsRepositoryContractTest;
import java.time.Clock;

class InMemoryEventsRepositoryTest implements EventsRepositoryContractTest {

  @Override
  public EventsRepository repository() {
    return new InMemoryEventsRepository();
  }

  @Override
  public Clock clock() {
    return Clock.systemUTC();
  }
}