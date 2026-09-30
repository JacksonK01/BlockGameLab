package edu.umn.cs.csci3081w.lab.intr;

import edu.umn.cs.csci3081w.lab.entity.Entity;

public interface Damageable {
    void damage(int amount, Entity source);
    int getHealth();
    int getCooldown();
    void onDeath();
}
