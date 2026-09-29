package edu.umn.cs.csci3081w.lab.pattern.factory.entity;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.entity.PlayerEntity;
import edu.umn.cs.csci3081w.lab.entity.ZombieEntity;

import java.util.Random;

public class RandomEntityFactory implements EntityFactory {

    @Override
    public Entity create(String type, World world) {
        Random random = new Random();
        int randInt = random.nextInt(0, 2);
        return switch (randInt) {
            case 0 -> new PlayerEntity(world);
            case 1 -> new ZombieEntity(world);
            default -> throw new IllegalArgumentException("Random Factory unable to output entity");
        };
    }
}
