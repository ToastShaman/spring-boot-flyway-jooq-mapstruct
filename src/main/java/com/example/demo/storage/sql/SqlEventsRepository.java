package com.example.demo.storage.sql;

import static org.jooq.generated.Tables.EVENT;
import static org.jooq.generated.Tables.EVENT_HISTORY;

import com.example.demo.api.domain.EventId;
import com.example.demo.events.domain.Event;
import com.example.demo.storage.DataConflictException;
import com.example.demo.storage.EventsRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
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
        .select()
        .from(EVENT)
        .where(EVENT.EVENT_ID.eq(id.unwrap()).and(EVENT.DELETED.ne(true)))
        .fetchOptional(EventRecordMapper::map);
  }

  @Override
  public Event<EventId> insert(Event<Void> event) {
    return context.transactionResult(tx -> {
      var record = tx.dsl()
          .insertInto(EVENT)
          .set(EVENT.TITLE, event.title().unwrap())
          .set(EVENT.START_DATE, event.startDate().asLocalDateTime())
          .returningResult()
          .fetchSingle(EventRecordMapper::map);

      tx.dsl()
          .insertInto(EVENT_HISTORY)
          .set(EVENT_HISTORY.CHANGE_TYPE, "INSERT")
          .set(EVENT_HISTORY.CHANGE_TIMESTAMP, LocalDateTime.now())
          .set(EVENT_HISTORY.USER_EMAIL, "foo@bar.com")
          .set(EVENT_HISTORY.EVENT_ID, record.id().unwrap())
          .set(EVENT_HISTORY.TITLE, event.title().unwrap())
          .set(EVENT_HISTORY.START_DATE, event.startDate().asLocalDateTime())
          .execute();
      return record;
    });
  }

  @Override
  public Event<EventId> update(Event<EventId> event) throws DataConflictException {
    try {
      return context.transactionResult(tx -> {
        var record = tx.dsl()
            .update(EVENT)
            .set(EVENT.TITLE, event.title().unwrap())
            .set(EVENT.START_DATE, event.startDate().asLocalDateTime())
            .set(EVENT.VERSION, event.version().inc().unwrap())
            .where(
                EVENT.EVENT_ID.eq(event.id().unwrap())
                    .and(EVENT.DELETED.ne(true))
                    .and(EVENT.VERSION.eq(event.version().unwrap()))
            )
            .returningResult()
            .fetchSingle(EventRecordMapper::map);

        tx.dsl()
            .insertInto(EVENT_HISTORY)
            .set(EVENT_HISTORY.CHANGE_TYPE, "UPDATE")
            .set(EVENT_HISTORY.CHANGE_TIMESTAMP, LocalDateTime.now())
            .set(EVENT_HISTORY.USER_EMAIL, "foo@bar.com")
            .set(EVENT_HISTORY.EVENT_ID, event.id().unwrap())
            .set(EVENT_HISTORY.TITLE, event.title().unwrap())
            .set(EVENT_HISTORY.START_DATE, event.startDate().asLocalDateTime())
            .execute();

        return record;
      });
    } catch (NoDataFoundException e) {
      throw new DataConflictException(e);
    }
  }
}
