package edu.umn.cs.csci3081w.lab.pattern.observer.impl.wave;

import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Event;

public class WaveEvent extends Event {
    public final int wave;
    public WaveEvent(int wave) {
        this.wave = wave;
    }
}
