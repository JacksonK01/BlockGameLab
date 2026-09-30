package edu.umn.cs.csci3081w.lab.pattern.observer.impl.booster;

import edu.umn.cs.csci3081w.lab.pattern.observer.impl.wave.WaveEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Subject;

import java.util.ArrayList;
import java.util.List;

public class DamageBoosterCollectedSubject implements Subject<BoosterCollectedEvent> {
    private final List<Observer<BoosterCollectedEvent>> observers = new ArrayList<>();
    private int collected = 0;

    @Override
    public void attach(Observer<BoosterCollectedEvent> o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer<BoosterCollectedEvent> o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        BoosterCollectedEvent e = new BoosterCollectedEvent(collected);
        for(Observer<BoosterCollectedEvent> o : observers) {
            o.update(e);
        }
    }

    public void setCollected(int collected) {
        this.collected = collected;
    }
}
