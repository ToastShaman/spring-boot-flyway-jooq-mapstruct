package com.example.demo.storage.sql;

import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2;

import com.example.demo.storage.EventsRepository;
import java.time.Clock;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;

@JooqTest
@AutoConfigureTestDatabase(connection = H2)
class SqlEventsRepositoryTest implements EventsRepositoryContractTest {

  @Autowired
  private DSLContext dslContext;

  @Override
  public EventsRepository repository() {
    return new SqlEventsRepository(dslContext, clock());
  }

  @Override
  public Clock clock() {
    return Clock.systemUTC();
  }
}