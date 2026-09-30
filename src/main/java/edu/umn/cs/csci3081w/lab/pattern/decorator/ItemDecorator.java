package edu.umn.cs.csci3081w.lab.pattern.decorator;

import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.util.Vector2D;

import java.awt.*;

public abstract class ItemDecorator extends Item {
    protected final Item item;

    public ItemDecorator(Item item) {
        super(0, 0, 0, 0, 0);
        this.item = item;
    }

    @Override
    public int getDamage() {
        return item.getDamage();
    }

    @Override
    public Vector2D getPos() {
        return item.getPos();
    }

    @Override
    public Rectangle getBoundingBox() {
        return item.getBoundingBox();
    }

    @Override
    public void setPos(Vector2D pos) {
        item.setPos(pos);
    }

    @Override
    public void onInteract() {
        item.onInteract();
    }

    @Override
    public void render(Graphics2D g2) {
        item.render(g2);
    }

    public Item getItem() {
        return this.item;
    }
}
