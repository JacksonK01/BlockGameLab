package edu.umn.cs.csci3081w.lab.pattern.observer.impl.booster;

import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Event;

public class BoosterCollectedEvent extends Event {
    public final int collected;
    public BoosterCollectedEvent(int collected) {
        this.collected = collected;
    }
}
