package edu.umn.cs.csci3081w.lab.pattern.observer.intr;

public interface Observer<T extends Event> {
    void update(T event);
}
