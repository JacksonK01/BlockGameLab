package edu.umn.cs.csci3081w.lab.pattern.observer.impl.wave;

import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Subject;

import java.util.ArrayList;
import java.util.List;

public class StartOfWaveSubject implements Subject<WaveEvent> {
    private final List<Observer<WaveEvent>> observers = new ArrayList<>();
    int wave = 0;

    @Override
    public void attach(Observer<WaveEvent> o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer<WaveEvent> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        WaveEvent waveEvent = new WaveEvent(wave);
        for(Observer<WaveEvent> o : observers) {
            o.update(waveEvent);
        }
    }

    public void setWave(int wave) {
        this.wave = wave;
    }
}
