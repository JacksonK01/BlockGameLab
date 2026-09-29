package edu.umn.cs.csci3081w.lab.item;

import edu.umn.cs.csci3081w.lab.intr.Detectable;
import edu.umn.cs.csci3081w.lab.intr.Renderable;
import edu.umn.cs.csci3081w.lab.math.Vector2D;

import java.awt.*;

public abstract class Item implements Renderable, Detectable {
    protected double x;
    protected double y;
    protected final int damage;
    protected Rectangle boundingBox;

    public Item(double x, double y, int width, int height, int damage) {
        this.x = x;
        this.y = y;
        this.boundingBox = new Rectangle((int) x, (int) y, width, height);
        this.damage = damage;
    }

    public int getDamage() {
        return this.damage;
    }

    @Override
    public Vector2D getPos() {
        return new Vector2D(this.x, this.y);
    }

    @Override
    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    public void setPos(Vector2D pos) {
        this.x = pos.getX();
        this.y = pos.getY();

        boundingBox.setLocation((int) x, (int) y);
    }

    //Mainly for items to hold
    public abstract void onInteract();
}
