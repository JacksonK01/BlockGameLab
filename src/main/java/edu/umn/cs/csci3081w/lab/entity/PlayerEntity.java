package edu.umn.cs.csci3081w.lab.entity;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.input.KeyHandler;

import java.awt.*;

public class PlayerEntity extends Entity {
    int speed = 200;

    public PlayerEntity(World world) {
        super(world, Color.CYAN, World.TILE_SIZE, World.TILE_SIZE);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);

        KeyHandler keyHandler = world.getKeyHandler();

        if (keyHandler.isUpPressed()) {
            this.y -= speed * delta;
        }
        else if (keyHandler.isDownPressed()) {
            this.y += speed * delta;
        }
        else if (keyHandler.isRightPressed()) {
            this.x += speed * delta;
        }
        else if (keyHandler.isLeftPressed()) {
            this.x -= speed * delta;
        }

    }
}
