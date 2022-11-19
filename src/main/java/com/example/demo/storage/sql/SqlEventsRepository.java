package com.example.demo.storage.sql;

import static org.jooq.generated.Tables.EVENT;
import static org.jooq.generated.Tables.EVENT_HISTORY;

import com.example.demo.api.domain.EventId;
import com.example.demo.events.domain.Event;
import com.example.demo.storage.EventsRepository;
import com.example.demo.storage.domain.EventRecord;
import java.time.LocalDateTime;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Records;
import org.springframework.stereotype.Service;

@Service
public class SqlEventsRepository implements EventsRepository {

  private final DSLContext context;

  public SqlEventsRepository(DSLContext context) {
    this.context = context;
  }

  @Override
  public Optional<Event<EventId>> findById(EventId id) {
    return context
        .select(EVENT.EVENT_ID, EVENT.TITLE, EVENT.START_DATE)
        .from(EVENT)
        .where(EVENT.EVENT_ID.eq(id.unwrap()).and(EVENT.DELETED.ne(true)))
        .fetchOptional(Records.mapping(EventRecord::new))
        .map(EventRecord::asEvent);
  }

  @Override
  public Event<EventId> insert(Event<Void> event) {
    return context.transactionResult(tx -> {
      var record = tx.dsl()
          .insertInto(EVENT)
          .set(EVENT.TITLE, event.title().unwrap())
          .set(EVENT.START_DATE, event.startDate().asLocalDateTime())
          .returningResult(EVENT.EVENT_ID, EVENT.TITLE, EVENT.START_DATE)
          .fetchSingle(Records.mapping(EventRecord::new));

      tx.dsl()
          .insertInto(EVENT_HISTORY)
          .set(EVENT_HISTORY.CHANGE_TYPE, "INSERT")
          .set(EVENT_HISTORY.CHANGE_TIMESTAMP, LocalDateTime.now())
          .set(EVENT_HISTORY.USER_EMAIL, "foo@bar.com")
          .set(EVENT_HISTORY.EVENT_ID, record.id())
          .set(EVENT_HISTORY.TITLE, event.title().unwrap())
          .set(EVENT_HISTORY.START_DATE, event.startDate().asLocalDateTime())
          .execute();

      return record.asEvent();
    });
  }

  @Override
  public Event<EventId> update(Event<EventId> event) {
    return context.transactionResult(tx -> {
      var record = tx.dsl()
          .update(EVENT)
          .set(EVENT.TITLE, event.title().unwrap())
          .set(EVENT.START_DATE, event.startDate().asLocalDateTime())
          .where(EVENT.EVENT_ID.eq(event.id().unwrap()).and(EVENT.DELETED.ne(true)))
          .returningResult(EVENT.EVENT_ID, EVENT.TITLE, EVENT.START_DATE)
          .fetchSingle(Records.mapping(EventRecord::new));

      tx.dsl()
          .insertInto(EVENT_HISTORY)
          .set(EVENT_HISTORY.CHANGE_TYPE, "UPDATE")
          .set(EVENT_HISTORY.CHANGE_TIMESTAMP, LocalDateTime.now())
          .set(EVENT_HISTORY.USER_EMAIL, "foo@bar.com")
          .set(EVENT_HISTORY.EVENT_ID, event.id().unwrap())
          .set(EVENT_HISTORY.TITLE, event.title().unwrap())
          .set(EVENT_HISTORY.START_DATE, event.startDate().asLocalDateTime())
          .execute();

      return record.asEvent();
    });
  }
}
