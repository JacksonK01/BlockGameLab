package edu.umn.cs.csci3081w.lab.entity;


import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.intr.Renderable;
import edu.umn.cs.csci3081w.lab.intr.Tickable;
import edu.umn.cs.csci3081w.lab.math.Vector2D;

import java.awt.*;

public abstract class Entity implements Tickable, Renderable {
    private static final int MAX_HEALTH = 20;

    protected World world;
    protected double x;
    protected double y;
    private final Color color;
    private final Rectangle hitbox;
    private int health;
    private int cooldown;

    public Entity(World world, Color color, double width, double height) {
        this.color = color;
        this.x = 0;
        this.y = 0;
        this.hitbox = new Rectangle((int) x, (int) y, (int) width, (int) height);
        this.world = world;
        this.health = MAX_HEALTH;
        this.cooldown = 0;
    }

    public void setPos(Vector2D pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.hitbox.setLocation((int) this.x, (int) this.y);
    }

    public Vector2D getPos() {
        return new Vector2D(x, y);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void damage(int damage) {
        if(cooldown <= 0) {
            this.cooldown = 10;
            this.health -= damage;
        }
    }

    @Override
    public void tick(float dt) {
        hitbox.setLocation((int) this.x, (int) this.y);

        if(cooldown > 0) {
            cooldown--;
        }

        if(this.health <= 0) {
            onDeath();
        }
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(hitbox);

        g2.setColor(Color.GRAY);
        int height = 20;
        int width = (int) hitbox.getWidth();
        int y = (int) this.y + 70;
        g2.fillRect((int) this.x, y, width, height);

        g2.setColor(Color.GREEN);
        int barWidth = width * health / MAX_HEALTH;
        g2.fillRect((int) this.x, y, barWidth, height);
    }

    private void onDeath() {
        world.getEntities().remove(this);
    }
}
