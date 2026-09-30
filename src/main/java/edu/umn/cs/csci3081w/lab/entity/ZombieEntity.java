package edu.umn.cs.csci3081w.lab.entity;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.util.Vector2D;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.search.SearchEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;

import java.awt.*;
import java.util.Random;

public class ZombieEntity extends Entity {
    int speed = 100;
    private Entity toChase;
    private final Random random;
    private int attention;
    double directionX;
    double directionY;
    private final Observer<SearchEvent> observer;

    public ZombieEntity(World world) {
        super(world, new Color(150, 255, 150), World.TILE_SIZE, World.TILE_SIZE);
        random = new Random();

        observer = (e) -> {
            if(e.searcher != this) {
                return;
            }
            Entity found = e.found;
            if(found instanceof PlayerEntity) {
                toChase = found;
                attention = 100;
            }
        };

        world.attachSearchObserver(observer);

        generateRandomDirection();
        attention = random.nextInt(1, 26);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);

        if(toChase == null) {
            if(attention <= 0) {
                attention = 50 + random.nextInt(0, 10);
                generateRandomDirection();
                if(random.nextBoolean()) {
                    int offset = 300;
                    int size = offset * 2;
                    Rectangle area = new Rectangle((int) this.x - offset, (int) this.y - offset, size, size);
                    world.searchAreaEntities(area, this);
                }
            }
        } else {
            Vector2D pos = new Vector2D(x, y);
            Vector2D chasePos = toChase.getPos();
            Vector2D distance = chasePos.subtract(pos).unitNormal2D();
            directionX = distance.getX();
            directionY = distance.getY();
        }

        //Allows zombie to forget target
        if(attention > 0) {
            attention--;
        } else {
            toChase = null;
            generateRandomDirection();
        }

        this.x += speed * delta * directionX;
        this.y += speed * delta * directionY;
    }

    @Override
    public void damage(int amount, Entity source) {
        super.damage(amount, source);
        attention = 100;
        this.toChase = source;
    }

    @Override
    public void onDeath() {
        super.onDeath();
        world.detachSearchObserver(observer);
    }

    private void generateRandomDirection() {
        directionX = random.nextInt(-1, 2);
        directionY = random.nextInt(-1, 2);
    }
}
