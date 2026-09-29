package edu.umn.cs.csci3081w.lab.entity;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.math.Vector2D;
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
        super(world, new Color(15, 255, 80), World.TILE_SIZE, World.TILE_SIZE);
        random = new Random();

        observer = (e) -> {
            Entity found = e.found;
            if(found instanceof PlayerEntity) {
                toChase = found;
                attention = 100;
            }
        };

        world.attachSearchObserver(observer);

        directionX = random.nextInt(-1, 2);
        directionY = random.nextInt(-1, 2);
    }

    @Override
    public void tick(float delta) {
        super.tick(delta);

        if(toChase == null) {
            if(attention <= 0) {
                attention = 50;
                directionX = random.nextInt(-1, 2);
                directionY = random.nextInt(-1, 2);
            }
            int offset = 300;
            int size = offset * 2;
            world.searchAreaEntities(new Rectangle((int) this.x - offset, (int) this.y - offset, size, size));
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
        }

        this.x += speed * delta * directionX;
        this.y += speed * delta * directionY;
    }

    @Override
    public void onDeath() {
        super.onDeath();
        world.detachSearchObserver(observer);
    }
}
