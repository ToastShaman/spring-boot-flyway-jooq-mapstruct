package com.example.demo.scenarios.actors;

import com.example.demo.events.DefaultEventEngine;
import com.example.demo.events.EventEngine;
import com.example.demo.events.EventNotifier;
import com.example.demo.storage.EventsRepository;
import com.example.demo.scenarios.storage.InMemoryEventsRepository;
import java.util.List;

public class Environment {

  public final EventsRepository repository = new InMemoryEventsRepository();

  public final EventNotifier notifier = new EventNotifier(List.of());

  public final EventEngine eventEngine = new DefaultEventEngine(repository, notifier);

  public final Api api = new Api(eventEngine);

  public final Web web = new Web(api);

}
