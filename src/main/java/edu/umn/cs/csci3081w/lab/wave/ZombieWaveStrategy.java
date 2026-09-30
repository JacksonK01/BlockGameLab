package edu.umn.cs.csci3081w.lab.wave;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.intr.Spawner;
import edu.umn.cs.csci3081w.lab.pattern.strategy.WaveSpawnerStrategy;
import edu.umn.cs.csci3081w.lab.util.Vector2D;

import java.awt.*;

public class ZombieWaveStrategy implements WaveSpawnerStrategy {

    @Override
    public void spawnWave(Spawner spawner, Rectangle boundaries, int wave) {
        int offset = 20;

        spawner.spawnEntity("zombie", new Vector2D(offset, offset));
        spawner.spawnEntity("zombie", new Vector2D(boundaries.getWidth() / 2, offset));
        spawner.spawnEntity("zombie", new Vector2D(boundaries.getWidth() - offset, offset));

        int zY = (int) (boundaries.getHeight() - offset);
        spawner.spawnEntity("zombie", new Vector2D(offset, zY));
        spawner.spawnEntity("zombie", new Vector2D(boundaries.getWidth() / 2, zY));
        spawner.spawnEntity("zombie", new Vector2D(boundaries.getWidth() - offset, zY));
    }
}
