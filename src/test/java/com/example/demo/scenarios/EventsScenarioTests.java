package com.example.demo.scenarios;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.api.domain.EventId;
import com.example.demo.scenarios.actors.Environment;
import com.example.demo.scenarios.actors.Web;
import com.example.demo.scenarios.domain.CreateEventRequests;
import org.junit.jupiter.api.Test;

public class EventsScenarioTests {

  private final Environment env = new Environment();

  private final Web web = env.web;

  @Test
  void creates_events() {
    var response = web.createEvent(CreateEventRequests.random());
    assertThat(env.repository.findById(EventId.of(response.id()))).isPresent();
  }
}
