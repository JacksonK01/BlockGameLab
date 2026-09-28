package edu.umn.cs.csci3081w.lab.item;

import edu.umn.cs.csci3081w.lab.intr.Renderable;
import edu.umn.cs.csci3081w.lab.math.Vector2D;

import java.awt.*;

public abstract class Item implements Renderable {
    protected double x;
    protected double y;
    protected final int damage;

    public Item(double x, double y, int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return this.damage;
    }

    public Vector2D getPos() {
        return new Vector2D(this.x, this.y);
    }

    public void setPos(Vector2D pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }
}
