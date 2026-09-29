package edu.umn.cs.csci3081w.lab.item;

import edu.umn.cs.csci3081w.lab.math.Vector2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Sword extends Item {
    private final BufferedImage sprite;
    private boolean toggleLeft = false;

    public Sword(double x, double y) {
        super(x, y, 64, 64, 1);
        try {
            sprite = ImageIO.read(Objects.requireNonNull(Item.class.getResourceAsStream("/textures/items/sword.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Sword() {
        this(0, 0);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(Color.RED);
        Rectangle rectangle = getBoundingBox();
        int width = sprite.getWidth();
        if(toggleLeft) {
            width = -width;
        }
        g2.drawImage(sprite, (int) x, (int) y, width, sprite.getHeight(), null);
        g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public void setPos(Vector2D pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        if(toggleLeft) {
            boundingBox.setLocation((int) x - (boundingBox.width), (int) y);
        } else {
            this.x = pos.getX() + boundingBox.width;
            boundingBox.setLocation((int) x, (int) y);
        }
    }

    @Override
    public void onInteract() {
        toggleLeft = !toggleLeft;
    }
}
