package edu.umn.cs.csci3081w.lab.entity;


import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.intr.Renderable;
import edu.umn.cs.csci3081w.lab.intr.Tickable;
import edu.umn.cs.csci3081w.lab.math.Vector2D;

import java.awt.*;

public abstract class Entity implements Tickable, Renderable {
    protected World world;
    protected double x = 0;
    protected double y = 0;
    private final Color color;
    private final Rectangle hitbox;

    public Entity(World world, Color color, double width, double height) {
        this.color = color;
        this.hitbox = new Rectangle((int) x, (int) y, (int) width, (int) height);
        this.world = world;
    }

    public void setPos(Vector2D pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    @Override
    public void tick(float dt) {
        hitbox.setLocation((int) this.x, (int) this.y);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(hitbox);
    }
}
