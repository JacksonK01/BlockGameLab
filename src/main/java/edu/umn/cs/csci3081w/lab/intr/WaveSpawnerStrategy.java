package edu.umn.cs.csci3081w.lab.intr;

import edu.umn.cs.csci3081w.lab.World;

public interface WaveSpawnerStrategy {
    void spawnWave(World world, int wave);
}
