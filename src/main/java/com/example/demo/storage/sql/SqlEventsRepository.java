package com.example.demo.storage.sql;

import static org.jooq.generated.Tables.EVENT;
import static org.jooq.generated.Tables.EVENT_HISTORY;

import com.example.demo.events.domain.Event;
import com.example.demo.events.domain.EventId;
import com.example.demo.storage.DataConflictException;
import com.example.demo.storage.EventsRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.exception.NoDataFoundException;
import org.jooq.generated.tables.records.EventRecord;
import org.springframework.stereotype.Service;

@Service
public class SqlEventsRepository implements EventsRepository {

  private final DSLContext context;
  private final Clock clock;

  public SqlEventsRepository(DSLContext context, Clock clock) {
    this.context = context;
    this.clock = clock;
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
      var now = LocalDateTime.now(clock);

      var record = tx.dsl()
          .insertInto(EVENT)
          .set(EVENT.VERSION, 0L)
          .set(EVENT.TITLE, event.title().unwrap())
          .set(EVENT.START_DATE, event.startDate().asLocalDateTime())
          .set(EVENT.DELETED, false)
          .set(EVENT.LAST_MODIFIED, now)
          .returningResult()
          .fetchSingleInto(EventRecord.class);

      insertAuditRecord(tx, record, now);

      return EventRecordMapper.map(record);
    });
  }

  @Override
  public Event<EventId> update(Event<EventId> event) throws DataConflictException {
    try {
      return context.transactionResult(tx -> {
        var now = LocalDateTime.now(clock);

        var record = tx.dsl()
            .update(EVENT)
            .set(EVENT.TITLE, event.title().unwrap())
            .set(EVENT.START_DATE, event.startDate().asLocalDateTime())
            .set(EVENT.VERSION, event.version().inc().unwrap())
            .set(EVENT.LAST_MODIFIED, now)
            .where(
                EVENT.EVENT_ID.eq(event.id().unwrap())
                    .and(EVENT.DELETED.ne(true))
                    .and(EVENT.VERSION.eq(event.version().unwrap()))
            )
            .returningResult()
            .fetchSingleInto(EventRecord.class);

        insertAuditRecord(tx, record, now);

        return EventRecordMapper.map(record);
      });
    } catch (NoDataFoundException e) {
      throw new DataConflictException(e);
    }
  }

  private void insertAuditRecord(
      Configuration tx,
      EventRecord record,
      LocalDateTime now
  ) {
    tx.dsl()
        .insertInto(EVENT_HISTORY)
        .set(EVENT_HISTORY.AUDIT_DATE, now)
        .set(EVENT_HISTORY.AUDIT_USER, "foo@bar.com")
        .set(EVENT_HISTORY.EVENT_ID, record.getEventId())
        .set(EVENT_HISTORY.VERSION, record.getVersion())
        .set(EVENT_HISTORY.TITLE, record.getTitle())
        .set(EVENT_HISTORY.START_DATE, record.getStartDate())
        .set(EVENT_HISTORY.DELETED, record.getDeleted())
        .set(EVENT_HISTORY.LAST_MODIFIED, record.getStartDate())
        .execute();
  }
}
