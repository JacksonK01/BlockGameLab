package edu.umn.cs.csci3081w.lab.item;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.entity.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class DamageBoosterItem extends Item {
    private final BufferedImage sprite;

    public DamageBoosterItem() {
        super(0, 0, World.TILE_SIZE, World.TILE_SIZE, 0);
        try {
            sprite = ImageIO.read(Objects.requireNonNull(Item.class.getResourceAsStream("/textures/items/upgrade.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onInteract() {

    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(sprite, (int) x, (int) y, sprite.getWidth(), sprite.getHeight(), null);
    }
}
