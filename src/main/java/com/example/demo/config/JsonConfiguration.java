package com.example.demo.config;

import com.example.demo.events.domain.EventId;
import com.example.demo.events.domain.EventStartDate;
import com.example.demo.events.domain.EventTitle;
import com.example.demo.events.domain.EventVersion;
import com.example.demo.tinytypes.ValueTypeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfiguration {

  @Bean
  public ValueTypeModule valueTypeModule() {
    return new ValueTypeModule()
        .text(EventTitle.class, EventTitle::new)
        .number(EventId.class, EventId::new)
        .instant(EventStartDate.class, EventStartDate::new)
        .number(EventVersion.class, EventVersion::new);
  }
}
