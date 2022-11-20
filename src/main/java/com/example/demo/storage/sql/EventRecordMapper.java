package com.example.demo.storage.sql;

import static java.time.ZoneOffset.UTC;
import static org.jooq.generated.Tables.EVENT;

import com.example.demo.events.domain.Event;
import com.example.demo.events.domain.EventBuilder;
import com.example.demo.events.domain.EventId;
import com.example.demo.events.domain.EventStartDate;
import com.example.demo.events.domain.EventTitle;
import com.example.demo.events.domain.EventVersion;
import org.jooq.Record;

public interface EventRecordMapper {

  static Event<EventId> map(Record record) {
    return EventBuilder.<EventId>builder()
        .id(new EventId(record.get(EVENT.EVENT_ID)))
        .version(new EventVersion(record.get(EVENT.VERSION)))
        .title(new EventTitle(record.get(EVENT.TITLE)))
        .startDate(new EventStartDate(record.get(EVENT.START_DATE).toInstant(UTC)))
        .build();
  }
}
