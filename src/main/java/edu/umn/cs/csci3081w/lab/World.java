package edu.umn.cs.csci3081w.lab;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.input.KeyHandler;
import edu.umn.cs.csci3081w.lab.intr.Renderable;
import edu.umn.cs.csci3081w.lab.intr.Tickable;
import edu.umn.cs.csci3081w.lab.math.Vector2D;
import edu.umn.cs.csci3081w.lab.pattern.factory.BasicEntityFactory;
import edu.umn.cs.csci3081w.lab.pattern.factory.EntityFactory;
import edu.umn.cs.csci3081w.setup.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World implements Tickable, Renderable {
    public static int TILE_SIZE = 64;
    private static final Color PRIMARY_GREEN = new Color(79, 121, 66);
    private static final Color SECONDARY_GREEN = new Color(53, 94, 59);

    private final List<Entity> entities = new ArrayList<>();
    private final GamePanel gamePanel;
    private final KeyHandler keyHandler = new KeyHandler();
    private final EntityFactory entityFactory;

    public World(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.entityFactory = new BasicEntityFactory();
    }

    //Runs right before the game loop starts
    public void onRun() {
        gamePanel.addKeyListener(keyHandler);

        double x = (double) (gamePanel.getWidth() / 2) - ((double) TILE_SIZE / 2);
        double y = (double) (gamePanel.getHeight() / 2) - ((double) TILE_SIZE / 2);
        spawnEntity("player", new Vector2D(x, y));


    }

    public void spawnEntity(String type, Vector2D pos) {
        Entity newEntity = entityFactory.create(type, this);
        newEntity.setPos(pos);
        entities.add(newEntity);
    }

    public KeyHandler getKeyHandler() {
        return this.keyHandler;
    }

    @Override
    public void tick(float dt) {
        entities.forEach((entity -> {entity.tick(dt);}));
    }

    @Override
    public void render(Graphics2D g2) {
        int rows = (gamePanel.getWidth() / TILE_SIZE) + 1;
        int cols = (gamePanel.getHeight() / TILE_SIZE) + 1;
        for(int i = 0; i < rows; i++) {
            int x = i * TILE_SIZE;
            for(int j = 0; j < cols; j++) {
                Color toUse;
                if(i % 2 == 0) {
                    toUse = PRIMARY_GREEN;
                } else {
                    toUse = SECONDARY_GREEN;
                }
                int y = j * TILE_SIZE;
                g2.setColor(toUse);
                g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }

        entities.forEach((entity -> {entity.render(g2);}));
    }

}
