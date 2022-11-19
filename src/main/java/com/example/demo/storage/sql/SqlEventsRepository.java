package com.example.demo.storage.sql;

import static org.jooq.generated.Tables.EVENT;
import static org.jooq.generated.Tables.EVENT_HISTORY;

import com.example.demo.api.domain.EventId;
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
  public Optional<EventRecord<Long>> findById(EventId id) {
    return context
        .select(EVENT.EVENT_ID, EVENT.TITLE, EVENT.START_DATE)
        .from(EVENT)
        .where(EVENT.EVENT_ID.eq(id.unwrap()))
        .fetchOptional(Records.mapping(EventRecord::new));
  }

  @Override
  public EventRecord<Long> insert(EventRecord<Void> event) {
    return context.transactionResult(tx -> {
      var record = tx.dsl()
          .insertInto(EVENT)
          .set(EVENT.TITLE, event.title())
          .set(EVENT.START_DATE, event.startDate())
          .returningResult(EVENT.EVENT_ID, EVENT.TITLE, EVENT.START_DATE)
          .fetchOne(Records.mapping(EventRecord::new));

      tx.dsl()
          .insertInto(EVENT_HISTORY)
          .set(EVENT_HISTORY.CHANGE_TYPE, "INSERT")
          .set(EVENT_HISTORY.CHANGE_TIMESTAMP, LocalDateTime.now())
          .set(EVENT_HISTORY.USER_EMAIL, "foo@bar.com")
          .set(EVENT_HISTORY.EVENT_ID, record.id())
          .set(EVENT_HISTORY.TITLE, event.title())
          .set(EVENT_HISTORY.START_DATE, event.startDate())
          .execute();

      return record;
    });
  }

  @Override
  public EventRecord<Long> update(EventRecord<Long> event) {
    return context.transactionResult(tx -> {
      var record = tx.dsl()
          .update(EVENT)
          .set(EVENT.TITLE, event.title())
          .set(EVENT.START_DATE, event.startDate())
          .where(EVENT.EVENT_ID.eq(event.id()))
          .returningResult(EVENT.EVENT_ID, EVENT.TITLE, EVENT.START_DATE)
          .fetchOne(Records.mapping(EventRecord::new));

      tx.dsl()
          .insertInto(EVENT_HISTORY)
          .set(EVENT_HISTORY.CHANGE_TYPE, "UPDATE")
          .set(EVENT_HISTORY.CHANGE_TIMESTAMP, LocalDateTime.now())
          .set(EVENT_HISTORY.USER_EMAIL, "foo@bar.com")
          .set(EVENT_HISTORY.EVENT_ID, event.id())
          .set(EVENT_HISTORY.TITLE, event.title())
          .set(EVENT_HISTORY.START_DATE, event.startDate())
          .execute();

      return record;
    });
  }
}
