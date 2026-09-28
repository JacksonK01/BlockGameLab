package edu.umn.cs.csci3081w.lab.pattern.observer.impl.search;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision.EntityCollisionEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Subject;

import java.util.ArrayList;
import java.util.List;

public class WorldSearchSubject implements Subject<SearchEvent> {
    private final List<Observer<SearchEvent>> observers = new ArrayList<>();
    private Entity found;

    public void setFound(Entity found) {
        this.found = found;
    }

    @Override
    public void attach(Observer<SearchEvent> o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer<SearchEvent> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        if(found == null) {
            throw new IllegalArgumentException("Missing found entity");
        }

        SearchEvent e = new SearchEvent(found);
        for(Observer<SearchEvent> o : observers) {
            o.update(e);
        }
    }
}
