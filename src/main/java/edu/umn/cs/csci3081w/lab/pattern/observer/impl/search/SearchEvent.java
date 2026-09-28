package edu.umn.cs.csci3081w.lab.pattern.observer.impl.search;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Event;

public class SearchEvent extends Event {
    public Entity found;

    public SearchEvent(Entity entity) {
        this.found = entity;
    }
}
