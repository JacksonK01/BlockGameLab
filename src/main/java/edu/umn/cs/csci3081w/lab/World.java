package edu.umn.cs.csci3081w.lab;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.entity.PlayerEntity;
import edu.umn.cs.csci3081w.lab.entity.ZombieEntity;
import edu.umn.cs.csci3081w.lab.input.KeyHandler;
import edu.umn.cs.csci3081w.lab.intr.Renderable;
import edu.umn.cs.csci3081w.lab.intr.Tickable;
import edu.umn.cs.csci3081w.lab.math.Vector2D;
import edu.umn.cs.csci3081w.lab.pattern.factory.BasicEntityFactory;
import edu.umn.cs.csci3081w.lab.pattern.factory.EntityFactory;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision.EntityCollisionSubject;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.search.SearchEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.search.WorldSearchSubject;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;
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
    private final EntityCollisionSubject collisionSubject;
    private final WorldSearchSubject worldSearchSubject;

    public World(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.entityFactory = new BasicEntityFactory();
        this.collisionSubject = new EntityCollisionSubject();
        this.worldSearchSubject = new WorldSearchSubject();

        this.collisionSubject.attach((e) -> {
            Entity a = e.a;
            Entity b = e.b;

            PlayerEntity player = pickEntity(a, b, PlayerEntity.class);
            ZombieEntity zombie = pickEntity(a, b, ZombieEntity.class);

            if(player == null || zombie == null) {
                return;
            }

            player.damage(1);
        });

        this.collisionSubject.attach((e) -> {
            Entity a = e.a;
            Entity b = e.b;

            Vector2D distance = a.getPos().subtract(b.getPos());
            if(distance.getX() == 0 && distance.getY() == 0) {
                distance = new Vector2D(0.25, 0.25);
            }
            distance = distance.unitNormal2D().multiply(2.5);

            a.setPos(a.getPos().add(distance));
            b.setPos(b.getPos().add(distance.flip()));
        });
    }

    //Runs right before the game loop starts
    public void onRun() {
        gamePanel.addKeyListener(keyHandler);

        double pX = (double) (gamePanel.getWidth() / 2) - ((double) TILE_SIZE / 2);
        double pY = (double) (gamePanel.getHeight() / 2) - ((double) TILE_SIZE / 2);
        spawnEntity("player", new Vector2D(pX, pY));
        spawnEntity("zombie", new Vector2D(0, 0));
    }

    public void searchArea(Rectangle areaToSearch) {
        for (Entity e : entities) {
            if (e.getHitbox().intersects(areaToSearch)) {
                worldSearchSubject.setFound(e);
                worldSearchSubject.notifyObservers();
            }
        }
    }

    public Entity spawnEntity(String type, Vector2D pos) {
        Entity newEntity = entityFactory.create(type, this);
        newEntity.setPos(pos);
        entities.add(newEntity);
        return newEntity;
    }

    public KeyHandler getKeyHandler() {
        return this.keyHandler;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void attachSearchObserver(Observer<SearchEvent> o) {
        worldSearchSubject.attach(o);
    }

    @Override
    public void tick(float dt) {
        //Checks for any hitbox collision
        for(int i = 0; i < entities.size(); i++) {
            for(int j = i + 1; j < entities.size(); j++) {
                Entity a = entities.get(i);
                Entity b = entities.get(j);
                if(a.getHitbox().intersects(b.getHitbox())) {
                    collisionSubject.setEntities(a, b);
                    collisionSubject.notifyObservers();
                }
            }
        }

        for (Entity entity : entities) {
            entity.tick(dt);

            Vector2D pos = entity.getPos();
            Rectangle hitbox = entity.getHitbox();
            if(pos.getX() < 0) {
                entity.setPos(new Vector2D(0, pos.getY()));
            }
            double width = gamePanel.getWidth() - hitbox.getWidth();
            if(pos.getX() > width) {
                entity.setPos(new Vector2D(width, pos.getY()));
            }
            if(pos.getY() < 0) {
                entity.setPos(new Vector2D(pos.getX(), 0));
            }
            double height = gamePanel.getHeight() - hitbox.getHeight();
            if(pos.getY() > height) {
                entity.setPos(new Vector2D(pos.getX(), height));
            }
        }
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

        for (Entity entity : entities) {
            entity.render(g2);
        }
    }

    private <T extends Entity> T pickEntity(Entity a, Entity b, Class<T> type) {
        if(type.isInstance(a)) {
            return type.cast(a);
        } else if(type.isInstance(b)) {
            return type.cast(b);
        } else {
            return null;
        }
    }
}
