package edu.umn.cs.csci3081w.lab.entity;

import edu.umn.cs.csci3081w.lab.World;

import java.awt.*;

public class ZombieEntity extends Entity {
    int speed = 100;

    public ZombieEntity(World world) {
        super(world, new Color(15, 255, 80), World.TILE_SIZE, World.TILE_SIZE);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);


    }
}
