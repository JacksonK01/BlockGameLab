package edu.umn.cs.csci3081w.lab.booster;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.entity.PlayerEntity;
import edu.umn.cs.csci3081w.lab.entity.ZombieEntity;
import edu.umn.cs.csci3081w.lab.item.DamageBoosterItem;
import edu.umn.cs.csci3081w.lab.item.Item;
import edu.umn.cs.csci3081w.lab.pattern.decorator.ConcreteDamageBoosterDecorator;
import edu.umn.cs.csci3081w.lab.pattern.decorator.ItemDecorator;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.booster.BoosterCollectedEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.booster.DamageBoosterCollectedSubject;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.collision.CollisionEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.impl.wave.WaveEvent;
import edu.umn.cs.csci3081w.lab.pattern.observer.intr.Subject;
import edu.umn.cs.csci3081w.lab.util.SourceFinder;
import edu.umn.cs.csci3081w.lab.util.Vector2D;

import java.awt.*;
import java.util.Random;

public class DamageBoosterManager {
    private final static int MAX_BOOSTER = 5;

    private final DamageBoosterCollectedSubject subject;
    private int boosterAmount;
    private int boostersCollected;

    public DamageBoosterManager(World world, Subject<WaveEvent> waveSubject, Subject<CollisionEvent> collisionSubject) {
        subject = new DamageBoosterCollectedSubject();
        boosterAmount = 0;
        boostersCollected = 0;

        waveSubject.attach((e) -> {
            Random random = new Random();
            Rectangle worldSize = world.getWorldSize();
            if(boostersCollected < MAX_BOOSTER && boosterAmount == boostersCollected) {
                int boostOffset = World.TILE_SIZE;
                int boostX = random.nextInt(boostOffset, (int) (worldSize.getWidth() - boostOffset));
                int boostY = random.nextInt(boostOffset, (int) (worldSize.getHeight() - boostOffset));
                world.spawnItem("damageBooster", new Vector2D(boostX, boostY));
                boosterAmount++;
            }
        });

        // On collide with damage booster item
        collisionSubject.attach((e) -> {
            PlayerEntity player = SourceFinder.findSource(e.a, e.b, PlayerEntity.class);
            DamageBoosterItem booster = SourceFinder.findSource(e.a, e.b, DamageBoosterItem.class);

            if(player == null || booster == null) {
                return;
            }

            Item hand = player.itemBeingHeld();
            if(hand == null) {
                return;
            }

            player.placeItemInHand(new ConcreteDamageBoosterDecorator(hand));
            world.getItems().remove(booster);
            boostersCollected++;
            subject.setCollected(boostersCollected);
            subject.notifyObservers();
        });

        // On hit by zombie
        collisionSubject.attach((e) -> {
            PlayerEntity player = SourceFinder.findSource(e.a, e.b, PlayerEntity.class);
            ZombieEntity zombie = SourceFinder.findSource(e.a, e.b, ZombieEntity.class);

            if(player == null || zombie == null) {
                return;
            }

            Item held = player.itemBeingHeld();
            if(held == null) {
                return;
            }

            int cooldown = player.getCooldown();
            if((cooldown == 0 || cooldown == Entity.STARTING_ENTITY_COOLDOWN)
                    && held instanceof ItemDecorator itemDecorator) {
                // Removes one booster level
                player.placeItemInHand(itemDecorator.getItem());
                // Implicitly collected will be > 0
                boostersCollected--;
                boosterAmount--;
                subject.setCollected(boostersCollected);
                subject.notifyObservers();
            }
        });
    }

    public Subject<BoosterCollectedEvent> getDamageBoosterCollectedSubject() {
        return this.subject;
    }
}
