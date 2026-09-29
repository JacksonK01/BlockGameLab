package edu.umn.cs.csci3081w.lab.pattern.factory.entity;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.entity.PlayerEntity;
import edu.umn.cs.csci3081w.lab.entity.ZombieEntity;

public class BasicEntityFactory implements EntityFactory {
    @Override
    public Entity create(String type, World world) {
        return switch (type) {
            case "player" -> new PlayerEntity(world);
            case "zombie" -> new ZombieEntity(world);
            default -> throw new IllegalArgumentException("Invalid Entity Type: " + type);
        };

    }
}
