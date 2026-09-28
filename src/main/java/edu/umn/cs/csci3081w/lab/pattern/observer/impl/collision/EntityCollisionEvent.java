package edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Event;

public class EntityCollisionEvent extends Event {
    public final Entity a;
    public final Entity b;

    public EntityCollisionEvent(Entity a, Entity b) {
        this.a = a;
        this.b = b;
    }
}
