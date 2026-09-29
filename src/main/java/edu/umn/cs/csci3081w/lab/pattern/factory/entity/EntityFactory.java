package edu.umn.cs.csci3081w.lab.pattern.factory.entity;

import edu.umn.cs.csci3081w.lab.World;
import edu.umn.cs.csci3081w.lab.entity.Entity;

public interface EntityFactory {
    Entity create(String type, World world);
}
