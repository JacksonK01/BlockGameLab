package edu.umn.cs.csci3081w.lab.pattern.observer.intr;

public interface Subject<T extends Event> {
    void attach(Observer<T> o);
    void detach(Observer<T> o);
    void notifyObservers();
}
