package com.example.demo.scenarios.actors;

import com.example.demo.storage.EventsRepository;
import com.example.demo.scenarios.storage.InMemoryEventsRepository;

public class Environment {

  public final EventsRepository repository = new InMemoryEventsRepository();

  public final Api api = new Api(repository);

  public final Web web = new Web(api);

}
