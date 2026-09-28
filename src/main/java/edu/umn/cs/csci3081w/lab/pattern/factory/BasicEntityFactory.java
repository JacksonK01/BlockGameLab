package edu.umn.cs.csci3081w.lab.pattern.factory;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.entity.Entity;
import edu.umn.cs.csci3081w.lab.entity.PlayerEntity;
import edu.umn.cs.csci3081w.lab.entity.ZombieEntity;

public class BasicEntityFactory implements EntityFactory {
    @Override
    public Entity create(String type, World world) {
        switch (type) {
            case "player": return new PlayerEntity(world);
            case "zombie": return new ZombieEntity(world);
        }

        throw new IllegalArgumentException("Invalid Entity Type");
    }
}
