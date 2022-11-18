package com.example.demo.storage.sql;

import static org.jooq.generated.Tables.EVENT;

import com.example.demo.storage.EventsRepository;
import com.example.demo.storage.domain.EventRecord;
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
  public Optional<EventRecord<Long>> findById(long id) {
    return context
        .select(EVENT.EVENT_ID, EVENT.TITLE, EVENT.START_DATE)
        .from(EVENT)
        .where(EVENT.EVENT_ID.eq(id))
        .fetchOptional(Records.mapping(EventRecord::new));
  }

  @Override
  public EventRecord<Long> insert(EventRecord<Void> event) {
    return context.insertInto(EVENT, EVENT.TITLE, EVENT.START_DATE)
        .values(event.title(), event.startDate())
        .returningResult(EVENT.EVENT_ID, EVENT.TITLE, EVENT.START_DATE)
        .fetchOne(Records.mapping(EventRecord::new));
  }
}
