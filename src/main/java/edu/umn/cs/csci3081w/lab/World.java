package edu.umn.cs.csci3081w.lab;

import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.entity.PlayerEntity;
import edu.umn.cs.csci3081w.lab.entity.ZombieEntity;
import edu.umn.cs.csci3081w.lab.input.KeyHandler;
import edu.umn.cs.csci3081w.lab.intr.Detectable;
import edu.umn.cs.csci3081w.lab.intr.ItemHolder;
import edu.umn.cs.csci3081w.lab.intr.Renderable;
import edu.umn.cs.csci3081w.lab.intr.Tickable;
import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.item.Sword;
import edu.umn.cs.csci3081w.lab.math.Vector2D;
import edu.umn.cs.csci3081w.lab.pattern.factory.entity.BasicEntityFactory;
import edu.umn.cs.csci3081w.lab.pattern.factory.entity.EntityFactory;
import edu.umn.cs.csci3081w.lab.pattern.factory.item.BasicItemFactory;
import edu.umn.cs.csci3081w.lab.pattern.factory.item.ItemFactory;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision.CollisionSubject;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.search.SearchEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.search.WorldSearchSubject;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;
import edu.umn.cs.csci3081w.setup.GamePanel;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World implements Tickable, Renderable {
    public static int TILE_SIZE = 64;
    private static final Color PRIMARY_GREEN = new Color(79, 121, 66);
    private static final Color SECONDARY_GREEN = new Color(53, 94, 59);

    private final List<Entity> entities = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    private final GamePanel gamePanel;
    private final KeyHandler keyHandler = new KeyHandler();
    private final EntityFactory entityFactory;
    private final ItemFactory itemFactory;
    private final CollisionSubject collisionSubject;
    private final WorldSearchSubject worldSearchSubject;

    public World(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.entityFactory = new BasicEntityFactory();
        this.itemFactory = new BasicItemFactory();
        this.collisionSubject = new CollisionSubject();
        this.worldSearchSubject = new WorldSearchSubject();
    }

    //Runs right before the game loop starts
    public void onRun() {
        gamePanel.addKeyListener(keyHandler);

        double pX = (double) (gamePanel.getWidth() / 2) - ((double) TILE_SIZE / 2);
        double pY = (double) (gamePanel.getHeight() / 2) - ((double) TILE_SIZE / 2);
        spawnEntity("player", new Vector2D(pX, pY));
        spawnItem("sword", new Vector2D(pX + 100, pY + 100));
        spawnZombieWave();

        this.collisionSubject.attach((e) -> {
            if(!(e.a instanceof Entity e1) || !(e.b instanceof Entity e2)) {
                return;
            }

            Vector2D distance = e1.getPos().subtract(e2.getPos());
            if(distance.getX() == 0 && distance.getY() == 0) {
                distance = new Vector2D(0.25, 0.25);
            }
            distance = distance.unitNormal2D().multiply(2.5);

            e1.setPos(e1.getPos().add(distance));
            e2.setPos(e2.getPos().add(distance.flip()));
        });

        this.collisionSubject.attach((e) -> {
            PlayerEntity player = findSource(e.a, e.b, PlayerEntity.class);
            ZombieEntity zombie = findSource(e.a, e.b, ZombieEntity.class);

            if(player == null || zombie == null) {
                return;
            }

            player.damage(1, zombie);
        });

        this.collisionSubject.attach((e) -> {
            ItemHolder holder = findSource(e.a, e.b, ItemHolder.class);
            Sword sword = findSource(e.a, e.b, Sword.class);

            if(holder == null || sword == null) {
                return;
            }

            holder.placeItemInHand(sword);
            items.remove(sword);
        });
    }

    public void searchAreaEntities(Rectangle areaToSearch) {
        for (Entity e : entities) {
            if (e.getBoundingBox().intersects(areaToSearch)) {
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

    public Item spawnItem(String item, Vector2D pos) {
        Item newItem = itemFactory.create(item);
        newItem.setPos(pos);
        items.add(newItem);
        return newItem;
    }

    public void setItem(Item item, Vector2D pos) {
        item.setPos(pos);
        if(!items.contains(item))
            items.add(item);
    }

    public KeyHandler getKeyHandler() {
        return this.keyHandler;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void attachSearchObserver(Observer<SearchEvent> o) {
        worldSearchSubject.attach(o);
    }

    public void detachSearchObserver(Observer<SearchEvent> o) {
        worldSearchSubject.detach(o);
    }

    @Override
    public void tick(float dt) {
        //Checks for any hitbox collision
        List<Detectable> detectables = new ArrayList<>();
        detectables.addAll(entities);
        detectables.addAll(items);
        for(int i = 0; i < detectables.size(); i++) {
            for(int j = i + 1; j < detectables.size(); j++) {
                Detectable a = detectables.get(i);
                Detectable b = detectables.get(j);
                if(a.getBoundingBox().intersects(b.getBoundingBox())) {
                    collisionSubject.setEntities(a, b);
                    collisionSubject.notifyObservers();
                }
            }
        }

        //DO NOT REPLACE WITH ENHANCE FOR LOOP
        //When entities die, they immediately remove themselves from world
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.tick(dt);

            Vector2D pos = entity.getPos();
            Rectangle hitbox = entity.getBoundingBox();
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

        for(Item item : items) {
            item.render(g2);
        }
    }

    //Source: https://stackoverflow.com/questions/36585185/instance-of-t-generic-type-in-java
    @Nullable
    private <T> T findSource(Detectable a, Detectable b, Class<T> type) {
        if(type.isInstance(a)) {
            return type.cast(a);
        } else if(type.isInstance(b)) {
            return type.cast(b);
        } else {
            return null;
        }
    }

    private void spawnZombieWave() {
        int offset = 20;
        spawnEntity("zombie", new Vector2D(offset, offset));
        spawnEntity("zombie", new Vector2D((double) gamePanel.getWidth() / 2, offset));
        spawnEntity("zombie", new Vector2D((double) gamePanel.getWidth() - offset, offset));

        int y = gamePanel.getHeight() - offset;
        spawnEntity("zombie", new Vector2D(offset, y));
        spawnEntity("zombie", new Vector2D((double) gamePanel.getWidth() / 2, y));
        spawnEntity("zombie", new Vector2D((double) gamePanel.getWidth() - offset, y));
    }
}
