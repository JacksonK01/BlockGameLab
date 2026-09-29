package edu.umn.cs.csci3081w.lab.entity;

import edu.umn.cs.csci3081w.lab.World;

import java.awt.*;

public class SpawnLocationEntity extends Entity {
    private int colorTimer;
    private final Color secondary = new Color(100, 5, 5);

    public SpawnLocationEntity(World world) {
        super(world, Color.red, World.TILE_SIZE, World.TILE_SIZE);
        colorTimer = 50;
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);

        if(colorTimer > 0) {
            colorTimer--;
        } else {
            colorTimer = 50;
            if(color.equals(secondary)) {
                this.color = Color.red;
            } else {
                this.color = secondary;
            }
        }
    }
}
