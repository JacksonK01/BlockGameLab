package edu.umn.cs.csci3081w.lab.wave;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.intr.WaveSpawnerStrategy;
import edu.umn.cs.csci3081w.lab.util.Vector2D;

import java.awt.*;

public class ZombieWaveStrategy implements WaveSpawnerStrategy {

    @Override
    public void spawnWave(World world, int wave) {
        int offset = 20;
        Rectangle worldSize = world.getWorldSize();

        world.spawnEntity("zombie", new Vector2D(offset, offset));
        world.spawnEntity("zombie", new Vector2D(worldSize.getWidth() / 2, offset));
        world.spawnEntity("zombie", new Vector2D(worldSize.getWidth() - offset, offset));

        int zY = (int) (worldSize.getHeight() - offset);
        world.spawnEntity("zombie", new Vector2D(offset, zY));
        world.spawnEntity("zombie", new Vector2D(worldSize.getWidth() / 2, zY));
        world.spawnEntity("zombie", new Vector2D(worldSize.getWidth() - offset, zY));
    }
}
