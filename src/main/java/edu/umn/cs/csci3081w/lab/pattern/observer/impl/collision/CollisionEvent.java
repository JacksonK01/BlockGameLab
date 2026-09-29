package edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.intr.Detectable;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Event;

public class CollisionEvent extends Event {
    public final Detectable a;
    public final Detectable b;

    public CollisionEvent(Detectable a, Detectable b) {
        this.a = a;
        this.b = b;
    }
}
