package edu.umn.cs.csci3081w.lab.entity;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.input.KeyHandler;
import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.item.Sword;
import edu.umn.cs.csci3081w.lab.math.Vector2D;

import java.awt.*;

public class PlayerEntity extends Entity {
    int speed = 200;
    //Current active item
    private Item hand;

    public PlayerEntity(World world) {
        super(world, Color.CYAN, World.TILE_SIZE, World.TILE_SIZE);
        hand = new Sword(0, 0, 1);
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

        if(hand == null) {
            return;
        }

        hand.setPos(getPos().add(new Vector2D(getHitbox().getWidth() - 5, 0)));
    }

    @Override
    public void render(Graphics2D g2) {
        super.render(g2);
        if(hand != null) {
            hand.render(g2);
        }
    }
}
