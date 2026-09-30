package edu.umn.cs.csci3081w.lab.pattern.observer.impl.search;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Event;

public class SearchEvent extends Event {
    public Entity found;
    public Entity searcher;

    public SearchEvent(Entity found, Entity searcher) {
        this.found = found;
        this.searcher = searcher;
    }
}
