package edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Subject;

import java.util.ArrayList;
import java.util.List;

public class EntityCollisionSubject implements Subject<EntityCollisionEvent> {
    List<Observer<EntityCollisionEvent>> observers = new ArrayList<>();
    Entity a;
    Entity b;

    public void setEntities(Entity a, Entity b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void attach(Observer<EntityCollisionEvent> o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer<EntityCollisionEvent> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        if(a == null || b == null) {
            throw new IllegalArgumentException("Missing entities");
        }

        EntityCollisionEvent e = new EntityCollisionEvent(a, b);
        for(Observer<EntityCollisionEvent> o : observers) {
            o.update(e);
        }
    }
}
