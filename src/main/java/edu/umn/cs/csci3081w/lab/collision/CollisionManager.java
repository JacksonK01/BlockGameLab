package edu.umn.cs.csci3081w.lab.collision;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.entity.PlayerEntity;
import edu.umn.cs.csci3081w.lab.entity.ZombieEntity;
import edu.umn.cs.csci3081w.lab.intr.Detectable;
import edu.umn.cs.csci3081w.lab.intr.ItemHolder;
import edu.umn.cs.csci3081w.lab.intr.Tickable;
import edu.umn.cs.csci3081w.lab.item.DamageBoosterItem;
import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.item.SwordItem;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Subject;
import edu.umn.cs.csci3081w.lab.util.SourceFinder;
import edu.umn.cs.csci3081w.lab.util.Vector2D;
import edu.umn.cs.csci3081w.lab.pattern.decorator.ConcreteDamageBoosterDecorator;
import edu.umn.cs.csci3081w.lab.pattern.decorator.ItemDecorator;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision.CollisionEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision.CollisionSubject;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Observer;
import edu.umn.cs.csci3081w.setup.GamePanel;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionManager implements Tickable {
    private final World world;
    private final CollisionSubject collisionSubject;

    public CollisionManager(World world) {
        this.world = world;
        this.collisionSubject = new CollisionSubject();

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
            PlayerEntity player = SourceFinder.findSource(e.a, e.b, PlayerEntity.class);
            ZombieEntity zombie = SourceFinder.findSource(e.a, e.b, ZombieEntity.class);

            if(player == null || zombie == null) {
                return;
            }

            player.damage(1, zombie);
        });

        this.collisionSubject.attach((e) -> {
            ItemHolder holder = SourceFinder.findSource(e.a, e.b, ItemHolder.class);
            SwordItem sword = SourceFinder.findSource(e.a, e.b, SwordItem.class);

            if(holder == null || sword == null) {
                return;
            }

            holder.placeItemInHand(sword);
            world.getItems().remove(sword);
        });
    }

    @Override
    public void tick(float dt) {
        List<Detectable> detectables = new ArrayList<>();
        detectables.addAll(world.getEntities());
        detectables.addAll(world.getItems());
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
    }

    public void checkOutOfBounds(GamePanel gamePanel, Entity entity) {
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

    public Subject<CollisionEvent> getCollisionSubject() {
        return this.collisionSubject;
    }
}
