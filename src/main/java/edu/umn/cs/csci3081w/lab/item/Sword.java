package edu.umn.cs.csci3081w.lab.item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Sword extends Item {
    BufferedImage sprite;

    public Sword(double x, double y, int damage) {
        super(x, y, damage);
        try {
            sprite = ImageIO.read(Objects.requireNonNull(Item.class.getResourceAsStream("/textures/items/sword.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(sprite, (int) x, (int) y, null);
    }
}
