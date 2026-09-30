package edu.umn.cs.csci3081w.lab;

import edu.umn.cs.csci3081w.lab.collision.CollisionManager;
import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.entity.PlayerEntity;
import edu.umn.cs.csci3081w.lab.input.KeyHandler;
import edu.umn.cs.csci3081w.lab.intr.*;
import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.spawner.DamageBoosterSpawner;
import edu.umn.cs.csci3081w.lab.util.Vector2D;
import edu.umn.cs.csci3081w.lab.pattern.factory.entity.BasicEntityFactory;
import edu.umn.cs.csci3081w.lab.pattern.factory.entity.EntityFactory;
import edu.umn.cs.csci3081w.lab.pattern.factory.item.BasicItemFactory;
import edu.umn.cs.csci3081w.lab.pattern.factory.item.ItemFactory;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.search.SearchEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.search.WorldSearchSubject;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;
import edu.umn.cs.csci3081w.lab.ui.UIManager;
import edu.umn.cs.csci3081w.lab.wave.WaveManager;
import edu.umn.cs.csci3081w.setup.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World implements Tickable, Renderable, Spawner {
    public static int TILE_SIZE = 64;

    private static final Color PRIMARY_GREEN = new Color(79, 121, 66);
    private static final Color SECONDARY_GREEN = new Color(53, 94, 59);

    private final List<Entity> entities = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();
    private final GamePanel gamePanel;
    private final KeyHandler keyHandler = new KeyHandler();
    private final UIManager uiManager;
    private final CollisionManager collisionManager;
    private final WaveManager waveManager;
    private final DamageBoosterSpawner damageBoosterSpawner;
    private final EntityFactory entityFactory;
    private final ItemFactory itemFactory;
    private final WorldSearchSubject worldSearchSubject;

    public World(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.entityFactory = new BasicEntityFactory();
        this.itemFactory = new BasicItemFactory();
        this.worldSearchSubject = new WorldSearchSubject();
        this.collisionManager = new CollisionManager(this);
        this.waveManager = new WaveManager(this);
        this.damageBoosterSpawner = new DamageBoosterSpawner(this, waveManager, collisionManager);
        this.uiManager = new UIManager(this, waveManager, damageBoosterSpawner);
    }

    //Runs right before the game loop starts
    public void onRun() {
        gamePanel.addKeyListener(keyHandler);

        double pX = (double) (gamePanel.getWidth() / 2) - ((double) TILE_SIZE / 2);
        double pY = (double) (gamePanel.getHeight() / 2) - ((double) TILE_SIZE / 2);
        spawnEntity("player", new Vector2D(pX, pY));
        spawnItem("sword", new Vector2D(pX + 100, pY + 100));
    }

    public void searchAreaEntities(Rectangle areaToSearch, Entity searcher) {
        for (Entity e : entities) {
            if (e.getBoundingBox().intersects(areaToSearch)) {
                worldSearchSubject.setFoundAndSearcher(e, searcher);
                worldSearchSubject.notifyObservers();
            }
        }
    }

    @Override
    public Entity spawnEntity(String type, Vector2D pos) {
        Entity newEntity = entityFactory.create(type, this);
        newEntity.setPos(pos);
        entities.add(newEntity);
        return newEntity;
    }

    @Override
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

    public Rectangle getWorldSize() {
        return new Rectangle(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
    }

    public void attachSearchObserver(Observer<SearchEvent> o) {
        worldSearchSubject.attach(o);
    }

    public void detachSearchObserver(Observer<SearchEvent> o) {
        worldSearchSubject.detach(o);
    }

    public boolean isGameOver() {
        for(Entity e : entities) {
            if(e instanceof PlayerEntity) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void tick(float dt) {
        waveManager.tick(dt);
        collisionManager.tick(dt);
        //DO NOT REPLACE WITH ENHANCE FOR LOOP
        //When entities die, they immediately remove themselves from world
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.tick(dt);
            collisionManager.checkOutOfBounds(gamePanel, entity);
        }
    }

    @Override
    public void render(Graphics2D g2) {
        drawBackground(g2);

        for(Item item : items) {
            item.render(g2);
        }

        for (Entity entity : entities) {
            entity.render(g2);
        }

        uiManager.render(g2);
    }

    private void drawBackground(Graphics2D g2) {
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
    }
}
