package edu.umn.cs.csci3081w.lab.pattern.strategy;

import edu.umn.cs.csci3081w.lab.intr.Spawner;

import java.awt.*;

public interface WaveSpawnerStrategy {
    void spawnWave(Spawner spawner, Rectangle boundaries, int wave);
}
