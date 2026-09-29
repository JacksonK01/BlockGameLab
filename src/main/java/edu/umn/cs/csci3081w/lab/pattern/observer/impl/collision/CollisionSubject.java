package edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.intr.Detectable;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Subject;

import java.util.ArrayList;
import java.util.List;

public class CollisionSubject implements Subject<CollisionEvent> {
    List<Observer<CollisionEvent>> observers = new ArrayList<>();
    Detectable a;
    Detectable b;

    public void setEntities(Detectable a, Detectable b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void attach(Observer<CollisionEvent> o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer<CollisionEvent> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        if(a == null || b == null) {
            throw new IllegalArgumentException("Missing entities");
        }

        CollisionEvent e = new CollisionEvent(a, b);
        for(Observer<CollisionEvent> o : observers) {
            o.update(e);
        }
    }
}
