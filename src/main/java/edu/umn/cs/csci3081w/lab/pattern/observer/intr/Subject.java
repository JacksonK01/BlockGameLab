package edu.umn.cs.csci3081w.lab.pattern.observer.intr;

public interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers();
}
